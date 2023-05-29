package com.jeonghi.bank.application.service.bank

import com.jeonghi.bank.application.exception.NotFoundException
import com.jeonghi.bank.domain.model.BankAccountTransaction
import com.jeonghi.bank.domain.model.common.TransactionCode
import com.jeonghi.bank.domain.model.common.TransactionStatus
import com.jeonghi.bank.domain.repository.BankAccountRepository
import com.jeonghi.bank.domain.repository.BankAccountTransactionRepository
import com.jeonghi.bank.presentation.api.v1.model.transfer.TransferRequest
import com.jeonghi.bank.presentation.api.v1.model.transfer.TransferResponse
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Propagation
import org.springframework.transaction.annotation.Transactional
import java.util.*

@Service
class TransferService(
    private val bankAccountRepository: BankAccountRepository,
    private val bankAccountTransactionRepository: BankAccountTransactionRepository,
    private val encoder: PasswordEncoder, // 추가
) {

    @Transactional
    fun createTransaction(
        userId: UUID,
        request: TransferRequest,
    ): TransferResponse {
        // 계좌번호로 계좌 찾기
        val bankAccount = bankAccountRepository.findByAccountNumber(request.accountNumber) ?: throw NotFoundException(message = "보내는이 계좌번호가 존재하지 않습니다.")
        val relateBankAccount = bankAccountRepository.findByAccountNumber(request.relateAccountNumber) ?: throw NotFoundException(message = "받는이 계좌번호가 존재하지 않습니다.")
        
        // 계좌 소유자 검증
        bankAccount.validateOwner(userId)
        
        // 계좌 비밀번호 검증
        bankAccount.validatePassword(request.password, encoder)
        
        var transaction = BankAccountTransaction(
            id = UUID.randomUUID(),
            account = bankAccount,
            amount = request.amount,
            relateAccount = relateBankAccount,
            transactionStatus = TransactionStatus.REQUESTED,
            transactionCode = TransactionCode.WITHDRAW, // 출금
        )
        
        var relateTransaction = BankAccountTransaction(
            id = UUID.randomUUID(),
            account = relateBankAccount,
            amount = request.amount,
            relateAccount = bankAccount,
            transactionStatus = TransactionStatus.REQUESTED,
            transactionCode = TransactionCode.DEPOSIT, // 입금
        )
        
        bankAccountTransactionRepository.saveAll(listOf(transaction, relateTransaction))
        
        try {
            executeTransaction(
                transaction,
                relateTransaction,
            )
        } catch (e: Exception) {
            transaction.transactionStatus = TransactionStatus.FAILURE
            relateTransaction.transactionStatus = TransactionStatus.FAILURE
            bankAccountTransactionRepository.saveAll(listOf(transaction, relateTransaction))
        }
        
        return TransferResponse.from(transaction)
    }
    
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    fun executeTransaction(
        transaction: BankAccountTransaction,
        relateTransaction: BankAccountTransaction,
    ) {
        val account = transaction.account
        val relateAccount = relateTransaction.account

        transaction.balance = account.balance
        
        // 출금
        account.withdraw(
            transaction.amount,
        )
        
        transaction.afterBalance = account.balance
        
        relateTransaction.balance = relateAccount.balance
        
        // 입금
        relateAccount.deposit(
            transaction.amount,
        )
        
        relateTransaction.afterBalance = relateAccount.balance
        
        bankAccountRepository.saveAll(listOf(account, relateAccount))

        transaction.transactionStatus = TransactionStatus.SUCCESS
        relateTransaction.transactionStatus = TransactionStatus.SUCCESS
        bankAccountTransactionRepository.saveAll(listOf(transaction, relateTransaction))
    }
}
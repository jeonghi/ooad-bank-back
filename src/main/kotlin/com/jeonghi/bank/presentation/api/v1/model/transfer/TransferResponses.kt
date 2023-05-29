package com.jeonghi.bank.presentation.api.v1.model.transfer

import com.jeonghi.bank.domain.model.BankAccountTransaction
import com.jeonghi.bank.domain.model.common.TransactionCode
import com.jeonghi.bank.domain.model.common.TransactionStatus
import java.math.BigDecimal
import java.time.LocalDateTime

data class TransferResponse(
    val accountNumber: String,
    val accountName: String,
    val relateAccountNumber: String,
    val amount: BigDecimal?,
    val balance: BigDecimal?,
    val after_balance: BigDecimal?,
    val transactionCode: TransactionCode,
    val transactionStatus: TransactionStatus,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime,
){
    companion object {
        fun from(transaction: BankAccountTransaction) = TransferResponse(
            accountNumber = transaction.account.accountNumber,
            accountName = transaction.account.name,
            relateAccountNumber = transaction.relateAccount.accountNumber,
            amount = transaction.amount,
            balance = transaction.balance,
            after_balance = transaction.afterBalance,
            transactionCode = transaction.transactionCode,
            transactionStatus = transaction.transactionStatus,
            createdAt = transaction.createdAt,
            updatedAt = transaction.updatedAt,
        )
    }
}

package com.jeonghi.bank.application.service.bank

import com.jeonghi.bank.application.exception.ConflictException
import com.jeonghi.bank.application.exception.NotFoundException
import com.jeonghi.bank.domain.model.BankAccount
import com.jeonghi.bank.domain.repository.BankAccountRepository
import com.jeonghi.bank.domain.repository.MemberRepository
import com.jeonghi.bank.domain.repository.utils.findByIdOrThrow
import com.jeonghi.bank.presentation.api.v1.model.account.*
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.*

@Service
class AccountService(
    private val memberRepository: MemberRepository,
    private val bankAccountRepository: BankAccountRepository,
    private val encoder: PasswordEncoder, // 추가
) {

    @Transactional(readOnly = true)
    fun getAccountInfo(
        memberId: UUID,
        request: AccountReadRequest,
    ): AccountInfoResponse {
        val member = memberRepository.findByIdOrThrow(memberId, "존재하지 않는 회원입니다.")
        val account = member.bankAccounts?.find { request.accountNumber == it.accountNumber }
            ?: throw NotFoundException(message = "존재하지 않는 계좌입니다.")
        return AccountInfoResponse.from(account)
    }

    @Transactional(readOnly = true)
    fun getAccountsInfo(
        memberId: UUID,
    ): List<AccountInfoResponse> {
        val member = memberRepository.findByIdOrThrow(memberId, "존재하지 않는 회원입니다.")
        val accounts = member.bankAccounts
        if (accounts != null) {
            return accounts.map { AccountInfoResponse.from(it) }
        }
        return emptyList()
    }

    @Transactional
    fun createAccount(
        memberId: UUID,
        request: AccountCreateRequest,
    ): AccountInfoResponse {
        val member = memberRepository.findByIdOrThrow(memberId, "존재하지 않는 회원입니다.")
        val accountNumber = generateAccountNumber(memberId)

        // 생성된 계좌번호 유일성 검증
        if( bankAccountRepository.existsBankAccountByAccountNumber(accountNumber) ){
            throw ConflictException(message = "이미 존재하는 계좌번호입니다.")
        }

        val bankAccount = BankAccount(
            accountNumber = accountNumber,
            password = encoder.encode(request.password),
            owner = member,
            name = request.accountName,
        )

        bankAccountRepository.save(bankAccount)
        return AccountInfoResponse.from(bankAccount)
    }

    @Transactional
    fun deleteAccount(
        memberId: UUID,
        request: AccountDeleteRequest,
    ): AccountDeleteResponse {
        // 회원 검증
        val member = memberRepository.findByIdOrThrow(memberId, "존재하지 않는 회원입니다.")

        // 계좌 검증
        val account = member.bankAccounts?.find { request.accountNumber == it.accountNumber }
            ?: throw NotFoundException(message = "존재하지 않는 계좌입니다.")

        // 계좌 비밀번호 검증
        account.validatePassword(request.password, encoder)

        // 계좌에서 은행 계좌 삭제
        bankAccountRepository.delete(account)
        return AccountDeleteResponse.from(account)
    }

    private fun generateAccountNumber(
        memberId: UUID,
    ): String {
        // 회원 코드
        val memberCode = memberId.toString().padStart(4, '0')
        val randomCode = (1000..9900).random().toString()

        // 계좌(4자리) 랜덤코드(4자리)
        val accountNumber = "$memberCode-$randomCode"

        return accountNumber
    }
}
package com.jeonghi.bank.domain.repository

import com.jeonghi.bank.domain.model.BankAccount
import com.jeonghi.bank.domain.model.Member
import org.springframework.data.jpa.repository.JpaRepository
import java.util.UUID

interface BankAccountRepository : JpaRepository<BankAccount, UUID> {
    fun findAllByOwner(owner: Member): List<BankAccount>

    fun findByAccountNumber(accountNumber: String): BankAccount?

    fun existsBankAccountByAccountNumber(accountNumber: String): Boolean
}
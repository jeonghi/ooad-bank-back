package com.jeonghi.bank.domain.repository

import com.jeonghi.bank.domain.model.BankAccount
import com.jeonghi.bank.domain.model.BankAccountTransaction
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface BankAccountTransactionRepository : JpaRepository<BankAccountTransaction, UUID> {
    fun findAllByAccount(bankAccount: BankAccount): List<BankAccountTransaction>
}
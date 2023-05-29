package com.jeonghi.bank.presentation.api.v1.model.account // ktlint-disable filename

import com.jeonghi.bank.domain.model.BankAccount
import io.swagger.v3.oas.annotations.media.Schema
import java.math.BigDecimal
import java.time.LocalDateTime
import java.time.ZoneId
import java.util.*

data class AccountInfoResponse(
    @Schema(description = "은행 고유키", example = "123e4567-e89b-12d3-a456-426614174000")
    val id: UUID,

    val accountNumber: String,
    
    val accountName: String,
    
    val ownerName: String,

    val balance: BigDecimal,
    
    @Schema(description = "계좌 생성일", example = "2023-05-11T15:00:00")
    val createdAt: LocalDateTime,
) {
    companion object {
        fun from(account: BankAccount) = AccountInfoResponse(
            id = account.id!!,
            accountName = account.name,
            accountNumber = account.accountNumber,
            ownerName = account.owner.name ?: "",
            balance = account.balance,
            createdAt = account.createdAt,
        )
    }
}

data class AccountDeleteResponse(
    val id: UUID,
    val deleteAt: LocalDateTime,
    val acccountNumber: String,
) {
    companion object {
        fun from(account: BankAccount) = AccountDeleteResponse(
            id = account.id!!,
            deleteAt = LocalDateTime.now(ZoneId.of("Asia/Seoul")),
            acccountNumber = account.accountNumber,
        )
    }
}
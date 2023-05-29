package com.jeonghi.bank.presentation.api.v1.model.account

import io.swagger.v3.oas.annotations.media.Schema

data class AccountCreateRequest(
    @Schema(description = "계좌 이름", example = "물먹는 하마")
    val accountName: String,
    @Schema(description = "계좌 비밀번호", example = "1111")
    var password: String,
)

data class AccountReadRequest(
    val accountNumber: String,
)

data class AccountDeleteRequest(
    val accountNumber: String,
    val password: String,
)
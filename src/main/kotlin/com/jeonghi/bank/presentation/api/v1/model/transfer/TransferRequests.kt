package com.jeonghi.bank.presentation.api.v1.model.transfer

import java.math.BigDecimal

data class TransferRequest(
    val accountNumber: String,
    val relateAccountNumber: String,
    val password: String,
    val amount: BigDecimal,
)
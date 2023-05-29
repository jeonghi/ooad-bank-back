package com.jeonghi.bank.presentation.api.model

data class ErrorResponse(
    val code: String,
    val message: String?,
    val debug: String? = null
)

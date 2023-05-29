package com.jeonghi.bank.application.exception

open class ApplicationException(
    val code: ApplicationErrorCode,
    override val message: String?
) : RuntimeException()

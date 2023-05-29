package com.jeonghi.bank.application.exception

class IllegalArgumentException(
    code: ApplicationErrorCode = ApplicationErrorCode.ILLEGAL_ARGUMENT,
    override val message: String?,
) : ApplicationException(code, message)
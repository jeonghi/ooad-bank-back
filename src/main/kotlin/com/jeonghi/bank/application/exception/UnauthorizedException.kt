package com.jeonghi.bank.application.exception

class UnauthorizedException(
    code: ApplicationErrorCode = ApplicationErrorCode.UNAUTHORIZED,
    override val message: String? = null,
) : ApplicationException(code, message)

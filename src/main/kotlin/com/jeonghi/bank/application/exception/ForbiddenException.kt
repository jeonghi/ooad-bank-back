package com.jeonghi.bank.application.exception

class ForbiddenException(
    code: ApplicationErrorCode = ApplicationErrorCode.FORBIDDEN,
    override val message: String?
) : ApplicationException(code, message)

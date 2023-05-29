package com.jeonghi.bank.application.exception

class ConflictException(
    code: ApplicationErrorCode = ApplicationErrorCode.CONFLICT,
    override val message: String?
) : ApplicationException(code, message)

package com.jeonghi.bank.application.exception

class BadRequestException(
    code: ApplicationErrorCode = ApplicationErrorCode.BAD_REQUEST,
    override val message: String?
) : ApplicationException(code, message)

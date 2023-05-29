package com.jeonghi.bank.application.exception

class InternalServerErrorException(
    code: ApplicationErrorCode = ApplicationErrorCode.INTERNAL_SERVER_ERROR,
    override val message: String?
) : ApplicationException(code, message)

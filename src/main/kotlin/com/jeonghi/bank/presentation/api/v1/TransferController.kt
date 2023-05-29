package com.jeonghi.bank.presentation.api.v1

import com.jeonghi.bank.application.service.bank.TransferService
import com.jeonghi.bank.presentation.api.auth.UserAuthorized
import com.jeonghi.bank.presentation.api.v1.model.transfer.TransferRequest
import com.jeonghi.bank.presentation.api.v1.model.transfer.TransferResponse
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.security.core.userdetails.User
import org.springframework.stereotype.Service
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.*

@Service
@UserAuthorized
@RestController
@RequestMapping("/api/v1/transfer")
class TransferController(
    val transferService: TransferService,
) {
    @PostMapping
    fun transfer(
        @AuthenticationPrincipal user: User,
        @RequestBody request: TransferRequest,
    ): TransferResponse {
        return transferService.createTransaction(
            userId = UUID.fromString(user.username),
            request = request,
        )
    }
}
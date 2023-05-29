package com.jeonghi.bank.presentation.api.v1

import com.jeonghi.bank.application.service.auth.MemberService
import com.jeonghi.bank.application.service.bank.AccountService
import com.jeonghi.bank.presentation.api.auth.UserAuthorized
import com.jeonghi.bank.presentation.api.v1.model.account.AccountCreateRequest
import com.jeonghi.bank.presentation.api.v1.model.account.AccountDeleteRequest
import com.jeonghi.bank.presentation.api.v1.model.account.AccountReadRequest
import com.jeonghi.bank.presentation.api.v1.model.common.ApiResponse
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.security.core.userdetails.User
import org.springframework.web.bind.annotation.* // ktlint-disable no-wildcard-imports
import java.util.*

@UserAuthorized
@RestController
@RequestMapping("/api/v1/account")
class AccountController(
    private val memberService: MemberService,
    private val accountService: AccountService,
) {
    @PostMapping
    fun createAccount(
        @AuthenticationPrincipal user: User,
        @RequestBody request: AccountCreateRequest,
    ) = ApiResponse.success(accountService.createAccount(UUID.fromString(user.username), request))

    @GetMapping("/{accountNumber}")
    fun getAccountInfo(
        @AuthenticationPrincipal user: User,
        @RequestParam request: AccountReadRequest,
    ) = ApiResponse.success(accountService.getAccountInfo(UUID.fromString(user.username), request))

    @GetMapping
    fun getAccountsInfo(
        @AuthenticationPrincipal user: User,
    ) = ApiResponse.success(accountService.getAccountsInfo(UUID.fromString(user.username)))

    @DeleteMapping
    fun deleteAccount(
        @AuthenticationPrincipal user: User,
        @RequestBody request: AccountDeleteRequest,
    ) =
        ApiResponse.success(accountService.deleteAccount(UUID.fromString(user.username), request))
}
package com.jeonghi.bank.presentation.api.v1

import com.jeonghi.bank.application.service.auth.SignService
import com.jeonghi.bank.presentation.api.v1.model.member.SignInRequest
import com.jeonghi.bank.presentation.api.v1.model.member.SignUpRequest
import com.jeonghi.bank.presentation.api.v1.model.common.ApiResponse
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping
class SignController(private val signService: SignService) {
    @PostMapping("/sign-up")
    fun signUp(@RequestBody request: SignUpRequest) = ApiResponse.success(signService.registMember(request))

    @PostMapping("/sign-in")
    fun signIn(@RequestBody request: SignInRequest) = ApiResponse.success(signService.signIn(request))
}
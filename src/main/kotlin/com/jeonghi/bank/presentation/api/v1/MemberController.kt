package com.jeonghi.bank.presentation.api.v1

import com.jeonghi.bank.application.service.auth.MemberService
import com.jeonghi.bank.presentation.api.auth.UserAuthorized
import com.jeonghi.bank.presentation.api.v1.model.common.ApiResponse
import com.jeonghi.bank.presentation.api.v1.model.member.MemberUpdateRequest
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.security.core.userdetails.User
import org.springframework.web.bind.annotation.*
import java.util.*

@UserAuthorized
@RestController
@RequestMapping("/api/v1/member")
class MemberController(
    private val memberService: MemberService,
) {
    @GetMapping
    fun getMemberInfo(@AuthenticationPrincipal user: User) = ApiResponse.success(memberService.getMemberInfo(UUID.fromString(user.username)))

    @DeleteMapping
    fun deleteMember(@AuthenticationPrincipal user: User) =
        ApiResponse.success(memberService.deleteMember(UUID.fromString(user.username)))

    @PutMapping
    fun updateMember(@AuthenticationPrincipal user: User, @RequestBody request: MemberUpdateRequest) =
        ApiResponse.success(memberService.updateMember(UUID.fromString(user.username), request))
}
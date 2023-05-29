package com.jeonghi.bank.presentation.api.v1

import com.jeonghi.bank.application.service.auth.MemberService
import com.jeonghi.bank.presentation.api.v1.model.member.MemberUpdateRequest
import com.jeonghi.bank.presentation.api.v1.model.common.ApiResponse
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping("/member")
class MemberController(
    private val memberService: MemberService,
) {
    @GetMapping
    fun getMemberInfo(id: String) = ApiResponse.success(memberService.getMemberInfo(UUID.fromString(id)))

    @DeleteMapping
    fun deleteMember(id: String) = ApiResponse.success(memberService.deleteMember(UUID.fromString(id)))

    @PutMapping
    fun updateMember(id: String, @RequestBody request: MemberUpdateRequest) = ApiResponse.success(
        memberService.updateMember(
            UUID.fromString(id),
            request,
        ),
    )
}
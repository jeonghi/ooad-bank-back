package com.jeonghi.bank.application.service.auth

import com.jeonghi.bank.application.exception.ConflictException
import com.jeonghi.bank.application.exception.UnauthorizedException
import com.jeonghi.bank.domain.model.Member
import com.jeonghi.bank.domain.repository.MemberRepository
import com.jeonghi.bank.domain.repository.utils.flushOrThrow
import com.jeonghi.bank.presentation.api.v1.model.member.SignInRequest
import com.jeonghi.bank.presentation.api.v1.model.member.SignInResponse
import com.jeonghi.bank.presentation.api.v1.model.member.SignUpRequest
import com.jeonghi.bank.presentation.api.v1.model.member.SignUpResponse
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
@Service
class SignService(
    private val memberRepository: MemberRepository,
    private val tokenProvider: TokenProvider,
    private val encoder: PasswordEncoder,
) {
    @Transactional
    fun registMember(request: SignUpRequest) = SignUpResponse.from(
        memberRepository.flushOrThrow(ConflictException(message = "이미 사용중인 아이디입니다.")) {
            save(Member.from(request, encoder))	// 회원가입 정보를 암호화하도록 수정
        },
    )

    @Transactional
    fun signIn(request: SignInRequest): SignInResponse {
        val member = memberRepository.findByAccount(request.account)
            ?.takeIf { encoder.matches(request.password, it.password) }	// 암호화된 비밀번호와 비교하도록 수정
            ?: throw UnauthorizedException(message = "아이디 또는 비밀번호가 일치하지 않습니다.")
        val token = tokenProvider.createToken("${member.id}:${member.type}")	// 토큰 생성
        return SignInResponse(member.name, member.type, token)	// 생성자에 토큰 추가
    }
}
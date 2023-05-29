package com.jeonghi.bank.application.service.auth
import com.jeonghi.bank.domain.repository.MemberRepository
import com.jeonghi.bank.domain.repository.utils.findByIdOrThrow
import com.jeonghi.bank.presentation.api.v1.model.member.MemberDeleteResponse
import com.jeonghi.bank.presentation.api.v1.model.member.MemberInfoResponse
import com.jeonghi.bank.presentation.api.v1.model.member.MemberUpdateRequest
import com.jeonghi.bank.presentation.api.v1.model.member.MemberUpdateResponse
import org.springframework.data.repository.findByIdOrNull
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.*

@Service
class MemberService(
    private val memberRepository: MemberRepository,
    private val encoder: PasswordEncoder, // 추가
) {

    @Transactional(readOnly = true)
    fun getMemberInfo(id: UUID) = MemberInfoResponse.from(memberRepository.findByIdOrThrow(id, "존재하지 않는 회원입니다."))

    @Transactional
    fun deleteMember(id: UUID): MemberDeleteResponse {
        if (!memberRepository.existsById(id)) return MemberDeleteResponse(false)
        memberRepository.deleteById(id)
        return MemberDeleteResponse(true)
    }
    @Transactional
    fun updateMember(id: UUID, request: MemberUpdateRequest): MemberUpdateResponse {
        val member = memberRepository.findByIdOrNull(id)?.takeIf { encoder.matches(request.password, it.password) }	// 암호화된 비밀번호와 비교하도록 수정
            ?: throw IllegalArgumentException("아이디 또는 비밀번호가 일치하지 않습니다.")
        member.update(request, encoder)	// 새 비밀번호를 암호화하도록 수정
        return MemberUpdateResponse.of(true, member)
    }
}
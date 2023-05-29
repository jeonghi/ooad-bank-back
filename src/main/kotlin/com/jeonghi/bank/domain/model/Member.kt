package com.jeonghi.bank.domain.model

import com.jeonghi.bank.domain.model.common.BaseTimeEntity
import com.jeonghi.bank.domain.model.common.MemberType
import com.jeonghi.bank.presentation.api.v1.model.member.MemberUpdateRequest
import com.jeonghi.bank.presentation.api.v1.model.member.SignUpRequest
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.persistence.* // ktlint-disable no-wildcard-imports
import org.springframework.security.crypto.password.PasswordEncoder
import java.util.*

@Tag(name = "로그인 후 사용할 수 있는 API")
@Entity
class Member(

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    val id: UUID? = null,

    @Column(nullable = false, scale = 20, unique = true)
    val account: String,

    @Column(nullable = false)
    var password: String,

    var name: String? = null,

    var age: Int? = null,

    @Enumerated(EnumType.STRING)
    val type: MemberType = MemberType.USER,

) : BaseTimeEntity() {
    companion object {
        fun from(request: SignUpRequest, encoder: PasswordEncoder) = Member(	// 파라미터에 PasswordEncoder 추가
            account = request.account,
            password = encoder.encode(request.password),	// 수정
            name = request.name,
            age = request.age,
        )
    }

    fun update(newMember: MemberUpdateRequest, encoder: PasswordEncoder) {	// 파라미터에 PasswordEncoder 추가
        this.password = newMember.newPassword
            ?.takeIf { it.isNotBlank() }
            ?.let { encoder.encode(it) }	// 추가
            ?: this.password
        this.name = newMember.name
        this.age = newMember.age
    }
}
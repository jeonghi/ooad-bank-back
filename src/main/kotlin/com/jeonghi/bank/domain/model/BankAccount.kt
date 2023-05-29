package com.jeonghi.bank.domain.model

import com.jeonghi.bank.application.exception.BadRequestException
import com.jeonghi.bank.application.exception.UnauthorizedException
import com.jeonghi.bank.domain.model.common.BaseTimeEntity
import jakarta.persistence.* // ktlint-disable no-wildcard-imports
import org.springframework.security.crypto.password.PasswordEncoder
import java.math.BigDecimal
import java.util.*

@Entity(name = "bank_account")
data class BankAccount(

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    val id: UUID? = null,

    @ManyToOne
    @JoinColumn(
        name = "member_id",
        nullable = false,
        updatable = false,
        foreignKey = ForeignKey(ConstraintMode.NO_CONSTRAINT),
    )
    val owner: Member,

    var name: String,

    @Column(
        nullable = false,
        unique = true,
    )
    val accountNumber: String,

    @Column(nullable = false)
    var balance: BigDecimal = BigDecimal.ZERO,

    @Column(nullable = false)
    var password: String,

) : BaseTimeEntity() {
    fun validatePassword(
        password: String,
        encoder: PasswordEncoder,
    ) {
        if (!encoder.matches(password, this.password)) {
            throw UnauthorizedException(message = "비밀번호가 일치하지 않습니다.")
        }
    }

    fun validateOwner(
        memberId: UUID,
    ) {
        if (owner.id != memberId) {
            throw UnauthorizedException(message = "계좌 소유자가 아닙니다.")
        }
    }

    fun withdraw(
        amount: BigDecimal,
    ) {
        if (balance < amount) {
            throw BadRequestException(message = "잔액이 부족합니다.")
        }
        balance -= amount
    }

    fun deposit(
        amount: BigDecimal,
    ) {
        balance += amount
    }
}
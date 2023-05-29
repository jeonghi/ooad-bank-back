package com.jeonghi.bank.domain.model // ktlint-disable filename

import com.jeonghi.bank.domain.model.common.BaseTimeEntity
import com.jeonghi.bank.domain.model.common.TransactionCode
import com.jeonghi.bank.domain.model.common.TransactionStatus
import jakarta.persistence.* // ktlint-disable no-wildcard-imports
import java.math.BigDecimal
import java.util.UUID

@Entity
@Table(name = "bank_account_transaction")
data class BankAccountTransaction(

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    val id: UUID, // 거래 고유번호

    @ManyToOne
    @JoinColumn(
        name = "account_id",
        nullable = false,
        updatable = false,
        foreignKey = ForeignKey(ConstraintMode.NO_CONSTRAINT),
    )
    val account: BankAccount,

    @ManyToOne
    @JoinColumn(
        name = "relate_account_id",
        nullable = false,
        updatable = false,
        foreignKey = ForeignKey(ConstraintMode.NO_CONSTRAINT),
    )
    val relateAccount: BankAccount,

    @Column(nullable = false)
    var transactionCode: TransactionCode,

    @Column(nullable = false)
    var transactionStatus: TransactionStatus,

    @Column(nullable = false)
    val amount: BigDecimal,

    @Column(nullable = true)
    var balance: BigDecimal? = null,

    @Column(nullable = true)
    var afterBalance: BigDecimal? = null,

) : BaseTimeEntity()
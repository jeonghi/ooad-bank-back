package com.jeonghi.bank.domain.repository

import com.jeonghi.bank.domain.model.Member
import com.jeonghi.bank.domain.model.common.MemberType
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface MemberRepository : JpaRepository<Member, UUID> {
    fun findByAccount(account: String): Member?
    fun findAllByType(type: MemberType): List<Member>
}
package com.aidata.fundingtrip.repository;

import com.aidata.fundingtrip.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, String> {
}

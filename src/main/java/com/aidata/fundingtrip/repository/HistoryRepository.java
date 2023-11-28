package com.aidata.fundingtrip.repository;

import com.aidata.fundingtrip.entity.History;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

@Transactional
public interface HistoryRepository extends JpaRepository<History, Integer> {
    Page<History> findByHisnameContains(String keyword, Pageable pb);
}

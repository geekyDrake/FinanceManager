package com.roshan.financemanager.repository;

import com.roshan.financemanager.domain.database.MonthlySubscriptionEntity;
import com.roshan.financemanager.domain.database.YearlySubscriptionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface YearlySubscriptionDB extends JpaRepository<YearlySubscriptionEntity, Long> {}

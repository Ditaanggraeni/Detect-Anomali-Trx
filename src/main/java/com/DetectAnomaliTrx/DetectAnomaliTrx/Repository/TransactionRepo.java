package com.DetectAnomaliTrx.DetectAnomaliTrx.Repository;

import com.DetectAnomaliTrx.DetectAnomaliTrx.Model.Transactions;
import com.DetectAnomaliTrx.DetectAnomaliTrx.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface TransactionRepo extends JpaRepository<Transactions, Long> {
    @Query(value = "SELECT AVG(amount) FROM transactions where user_id = :userId AND transaction_date BETWEEN :startDate AND :endDate", nativeQuery = true)
    Optional<BigDecimal> AverageSpendUser(@Param("userId") Long userId,
                                     @Param("startDate")LocalDateTime startDate,
                                     @Param("endDate") LocalDateTime endDate);

    List<Transactions> findByUserAndTransactionDateAfter(User user, LocalDateTime transactionDate);

}

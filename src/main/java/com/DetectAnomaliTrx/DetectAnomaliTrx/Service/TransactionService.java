package com.DetectAnomaliTrx.DetectAnomaliTrx.Service;

import com.DetectAnomaliTrx.DetectAnomaliTrx.Model.Transactions;
import com.DetectAnomaliTrx.DetectAnomaliTrx.Model.User;
import com.DetectAnomaliTrx.DetectAnomaliTrx.Repository.TransactionRepo;
import com.DetectAnomaliTrx.DetectAnomaliTrx.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class TransactionService {
    @Autowired //IOC
    private TransactionRepo transactionRepo;

    @Autowired
    private UserRepository userRepository;

    public boolean isTrxIsSupicious(Transactions newTransaction) {
        Optional<User> userOptional = userRepository.findById(newTransaction.getUser().getId());
        if (!userOptional.isPresent()){
            newTransaction.setSuspicious(false);
            return false;
        }
        User user = userOptional.get();

        //deteksi jumlah transaksi 2x lebih besar dari rata-rata pengeluaran perbulan
        LocalDateTime oneMonthAgo = LocalDateTime.now().minusMonths(1);
        Optional<BigDecimal> avgAmountOptional = transactionRepo.AverageSpendUser(Long.valueOf(user.getId()), oneMonthAgo, LocalDateTime.now());
        if (avgAmountOptional.isPresent()){
            BigDecimal avgAmout = avgAmountOptional.get();
            if (newTransaction.getAmount().compareTo(avgAmout.multiply(BigDecimal.valueOf(2))) > 0) {
                newTransaction.setSuspicious(true);
                return true;
            }
        }

        //deteksi transaksi yang berbeda lokasi dalam waktu yang singkat dari trx terakhir (10 menit)
        // 2. Deteksi Anomali Lokasi dan Waktu (Java Stream)
        LocalDateTime tenMinutesAgo = LocalDateTime.now().minusMinutes(10);
        List<Transactions> recentTransactions = transactionRepo.findByUserAndTransactionDateAfter(user, tenMinutesAgo);

        boolean isLocationAnomalous = recentTransactions.stream()
                .anyMatch(existingTrx -> !existingTrx.getLocation().equals(newTransaction.getLocation()));

        if (isLocationAnomalous) {
            newTransaction.setSuspicious(true);
            return true;
        }
        newTransaction.setSuspicious(false);
        return false;
    }
}

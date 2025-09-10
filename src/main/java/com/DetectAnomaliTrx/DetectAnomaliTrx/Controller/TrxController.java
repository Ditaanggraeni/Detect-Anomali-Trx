package com.DetectAnomaliTrx.DetectAnomaliTrx.Controller;

import com.DetectAnomaliTrx.DetectAnomaliTrx.Model.Transactions;
import com.DetectAnomaliTrx.DetectAnomaliTrx.Repository.TransactionRepo;
import com.DetectAnomaliTrx.DetectAnomaliTrx.Service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/trx")
public class TrxController {
    @Autowired
    private TransactionRepo transactionRepo;

    @Autowired
    private TransactionService transactionService;

    @PostMapping
    public ResponseEntity<Transactions> createTrx(@RequestBody Transactions newTransactions) {
        boolean isSuspicious = transactionService.isTrxIsSupicious(newTransactions);
        newTransactions.setSuspicious(isSuspicious);

        Transactions savedTrx = transactionRepo.save(newTransactions);
        return ResponseEntity.ok(savedTrx);
    }
}

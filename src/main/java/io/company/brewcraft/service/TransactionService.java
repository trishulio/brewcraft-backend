package io.company.brewcraft.service;

import org.springframework.transaction.interceptor.TransactionAspectSupport;

public class TransactionService {
    public void setRollbackOnly() {
        TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
    }
}

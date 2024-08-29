package com.flance.assessment.dto.response;

import com.flance.assessment.config.HeaderBase;
import com.flance.assessment.model.Transaction;
import lombok.Data;

import java.util.List;

@Data
public class GetTransactionsResponse {
    private HeaderBase headerBase;
    private List<Transaction> transactionList;
}

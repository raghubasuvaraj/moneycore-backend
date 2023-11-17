package com.moneycore.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.moneycore.entity.Wallet;

public interface WalletTransaction121Service {

    Wallet findWalletByPhone(String phoneNum, String institutionCode);
    Wallet findWallet(String walletId);
    String doTransaction(Long importLineId) throws JsonProcessingException;

}

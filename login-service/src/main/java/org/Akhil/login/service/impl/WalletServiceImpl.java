package org.Akhil.login.service.impl;

import org.Akhil.common.model.Wallet;
import org.Akhil.common.repo.WalletRepo;
import org.Akhil.login.service.WalletService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class WalletServiceImpl implements WalletService {
    @Autowired
    private WalletRepo walletRepo;
    @Override
    public Wallet addWallet() {
        Wallet wallet=new Wallet();
        wallet.setWalletId("w"+ UUID.randomUUID().toString());
        return walletRepo.save(wallet);
    }
}

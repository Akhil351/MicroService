package org.Akhil.common.repo;

import org.Akhil.common.model.Wallet;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface WalletRepo extends MongoRepository<Wallet,String> {
    Optional<Wallet> findByWalletId(String walletId);
}

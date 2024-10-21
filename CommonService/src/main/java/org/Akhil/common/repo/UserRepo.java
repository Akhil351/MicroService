package org.Akhil.common.repo;

import org.Akhil.common.model.User;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepo extends MongoRepository<User,String>, JpaSpecificationExecutor<User> {
    Optional<User> findByEmail(String email);
}

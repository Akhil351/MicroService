package org.Akhil.common.repo;

import org.Akhil.common.model.Roles;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RolesRepo extends MongoRepository<Roles,String> {
    List<Roles> findByUserId(String userId);
    void deleteAllByUserId(String userId);
}

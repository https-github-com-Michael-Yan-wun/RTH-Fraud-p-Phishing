package com.ntc.fraud.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import com.ntc.fraud.model.BlackList;
import java.util.Optional;

@Repository
public interface BlackListRepository extends MongoRepository<BlackList, Long> {
    Boolean existsByDomain(String domain);
    Optional<BlackList> findByDomain(String domain);
}

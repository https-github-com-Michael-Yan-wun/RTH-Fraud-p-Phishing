package com.ntc.fraud.repository;


import com.ntc.fraud.model.Report;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ReportRepository extends MongoRepository<Report, String> {
    Boolean existsByDomain(String domain);
    Optional<Report> findByDomain(String domain);
}

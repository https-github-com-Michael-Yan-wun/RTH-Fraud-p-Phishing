package com.ntc.fraud.service;

import com.ntc.fraud.model.Report;
import com.ntc.fraud.exception.ResourceNotFoundException;
import com.ntc.fraud.repository.ReportRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Report Service
 * @author Michael
 * @apiNote Build service for reporting
 */
@Service
public class ReportService {

    @Autowired
    private ReportRepository Report;

    //Report全部資料
    public List<Report> getAllReported() {
        return Report.findAll();
    }

    //查詢Report find by DomainName
    public Optional<Report> getReportedByDomainName(String domain) {
        return Report.findByDomain(domain);
    }

    //create reported data
    public Report addReportedDomain(Report report) {
        return Report.save(report);
    }

    //update reported data
    public Report updateReportedDomain(String domainName,
                                  Report ReportDetails) throws ResourceNotFoundException {
        Optional<Report> optionalReport = Report.findAll()
                                                .stream()
                                                .filter(r -> r.getDomain().equals(domainName))
                                                .findFirst();

        Report report = optionalReport
                .orElseThrow(
                        () -> new ResourceNotFoundException("Reported not found for this domain ===> " + domainName)
                );
        report.setDomain(ReportDetails.getDomain());
        return  Report.save(report);
    }

    //delete Reported data
    public Map<String, Boolean> deleteReportedDomain(String domainName)
            throws ResourceNotFoundException {
        Optional<Report> optionalReport = Report.findAll()
                                                .stream()
                                                .filter(r -> r.getDomain().equals(domainName))
                                                .findFirst();
        Report report = optionalReport
                .orElseThrow(
                        () -> new ResourceNotFoundException("Reported not found for this domain ===> " + domainName)
                );
        Report.delete(report);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return response;
    }
}

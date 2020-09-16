package com.ntc.fraud.controller;

import com.ntc.fraud.exception.ErrorResponse;
import com.ntc.fraud.exception.ResourceNotFoundException;
import com.ntc.fraud.model.Report;
import com.ntc.fraud.repository.ReportRepository;
import com.ntc.fraud.service.ReportService;
import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;
import io.github.bucket4j.Bucket4j;
import io.github.bucket4j.Refill;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.time.Duration;
import java.util.List;
import java.util.Map;

@ApiOperation(value = "api/reported" , tags = "Reported Profile Controller")
@RestController
@RequestMapping("/api")
public class  ReportedController {
    /*
     * Controller for Report api
     * @author Michael
     *
     */
    private final Bucket bucket;

    public ReportedController(){
        Bandwidth limit = Bandwidth.classic(50, Refill.greedy(50, Duration.ofMinutes(1)));
        this.bucket = Bucket4j.builder().addLimit(limit).build();
    }

    @Autowired
    private  ReportService ReportService;

    @Autowired
    private ReportRepository reportRepository;


    @ApiOperation(value = "Fetch All Reported List" , response = Iterable.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200,message = "Success",response = Report.class),
            @ApiResponse(code = 401,message = "Unauthorized",response = ErrorResponse.class),
            @ApiResponse(code = 403,message = "Forbidden",response = ErrorResponse.class),
            @ApiResponse(code = 404,message = "Not Found")
    })

    @CrossOrigin
    @GetMapping(value = "/reported",produces= MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<List<Report>> getAllReport() {
        if(bucket.tryConsume(1)){
            return ResponseEntity.ok(ReportService.getAllReported());
        } else {
            return ResponseEntity.status(HttpStatus.TOO_MANY_REQUESTS).build();
        }
    }

    @CrossOrigin
    @ApiOperation(value = "Get Reported By DomainName" , response = Report.class)
    @GetMapping("/reported/{domain}")
    public ResponseEntity<Report> getReportByDomainName(@PathVariable(value = "domain") String domain) throws ResourceNotFoundException {
        if(bucket.tryConsume(1)){
            Report report = ReportService.getReportedByDomainName(domain)
                    .orElseThrow(() -> new ResourceNotFoundException("Not fund for this domain : " + domain));
            return ResponseEntity.ok().body(report);
        } else {
            return ResponseEntity.status(HttpStatus.TOO_MANY_REQUESTS).build();
        }
    }

    @CrossOrigin
    @ApiOperation(value = "Add Reported By DomainName" , response = Report.class)
    @PostMapping("/reported")
    public ResponseEntity<Report> addReportedDomain(@Valid @RequestBody Report report) {
        if(bucket.tryConsume(1)){
            report.setCreateTime();
            if (reportRepository.existsByDomain(report.getDomain())){
                return ResponseEntity.status(HttpStatus.CONFLICT).build();
            } else {
                return ResponseEntity.ok(ReportService.addReportedDomain(report));
            }
        }else{
            return ResponseEntity.status(HttpStatus.TOO_MANY_REQUESTS).build();
        }
    }

    @CrossOrigin
    @ApiOperation(value = "Modify Reported By DomainName" , response = Report.class)
    @PutMapping("/reported/{domain}")
    public ResponseEntity<Report> updateDomain(@PathVariable(value = "domain") String domain,
                                                 @Valid @RequestBody Report reportdetails) throws ResourceNotFoundException {
        if (!reportRepository.existsByDomain(domain)){
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).build();
        }
        Report updatedReported = ReportService.updateReportedDomain(domain, reportdetails);
        return ResponseEntity.ok(updatedReported);
    }

    @CrossOrigin
    @ApiOperation(value = "Delete Reported By DomainName" , response = Report.class)
    @DeleteMapping("/reported/{domain}")
    public Map<String, Boolean> deleteStudent(@PathVariable(value = "domain") String domainName)
            throws ResourceNotFoundException {
        return ReportService.deleteReportedDomain(domainName);
    }

}

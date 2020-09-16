package com.ntc.fraud.controller;

import java.time.Duration;
import java.util.List;
import java.util.Map;
import javax.validation.Valid;

import com.ntc.fraud.repository.BlackListRepository;
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
import com.ntc.fraud.exception.ResourceNotFoundException;
import com.ntc.fraud.model.BlackList;
import com.ntc.fraud.service.BlackListService;
import com.ntc.fraud.exception.ErrorResponse;

@ApiOperation(value = "api/blacklist" , tags = "BlackList Profile Controller")
@RestController
@RequestMapping("/api")
public class BlackListController {

	private final Bucket bucket;

	public BlackListController(){
		Bandwidth limit = Bandwidth.classic(50, Refill.greedy(50, Duration.ofMinutes(1)));
		this.bucket = Bucket4j.builder().addLimit(limit).build();
	}

	@Autowired
	private BlackListService blackListService;

	@Autowired
	private BlackListRepository blackListRepository;

	@ApiOperation(value = "Fetch All BlackList" , response = Iterable.class)
	@ApiResponses(value = {
			@ApiResponse(code = 200,message = "Success",response = BlackList.class),
			@ApiResponse(code = 401,message = "Unauthorized",response = ErrorResponse.class),
			@ApiResponse(code = 403,message = "Forbidden",response = ErrorResponse.class),
			@ApiResponse(code = 404,message = "Not Found")
	})

	@CrossOrigin
	@GetMapping(value = "/blacklist",produces= MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<List<BlackList>> getAllBlackList() {
		if(bucket.tryConsume(1)){
			return ResponseEntity.ok(blackListService.getAllBlackList());
		} else {
			return ResponseEntity.status(HttpStatus.TOO_MANY_REQUESTS).build();
		}
	}

	@CrossOrigin
	@ApiOperation(value = "Get Blacklist By DomainName" , response = BlackList.class)
	@GetMapping("/blacklist/{domainName}")
	public ResponseEntity<BlackList> getBlackListByDomainName(@PathVariable(value = "domainName") String domainName) throws ResourceNotFoundException {
		if(bucket.tryConsume(1)){
			BlackList blackList = blackListService.getBlackListByDomain(domainName)
					.orElseThrow(() -> new ResourceNotFoundException("Not fund for this domain : " + domainName));
			return ResponseEntity.ok().body(blackList);
		} else {
			return ResponseEntity.status(HttpStatus.TOO_MANY_REQUESTS).build();
		}
	}

	@CrossOrigin
	@ApiOperation(value = "Add Blacklist By DomainName" , response = BlackList.class )
	@PostMapping("/blacklist")
	public ResponseEntity<BlackList> addBlackListDomain(@Valid @RequestBody BlackList blackList) {
		if(bucket.tryConsume(1)){
			if (blackListRepository.existsByDomain(blackList.getDomain())){
				return ResponseEntity.status(HttpStatus.CONFLICT).build();
			} else {
				return ResponseEntity.ok(blackListService.addDomain(blackList));
			}
		}else{
			return ResponseEntity.status(HttpStatus.TOO_MANY_REQUESTS).build();
		}
	}

	@CrossOrigin
	@ApiOperation(value = "Modify Blacklist By DomainName" , response = BlackList.class )
	@PutMapping("/blacklist/{domain}")
	public ResponseEntity<BlackList> updateDomain(@PathVariable(value = "domain") String domain,
											   @Valid @RequestBody BlackList BlackDetails) throws ResourceNotFoundException {
		if (!blackListRepository.existsByDomain(domain)){
			return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).build();
		}
		BlackList updatedBlackList= blackListService.updateBlackListDomain(domain, BlackDetails);
		return ResponseEntity.ok(updatedBlackList);
	}

	@CrossOrigin
	@ApiOperation(value = "Delete Blacklist By DomainName" , response = BlackList.class )
	@DeleteMapping("/blacklist/{domain}")
	public Map<String, Boolean> deleteStudent(@PathVariable(value = "domain") String domainName)
			throws ResourceNotFoundException {
		return blackListService.deleteBlackListDomain(domainName);
	}

}

package com.ntc.fraud.whois;


import com.ntc.fraud.repository.BlackListRepository;
import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;
import io.github.bucket4j.Bucket4j;
import io.github.bucket4j.Refill;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.time.Duration;
import javax.servlet.http.HttpServlet;



/**
 * Servlet implementation class whoisController
 */
@ApiOperation(value = "api/whois" , tags = "Whois Controller")
@RestController
@RequestMapping("/api")
public class whoisController extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private final Bucket bucket;
	public whoisController(){
		Bandwidth limit = Bandwidth.classic(50, Refill.greedy(50, Duration.ofMinutes(1)));
		this.bucket = Bucket4j.builder().addLimit(limit).build();
	}

	@Autowired
	BlackListRepository blackListRepository;


    whoIsQuery whois = new whoIsQuery();

	@CrossOrigin
	@GetMapping("/whois/{domain}")
	public ResponseEntity<WhoisEntity> whoisSystem(@PathVariable(value = "domain") String splitemail) {
		dataProcess dp = new dataProcess();
		String status = "4";
		WhoisEntity whoisEntity = new WhoisEntity();
		if(bucket.tryConsume(1)){
			if (blackListRepository.existsByDomain(splitemail)){
				status = "1";
				whoisEntity.setPrediction("此網域為黑名單所發出");
			} else if (dp.checkIgnore(splitemail) == true) {
				status = "2";
				whoisEntity.setPrediction("此網域可疑性低");
			} else {
				String[] whoisResult;
				try {
					whoisResult = whois.query(splitemail);
					whoisEntity.setDomainName(whoisResult[0]);
					if (whoisResult[1] != null) {
						status = "3";
						String pyResult = dp.callPython(splitemail, whoisResult[1], whoisResult[2], whoisResult[3]);
						System.out.println(pyResult);
						String a = pyResult.split(",")[0].split(":")[1].replace("\"","").substring(1);
						String b = pyResult.split(",")[1].split(":")[1].replace("\"","").replace("}", "").substring(1);
						whoisEntity.setPrediction(a);
						whoisEntity.setDicScore(b);
						whoisEntity.setScoreCre(whoisResult[1]);
						whoisEntity.setScoreUpd(whoisResult[2]);
						whoisEntity.setScoreExp(whoisResult[3]);
					} else {
						whoisEntity.setPrediction("輸入的網址不存在");
					}
				} catch (Exception e) {
					whoisEntity.setPrediction("輸入的網域不存在");
					e.printStackTrace();
				}
			}
			return ResponseEntity.ok(whoisEntity);
		}else{
			return ResponseEntity.status(HttpStatus.TOO_MANY_REQUESTS).build();
		}
	}
}

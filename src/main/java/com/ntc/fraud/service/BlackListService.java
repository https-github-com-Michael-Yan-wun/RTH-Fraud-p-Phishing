package com.ntc.fraud.service;

import com.ntc.fraud.repository.BlackListRepository;
import com.ntc.fraud.exception.ResourceNotFoundException;
import com.ntc.fraud.model.BlackList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Black Service
 * @author Michael
 * @apiNote 建立blacklist Service以提供黑名單資料
 */
@Service
public class BlackListService {

	@Autowired
	private BlackListRepository blackListRepository;

	//BlackList全部資料
	public List<BlackList> getAllBlackList() {
		return blackListRepository.findAll();
	}

	//查詢BlackList By Domain
	public Optional<BlackList> getBlackListByDomain(String domain) {
		return blackListRepository.findByDomain(domain);
	}

	//add BlackList data
	public BlackList addDomain(BlackList blackList) {
		return blackListRepository.save(blackList);
	}

	//update BlackList資料
	public BlackList updateBlackListDomain(String domainName,
										   BlackList blackListDetails) throws ResourceNotFoundException {
		Optional<BlackList> optionalReport = blackListRepository.findAll()
																.stream()
																.filter(r -> r.getDomain().equals(domainName))
																.findFirst();

		BlackList blackList = optionalReport
				.orElseThrow(
						() -> new ResourceNotFoundException("Reported not found for this domain ===> " + domainName)
				);
		blackList.setDomain(blackListDetails.getDomain());
		return blackListRepository.save(blackList);
	}


	//delete Student資料
	public Map<String, Boolean> deleteBlackListDomain(String domainName)
			throws ResourceNotFoundException {
		Optional<BlackList> optionalReport = blackListRepository.findAll()
																.stream()
																.filter(r -> r.getDomain().equals(domainName))
																.findFirst();
		BlackList blackList = optionalReport
				.orElseThrow(
						() -> new ResourceNotFoundException("Reported not found for this domain ===> " + domainName)
				);
		blackListRepository.delete(blackList);
		Map<String, Boolean> response = new HashMap<>();
		response.put("deleted", Boolean.TRUE);
		return response;
	}
}

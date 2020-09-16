package com.ntc.fraud.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import javax.persistence.Id;


@ApiModel(description = "Blacklist Model")
@Document(collection = "Blacklist")
public class BlackList {

	@ApiModelProperty(notes = "ID of blacklist domain",name = "id",required = true,value = "1dV8d1sAd8s")
	private String id;

	@ApiModelProperty(notes = "Name of blacklist domain",name = "domain",required = true,value = "com.ntc.com")
	private String domain;

	public BlackList() {
	}
	public BlackList(String domain) {
		this.domain = domain;
	}

	@Id
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
	@Field("domain")
	public String getDomain() {
		return domain;
	}
	public void setDomain(String domainName) {
		this.domain = domainName;
	}

	@Override
	public String toString() {
		return "black list ==> [id=" + id + ", Name=" + domain+ "]";
	}
	
}

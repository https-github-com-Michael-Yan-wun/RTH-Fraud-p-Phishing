package com.ntc.fraud.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import javax.persistence.Id;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@ApiModel(description = "Reported Model")
@Document(collection = "Report")
public class Report {

    @ApiModelProperty(notes = "ID of reported domain",name = "id",required = true,value = "2d8gF9S1seq")
    private String id;

    @ApiModelProperty(notes = "Name of reported domain",name = "domain",required = true,value = "com.ntc.com")
    private String domain;

    @ApiModelProperty(notes = "When did report occur",name = "time",required = true,value = "20200909")
    private String time;

    public Report() {
    }
    public Report(String domain) {
        this.domain = domain;
    }

    //id ->mongodb自動產出
    @Id
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }

    @Field("createTime")
    public String getCreateTime() {
        return time;
    }
    //創建時自動建立當地時間
    public void setCreateTime() {
        this.time = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
    }


    //domainName
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

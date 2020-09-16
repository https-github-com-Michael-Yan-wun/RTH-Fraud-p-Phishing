package com.ntc.fraud.whois;

import java.io.BufferedReader;

import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


public class WhoisQueryMethod {
	public WhoisEntity performWhoisQuery(String host, int port, String query) throws Exception {
		System.out.println("**** Performing whois query for '" + query + "' at " + host + ":" + port);
		WhoisEntity entity = new WhoisEntity();
		Socket socket = new Socket(host, port);

		InputStreamReader isr = new InputStreamReader(socket.getInputStream());
		BufferedReader in = new BufferedReader(isr);

		PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
		out.println(query);
		
		
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		String strDate = formatter.format(new Date());
		
		String line = "";
		String domain = "";
		// create_date,updated_date,expiry_date 存一般登記的格式顯示日期
		String create_date = "";
		String updated_date = "";
		String expiry_date = "";
		// record_expires, record_created 用來存學校或公司行號的日期格式
		String record_expires = "";
		String record_created = "";
		boolean notExist=true;
		
		while ((line = in.readLine()) != null) {
			//System.out.println(line);
			if (line.indexOf("Domain Name") != -1) {
				entity.setDomainName(line.split(":")[1]);
				notExist=false;
				entity.setNotExist(notExist);
				System.out.println(line);// Domain Name
			}else {
				entity.setNotExist(notExist);
			}
			
			if (line.indexOf("Registry Expiry Date") != -1) {
				System.out.println(line);
				String[] s = line.split(":");
				expiry_date = s[1].substring(1, 11);
				entity.setRegistryExpiryDate(expiry_date);
				
			}
			if (line.indexOf("Creation Date") != -1) {
				System.out.println(line);
				String[] s = line.split(":");
				create_date = s[1].substring(1, 11);
				entity.setCreationDate(create_date);
				
			}
			if (line.indexOf("Updated Date") != -1) {
				System.out.println(line);
				String[] s = line.split(":");
				updated_date = s[1].substring(1, 11);
				entity.setUpdatedDate(updated_date);
				
			}
			if (line.indexOf("Record expires on") != -1) {
				System.out.println(line);
				String[] s = line.split(" ");
				record_expires = s[6].substring(0, 10);
				entity.setRecordEexpiresOn(record_expires);
				
			}
			if (line.indexOf("Record created on") != -1) {
				System.out.println(line);
				String[] s = line.split(" ");
				record_created = s[6].substring(0, 10);
				entity.setRecordCreatedOn(record_created);
		
			}
			
			
		}
		if (!expiry_date.equals("")) {
			
			long day1;
			try {
				day1 = transDate(strDate,expiry_date);
				entity.setNowToRegistryExpiryDate(day1);
				System.out.println("目前到登記到期日" + day1 + "天");
				
				if (day1 <= 30) {
					entity.setScoreExp("10");
				}
				else if(day1 <= 90){
					entity.setScoreExp("5");
				}else {
					entity.setScoreExp("0");
				}
			}catch(Exception e) {
				entity.setScoreExp("");
				System.out.println("格式不符");
			}
		}
			
		if (!updated_date.equals("")) {
			
			long day2;
			try {
				day2 = transDate(updated_date, strDate);
				entity.setUpdateToNow(day2);
				System.out.println("當天和更新日期差" + day2 + "天");
				if (day2 <= 30) {
					entity.setScoreUpd("10");
				}
				else if(day2 <= 60){
					entity.setScoreUpd("8");
				}else if(day2 <= 90){
					entity.setScoreUpd("5");
				}else if(day2 <= 120){
					entity.setScoreUpd("3");
				}else {
					entity.setScoreUpd("0");
				}
			}catch(Exception e) {
				entity.setScoreUpd("");
				System.out.println("格式不符");
			}
			

		}
		if (!create_date.equals("")) {
			
			long day3;
			try {
				day3 = transDate(create_date, strDate);
				entity.setCreationDateToNow(day3);
				System.out.println("當天和創建日期差" + day3 + "天");
				if (day3 <= 365) {
					entity.setScoreCre("10");
				}
				else if(day3 <= 730){
					entity.setScoreCre("5");
				}else {
					entity.setScoreCre("0");
				}
			}catch(Exception e) {
				entity.setScoreCre("");
				System.out.println("格式不符");
			}
			

		}
		
		return entity;
	}
	public static long transDate(String date1, String date2) throws ParseException {
		// 計算區間
		Date date_format1 = new SimpleDateFormat("yyyy-MM-dd").parse(date1);
		Date date_format2 = new SimpleDateFormat("yyyy-MM-dd").parse(date2);
		long days = (date_format1.getTime() - date_format2.getTime()) / (24 * 60 * 60 * 1000) > 0
				? (date_format1.getTime() - date_format2.getTime()) / (24 * 60 * 60 * 1000)
				: (date_format2.getTime() - date_format1.getTime()) / (24 * 60 * 60 * 1000);
		return days;
	}
	
}


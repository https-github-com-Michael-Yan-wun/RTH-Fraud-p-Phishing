package com.ntc.fraud.whois;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Optional;




public class whoIsQuery {
	static WhoisQueryMethod wqm = new WhoisQueryMethod();
	public String[] query(String splitemail) throws Exception {
		
		//找 whois-servers
		String hostname = getHostName(splitemail);
		WhoisEntity searchRes = new WhoisEntity();
		// 執行查詢
		try {
			System.out.println("查詢domain-> '" + splitemail + "' at " + hostname + ":" + 43);
			
			 searchRes = wqm.performWhoisQuery(hostname, 43, splitemail);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		//還沒決定要怎麼取分數
		//scores[] = registryDate(info);
		
		//domain, creation, update, expired
		String[] toPython = {splitemail, searchRes.getScoreCre(), searchRes.getScoreUpd(),searchRes.getScoreExp()};
		return toPython;
	}
				
	public static String getHostName(String splitemail) {
		WhoisPool wp = new WhoisPool();
		// 切/
		String[] line = splitemail.split("\\."); 
		String hostname = null;
		for (String sr : wp.list.keySet()) {
			if (line[line.length - 1].equals(sr)) {
				hostname = wp.list.get(sr);
				break;
			}
		}
		return hostname == null ? "whois.internic.net" : hostname;
	}	

}

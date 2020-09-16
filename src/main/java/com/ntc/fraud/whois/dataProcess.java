package com.ntc.fraud.whois;

import com.ntc.fraud.whois.IgnoreEmail;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class dataProcess {


	public static boolean checkIgnore(String domain) {
		boolean is_ignore = false;
		IgnoreEmail il = new IgnoreEmail();
		String[] line = domain.split("\\.");
		String igcheck = line[line.length - 2] + "." + line[line.length - 1];
		for (String i : il.list) {
			if (i.equals(igcheck)) {
				is_ignore = true;
				break;
			}
		}
		return is_ignore;
	}

	public String callPython(String email, String a, String b, String c) {

		try {

			String[] args1 = new String[]{"python", "D:\\predict.py", email, a, b, c};

			Process proc = Runtime.getRuntime().exec(args1);
			final InputStream is = proc.getInputStream();
			InputStreamReader isr = new InputStreamReader(is);
			BufferedReader br = new BufferedReader(isr);
			String result = br.readLine();

			proc.waitFor();
			br.close();
			proc.destroy();
			System.out.println(result);
			return result;

		} catch (IOException e) {

			e.printStackTrace();
			return null;

		} catch (InterruptedException ie) {

			ie.printStackTrace();
			return null;
		} catch (NullPointerException ne) {
			ne.printStackTrace();
			return null;
		}

	}
}

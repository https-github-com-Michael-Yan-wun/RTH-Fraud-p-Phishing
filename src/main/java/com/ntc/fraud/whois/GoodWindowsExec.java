package com.ntc.fraud.whois;

import java.io.FileOutputStream;
import java.io.InputStream;

public class GoodWindowsExec{
	GoodWindowsExec(){}
	 String GoodWindowsExec (String[] args,String a,String b,String c,String d){
	
	        if (args.length < 1)
	        {
	            System.out.println("USAGE java GoodWinRedirect <outputfile>");
	            System.exit(1);
	        }
	        
	        try
	        {            
	            FileOutputStream fos = new FileOutputStream(args[0]);
	            Runtime rt = Runtime.getRuntime();
	            Process proc = rt.exec("python D:\\predict.py "+a+" "+b+" "+c+" "+d);
	       //     Process proc = rt.exec("python C:\\Users\\windy\\eclipse-workspace-jee\\whois0905\\src\\prediction.py gmail.com 0 5 0");
	          //  Process proc = rt.exec("C:\\Users\\windy\\eclipse-workspace-jee\\whois0905\\src");
	       //     Process proc = rt.exec("python scripts/prediction.py gmail.com 0 5 0");
	            // any error message?
	            StreamGobbler errorGobbler = new 
	                StreamGobbler(proc.getErrorStream(), "ERROR");            
	            
	            // any output?
	            StreamGobbler outputGobbler = new 
	                StreamGobbler(proc.getInputStream(), "OUTPUT", fos);
	                
	            // kick them off
	            errorGobbler.start();
	            outputGobbler.start();
	                                    
	            // any error???
	            int exitVal = proc.waitFor();
	            System.out.println("ExitValue: " + exitVal);
	            fos.flush();
	            fos.close();        
	           // return proc.getInputStream();
	            String res = outputGobbler.getRes();
	            System.out.println(res);
	            return res;
	            
	        } catch (Throwable t)
	          {
	            t.printStackTrace();
	            return null;
	          }
	    }
}

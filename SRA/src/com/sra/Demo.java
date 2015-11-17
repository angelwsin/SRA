package com.sra;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Demo {
	  private static final String PRI_KEY ="MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBAKLXB6Y0NCe1PqrT0S1RjsxB8bLpIcLaA7AT4e4D/17seThRYhVJhMsJAO3Iy7FksLPAjH0nvyev+MI6vF1315yqdBMZpHJ9FZ5QhvMKMXoN0vac4lyks/D98eO5FY7HjmGCoIS1cBOyRCH8tc7WQRIwXkVQjLNCZTy0qdRX8UrVAgMBAAECgYAapV0XEUvHg5zxjx+XbtUTD2dhg0yG+e3IjjhWHklXWxKq6IZIPXOBVcPVJRpPuM5BW6yhfjOFZwIppjrUgJvDQBVVTkWq1YQKjkLloNEGWj8f4lCB5apszDuHRk/uLbyBgyxjrRAoCXn58nUJL7wybq3A0hEG/HUIMI/EsAQssQJBANr3bEplHmKwl3+BZY6AB62uPA+eJGmB/HgEpt81vrj7EqCope3eor6PinstXghPSpKki4q5gZza+OJOrV4/tcMCQQC+YYANJbYwAGHCqfvG+AgFTOC2UQKECzlmsdtQrcQU0D8ikT/a3EbNp1U6UvoAIzCtqtd5M1LvoyvpdwEq8buHAkAOngvNn5h6sB089s9nxuY8UvBRfpYhsPrArFbTecwEn0YsEXYaPg2RORXeiTXIyjpJNdDW7vFXHksVGHl8XDZvAkADV/UGxhXIDAmrgI7CdNK3PR1pNk0EAqp0akU+yn56GxowwjpL3riDnh+6EYEAhZ2yPyDfqQ4Y1VXNGOD1Xx97AkEAiwknpIoR8r1za8m7yBpLjgWkFbszOPE0gNuhLVyPq8SEgpheXZfFW0Ew5HV9evhCzpiQIteES31dXPDOJLmSpQ==";
	 public static void main(String[] args) {
		 System.out.println("---------------私钥签名过程------------------");  
		   Map<String,String> map = new HashMap<String, String>();
					map.put("userId", "owFQ-uBg7admIgc7wgcXzkiZa5Mk");
					map.put("bankNum", "0321");
	        String content= wxCheckSignature(map);  
	     
			/*
			 * 1. 将userid,bankNum三个参数进行字典序排序
			   2. 将两个参数字符串拼接成一个字符串进行签名
			 */
	        String signstr=RSASignature.sign(content,PRI_KEY);  
	        System.out.println("签名原串："+content);  
	        System.out.println("签名串："+signstr);  
	        System.out.println();  
	        
	        //链接要求参数  userId,bankNum,sign(签名)
	}
	 
	 @SuppressWarnings("unchecked")
		public static String   wxCheckSignature(Map<String,String> sortedParams){
			   StringBuffer content = new StringBuffer();
			    List keys = new ArrayList(sortedParams.keySet());
			    Collections.sort(keys);
			    int index = 0;
			    for (int i = 0; i < keys.size(); i++) {
			      String key = (String)keys.get(i);
			      String value = (String)sortedParams.get(key);
			        content.append((index == 0 ? "" : "&") + key + "=" + value);
			        index++;
			    }
			    return content.toString();
		      }

}

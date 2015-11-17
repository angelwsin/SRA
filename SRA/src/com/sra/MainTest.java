package com.sra;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainTest {
	public static void main(String[] args) throws Exception {  
        String filepath="d:/tmp/";  
  
       //RSAEncrypt.genKeyPair(filepath);  
          
          
        System.out.println("--------------公钥加密私钥解密过程-------------------");  
        String plainText="ihep_公钥加密私钥解密";  
        //公钥加密过程  
        byte[] cipherData=RSAEncrypt.encrypt(RSAEncrypt.loadPublicKeyByStr(RSAEncrypt.loadPublicKeyByFile(filepath)),plainText.getBytes());  
        String cipher=Base64.encode(cipherData);  
        //私钥解密过程  
        byte[] res=RSAEncrypt.decrypt(RSAEncrypt.loadPrivateKeyByStr(RSAEncrypt.loadPrivateKeyByFile(filepath)), Base64.decode(cipher));  
        String restr=new String(res);  
        System.out.println("原文："+plainText);  
        System.out.println("加密："+cipher);  
        System.out.println("解密："+restr);  
        System.out.println();  
        Map<String,String> map = new HashMap<String, String>();
		map.put("userId", "owFQ-uBg7admIgc7wgcXzkiZa5Mk");
		map.put("bankNum", "0321");
        System.out.println("--------------私钥加密公钥解密过程-------------------");  
        plainText=wxCheckSignature(map);  
        //私钥加密过程  
        cipherData=RSAEncrypt.encrypt(RSAEncrypt.loadPrivateKeyByStr(RSAEncrypt.loadPrivateKeyByFile(filepath)),plainText.getBytes());  
        cipher=Base64.encode(cipherData);  
        //公钥解密过程  
        res=RSAEncrypt.decrypt(RSAEncrypt.loadPublicKeyByStr(RSAEncrypt.loadPublicKeyByFile(filepath)), Base64.decode(cipher));  
        restr=new String(res);  
        System.out.println("原文："+plainText);  
        System.out.println("加密："+cipher);  
        System.out.println("解密："+restr);  
        System.out.println();  
          
        System.out.println("---------------私钥签名过程------------------");  
        String content="ihep_这是用于签名的原始数据";  
        
        String signstr=RSASignature.sign(wxCheckSignature(map),RSAEncrypt.loadPrivateKeyByFile(filepath));  
        System.out.println("签名原串："+content);  
        System.out.println("签名串："+signstr);  
        System.out.println();  
          
        System.out.println("---------------公钥校验签名------------------");  
        System.out.println("签名原串："+content);  
        System.out.println("签名串："+signstr);  
        //N YdEYPuuk2ef9WdjoiP9SpQpSnfs8u9MTvzBWib0cJqPo/60AZWeivzX8EElm6xLz/AVUgWGPLrspyWX8MAcrKWPHQh9pwquQWGyyPjSwIaFkbmE2Daitk5WI5ZBQdezq6Q17/E gvqaOB9HTpCcoIq8X eknJ0sKVW0vno6sE=
          //MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCi1wemNDQntT6q09EtUY7MQfGy6SHC2gOwE+HuA/9e7Hk4UWIVSYTLCQDtyMuxZLCzwIx9J78nr/jCOrxdd9ecqnQTGaRyfRWeUIbzCjF6DdL2nOJcpLPw/fHjuRWOx45hgqCEtXATskQh/LXO1kESMF5FUIyzQmU8tKnUV/FK1QIDAQAB
        System.out.println("验签结果："+RSASignature.doCheck(wxCheckSignature(map), signstr,RSAEncrypt.loadPublicKeyByFile(filepath)));  
        System.out.println();  
          
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

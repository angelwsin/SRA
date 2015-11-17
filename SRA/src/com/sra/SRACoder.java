package com.sra;

import java.security.Key;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.HashMap;
import java.util.Map;

public class SRACoder {
   public static final String RSA="RSA";
   public static final String MD5="MD5withRSA";
   private static final String PUBK="RSAPPUBK";
   private static final String PRIK="RSAPRIK";
   private  static final int KEY_SIZE =512;
   public static byte[] sign(byte[] data,byte[] priKey) throws Exception{
	   PKCS8EncodedKeySpec   pKCS8EncodedKeySpec =new PKCS8EncodedKeySpec(priKey);
	   KeyFactory keyFactory= KeyFactory.getInstance(RSA);
	   PrivateKey  privateKey=  keyFactory.generatePrivate(pKCS8EncodedKeySpec);
	   Signature signature= Signature.getInstance(MD5);
	   signature.initSign(privateKey);
	   signature.update(data);
	   return signature.sign();
   }
   
   public static boolean verify(byte[] data,byte[] pubKey,byte[] sign)throws Exception{
	     X509EncodedKeySpec keySpec = new X509EncodedKeySpec(pubKey);
	     KeyFactory keyFactory= KeyFactory.getInstance(RSA);
	     PublicKey  pKey =   keyFactory.generatePublic(keySpec);
	     Signature signature= Signature.getInstance(MD5);
	     signature.initVerify(pKey);
	     signature.update(data);
	     return signature.verify(sign);
   }
   
   public static byte[] getPriKey(Map<String,Object> keyMap)throws Exception{
	    Key  key =  (Key) keyMap.get(PRIK);
	    return key.getEncoded();
	   
   }
   public static byte[] getPubKey(Map<String,Object> keyMap)throws Exception{
	    Key  key =  (Key) keyMap.get(PUBK);
	    return key.getEncoded();
	   
  }
   
   public static Map<String,Object> initKey()throws Exception{
	   KeyPairGenerator keyPairGenerator =   KeyPairGenerator.getInstance(RSA);
	   keyPairGenerator.initialize(KEY_SIZE);
	   KeyPair pair =  keyPairGenerator.generateKeyPair();
	    RSAPublicKey rSAPublicKey=  (RSAPublicKey) pair.getPublic();
	    RSAPrivateKey rSAPrivateKey =  (RSAPrivateKey) pair.getPrivate();
	    Map<String,Object> keyMap = new HashMap<String,Object>();
	    System.out.println(Base64.encode(rSAPublicKey.getEncoded()));
	    keyMap.put(PRIK, rSAPrivateKey);
	    keyMap.put(PUBK, rSAPublicKey);
	    return keyMap;
	   
   }
   
   public static void main(String[] args) throws Exception{
	   Map<String,Object> keyMap = initKey();
	   String input = "0321owFQ-uBg7admIgc7wgcXzkiZa5Mk";
	   byte[] data = input.getBytes();
	  byte[]  sign =  sign(data,getPriKey(keyMap));
	  System.out.println(Base64.encode(sign));
	 System.out.println(verify(data, getPubKey(keyMap), sign));
}
}

package com.frmichetti.carhollicsandroid.security;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class PassGenerator {
	
	public static byte[] gerarHash(String frase, String algoritmo) {
		  try {

		    MessageDigest md = MessageDigest.getInstance(algoritmo);

		    md.update(frase.getBytes());

		    return md.digest();

		  } catch (NoSuchAlgorithmException e) {

		    return null;
		  }
		}

}

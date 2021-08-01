package com.oasis.util.security;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.math.BigInteger;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.MessageDigest;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.HashMap;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Value;

import com.oasis.util.StringUtil;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SecureUtil {

	public static String AES = "AES";
	public static String AES256 = "AES256";
	public static String RSA = "RSA";
	public static String SHA = "SHA";

	static SecureUtil sutil = new SecureUtil();
	HashMap secures = new HashMap();
	ISecure defSecure = new DefaultSecure();

	private SecureUtil() {
		secures.put(SecureUtil.AES, new AES());
		secures.put(SecureUtil.AES256, new AES256());
		secures.put(SecureUtil.RSA, new RSA());
		secures.put(SecureUtil.SHA, new SHA());
	}

	public static SecureUtil getInstance() {
		return sutil;
	}

	public ISecure getSecure(String cls) {
		ISecure sec = null;
		try {
			sec = (ISecure) secures.get(cls);
			if (sec == null) {
				sec = (ISecure) Class.forName(cls).newInstance();
				secures.put(cls, sec);
			}
		} catch (Exception e) {
			log.error("", e);
		}

		if (sec == null)
			return defSecure;
		return sec;
	}
	
	@Value("${AES256.secretKey}") private String aes256_secretKey;
	@Value("${SHA.secretKey}") private String sha_secretKey;
	@Value("${AES.secretKey}") private String aes_secretKey;

	public class SHA implements ISecure {
		//String ALGO = "SHA-256";

		public String getSecretKey() {
			return sha_secretKey;
		}

		public String encrypt(String str) {
			if (!StringUtil.valid(str))
				return "";

			MessageDigest md;
			String enc = "";
			try {
				md = MessageDigest.getInstance(getSecretKey());

				md.update(str.getBytes());
				byte[] mb = md.digest();
				enc = byte2hex(mb);
				return enc;
			} catch (Exception e) {
				log.error("SECURE-201911", e);
			}
			return str;
		}

		// 무의미 함.
		public String encrypt(String str, String key) {
			if (!StringUtil.valid(str))
				return "";

			MessageDigest md;
			String enc = "";
			try {
				md = MessageDigest.getInstance(key);

				md.update(str.getBytes());
				byte[] mb = md.digest();
				enc = byte2hex(mb);
				return enc;
			} catch (Exception e) {
				log.error("SECURE-201911", e);
			}
			return str;
		}

		public String decrypt(String str) {
			if (!StringUtil.valid(str))
				return "";
			return str;
		}

		public String decrypt(String str, String key) {
			if (!StringUtil.valid(str))
				return "";
			return str;
		}

		public boolean isOneWay() {
			return true;
		}
	}

	public class AES implements ISecure {
//		public byte[] key = "0987654321098765".getBytes();

		protected SecretKeySpec getSecretKeySpec() throws Exception {
			/*
			 * try{
			 *
			 * KeyGenerator kgen = KeyGenerator.getInstance("AES"); kgen.init(128);
			 * SecretKey skey = kgen.generateKey(); byte[] raw = skey.getEncoded();
			 * SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES"); return skeySpec;
			 * }catch(Exception e){}
			 */
			String key = aes_secretKey;
			return new SecretKeySpec(key.getBytes(), "AES");
		}

		/**
		 * AES 방식의 암호화
		 *
		 * @param str
		 * @return
		 * @throws Exception
		 */
		public String encrypt(String str) {
			if (!StringUtil.valid(str))
				return "";
			try {
				SecretKeySpec skeySpec = getSecretKeySpec();
				Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5PADDING");
				cipher.init(Cipher.ENCRYPT_MODE, skeySpec);

				byte[] encrypted = cipher.doFinal(str.getBytes());
				String s = byte2hex(encrypted);
				return s.toUpperCase();
			} catch (Exception e) {
				log.error("SECURE-201911", e);
			}
			return str;
		}

		public String encrypt(String str, String key) {
			if (!StringUtil.valid(str))
				return "";
			try {
				SecretKeySpec skeySpec = new SecretKeySpec(key.getBytes(), "AES");
				Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5PADDING");
				cipher.init(Cipher.ENCRYPT_MODE, skeySpec);

				byte[] encrypted = cipher.doFinal(str.getBytes());
				String s = byte2hex(encrypted);
				return s.toUpperCase();
			} catch (Exception e) {
				log.error("SECURE-201911", e);
			}
			return str;
		}

		/**
		 * AES 방식의 복호화
		 *
		 * @param str
		 * @return
		 * @throws Exception
		 */
		public String decrypt(String str) {
			if (!StringUtil.valid(str))
				return "";
			try {
				str = str.toLowerCase();

				SecretKeySpec skeySpec = getSecretKeySpec();
				Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5PADDING");
				cipher.init(Cipher.DECRYPT_MODE, skeySpec);
				byte[] original = cipher.doFinal(hex2byte(str));
				String originalString = new String(original);
				return originalString;
			} catch (Exception e) {
				log.error("SECURE-201911 decrypt value=" + str, e);
			}
			return str;
		}

		public String decrypt(String str, String key) {
			if (!StringUtil.valid(str))
				return "";
			try {
				str = str.toLowerCase();

				SecretKeySpec skeySpec = new SecretKeySpec(key.getBytes(), "AES");
				Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5PADDING");
				cipher.init(Cipher.DECRYPT_MODE, skeySpec);
				byte[] original = cipher.doFinal(hex2byte(str));
				String originalString = new String(original);
				return originalString;
			} catch (Exception e) {
				log.error("SECURE-201911 decrypt str=" + str +"/key=" + key, e);
			}
			return str;
		}

		public boolean isOneWay() {
			return false;
		}

	}

	public class RSA implements ISecure {

		int KEY_SIZE = 1024;
		private PublicKey publicKey;
		private PrivateKey privateKey;

		private String pubfile = "PublicKey.ser";
		private String prifile = "PrivateKey.ser";

		RSA() {
			loadKey();
		}

		void loadKey() {
			try {
				File file = new File("/", "WEB-INF/rsa/" + pubfile);

				if (!file.exists()) {
					makeKey();
				}
				ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file));
				publicKey = (PublicKey) ois.readObject();
				ois.close();

				file = new File("/", "WEB-INF/rsa/" + prifile);

				ois = new ObjectInputStream(new FileInputStream(file));
				privateKey = (PrivateKey) ois.readObject();
				ois.close();

			} catch (Exception e) {
				log.error("RSA Key load failed.", e);
			}

		}

		void makeKey() {
			try {
				File dir = new File("/", "WEB-INF/rsa/");
				if (!dir.exists())
					dir.mkdirs();

				KeyPairGenerator kpg = KeyPairGenerator.getInstance("RSA");
				kpg.initialize(KEY_SIZE);
				KeyPair kp = kpg.genKeyPair();
				PrivateKey privateKey = kp.getPrivate();
				PublicKey publicKey = kp.getPublic();
				File file = new File(dir, prifile);

				ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(file));
				out.writeObject(privateKey);
				out.close();

				log.info("RAS PrivateKey created:" + file);
				file = new File(dir, pubfile);
				if (file.exists()) {
					file.delete();
				}

				out = new ObjectOutputStream(new FileOutputStream(file));
				out.writeObject(publicKey);
				out.close();

				log.info("RAS publicKeycreated:" + file);
			} catch (Exception e) {
				log.error("SECURE-201911", e);
			}

		}

		/*
		 * static RSAPublicKeySpec getPublicSpec() throws Exception{ KeyPairGenerator
		 * generator = KeyPairGenerator.getInstance("RSA");
		 * generator.initialize(KEY_SIZE);
		 *
		 * KeyPair keyPair = generator.genKeyPair(); KeyFactory keyFactory =
		 * KeyFactory.getInstance("RSA");
		 *
		 * publicKey = keyPair.getPublic(); privateKey = keyPair.getPrivate();
		 * //session.setAttribute("__rsaPrivateKey__", privateKey); RSAPublicKeySpec
		 * publicSpec = (RSAPublicKeySpec) keyFactory.getKeySpec(publicKey,
		 * RSAPublicKeySpec.class);
		 *
		 * return publicSpec; }
		 */

		public String encrypt(String s) {
			if (!StringUtil.valid(s))
				return "";
			if (publicKey == null) {
				log.error("WEB-INF/rsa/" + pubfile + " not found.");
			}
			try {

				Cipher rsaCipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
				rsaCipher.init(Cipher.ENCRYPT_MODE, publicKey);

				// the log plainText encrypted by public key
				byte[] encrypted = rsaCipher.doFinal(s.getBytes());

				String str = byte2hex(encrypted);
				return str.toUpperCase();
			} catch (Exception e) {
				log.error("SECURE-201911", e);
			}
			return s;
		}

		public String encrypt(String s, String key) {

			return s;
		}

		public String decrypt(String s) {
			if (!StringUtil.valid(s))
				return "";
			if (privateKey == null) {
				log.error("WEB-INF/rsa/" + privateKey + " not found.");
			}

			try {
				Cipher cipher = Cipher.getInstance("RSA");
				byte[] encryptedBytes = hex2byte(s);
				cipher.init(Cipher.DECRYPT_MODE, privateKey);
				byte[] decryptedBytes = cipher.doFinal(encryptedBytes);
				// String decryptedValue = new String(decryptedBytes, "utf-8");
				String decryptedValue = new String(decryptedBytes);
				return decryptedValue;
			} catch (Exception e) {
				log.error("SECURE-201911", e);
			}
			return s;
		}

		public String decrypt(String s, String key) {
			return s;
		}

		/*
		 * private byte[] hex2byte(String hex) { if (hex == null || hex.length() % 2 !=
		 * 0) { return new byte[]{}; }
		 *
		 * byte[] bytes = new byte[hex.length() / 2]; for (int i = 0; i < hex.length();
		 * i += 2) { byte value = (byte)Integer.parseInt(hex.substring(i, i + 2), 16);
		 * bytes[(int) Math.floor(i / 2)] = value; } return bytes; }
		 */

		public byte[] hexToByteArrayBI(String hexString) {
			return new BigInteger(hexString, 16).toByteArray();
		}

		public boolean isOneWay() {
			return false;
		}
	}

	public class AES256 implements ISecure {

		//final String secretKey = "M0bisSecurity12#M0bisSecurity12#"; // 32bit
		final byte[] IV = { 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,
				0x00 }; // 16bit

		public String getSecretKey() {
			return aes256_secretKey;
		}
		// 암호화
		public String encrypt(String str) {

			try {
				byte[] keyData = getSecretKey().getBytes();

				SecretKey secureKey = new SecretKeySpec(keyData, "AES");

				Cipher c = Cipher.getInstance("AES/CBC/PKCS5Padding");
				c.init(Cipher.ENCRYPT_MODE, secureKey, new IvParameterSpec(IV));

				byte[] encrypted = c.doFinal(str.getBytes("UTF-8"));
				return new String(Base64.encodeBase64(encrypted));
			} catch (Exception e) {
				log.error("SECURE-201911 encrypt str=" + str, e);
			}
			return str;
		}

		public String encrypt(String str, String key) {

			try {
				byte[] keyData = key.getBytes();

				SecretKey secureKey = new SecretKeySpec(keyData, "AES");

				Cipher c = Cipher.getInstance("AES/CBC/PKCS5Padding");
				c.init(Cipher.ENCRYPT_MODE, secureKey, new IvParameterSpec(IV));

				byte[] encrypted = c.doFinal(str.getBytes("UTF-8"));
				return new String(Base64.encodeBase64(encrypted));
			} catch (Exception e) {
				log.error("SECURE-201911 encrypt str=" + str + "/ key=" + key, e);
			}
			return str;
		}

		// 복호화
		public String decrypt(String str) {
			try {
				byte[] keyData = getSecretKey().getBytes();
				SecretKey secureKey = new SecretKeySpec(keyData, "AES");
				Cipher c = Cipher.getInstance("AES/CBC/PKCS5Padding");
				c.init(Cipher.DECRYPT_MODE, secureKey, new IvParameterSpec(IV));
				// c.init(Cipher.DECRYPT_MODE, secureKey);
				byte[] byteStr = Base64.decodeBase64(str.getBytes());
				return new String(c.doFinal(byteStr), "UTF-8");
			} catch (Exception e) {
				log.error("SECURE-201911", e);
			}
			return str;
		}

		/**
		 * 복호화
		 * @param str
		 * @param key
		 * @return
		 */
		public String decrypt(String str, String key) {

			try {
				byte[] keyData = key.getBytes();
				SecretKey secureKey = new SecretKeySpec(keyData, "AES");
				Cipher c = Cipher.getInstance("AES/CBC/PKCS5Padding");
				c.init(Cipher.DECRYPT_MODE, secureKey, new IvParameterSpec(IV));
				// c.init(Cipher.DECRYPT_MODE, secureKey);
				byte[] byteStr = Base64.decodeBase64(str.getBytes());
				return  new String(c.doFinal(byteStr), "UTF-8");
			} catch (Exception e) {
				log.error("SECURE-201911", e);
			}
			return str;
		}

		public boolean isOneWay() {
			return false;
		}

	}

	/**
	 * byte[] to hex : unsigned byte(바이트) 배열을 16진수 문자열로 바꾼다.
	 *
	 * @param ba
	 *            byte[]
	 * @return
	 */
	public String byte2hex(byte[] ba) {

		if (ba == null || ba.length == 0) {
			return null;
		}

		StringBuffer sb = new StringBuffer(ba.length * 2);
		String hexNumber;
		for (int x = 0; x < ba.length; x++) {
			hexNumber = "0" + Integer.toHexString(0xff & ba[x]);

			sb.append(hexNumber.substring(hexNumber.length() - 2));
		}
		return sb.toString();

	}

	public byte[] hex2byte(String hex) {
		if (hex == null || hex.length() == 0) {
			return null;
		}

		byte[] ba = new byte[hex.length() / 2];
		for (int i = 0; i < ba.length; i++) {
			ba[i] = (byte) Integer.parseInt(hex.substring(2 * i, 2 * i + 2), 16);
		}
		return ba;
	}

	public HashMap getSecures() {
		return secures;
	}
/*

	public static void main(String args[]) {

		SecureUtil secureUtil = SecureUtil.getInstance();
		ISecure iSecure = secureUtil.getSecure("AES256");

		String target = "";
		String source = "ZLgCWn/dsNhOepXFrnugoce0vQ2tYeNNtVY8AmMC2Os=";

		//source = iSecure.encrypt(target);


		String tmp = iSecure.decrypt(source, "M0bisSecurity12#M0bisSecurity12#");
		System.out.println(target +"/" + source + "/" + tmp);

		String key = "lightning12#lightning12#";
		String val = iSecure.encrypt(tmp, key);
		System.out.println(val +"/" + iSecure.decrypt(val, key));
	}
*/

}

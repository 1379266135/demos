package com.niu.demos.preferences;

import java.security.InvalidKeyException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;

import android.util.Base64;

public class EncrypDES {
	private SecretKey deskey;
	private Cipher c;
	private String cipherStr;
	private static EncrypDES instance = null;

	public static EncrypDES getInstance() {
		if (instance == null) { // line 12
			try {
				instance = new EncrypDES();
			} catch (Exception e) {
				e.printStackTrace();
			}

		}
		return instance;
	}

	public EncrypDES() throws Exception {
		String sKey = "%^#TDGhs";
		DESKeySpec desKeySpec = new DESKeySpec(sKey.getBytes());
		SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
		deskey = keyFactory.generateSecret(desKeySpec);
		// 生成Cipher对象,指定其支持的DES算法
		c = Cipher.getInstance("DES");
	}

	/**
	 * 对字符串加密
	 * 
	 * @param str
	 * @return
	 * @throws InvalidKeyException
	 * @throws IllegalBlockSizeException
	 * @throws BadPaddingException
	 */
	public String Encrytor(String str) throws InvalidKeyException,
			IllegalBlockSizeException, BadPaddingException {

		if (SmsFilterLog.DEBUG) {
			SmsFilterLog.debugLog("json str:" + str);
		}

		// 根据密钥，对Cipher对象进行初始化，ENCRYPT_MODE表示加密模式
		c.init(Cipher.ENCRYPT_MODE, deskey);
		byte[] src = str.getBytes();
		// 加密，结果保存进cipherByte
		byte[] cipherByte = c.doFinal(src);
		cipherStr = Base64.encodeToString(cipherByte, Base64.DEFAULT);
		return cipherStr;
	}

	/**
	 * 对字符串解密
	 * 
	 * @param buff
	 * @return
	 * @throws InvalidKeyException
	 * @throws IllegalBlockSizeException
	 * @throws BadPaddingException
	 */
	public String Decryptor(String str) throws InvalidKeyException,
			IllegalBlockSizeException, BadPaddingException {
		// 根据密钥，对Cipher对象进行初始化，DECRYPT_MODE表示加密模式
		c.init(Cipher.DECRYPT_MODE, deskey);
		byte[] buff = Base64.decode(str, Base64.DEFAULT);
		byte[] cipherByte = c.doFinal(buff);
		cipherStr = new String(cipherByte);
		return cipherStr;
	}

}

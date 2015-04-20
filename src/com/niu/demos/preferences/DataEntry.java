package com.niu.demos.preferences;

import java.security.InvalidKeyException;
import java.util.UUID;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;

public class DataEntry {

	public static String urlJiaMi(String input, String urlstr) {
		UUID uid = UUID.randomUUID();
		String key = uid.toString();
		String sign = input + key + "Sdk&^12321";
		sign = sign.replaceAll("\n", "");
		sign = SettingUtil.getMD5Str(sign).toLowerCase().replace("\r", "").replace("\n", "");
		urlstr = urlstr +"key=" + key + "&sign=" + sign ;
		SmsFilterLog.debugLog("Http Header data=   " + urlstr);
		return urlstr;
	}

	public String dataJiaMi(String input,boolean isformat) {
		try {
			String res = EncrypDES.getInstance().Encrytor(input);
			if (isformat) {
				res = res.replaceAll("\n", "");
			}
			return res;
		} catch (InvalidKeyException e) {
			e.printStackTrace();
		} catch (IllegalBlockSizeException e) {
			e.printStackTrace();
		} catch (BadPaddingException e) {
			e.printStackTrace();
		}
		return "";
	}

	public String dataJieMi(String input) {
		try {
			return EncrypDES.getInstance().Encrytor(input);
		} catch (InvalidKeyException e) {
			e.printStackTrace();
		} catch (IllegalBlockSizeException e) {
			e.printStackTrace();
		} catch (BadPaddingException e) {
			e.printStackTrace();
		}
		return "";
	}
}

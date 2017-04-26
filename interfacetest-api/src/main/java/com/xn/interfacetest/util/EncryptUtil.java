package com.xn.interfacetest.util;



import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import org.apache.commons.codec.binary.Base64;


/**
 * 登录密码相关加密类
 * @author 颜彩云
 *
 */
public final class EncryptUtil {

	private static final Charset CHARSET = Charset.forName("UTF-8");

	private static final String SIGN_KEY = "g1u2x3u4n5c6h7e8n9g";//禁止修改
	
	public static String SHA1ToBase64(String decript) {
		try {
			MessageDigest digest = MessageDigest.getInstance("SHA-1");
			digest.update(decript.getBytes());
			byte messageDigest[] = digest.digest();
			return Base64.encodeBase64String(messageDigest);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return "";
	}
	
	public static String specialMd5(String decript){
		//请谨慎使用该方法，对应php代码为 MD5(MD5('g1u2x3u4n5c6h7e8n9g').substr(MD5('a123456'),5,20));
		StringBuffer sb = new StringBuffer();
		sb.append(md5(SIGN_KEY));
		sb.append(md5(decript).substring(5,25));
		return md5(sb.toString());
	}
	
	/**
	 * 
	 * @param str
	 * @return
	 */
	private static String md5(String str) {
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			md.update(str.getBytes(CHARSET));
			byte b[] = md.digest();

			int i;

			StringBuffer buf = new StringBuffer("");
			for (int offset = 0; offset < b.length; offset++) {
				i = b[offset];
				if (i < 0)
					i += 256;
				if (i < 16)
					buf.append("0");
				buf.append(Integer.toHexString(i));
			}
			str = buf.toString();
		} catch (Exception e) {
			e.printStackTrace();

		}
		return str;
	}
	
	public static void main(String[] args) {
		System.out.println(specialMd5("123456"));
	}
}

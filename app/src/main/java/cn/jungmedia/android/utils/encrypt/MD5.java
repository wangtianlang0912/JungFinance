package com.jung.android.utils.encrypt;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.MessageDigest;

public class MD5 {
	private static String toHex(byte b) {
	    final String hexChar = "0123456789ABCDEF";
	    StringBuilder sb = new StringBuilder();
	    sb.append(hexChar.charAt(0xf & b >> 4));
	    sb.append(hexChar.charAt(b & 0xf));
	    return sb.toString();
	}
	
	private static String convert_To_HexString(byte[] b){
		String hex = "";
		for (byte element : b) { 
			hex += toHex(element);	     
		}
		return hex;
	} 
	
	public static String EncoderByMD5(String str){
		String code = str;
		if(str != null){
			try {
				 MessageDigest md5 = MessageDigest.getInstance("MD5");
				 code = convert_To_HexString(md5.digest(str.getBytes("utf-8")));
			} catch (Exception e) {
//				e.printStackTrace();
			}
		}
		return code;
	}

	public final static String get32MD5(String s) {
		char hexDigits[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };
		try {
			byte[] strTemp = s.getBytes();
//使用MD5创建MessageDigest对象
			MessageDigest mdTemp = MessageDigest.getInstance("MD5");
			mdTemp.update(strTemp);
			byte[] md = mdTemp.digest();
			int j = md.length;
			char str[] = new char[j * 2];
			int k = 0;
			for (int i = 0; i < j; i++) {
				byte b = md[i];
//System.out.println((int)b);
//将没个数(int)b进行双字节加密
				str[k++] = hexDigits[b >> 4 & 0xf];
				str[k++] = hexDigits[b & 0xf];
			}
			return new String(str).toLowerCase();
		} catch (Exception e) {
			return null;
		}
	}


	public static String EncoderByMD5(byte[] data){
		String code = "";
		try {
			 MessageDigest md5 = MessageDigest.getInstance("MD5");
			 code = convert_To_HexString(md5.digest(data));
		} catch (Exception e) {
//			e.printStackTrace();
		}
		return code;
	}
	
	public static byte[] getByteEncoder(byte[] data){
		try {
			 MessageDigest md5 = MessageDigest.getInstance("MD5");
			 return md5.digest(data);
		} catch (Exception e) {
//			e.printStackTrace();
		}
		return null;
	}
	
	// 返回一个byte数组

	public static byte[] getBytesFromFile(File file) throws IOException {

		InputStream is = new FileInputStream(file);

		// 获取文件大小

		long length = file.length();

		if (length > Integer.MAX_VALUE) {

			// 文件太大，无法读取
			if(is != null){
				is.close();
			}
			throw new IOException("File is to large " + file.getName());
		}

		// 创建一个数据来保存文件数据

		byte[] bytes = new byte[(int) length];

		// 读取数据到byte数组中

		int offset = 0;

		int numRead = 0;

		while (offset < bytes.length

		&& (numRead = is.read(bytes, offset, bytes.length - offset)) >= 0) {

			offset += numRead;

		}

		// 确保所有数据均被读取

		if (offset < bytes.length) {
			if(is != null){
				is.close();
			}
			throw new IOException("Could not completely read file " + file.getName());
		}

		// Close the input stream and return bytes

		is.close();

		return bytes;

	}
}

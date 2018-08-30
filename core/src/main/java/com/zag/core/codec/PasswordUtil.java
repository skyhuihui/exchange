package com.zag.core.codec;

import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;
import org.apache.commons.lang.StringUtils;

/**
 * 搬运
 * @author stone
 * @since 2017年8月4日
 * @usage 
 * @reviewer
 */
public class PasswordUtil {

	public static final String AES_KEY = "626ededc9c3a47a2aec9eb3e9c34b464";

	public static String aes2Original(String aesStr) {
		try {
			AesCryptographer aes = new AesCryptographer();
			byte[] b = aes.decrypt(
					Hex.decodeHex(aesStr.toCharArray()), StringUtils.left(AES_KEY, 16), StringUtils.right(AES_KEY, 16));
			return new String(b);
		} catch (Exception e) {
			return aesStr;
		}
	}

	public static String original2Aes(String original) {
		AesCryptographer aes = new AesCryptographer();
		byte[] b = aes.encrypt(original, StringUtils.left(AES_KEY, 16), StringUtils.right(AES_KEY, 16));
		return Hex.encodeHexString(b);
	}

	public static void main(String[] args) throws DecoderException {
		String o = "111111";
		String aes = original2Aes(o);
		System.out.println(aes);

		System.out.println(aes2Original(aes));
		//
		// AesCryptographer aes = new AesCryptographer();
		// byte[] b = aes.encrypt("111111", StringUtils.left(IUser.AES_KEY, 16),
		// StringUtils.right(IUser.AES_KEY, 16));
		// String originalPassword = Hex.encodeHexString(b);
		// System.out.println(originalPassword);
		//
		//
		//// 5850c89f7cd7f15c3617a023195e361a
		////
		////
		////
		// byte[] b1 =
		// aes.decrypt(Hex.decodeHex(originalPassword.toCharArray()),
		// StringUtils.left(IUser.AES_KEY, 16), StringUtils.right(IUser.AES_KEY,
		// 16));
		////
		// System.out.println(new String(b1));
	}

}

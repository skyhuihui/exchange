package com.zag.core.codec;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;

/**
 * AES/CBC/PKCS5Padding 加密解密
 * Created by stone on 11/19/15.
 */
public class AesCryptographer {

	/**
	 * 使用AES 算法 加密，默认模式 AES/CBC/PKCS5Padding
	 */
	public byte[] encrypt(byte[] content, String password, String ivParam) throws AesEncryptException {

		try {
			Cipher cipher = getCipher();
			cipher.init(Cipher.ENCRYPT_MODE, getAesSecretKeySpec(password), new IvParameterSpec(ivParam.getBytes(ENCODING)));
			return cipher.doFinal(content);
		} catch (Exception e) {
			throw new AesEncryptException(e);
		}
	}


	/**
	 * 使用AES 算法 加密，默认模式 AES/CBC/PKCS5Padding
	 */
	public byte[] encrypt(String content, String password, String ivParam) throws AesEncryptException {

		try {
			return encrypt(content.getBytes(ENCODING), password, ivParam);
		} catch (Exception e) {
			throw new AesEncryptException(e);
		}
	}

	public byte[] decrypt(byte[] encryptedContent, String password, String ivParam) throws AesDecryptException {
		try {
			Cipher cipher = getCipher();
			cipher.init(Cipher.DECRYPT_MODE, getAesSecretKeySpec(password), new IvParameterSpec(ivParam.getBytes(ENCODING)));
			return cipher.doFinal(encryptedContent);
		} catch (Exception e) {
			throw new AesDecryptException(e);
		}
	}

	private Cipher getCipher() {
		if (cipherThreadLocal.get() == null) {
			try {
				cipherThreadLocal.set(Cipher.getInstance(CIPHER_ALGORITHM_CBC));
			} catch (Exception e) {
				throw new AesEncryptException(e);
			}
		}
		return cipherThreadLocal.get();
	}

	/*
	 * AES/CBC/NoPadding 要求
	 * 密钥必须是16位的；Initialization vector (IV) 必须是16位
	 * 待加密内容的长度必须是16的倍数，如果不是16的倍数，就会出如下异常：
	 * javax.crypto.IllegalBlockSizeException: Input length not multiple of 16 bytes
	 *
	 *  由于固定了位数，所以对于被加密数据有中文的, 加、解密不完整
	 *
	 *  可 以看到，在原始数据长度为16的整数n倍时，假如原始数据长度等于16*n，则使用NoPadding时加密后数据长度等于16*n，
	 *  其它情况下加密数据长 度等于16*(n+1)。在不足16的整数倍的情况下，假如原始数据长度等于16*n+m[其中m小于16]，
	 *  除了NoPadding填充之外的任何方 式，加密数据长度都等于16*(n+1).
	 */
	private SecretKeySpec getAesSecretKeySpec(String password) throws UnsupportedEncodingException {
		return new SecretKeySpec(password.getBytes(ENCODING), KEY_ALGORITHM);
	}

	public static final String ENCODING = "UTF-8";

	private static final String KEY_ALGORITHM = "AES";

	private static final String CIPHER_ALGORITHM_CBC = "AES/CBC/PKCS5Padding";

	private static ThreadLocal<Cipher> cipherThreadLocal = new ThreadLocal<Cipher>();

}

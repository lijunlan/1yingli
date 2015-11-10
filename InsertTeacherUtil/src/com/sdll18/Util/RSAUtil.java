package com.sdll18.Util;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.math.BigInteger;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.RSAPrivateKeySpec;
import java.security.spec.RSAPublicKeySpec;

import javax.crypto.Cipher;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang.StringUtils;

/**
 * RSA 工具类。提供加密，解密，生成密钥对等方法。 需要bcprov-jdk16-140.jar包。
 * 
 */
public class RSAUtil {

	// public static final String RSAKEY_BASE_PATH =
	// "C:/Users/Administrator/Desktop/RSAKey/";
	public static String RSAKEY_BASE_PATH = "/Users/SDLL18/Desktop/RSAKey/";
	private static final String RSAKeyStore = "RSAKey.txt";

	public static final BigInteger PUBLIC_KEY = new BigInteger(
			"96451063016805033800027127320160190544690107957964520623086619603955990622402316130867465294252478727081946620172982016648488680613406746054486058723216807354746332938444830709086411155360527093548752118353000925065291770523174122814749704526780513401459508977800145859899813360807295192762712334611950064457");
	
	// public static final String PUBLIC_KEY =
	// "8959d2ced61bee338accd16794538ec0a49da0655ddca8fa2461d4cf419dafaf4d7c47813f6ac8c6e5646a2beb08cccf4184a831e683a631e3c528b908deecc57235d03935d0650fbe53d44f717da7f5d1622e7405a3b4f06377eb506880dae21e5065c878c03d85113e068ac82af6b29037d57163d9a311807bee654927d349";
	// public static final String PUBLIC_KEY =
	// "9b12eab350d5560dc5f729b8b985a641d94f13006d246117d5a0586b7f3064e512baf409bedc7006a6c11acad2417dca4fc07ccf2f4c46b3cd4643c89955523fe6d7cddcec88ab90e3f1d35a58d0e33024ab17987eedb781b8ad86231d900ddccf38541e4be24ac0930e060785b86c0da680bf22728e7fba65fa0f9225d8c829";

	public static void main(String[] args) {
		// createPublicKey();
		// try {
		// generateKeyPair("C:/Users/Administrator/Desktop/RSAKey/");
		// } catch (Exception e) {
		// e.printStackTrace();
		// }
		//System.out.println(Base64.encodeBase64String(PUBLIC_KEY.toByteArray()));
		//System.out.println(new String(PUBLIC_KEY.toByteArray()));
		String ba = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDEChqe80lJLTTkJD3X3Lyd7Fj+zuOhDZkjuLNPog3YR20e5JcrdqI9IFzNbACY/GQVhbnbvBqYgyql8DfPCGXpn0+XNSxELIUw9Vh32QuhGNr3/TBpechrVeVpFPLwyaYNEk1CawgHCeQqf5uaqiaoBDOTqeox88Lc1ld7MsfggQIDAQAB";
		byte[] te = Base64.decodeBase64(ba);
		System.out.println(te.length);
		System.out.println(PUBLIC_KEY.toByteArray().length);
		//byte[] b = Base64.decodeBase64("MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQChDzcjw/rWgFwnxunbKp7/4e8w/UmXx2jk6qEEn69t6N2R1i/LmcyDT1xr/T2AHGOiXNQ5V8W4iCaaeNawi7aJaRhtVx1uOH/2U378fscEESEG8XDqll0GCfB1/TjKI2aitVSzXOtRs8kYgGU78f7VmDNgXIlk3gdhnzh+uoEQywIDAQAB");
		//System.out.println(new BigInteger(b).toString(16));
		//PublicKey publicKey = null;
//		try {
//			publicKey = RSAUtil.generateRSAPublicKey(tt.toByteArray(),
//					new BigInteger("65537").toByteArray());
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		System.out.println(publicKey);
		// PUBLIC_KEY.toByteArray();
		try {
			// KeyPair keyPair = getKeyPair(RSAKEY_BASE_PATH);
			// // byte[] encode = encrypt(keyPair.getPublic(),
			// // "1234567890".getBytes());
			// // String pwd = bytesToHexString(encode);
			// String pwd = encryptStr("ff");
			// System.out.println(pwd);
			// String decode = decryptStr(pwd, RSAKEY_BASE_PATH);
			// System.out.println(decode);
			// System.out.println(((RSAPublicKey) keyPair.getPublic())
			// .getModulus());
			// System.out.println(((RSAPublicKey) keyPair.getPublic())
			// .getPublicExponent());
			// System.out.println("public key:\t" + keyPair.getPublic());
			// System.out.println("private key:\t" + keyPair.getPrivate());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	//
	private static KeyPair keySingleInstance = null;

	/**
	 * * 生成密钥对 *
	 * 
	 * @return KeyPair *
	 * @throws EncryptException
	 */
	public static KeyPair generateKeyPair(String basePath) throws Exception {
		try {
			KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance("RSA",
					new org.bouncycastle.jce.provider.BouncyCastleProvider());
			// 大小
			final int KEY_SIZE = 1024;
			keyPairGen.initialize(KEY_SIZE, new SecureRandom());
			KeyPair keyPair = keyPairGen.generateKeyPair();
			saveKeyPair(keyPair, basePath);
			return keyPair;
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
	}

	/**
	 * 获取密钥对
	 * 
	 * @return
	 * @throws Exception
	 */
	public static KeyPair getKeyPair(String basePath) throws Exception {
		FileInputStream fis = new FileInputStream(
				StringUtils.isNotBlank(basePath) ? (basePath + RSAKeyStore)
						: RSAKeyStore);
		ObjectInputStream oos = new ObjectInputStream(fis);
		KeyPair kp = (KeyPair) oos.readObject();
		oos.close();
		fis.close();
		return kp;
	}

	/**
	 * 保存密钥
	 * 
	 * @param kp
	 * @throws Exception
	 */
	public static void saveKeyPair(KeyPair kp, String basePath)
			throws Exception {
		File file = new File(basePath + RSAKeyStore);
		if (!file.getParentFile().exists()) {
			file.getParentFile().mkdirs();
		}
		FileOutputStream fos = new FileOutputStream(
				StringUtils.isNotBlank(basePath) ? (basePath + RSAKeyStore)
						: RSAKeyStore);
		ObjectOutputStream oos = new ObjectOutputStream(fos);
		// 生成密钥
		oos.writeObject(kp);
		oos.close();
		fos.close();
	}

	public static PublicKey createPublicKey() {
		PublicKey publicKey = null;
		try {
			publicKey = RSAUtil.generateRSAPublicKey(PUBLIC_KEY.toByteArray(),
					new BigInteger("65537").toByteArray());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(publicKey);
		return publicKey;
	}

	/**
	 * * 生成公钥 *
	 * 
	 * @param modulus
	 *            *
	 * @param publicExponent
	 *            *
	 * @return RSAPublicKey *
	 * @throws Exception
	 */
	public static RSAPublicKey generateRSAPublicKey(byte[] modulus,
			byte[] publicExponent) throws Exception {
		KeyFactory keyFac = null;
		try {
			keyFac = KeyFactory.getInstance("RSA",
					new org.bouncycastle.jce.provider.BouncyCastleProvider());
		} catch (NoSuchAlgorithmException ex) {
			throw new Exception(ex.getMessage());
		}
		RSAPublicKeySpec pubKeySpec = new RSAPublicKeySpec(new BigInteger(
				modulus), new BigInteger(publicExponent));
		try {
			return (RSAPublicKey) keyFac.generatePublic(pubKeySpec);
		} catch (InvalidKeySpecException ex) {
			throw new Exception(ex.getMessage());
		}
	}

	/**
	 * * 生成私钥 *
	 * 
	 * @param modulus
	 *            *
	 * @param privateExponent
	 *            *
	 * @return RSAPrivateKey *
	 * @throws Exception
	 */
	public static RSAPrivateKey generateRSAPrivateKey(byte[] modulus,
			byte[] privateExponent) throws Exception {
		KeyFactory keyFac = null;
		try {
			keyFac = KeyFactory.getInstance("RSA",
					new org.bouncycastle.jce.provider.BouncyCastleProvider());
		} catch (NoSuchAlgorithmException ex) {
			throw new Exception(ex.getMessage());
		}
		RSAPrivateKeySpec priKeySpec = new RSAPrivateKeySpec(new BigInteger(
				modulus), new BigInteger(privateExponent));
		try {
			return (RSAPrivateKey) keyFac.generatePrivate(priKeySpec);
		} catch (InvalidKeySpecException ex) {
			throw new Exception(ex.getMessage());
		}
	}

	public static String encryptStr(String str) {
		String pwd = new StringBuffer(str).reverse().toString();
		byte[] r = {};
		try {
			r = encrypt(createPublicKey(), pwd.getBytes());

		} catch (Exception e) {
			e.printStackTrace();
		}
		// 返回解密的字符串
		return bytesToHexString(r);
	}

	/**
	 * * 加密 *
	 * 
	 * @param key
	 *            加密的密钥 *
	 * @param data
	 *            待加密的明文数据 *
	 * @return 加密后的数据 *
	 * @throws Exception
	 */
	public static byte[] encrypt(PublicKey pk, byte[] data) throws Exception {
		try {
			Cipher cipher = Cipher.getInstance("RSA",
					new org.bouncycastle.jce.provider.BouncyCastleProvider());
			cipher.init(Cipher.ENCRYPT_MODE, pk);
			// 获得加密块大小，如：加密前数据为128个byte，而key_size=1024
			int blockSize = cipher.getBlockSize();
			// 加密块大小为127
			// byte,加密后为128个byte;因此共有2个加密块，第一个127
			// byte第二个为1个byte
			int outputSize = cipher.getOutputSize(data.length);// 获得加密块加密后块大小
			int leavedSize = data.length % blockSize;
			int blocksSize = leavedSize != 0 ? data.length / blockSize + 1
					: data.length / blockSize;
			byte[] raw = new byte[outputSize * blocksSize];
			int i = 0;
			while (data.length - i * blockSize > 0) {
				if (data.length - i * blockSize > blockSize) {
					cipher.doFinal(data, i * blockSize, blockSize, raw, i
							* outputSize);
				} else {
					cipher.doFinal(data, i * blockSize, data.length - i
							* blockSize, raw, i * outputSize);
				}
				i++;
			}
			return raw;
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
	}

	/**
	 * * 解密 *
	 * 
	 * @param key
	 *            解密的密钥 *
	 * @param raw
	 *            已经加密的数据 *
	 * @return 解密后的明文 *
	 * @throws Exception
	 */
	@SuppressWarnings("static-access")
	public static byte[] decrypt(PrivateKey pk, byte[] raw) throws Exception {
		try {
			Cipher cipher = Cipher.getInstance("RSA",
					new org.bouncycastle.jce.provider.BouncyCastleProvider());
			cipher.init(cipher.DECRYPT_MODE, pk);
			int blockSize = cipher.getBlockSize();
			ByteArrayOutputStream bout = new ByteArrayOutputStream(64);
			int j = 0;
			while (raw.length - j * blockSize > 0) {
				bout.write(cipher.doFinal(raw, j * blockSize, blockSize));
				j++;
			}
			return bout.toByteArray();
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception(e.getMessage());
		}
	}

	/**
	 * 解密方法 paramStr ->密文 basePath ->RSAKey.txt所在的文件夹路径
	 **/
	public static String decryptStr(String paramStr, String basePath)
			throws Exception {
		byte[] en_result = hexStringToBytes(paramStr);
		if (keySingleInstance == null) {
			keySingleInstance = getKeyPair(basePath);
		}
		byte[] de_result = decrypt(keySingleInstance.getPrivate(), en_result);
		StringBuffer sb = new StringBuffer();
		sb.append(new String(de_result));
		// 返回解密的字符串
		return sb.reverse().toString();
	}

	/** * 16进制 To byte[] * @param hexString * @return byte[] */
	public static byte[] hexStringToBytes(String hexString) {
		if (hexString == null || hexString.equals("")) {
			return null;
		}
		hexString = hexString.toUpperCase();
		int length = hexString.length() / 2;
		char[] hexChars = hexString.toCharArray();
		byte[] d = new byte[length];
		for (int i = 0; i < length; i++) {
			int pos = i * 2;
			d[i] = (byte) (charToByte(hexChars[pos]) << 4 | charToByte(hexChars[pos + 1]));
		}
		return d;
	}

	/** * Convert char to byte * @param c char * @return byte */
	private static byte charToByte(char c) {
		return (byte) "0123456789ABCDEF".indexOf(c);
	}

	/**
	 * Convert byte[] to hex
	 * string.这里我们可以将byte转换成int，然后利用Integer.toHexString(int)来转换成16进制字符串。
	 * 
	 * @param src
	 *            byte[] data
	 * @return hex string
	 */
	public static String bytesToHexString(byte[] src) {
		StringBuilder stringBuilder = new StringBuilder("");
		if (src == null || src.length <= 0) {
			return null;
		}
		for (int i = 0; i < src.length; i++) {
			int v = src[i] & 0xFF;
			String hv = Integer.toHexString(v);
			if (hv.length() < 2) {
				stringBuilder.append(0);
			}
			stringBuilder.append(hv);
		}
		return stringBuilder.toString();
	}

}
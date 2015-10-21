package cn.yiyingli.Util;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStreamWriter;
import java.math.BigInteger;
import java.security.Key;
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

import org.apache.commons.lang.StringUtils;
import org.bouncycastle.util.io.pem.PemObject;
import org.bouncycastle.util.io.pem.PemWriter;

/**
 * RSA 工具类。提供加密，解密，生成密钥对等方法。 需要bcprov-jdk16-140.jar包。
 * 
 */
public class RSAUtil {

	public static class PemFile {
		private PemObject pemObject;

		public PemFile(Key key, String description) {
			pemObject = new PemObject(description, key.getEncoded());
		}

		public void write(String fileName) throws IOException {
			PemWriter pemWriter = new PemWriter(new OutputStreamWriter(new FileOutputStream(fileName)));
			try {
				pemWriter.writeObject(pemObject);
			} finally {
				pemWriter.close();
			}
		}
	}

	// public static final String RSAKEY_BASE_PATH =
	// "C:/Users/Administrator/Desktop/RSAKey/";
	public static String RSAKEY_BASE_PATH = ConfigurationXmlUtil.getInstance().getSettingData().get("/RSAKey/");
	private static final String RSAKeyStore = "RSAKey.txt";

	public static void main(String[] args) {
		// try {
		// generateKeyPair("C:/Users/Administrator/Desktop/RSAKey/");
		// } catch (Exception e) {
		// e.printStackTrace();
		// }
		try {
			// KeyPair keyPair = getKeyPair(RSAKEY_BASE_PATH);
			// byte[] t = encrypt(keyPair.getPublic(), "123".getBytes());
			// String ss = new String(Base64.encodeBase64(t));
			// System.out.println(ss);
			// byte[] tt = decrypt(keyPair.getPrivate(),
			// Base64.decodeBase64("V0WXhMaJjr2UybBbm3IEoVO0k9ByVYYMxTjPdrynxrgXr5wyyRonNViUIcjYzHNjOu60TopbqovrAaunStDPnD6R9Ru5hJaWANSqFthAXunhfF9hRO7IfWRuAlefxDYQdolS+/8yvzaq8MmKfmxxY7E8fNSML9WHBj2MTmLVhSE="));
			// System.out.println(tt.length);
			// StringBuffer sb = new StringBuffer();
			// sb.append(new String(tt));
			// System.out.println(sb.toString());
			// PrivateKey privateKey = keyPair.getPrivate();
			// PublicKey publicKey = keyPair.getPublic();
			// PemFile pemFile = new PemFile(publicKey, "");
			// pemFile.write("C:/Users/Administrator/Desktop/RSAKey/RSA.pub");
			// Integer.
			// byte[] encode = encrypt(keyPair.getPublic(),
			// "1234567890".getBytes());
			// System.out.println(encode[encode.length-1]);
			// String decode = decryptStrIOS(
			// "0ba24587b2ab0bb613ff3ec2fd6b8ec4e3b8890cd010086778aec9cdfed8278f9556d4a65fee79b27e4529985c598826574172069f05532ed4b34ccdeccdb262eb2f0e2d1c30f575365f5efc26aeb380845b958b4c541b6ef6961846d4cfcb912ca90eed4741f25b4c134301cbc9672d7c130e037b7fddff6eba4fd108bbac21",
			// RSAKEY_BASE_PATH);
			// System.out.println(decode.length());
			// System.out.println(decode);
			// byte[] bb = {00,(byte) 0xff};
			// System.out.println(DES3Util.byte2hexClean(bb));
			// System.out.println(((RSAPublicKey)keyPair.getPublic()).getModulus());
			// System.out.println(((RSAPublicKey)keyPair.getPublic()).getPublicExponent());
			// System.out.println("public key:\t" +
			// keyPair.getPublic());
			// System.out.println("private key:\t" +
			// keyPair.getPrivate());

			// byte[] bts =
			// hexStringToBytes("8959d2ced61bee338accd16794538ec0a49da0655ddca8fa2461d4cf419dafaf4d7c47813f6ac8c6e5646a2beb08cccf4184a831e683a631e3c528b908deecc57235d03935d0650fbe53d44f717da7f5d1622e7405a3b4f06377eb506880dae21e5065c878c03d85113e068ac82af6b29037d57163d9a311807bee654927d349");
			// byte[] base64 =
			// Base64.decodeBase64("MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCJWdLO1hvuM4rM0WeUU47ApJ2gZV3cqPokYdTPQZ2vr018R4E/asjG5WRqK+sIzM9BhKgx5oOmMePFKLkI3uzFcjXQOTXQZQ++U9RPcX2n9dFiLnQFo7TwY3frUGiA2uIeUGXIeMA9hRE+BorIKvaykDfVcWPZoxGAe+5lSSfTSQIDAQAB");
			// System.out.println(base64.length);
			// System.out.println(base64);
			// byte[] t = { (byte) 0xC8, 0X67,0x54 };
			// String b = Base64.encodeBase64String(t);
			// System.out.println(b);
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
				StringUtils.isNotBlank(basePath) ? (basePath + RSAKeyStore) : RSAKeyStore);
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
	public static void saveKeyPair(KeyPair kp, String basePath) throws Exception {
		File file = new File(basePath + RSAKeyStore);
		if (!file.getParentFile().exists()) {
			file.getParentFile().mkdirs();
		}
		FileOutputStream fos = new FileOutputStream(
				StringUtils.isNotBlank(basePath) ? (basePath + RSAKeyStore) : RSAKeyStore);
		ObjectOutputStream oos = new ObjectOutputStream(fos);
		// 生成密钥
		oos.writeObject(kp);
		oos.close();
		fos.close();
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
	public static RSAPublicKey generateRSAPublicKey(byte[] modulus, byte[] publicExponent) throws Exception {
		KeyFactory keyFac = null;
		try {
			keyFac = KeyFactory.getInstance("RSA", new org.bouncycastle.jce.provider.BouncyCastleProvider());
		} catch (NoSuchAlgorithmException ex) {
			throw new Exception(ex.getMessage());
		}
		RSAPublicKeySpec pubKeySpec = new RSAPublicKeySpec(new BigInteger(modulus), new BigInteger(publicExponent));
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
	public static RSAPrivateKey generateRSAPrivateKey(byte[] modulus, byte[] privateExponent) throws Exception {
		KeyFactory keyFac = null;
		try {
			keyFac = KeyFactory.getInstance("RSA", new org.bouncycastle.jce.provider.BouncyCastleProvider());
		} catch (NoSuchAlgorithmException ex) {
			throw new Exception(ex.getMessage());
		}
		RSAPrivateKeySpec priKeySpec = new RSAPrivateKeySpec(new BigInteger(modulus), new BigInteger(privateExponent));
		try {
			return (RSAPrivateKey) keyFac.generatePrivate(priKeySpec);
		} catch (InvalidKeySpecException ex) {
			throw new Exception(ex.getMessage());
		}
	}

	/**
	 * * 加密 *
	 * 
	 * @param MD5Key
	 *            加密的密钥 *
	 * @param data
	 *            待加密的明文数据 *
	 * @return 加密后的数据 *
	 * @throws Exception
	 */
	public static byte[] encrypt(PublicKey pk, byte[] data) throws Exception {
		try {
			Cipher cipher = Cipher.getInstance("RSA", new org.bouncycastle.jce.provider.BouncyCastleProvider());
			cipher.init(Cipher.ENCRYPT_MODE, pk);
			// 获得加密块大小，如：加密前数据为128个byte，而key_size=1024
			int blockSize = cipher.getBlockSize();
			// 加密块大小为127
			// byte,加密后为128个byte;因此共有2个加密块，第一个127
			// byte第二个为1个byte
			int outputSize = cipher.getOutputSize(data.length);// 获得加密块加密后块大小
			int leavedSize = data.length % blockSize;
			int blocksSize = leavedSize != 0 ? data.length / blockSize + 1 : data.length / blockSize;
			byte[] raw = new byte[outputSize * blocksSize];
			int i = 0;
			while (data.length - i * blockSize > 0) {
				if (data.length - i * blockSize > blockSize) {
					cipher.doFinal(data, i * blockSize, blockSize, raw, i * outputSize);
				} else {
					cipher.doFinal(data, i * blockSize, data.length - i * blockSize, raw, i * outputSize);
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
	 * @param MD5Key
	 *            解密的密钥 *
	 * @param raw
	 *            已经加密的数据 *
	 * @return 解密后的明文 *
	 * @throws Exception
	 */
	@SuppressWarnings("static-access")
	public static byte[] decrypt(PrivateKey pk, byte[] raw) throws Exception {
		try {
			Cipher cipher = Cipher.getInstance("RSA", new org.bouncycastle.jce.provider.BouncyCastleProvider());
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
	public static String decryptStrIOS(String paramStr, String basePath) throws Exception {
		byte[] en_result = hexStringToBytes(paramStr);
		if (keySingleInstance == null) {
			keySingleInstance = getKeyPair(basePath);
		}
		byte[] de_result = decrypt(keySingleInstance.getPrivate(), en_result);

		StringBuffer sb = new StringBuffer();
		sb.append(new String(de_result));
		String[] t = sb.reverse().toString().split(new String(new byte[] { 0x00 }));
		// 返回解密的字符串
		return t[0];
	}

	/**
	 * 解密方法 paramStr ->密文 basePath ->RSAKey.txt所在的文件夹路径
	 **/
	public static String decryptStr(String paramStr, String basePath) throws Exception {
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

}
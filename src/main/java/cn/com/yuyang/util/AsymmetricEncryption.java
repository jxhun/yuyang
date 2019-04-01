package cn.com.yuyang.util;

import java.security.*;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.codec.binary.Base64;

/**
 * 非对称性加密（公私钥加密）
 * 1、每次生成的公私钥对都是不同的，所以要求对其进行存储
 *
 * @author wb-zcf274530
 */
public class AsymmetricEncryption {

    private static final String RSA = "RSA";

    /**
     * 加密
     *
     * @param publicKey
     * @param srcBytes
     * @return
     * @throws NoSuchAlgorithmException
     * @throws NoSuchPaddingException
     * @throws InvalidKeyException
     * @throws IllegalBlockSizeException
     * @throws BadPaddingException
     */
    public byte[] encrypt(String publicKey, byte[] srcBytes) throws NoSuchAlgorithmException,
            NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
        try {
            if (publicKey != null) {
                PublicKey key = getPublicKey(publicKey);
                return rsa(key, srcBytes, Cipher.ENCRYPT_MODE);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取公钥
     *
     * @param publicKey
     * @return
     * @throws Exception
     */
    private static PublicKey getPublicKey(String publicKey) throws Exception {
        byte[] publicKeys = Base64.decodeBase64(publicKey);
        X509EncodedKeySpec spec = new X509EncodedKeySpec(publicKeys);
        KeyFactory kf = KeyFactory.getInstance(RSA);
        return kf.generatePublic(spec);
    }

    /**
     * 获取私钥
     *
     * @param privateKey
     * @return
     * @throws Exception
     */
    private static PrivateKey getPrivateKey(String privateKey) throws Exception {
        byte[] keyBytes = Base64.decodeBase64(privateKey);
        PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory kf = KeyFactory.getInstance(RSA);
        return kf.generatePrivate(spec);
    }


    /**
     * 解密
     *
     * @param privateKey
     * @param srcBytes
     * @return
     * @throws NoSuchAlgorithmException
     * @throws NoSuchPaddingException
     * @throws InvalidKeyException
     * @throws IllegalBlockSizeException
     * @throws BadPaddingException
     */
    public byte[] decrypt(String privateKey, byte[] srcBytes) throws NoSuchAlgorithmException,
            NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
        try {
            if (privateKey != null) {
                PrivateKey key = getPrivateKey(privateKey);
                // 根据公钥，对Cipher对象进行初始化
                return rsa(key, srcBytes, Cipher.DECRYPT_MODE);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public byte[] rsa(Key key, byte[] input, int mode) throws IllegalBlockSizeException,
            BadPaddingException, InvalidKeyException,
            NoSuchAlgorithmException, NoSuchPaddingException {
        Cipher cipher = Cipher.getInstance(RSA);
        // 根据公钥，对Cipher对象进行初始化
        cipher.init(mode, key);
        byte[] resultBytes = cipher.doFinal(input);
        return resultBytes;
    }

    /**
     * 这个方法加密
     *
     */
    public byte[] jiaMi(String msg, HttpServletRequest request)  {
        HttpSession session = request.getSession();  // 获取到session
        AsymmetricEncryption rsa = new AsymmetricEncryption();
//        String msg = "www.suning.com/index.jsp";
        // KeyPairGenerator类用于生成公钥和私钥对，基于RSA算法生成对象
        KeyPairGenerator keyPairGen = null;
        try {
            keyPairGen = KeyPairGenerator.getInstance(RSA);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        // 初始化密钥对生成器，密钥大小为1024位
        keyPairGen.initialize(1024);
        // 生成一个密钥对，保存在keyPair中
        KeyPair keyPair = keyPairGen.generateKeyPair();
        // 得到私钥
        RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();
        // 得到公钥
        RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();

        // 用公钥加密
        byte[] srcBytes = msg.getBytes();
        byte[] resultBytes = new byte[0];
        try {
            resultBytes = rsa.encrypt(Base64.encodeBase64String(publicKey.getEncoded()), srcBytes);
        } catch (GeneralSecurityException e) {
            e.printStackTrace();
        }
        System.out.println(Base64.encodeBase64String(publicKey.getEncoded()));
        // 用私钥解密
        byte[] decBytes = new byte[0];
        try {
            decBytes = rsa.decrypt(Base64.encodeBase64String(privateKey.getEncoded()), resultBytes);
        } catch (GeneralSecurityException e) {
            e.printStackTrace();
        }
        System.out.println(Base64.encodeBase64String(privateKey.getEncoded()));
        session.setAttribute(SessionKey.SIYAO, Base64.encodeBase64String(privateKey.getEncoded()));
        System.out.println("明文是:" + msg);
        System.out.println("加密后是:" + new String(resultBytes));
        System.out.println("解密后是:" + new String(decBytes));
        return resultBytes;
    }

    /**
     * 这个方法用来解密
     *
     * @param msg 密码
     * @param shiYao 私钥
     * @return
     */
    public String jieMi(byte[] msg, String shiYao)  {
        AsymmetricEncryption rsa = new AsymmetricEncryption();
        byte[] decBytes = new byte[0];
        try {
            decBytes = rsa.decrypt(shiYao, msg);
        } catch (GeneralSecurityException e) {
            e.printStackTrace();
        }

        if(decBytes!=null){
            return new String(decBytes);
        }
      return  null;
    }
}


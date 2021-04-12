package com.sardine.common.util;

import org.apache.commons.codec.binary.Base64;
import org.bouncycastle.util.io.pem.PemObject;
import org.bouncycastle.util.io.pem.PemWriter;

import javax.crypto.Cipher;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

/**
 * Jwt工具类
 */
@SuppressWarnings("unused")
public class RsaUtils {
    /**
     * generate rsa public key and private key according to secret, and print to console.
     *
     * @param secret             the secret key used to generate encrypted file
     * @throws Exception generate files failed
     */
    public static void generateKey(String secret) throws Exception {
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
        SecureRandom secureRandom = new SecureRandom(secret.getBytes());
        keyPairGenerator.initialize(1024, secureRandom);
        KeyPair keyPair = keyPairGenerator.genKeyPair();
        PublicKey publicKey = keyPair.getPublic();
        PrivateKey privateKey = keyPair.getPrivate();
        StringWriter writer = new StringWriter();
        PemWriter pemWriter = new PemWriter(writer);
        pemWriter.writeObject(new PemObject("PUBLIC KEY", publicKey.getEncoded()));
        pemWriter.flush();
        pemWriter.close();
        System.out.println(writer.toString());
        System.out.println("--- PRIVATE KEY ---");
        System.out.println(Base64.encodeBase64String(privateKey.getEncoded()));
    }

    /**
     * Read public key from bytes
     *
     * @param bytes         public key bytes
     * @return              public key object
     * @throws Exception    get public key failed
     */
    public static PublicKey getPublicKey(byte[] bytes) throws Exception {
        X509EncodedKeySpec spec = new X509EncodedKeySpec(bytes);
        KeyFactory factory = KeyFactory.getInstance("RSA");
        return factory.generatePublic(spec);
    }

    /**
     * Read private key from bytes
     *
     * @param bytes         private key bytes
     * @return              private key object
     * @throws Exception    get private key failed
     */
    public static PrivateKey getPrivateKey(byte[] bytes) throws Exception {
        PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(bytes);
        KeyFactory factory = KeyFactory.getInstance("RSA");
        return factory.generatePrivate(spec);
    }

    /**
     * encrypt message by public key which encoded by base64
     *
     * @param pubKey        public key encoded by base64
     * @param message       message
     * @return              cipher text
     */
    public static String encrypt(String pubKey, String message) throws Exception {
        Cipher cipher = Cipher.getInstance("RSA");
        PublicKey publicKey = getPublicKey(Base64.decodeBase64(pubKey));
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);
        return Base64.encodeBase64String(cipher.doFinal(message.getBytes(StandardCharsets.UTF_8)));
    }

    /**
     * decrypt message by private key which encoded by base64
     *
     * @param priKey        private key encoded by base64
     * @param message       message
     * @return              plain text
     */
    public static String decrypt(String priKey, String message) throws Exception {
        Cipher cipher = Cipher.getInstance("RSA");
        PrivateKey privateKey = getPrivateKey(Base64.decodeBase64(priKey));
        cipher.init(Cipher.DECRYPT_MODE, privateKey);
        return new String(cipher.doFinal(Base64.decodeBase64(message)));
    }

    public static void main(String[] args) throws Exception {
        generateKey("8UYFd@LKbo!g64MHsv-RJMmw");
    }
}
package rsa;

import javax.crypto.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;

/**
 * RSA加密操作
 * ① 公钥加密，私钥解密
 * ② 私钥签名，公钥验签
 */
public class RSADemo {

    static final int RSA_LENGH_1024 = 1024;
    static final int RSA_LENGH_2048 = 2048;

    static String data = "考试了";

    public static void main(String[] args) {
        System.out.println("hello world");
        //密钥对
        RSADemo demo = new RSADemo();
        //1.生成密钥对
        KeyPair keyPair = demo.createKeyPair(RSA_LENGH_1024);
        //2.对数据进行加密
        byte[] ciperTxt = demo.encrypt(data.getBytes(), keyPair.getPublic());
        //3.对密文进行解密
        byte[] originalTxt = demo.decrypt(ciperTxt, keyPair.getPrivate());

    }

    //==============================加载pem格式的私钥和公钥文件=========================
    /**
     * 思路：
     * 1、准备了两个文件，分别是pri.pem和pub.pem文件，存放私钥和公钥
     *      使用OpenSSL工具的命令生成两个文件：
     *          ① 生成私钥：genrsa -out 文件名.pem 长度1024/2048(可写)
     *          ② 生成公钥： rsa -in 私钥文件名 -pubout -out 文件名.pem
     *
     *          pkcs8 格式转换 PEM--->DER
     *          -in 要输入的文件
     *          -inform 指定输入文件的格式（DER或PEM）
     *          -outform 指定输出文件的格式（DER或PEM）
     *          -out 将输出的内容保存到的文件
     *          -topk8 输出一个pkcs8文件
     *          -nocrypt 不加密
     *
     *          pkcs8 -topk8 -inform PEM -outform DER -in 私钥pem文件 -out der文件名 -nocrypt
     *          rsa -inform DER -in 私钥der文件 -pubout -outform DER -out 公钥der文件
     * 2、编写两个java方法，分别实现加载私钥文件和公钥文件
     *      ① readPriByDerName(String fileName)、loadPriByPemName(String fileName)
     *      ② readPubByDerName(String fileName)、loadPubByPemName(String fileName)
     *
     *
     *  常用API方法：
     *      X509EncodedKeySpec
     *      KeyFactory
     */


    /**
     *  通过文件加载私钥的方法
     * @param fileName 文件名称
     * @return 根据私钥文件读取到的私钥
     */
    public PrivateKey readPriByDerName(String fileName){
        try {
            //1、从文件中读取文件数据，读取为byte[]
            byte[] priBytes = Files.readAllBytes(Paths.get(fileName));

            X509EncodedKeySpec spec =new X509EncodedKeySpec(priBytes);
            System.out.println(spec.getFormat());
            KeyFactory factory = KeyFactory.getInstance("RSA");
            return factory.generatePrivate(spec);

        } catch (IOException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (InvalidKeySpecException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 通过读取der格式的文件，加载生成公钥
     *
     * @param fileName der格式的文件名称
     * @return 根据文件读取到的公钥
     */
    public PublicKey readPubByDerName(String fileName){
        try {
            //从文件中读取公钥数据内容
            byte[] pubBytes = Files.readAllBytes(Paths.get(fileName));
            X509EncodedKeySpec spec =new X509EncodedKeySpec(pubBytes);
            System.out.println(spec.getFormat());
            KeyFactory factory = KeyFactory.getInstance("RSA");
            return factory.generatePublic(spec);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (InvalidKeySpecException e) {
            e.printStackTrace();
        }
        return null;
    }


    //
    /**
     * 加载私钥的pem格式，得到私钥数据
     * 
     * @param fileName pem文件格式
     * @return 私钥
     */
    public PrivateKey loadPriByPem(String fileName){
        // TODO: 2020/11/30 读取pem文件，生成私钥
        return null;
    }

    /**
     *  加载公钥的pem格式， 得到公钥数据
     *
     * @param fileName pem文件格式
     * @return 公钥
     */
    public PublicKey loadPubByPem(String fileName){
        // TODO: 2020/11/30 读取pem文件，生成公钥
        return null;
    }

    //========================================MD5哈希计算==========================

    /**
     * 使用MD5哈希算法对数据进行hash计算
     *
     * @param data 原文
     * @return 摘要
     */
    public byte[] md5Hash(byte[] data){
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("MD5");

            return messageDigest.digest(data);

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }

    //=========================私钥签名，公钥验签=========================

    /**
     * 使用RSA的公钥对签名进行验证
     *
     * @param signTxt 签名数据
     * @param data 原文数据
     * @param pub 公钥
     * @return 返回值为签名是否验证通过
     */
    public boolean verify(byte[] signTxt, byte[] data, PublicKey pub){
        try {
            Signature signature = Signature.getInstance("MD5withRSA");
            signature.initVerify(pub);
            //对原文进行hash计算,使用MD5进行hash计算
            byte[] md5Hash = md5Hash(data);
            signature.update(data);
            return signature.verify(signTxt);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (SignatureException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 使用RSA算法对数据进行签名
     *
     * @param data hash后的数据
     * @param pri 私钥
     * @return 签名后的数据
     */
    public byte[] sign(byte[] data, PrivateKey pri){
        try {
            Signature signature = Signature.getInstance("MD5withRSA");
            signature.initSign(pri);
            signature.update(data);//把数据更新写入到签名器中
            return signature.sign();//对数据进行签名
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (SignatureException e) {
            e.printStackTrace();
        }
        return null;
    }

    //=========================公钥加密，私钥解密=========================

    /**
     *  使用RSA算法的公钥对数据进行解密
     *
     * @param cipherTxt
     * @param pri
     * @return
     */
    public byte[] decrypt(byte[] cipherTxt, PrivateKey pri){
        //Cipher
        try {
            Cipher cipher = Cipher.getInstance("RSA");
            cipher.init(Cipher.ENCRYPT_MODE, pri);
            return cipher.doFinal(cipherTxt);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        }
        return null;
    }
    /**
     * 使用RSA算法的公钥对数据进行加密
     * @param data 要加密的数据
     * @param pub 公钥
     * @return 返回秘文
     */
    public byte[] encrypt(byte[] data, PublicKey pub){
        //Cipher: 真正执行加密或者解密动作的类
        try {
            Cipher cipher = Cipher.getInstance("RSA");
            cipher.init(Cipher.ENCRYPT_MODE, pub);
            return cipher.doFinal(data);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        }
        return null;
    }

//================================================================
    /**
     * 使用java的api生成一对密钥，返回KeyPair类型
     * @param size 密钥长度
     * @return 生成的密钥对
     */
    public KeyPair createKeyPair(int size){
        //密钥生成器
        try {
            //密钥生成器
            KeyPairGenerator generator = KeyPairGenerator.getInstance("RSA");

            generator.initialize(size);
            //生成密钥对
            KeyPair keyPair = generator.genKeyPair();
            return keyPair;

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }
}

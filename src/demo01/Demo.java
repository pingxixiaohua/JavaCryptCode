package demo01;

import javax.crypto.*;
import javax.crypto.spec.DESKeySpec;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

public class Demo {
    //密钥
    String key = "c1906031";
    //数据
    String data = "南昌的冬天忒冷了";
    //程序入口
    public static void main(String[] args) {
        System.out.println("hello workd");

        Demo demo = new Demo();
        print();
    }

    public static void print(){
        System.out.println("hello world");
    }

    public byte[] encrypt(byte[] key, byte[] msg){
        //1.面向百度编程：java des
        try {
            //DES秘钥初始化
            DESKeySpec spec = new DESKeySpec(key);
            //factory:工厂
            SecretKeyFactory factory = SecretKeyFactory.getInstance("DES");

            SecretKey secretKey = factory.generateSecret(spec);
            Cipher cipher = Cipher.getInstance("DES");
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);//初始化
            return cipher.doFinal(msg);


        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (InvalidKeySpecException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        } finally {
            System.out.println("finally代码");
        }
        return null;

    }


    /**
     *  使用DES算法进行解密
     *
     * @param key DES算法的密钥
     * @param msg 要解密的密文
     * @return 返回解密后的明文
     */
    public byte[] decrypt(byte[] key, byte[] msg){
        try {
            //DES密钥的初始化
            DESKeySpec spec = new DESKeySpec(key);
            //工厂类：根据需求返回不同的实例
            SecretKeyFactory factory = SecretKeyFactory.getInstance("DES");
            //统一的密钥对象
            SecretKey secretKey = factory.generateSecret(spec);
            //真正执行解密的对象
            Cipher cipher = Cipher.getInstance("DES");
            //设置cipher的工作模式
            cipher.init(Cipher.DECRYPT_MODE, secretKey);
            //返回DES算法操作后的数据
            return cipher.doFinal(msg);
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (InvalidKeySpecException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        }
        return null;
    }
}
//javac Demo.java ->编译
class Test{

}

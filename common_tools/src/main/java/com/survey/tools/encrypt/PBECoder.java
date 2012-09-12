package com.survey.tools.encrypt;

import java.security.Key;
import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.PBEParameterSpec;

import org.apache.commons.codec.binary.Hex;

/** 
 * PBE Coder<br/> 
 * Algorithm/secret key length/default secret key length/<br/> 
 * 1.PBEWithMD5AndDES/56/56 
 * 2.PBEWithMD5AndTripleDES/112,168/168 
 * 3.PBEWithSHA1AndDESede/112,168/168 
 * 4.PBEWithSHA1AndRC2_40/40 to 1024/128 
 * mode:    CBC <br/> 
 * padding: PKCS5Padding 
 * @author Aub 
 *  
 */  
public class PBECoder {  
  
    public static final String ALGORITHM = "PBEWITHMD5andDES";  
      
    public static final int ITERATION_COUNT = 100;  
      
    /** 
     * 初始盐<br/> 
     * 盐的长度必须为8位 
     * @return byte[] 盐  
     * @throws Exception 
     */  
    public static byte[] initSalt() throws Exception{  
        //实例化安全随机数  
        SecureRandom random = new SecureRandom();  
        //产出盐  
        return random.generateSeed(8);  
    }  
      
    /** 
     * 转换密钥 
     *  
     * @param password  密码 
     * @return Key 密钥 
     */  
    private static Key toKey(String password) throws Exception{  
        //密钥材料  
        PBEKeySpec keySpec = new PBEKeySpec(password.toCharArray());  
        //实例化  
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(ALGORITHM);  
        //生成密钥  
        return keyFactory.generateSecret(keySpec);  
    }  
      
    /** 
     * 加密 
     *  
     * @param data  待加密数据 
     * @param key   密钥 
     * @param salt  盐 
     * @return byte[]   加密数据 
     * @throws Exception 
     */  
    public static byte[] encrypt(byte[] data,String password,byte[] salt) throws Exception{  
        //转换密钥  
        Key key = toKey(password);  
        //实例化PBE参数材料  
        PBEParameterSpec paramSpec = new PBEParameterSpec(salt, ITERATION_COUNT);  
        //实例化  
        Cipher cipher = Cipher.getInstance(ALGORITHM);  
        //初始化  
        cipher.init(Cipher.ENCRYPT_MODE, key, paramSpec);  
        //执行操作  
        return cipher.doFinal(data);  
    }  
      
    /** 
     * 解密 
     *  
     * @param data  待机密数据 
     * @param key   密钥 
     * @param salt  盐 
     * @return byte[]   解密数据 
     * @throws Exception 
     */  
    public static byte[] decrypt(byte[] data,String password,byte[] salt)throws Exception{  
        //转换密钥  
        Key key = toKey(password);  
        //实例化PBE参数材料  
        PBEParameterSpec paramSpec = new PBEParameterSpec(salt, ITERATION_COUNT);  
        //实例化  
        Cipher cipher = Cipher.getInstance(ALGORITHM);  
        //初始化  
        cipher.init(Cipher.DECRYPT_MODE, key, paramSpec);  
        //执行操作  
        return cipher.doFinal(data);  
    }  
      
    private static String  showByteArray(byte[] data){  
        if(null == data){  
            return null;  
        }  
        StringBuilder sb = new StringBuilder("{");  
        for(byte b:data){  
            sb.append(b).append(",");  
        }  
        sb.deleteCharAt(sb.length()-1);  
        sb.append("}");  
        return sb.toString();  
    }  
      
    public static void main(String[] args) throws Exception {  
        byte[] salt = initSalt();  
        System.out.println("salt："+showByteArray(salt));  
        //这里的password需要时ASCII码，不然会报异常  
        String password = "1111";  
        System.out.println("口令："+password);  
          
        String data ="PBE数据";  
        System.out.println("加密前数据: string:"+data);  
        System.out.println("加密前数据: byte[]:"+showByteArray(data.getBytes()));  
        System.out.println();  
        byte[] encryptData = encrypt(data.getBytes(), password,salt);  
        System.out.println("加密后数据: byte[]:"+showByteArray(encryptData));  
        System.out.println("加密后数据: hexStr:"+Hex.encodeHexString(encryptData));  
        System.out.println();  
        byte[] decryptData = decrypt(encryptData, password,salt);  
        System.out.println("解密后数据: byte[]:"+showByteArray(decryptData));  
        System.out.println("解密后数据: string:"+new String(decryptData));  
          
    }  
}  
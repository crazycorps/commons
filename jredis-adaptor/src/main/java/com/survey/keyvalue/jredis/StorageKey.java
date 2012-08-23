package com.survey.keyvalue.jredis;


/**
 * 由userId生成的key第一个byte为0
 * 由platformId生成的key第一个byte不为0 表示platformId.getBytes()的长度
 * @author luzj
 *
 */
public class StorageKey {
    
    /**使用用户ID生成的key的长度*/
    private static final int GENERATED_BY_USERID_KEY_LEN = 6;
    
    /**
     * 根据用户ID生成表示key的数组
     * @param userId //转换成int进行操作
     * @param keyFlag
     * @return
     */
    public static byte[] getByteArray(long userId, byte keyFlag){
        byte[] bytes = new byte[GENERATED_BY_USERID_KEY_LEN];
        bytes[0] = 4;
        bytes[1] = (byte) (userId & 0XFF);
        bytes[2] = (byte) ((userId >> 8) & 0XFF);
        bytes[3] = (byte) ((userId >> 16) & 0XFF);
        bytes[4] = (byte) ((userId >> 24) & 0XFF);
        bytes[5] = keyFlag;
        return bytes;
    }
    
    public static byte[] getByteArray(long userId, short keyFlag) {
        if(keyFlag < 257) throw new IllegalArgumentException("short must big than 256");
        
        byte[] bytes = new byte[7];
        bytes[0] = 4;
        bytes[1] = (byte) (userId & 0XFF);
        bytes[2] = (byte) ((userId >> 8) & 0XFF);
        bytes[3] = (byte) ((userId >> 16) & 0XFF);
        bytes[4] = (byte) ((userId >> 24) & 0XFF);
        bytes[5] = (byte) (keyFlag & 0XFF);
        bytes[6] = (byte) ((keyFlag >> 8) & 0XFF) ;
        return bytes;
    }

    /**
     * 根据平台ID生成表示key的byte数组
     * @param platformId
     * @param keyFlag
     * @return
     */
    public static byte[] getByteArray(String platformId, byte keyFlag){
        int len = platformId.length();
        byte[] bytes = new byte[len+2];
        bytes[0] = (byte) len;
        for (int i = 0; i < len; i++) {
            bytes[i+1] = (byte)platformId.charAt(i);
        }
        bytes[len+1] = keyFlag;
        return bytes;
    }
    
    /**
     * 获取key中用来hash的int值
     * 使用userId生成的返回userId
     * 使用platformId生成的返回platformId的hashCode
     * @param bytes
     * @return
     */
    public static int getHash(byte[] bytes){
        int len = bytes[0];
        if(len == 4){
            return decodeInt(bytes);
        } else {
            return Math.abs(decodeString(bytes, 1, len).hashCode());
        }
    }
    
    /**
     * 获取用户ID
     * @param bytes
     * @return
     */
    public static int getUserId(byte[] bytes){
        return decodeInt(bytes);
    }
    
    /**
     * 获取平台ID
     * @param bytes
     * @return
     */
    public static String getPlatformId(byte[] bytes){
        int len = bytes[0];
        return decodeString(bytes, 1, len);
    }

    /**
     * decode int
     * @param bytes
     * @return
     */
    private static int decodeInt(byte[] bytes) {
        int value = 0;
        value |= (bytes[1] & 0xFF);
        value |= ((bytes[2] & 0xFF) << 8);
        value |= ((bytes[3] & 0xFF) << 16);
        value |= ((bytes[4] & 0xFF) << 24);
        return value;
    }
    
    /**
     * keyFlag @see {@link sango3-backed:com.hoolai.sango.repo.KeysFlag}
     * @param bytes
     * @return
     */
    public static byte getKeyFlag(byte[] bytes){
        return bytes[bytes.length-1];
    }
    
    /**
     * decode String
     * @param bytes
     * @return
     */
    private static String decodeString(byte[] bytes, int spos, int len){
        char[] chars = new char[len];
        int epos = len + spos;
        int index = 0;
        for (int i = spos; i < epos; i++) {
            chars[index++] = (char) bytes[i];
        }
        return new String(chars);
    }
   
}

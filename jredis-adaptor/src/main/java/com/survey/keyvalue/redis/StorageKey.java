package com.survey.keyvalue.redis;

public class StorageKey {
	
    /**
     * 根据用户ID生成表示key的数组
     * @param busiId //转换成int进行操作
     * @param keyFlag
     * @return
     */
    public static byte[] getByteArray(long busiId, short keyFlag) {
        
        byte[] bytes = new byte[7];
        bytes[0] = 4;
        bytes[1] = (byte) (busiId & 0XFF);
        bytes[2] = (byte) ((busiId >> 8) & 0XFF);
        bytes[3] = (byte) ((busiId >> 16) & 0XFF);
        bytes[4] = (byte) ((busiId >> 24) & 0XFF);
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
    public static byte[] getByteArray(String busiName, byte keyFlag){
        int len = busiName.length();
        byte[] bytes = new byte[len+2];
        bytes[0] = (byte) len;
        for (int i = 0; i < len; i++) {
            bytes[i+1] = (byte)busiName.charAt(i);
        }
        bytes[len+1] = keyFlag;
        return bytes;
    }
    
    /**
     * 获取key中用来hash的int值
     * 使用busiId生成的返回busiId
     * 使用busiName生成的返回busiName的hashCode
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
    
    public static int getBusiId(byte[] bytes){
        return decodeInt(bytes);
    }
    
    public static String getBusiName(byte[] bytes){
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
    
    public static byte getKeyFlag(byte[] bytes){
        return bytes[bytes.length-1];
    }
    
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

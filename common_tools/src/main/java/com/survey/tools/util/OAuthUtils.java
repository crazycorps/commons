package com.survey.tools.util;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.List;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import org.apache.http.NameValuePair;

public final class OAuthUtils {
	
	public static final int ONE = 1;
    public static final int TWO = 2;
    public static final int THREE = 3;
    public static final int FOUR = 4;
    public static final int SIX = 6;
    public static final int EIGHT = 8;
    public static final double TEMP = 1.34;
    private static final char LAST2BYTE = (char) Integer.parseInt("00000011", TWO);
    private static final char LAST4BYTE = (char) Integer.parseInt("00001111", TWO);
    private static final char LAST6BYTE = (char) Integer.parseInt("00111111", TWO);
    private static final char LEAD6BYTE = (char) Integer.parseInt("11111100", TWO);
    private static final char LEAD4BYTE = (char) Integer.parseInt("11110000", TWO);
    private static final char LEAD2BYTE = (char) Integer.parseInt("11000000", TWO);
    private static final char[] ENCODE_TABLE = new char[] {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P',
            'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o',
            'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '+', '/' };


    public static String getBase64Mac(String stepA, String stepB) throws NoSuchAlgorithmException, UnsupportedEncodingException,
            InvalidKeyException {
        byte[] oauthSignature = null;
        Mac mac = Mac.getInstance("HmacSHA1");
        SecretKeySpec spec = new SecretKeySpec(stepB.getBytes("US-ASCII"), "HmacSHA1");
        mac.init(spec);
        oauthSignature = mac.doFinal(stepA.getBytes("US-ASCII"));
        return encode(oauthSignature);
    }
    
    public static String encode(byte[] from) {
        StringBuffer to = new StringBuffer((int) (from.length * TEMP) + THREE);
        int num = 0;
        char currentByte = 0;
        for (int i = 0; i < from.length; i++) {
            num = num % EIGHT;
            while (num < EIGHT) {
                switch (num) {
                case 0:
                    currentByte = (char) (from[i] & LEAD6BYTE);
                    currentByte = (char) (currentByte >>> TWO);
                    break;
                case TWO:
                    currentByte = (char) (from[i] & LAST6BYTE);
                    break;
                case FOUR:
                    currentByte = (char) (from[i] & LAST4BYTE);
                    currentByte = (char) (currentByte << TWO);
                    if ((i + ONE) < from.length) {
                        currentByte |= (from[i + ONE] & LEAD2BYTE) >>> SIX;
                    }
                    break;
                case SIX:
                    currentByte = (char) (from[i] & LAST2BYTE);
                    currentByte = (char) (currentByte << FOUR);
                    if ((i + ONE) < from.length) {
                        currentByte |= (from[i + ONE] & LEAD4BYTE) >>> FOUR;
                    }
                    break;
                default:
                    break;
                }
                to.append(ENCODE_TABLE[currentByte]);
                num += SIX;
            }
        }
        if (to.length() % FOUR != 0) {
            for (int i = FOUR - to.length() % FOUR; i > 0; i--) {
                to.append("=");
            }
        }
        return to.toString();
    }

}

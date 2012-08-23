package com.survey.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import com.survey.keyvalue.jredis.JRedis;

public class ObjectCodec {
    
    public final static String SUPPORTED_CHARSET_NAME = "UTF-8"; // this is for
                                                                 // jdk 1.5
    public final static Charset SUPPORTED_CHARSET = Charset.forName("UTF-8");

    /**
     * This helper method is mainly intended for use with a list of keys
     * returned from Redis, given that it will use the UTF-8 {@link Charset} in
     * decoding the byte array. Typical use would be to convert from the
     * List<byte[]> output of {@link JRedis#keys()}
     * 
     * @param bytearray
     * @return
     */
    public static final List<String> toStr(List<byte[]> bytearray) {
        List<String> list = new ArrayList<String>(bytearray.size());
        for (byte[] b : bytearray)
            if (null != b)
                list.add(toStr(b));
            else
                list.add(null);
        return list;
    }

    /**
     * @param bytes
     * @return new {@link String#String(byte[])} or null if bytes is null.
     */
    public static final String toStr(byte[] bytes) {
        String str = null;
        if (null != bytes) {
            try {
                str = new String(bytes, SUPPORTED_CHARSET_NAME);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        return str;
        // return new String(bytes, SUPPORTED_CHARSET); // Java 1.6 only
    }

    public static final byte[] encode(String value) {
        byte[] bytes = null;
        try {
            bytes = value.getBytes(SUPPORTED_CHARSET_NAME);
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
        return bytes;
    }
    
    public static final List<Long> toLong(List<byte[]> bytearray) {
        List<Long> list = new ArrayList<Long>(bytearray.size());
        for (byte[] b : bytearray)
            list.add(NativeCodec.toLong(b));
        return list;
    }

    public static final List<Double> toDouble(List<byte[]> bytearray) {
        List<Double> list = new ArrayList<Double>(bytearray.size());
        for (byte[] b : bytearray)
            list.add(NativeCodec.toDouble(b));
        return list;
    }

    /**
     * @param bs
     * @return
     */
    public static double toDouble(byte[] bs) {
        return NativeCodec.toDouble(bs);
    }

    /**
     * This helper method will assume the List<byte[]> being presented is the
     * list returned from a {@link JRedis} method such as
     * {@link JRedis#smembers(String)}, and that this list contains the
     * {@link ObjectCodec#encode(Serializable)}ed bytes of the parametric type
     * <code>T</code>.
     * <p>
     * Specifically, this method will instantiate an {@link ArrayList} for type
     * T, of equal size to the size of bytelist {@link List}. Then it will
     * iterate over the byte list and for each byte[] list item call
     * {@link ObjectCodec#decode(byte[])}.
     * <p>
     * <b>Usage example:</b>
     * 
     * <pre>
     * <code>
     * List<byte[]>  memberBytes = redis.smembers("my-object-set");
     * List<MySerializableClass>  members = decode (memberBytes);
     * </code>
     * </pre>
     * 
     * @param <T>
     * @param byteList
     * @return
     */
    @SuppressWarnings("unchecked")
    public static final <T extends Serializable> List<T> decode(List<byte[]> byteList) {
        List<T> objectList = new ArrayList<T>(byteList.size());
        for (byte[] bytes : byteList) {
            if (null != bytes) {
                T object = (T) decode(bytes);
                objectList.add((T) object);
            } else {
                objectList.add(null);
            }
        }
        return objectList;
    }

    /**
     * This helper method will assume that the byte[] provided are the
     * serialized bytes obtainable for an instance of type T obtained from
     * {@link ObjectOutputStream} and subsequently stored as a value for a Redis
     * key (regardless of key type).
     * <p>
     * Specifically, this method will simply do:
     * 
     * <pre>
     * <code>
     * ObjectInputStream oin = new ObjectInputStream(new ByteArrayInputStream(bytes));
     * t = (T) oin.readObject();
     * </code>
     * </pre>
     * 
     * and returning the reference <i>t</i>, and throwing any exceptions
     * encountered along the way.
     * <p>
     * This method is the decoding peer of
     * {@link ObjectCodec#encode(Serializable)}, and it is assumed (and
     * certainly recommended) that you use these two methods in tandem.
     * <p>
     * Naturally, all caveats, rules, and considerations that generally apply to
     * {@link Serializable} and the Object Serialization specification apply.
     * 
     * @param <T>
     * @param bytes
     * @return the instance for <code><b>T</b></code>
     */
    @SuppressWarnings("unchecked")
    public static final <T extends Serializable> T decode(byte[] bytes) {
        T t = null;
        Exception thrown = null;
        try {
            ObjectInputStream oin = new ObjectInputStream(new ByteArrayInputStream(bytes));
            t = (T) oin.readObject();
        } catch (IOException e) {
            e.printStackTrace();
            thrown = e;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            thrown = e;
        } catch (ClassCastException e) {
            e.printStackTrace();
            thrown = e;
        } finally {
            if (null != thrown)
                throw new RuntimeException("Error decoding byte[] data to instantiate java object - "
                        + "data at key may not have been of this type or even an object", thrown);
        }
        return t;
    }

    /**
     * This helper method will serialize the given serializable object of type T
     * to a byte[], suitable for use as a value for a redis key, regardless of
     * the key type.
     * 
     * @param <T>
     * @param obj
     * @return
     */
    public static final <T extends Serializable> byte[] encode(T obj) {
        byte[] bytes = null;
        try {
            ByteArrayOutputStream bout = new ByteArrayOutputStream();
            ObjectOutputStream out = new ObjectOutputStream(bout);
            out.writeObject(obj);
            bytes = bout.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Error serializing object" + obj + " => " + e);
        }
        return bytes;
    }

    public static byte[][] toBytesArray(List<String> srcList) {
        byte[][] bytesArr = new byte[srcList.size()][];
        int index = 0;
        for (String  src : srcList) {
            bytesArr[index++] = encode(src);
        }
        return bytesArr;
    }
}

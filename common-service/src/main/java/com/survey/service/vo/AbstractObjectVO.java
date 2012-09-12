package com.survey.service.vo;

import java.io.Serializable;

import org.apache.commons.codec.binary.Hex;

import com.survey.tools.encrypt.AESCoder;
import com.survey.tools.util.BitUtils;

@SuppressWarnings("serial")
public abstract class AbstractObjectVO<T> implements Serializable {
	
	protected T entity;

	public AbstractObjectVO() {
		super();
	}

	public T getEntity() {
		return entity;
	}

	public void setEntity(T entity) {
		this.entity = entity;
	}
	
	public static String toSn(long id,byte[] secretKey){
		
		try {
			return Hex.encodeHexString(AESCoder.encrypt(BitUtils.long2bytes(id), secretKey));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static long parseSn(String sn,byte[] secretKey){
		try {
			byte[] data = AESCoder.decrypt(sn.getBytes(), secretKey);
			return BitUtils.bytes2long(data, 0);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return -1L;
	}

}

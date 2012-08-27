package com.survey.protobuf;

import org.junit.Ignore;
import org.junit.Test;

import com.survey.protobuf.ProtoIntList;

@Ignore
public class ProtoIntListTest {

	@Test
	public void testSize() {
		ProtoIntList protoIntList = new ProtoIntList();
		for (int i = 1000000; i < 1010000; i++) {
			protoIntList.add(i);
		}
		byte[] bytes = protoIntList.toByteArray();
		System.err.println(bytes.length);
		int len = 10000;
		long start = System.currentTimeMillis();
		for (int i = 0; i < len; i++) {
			new ProtoIntList(bytes);
		}
		System.err.println(System.currentTimeMillis() - start);
		
		StringBuilder sb = new StringBuilder();
		for (int i = 1000000; i < 1010000; i++) {
			sb.append(i).append(",");
		}
		String src = sb.toString();
		System.out.println(src.length());
		
		start = System.currentTimeMillis();
		for (int i = 0; i < len; i++) {
			src.split(",");
		}
		System.out.println(System.currentTimeMillis() - start);
	}
	
}

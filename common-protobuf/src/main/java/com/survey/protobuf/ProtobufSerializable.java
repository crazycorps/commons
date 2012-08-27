package com.survey.protobuf;

import com.google.protobuf.GeneratedMessage;

public interface ProtobufSerializable<T extends GeneratedMessage> {
	
	T copyTo();

	byte[] toByteArray();

	void parseFrom(byte[] bytes);

	void copyFrom(T proto);
}

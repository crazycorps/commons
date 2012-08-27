package com.survey.protobuf;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

import com.google.protobuf.ByteString;
import com.google.protobuf.InvalidProtocolBufferException;
import com.survey.protobuf.proto.ToolsProto;

public class BytesList implements List<byte[]>, ProtobufSerializable<ToolsProto.SimpleList>{

	protected List<byte[]> elements;

	public BytesList() {
		this.elements = new LinkedList<byte[]>();
	}
	
	public BytesList(int size){
	    this.elements = new ArrayList<byte[]>(size);
	}
	
	public BytesList(byte[] bytes) {
		this();
		parseFrom(bytes);
	}
	
	public List<byte[]> getElements() {
		return elements;
	}

	public void setElements(LinkedList<byte[]> elements) {
		this.elements = elements;
	}

	@Override
	public void copyFrom(ToolsProto.SimpleList simpleList) {
		int size = simpleList.getElementsCount();
		for (int i = 0; i < size; i++) {
			this.elements.add(simpleList.getElements(i).toByteArray());
		}
	}

	@Override
	public ToolsProto.SimpleList copyTo() {
		ToolsProto.SimpleList.Builder builder = ToolsProto.SimpleList.newBuilder();
		for (byte[] bytes : elements) {
			builder.addElements(ByteString.copyFrom(bytes));
		}
		return builder.build();
	}

	@Override
	public void parseFrom(byte[] bytes) {
		try {
			ToolsProto.SimpleList simpleList = ToolsProto.SimpleList.parseFrom(bytes);
			copyFrom(simpleList);
		} catch (InvalidProtocolBufferException e) {
			throw new IllegalArgumentException(e);
		}
	}

	@Override
	public byte[] toByteArray() {
		return copyTo().toByteArray();
	}

	@Override
	public boolean add(byte[] e) {
		return this.elements.add(e);
	}

	@Override
	public void add(int index, byte[] element) {
		this.elements.add(index, element);
	}

	@Override
	public boolean addAll(Collection<? extends byte[]> c) {
		return this.elements.addAll(c);
	}

	@Override
	public boolean addAll(int index, Collection<? extends byte[]> c) {
		return this.elements.addAll(index, c);
	}

	@Override
	public void clear() {
		this.elements.clear();
	}

	@Override
	public boolean contains(Object o) {
		return this.elements.contains(o);
	}

	@Override
	public boolean containsAll(Collection<?> c) {
		return this.elements.containsAll(c);
	}

	@Override
	public byte[] get(int index) {
		return this.elements.get(index);
	}

	@Override
	public int indexOf(Object o) {
		return this.elements.indexOf(o);
	}

	@Override
	public boolean isEmpty() {
		return this.elements.isEmpty();
	}

	@Override
	public Iterator<byte[]> iterator() {
		return this.elements.iterator();
	}

	@Override
	public int lastIndexOf(Object o) {
		return this.elements.lastIndexOf(o);
	}

	@Override
	public ListIterator<byte[]> listIterator() {
		return this.elements.listIterator();
	}

	@Override
	public ListIterator<byte[]> listIterator(int index) {
		return this.elements.listIterator(index);
	}

	@Override
	public boolean remove(Object o) {
		return this.elements.remove(o);
	}

	@Override
	public byte[] remove(int index) {
		return this.elements.remove(index);
	}

	public byte[] removeFirst(){
	    return this.elements.remove(0);
	}
	
	@Override
	public boolean removeAll(Collection<?> c) {
		return this.elements.removeAll(c);
	}

	@Override
	public boolean retainAll(Collection<?> c) {
		return this.elements.retainAll(c);
	}

	@Override
	public byte[] set(int index, byte[] element) {
		return this.elements.set(index, element);
	}

	@Override
	public int size() {
		return this.elements.size();
	}

	@Override
	public List<byte[]> subList(int fromIndex, int toIndex) {
		return this.elements.subList(fromIndex, toIndex);
	}

	@Override
	public Object[] toArray() {
		return this.elements.toArray();
	}

	@Override
	public <T> T[] toArray(T[] a) {
		return this.elements.toArray(a);
	}

	public void add(byte[] bytes, int limit) {
		while(this.size() > limit){
			this.removeFirst();
		}
		
		this.elements.add(bytes);
	}
	
}

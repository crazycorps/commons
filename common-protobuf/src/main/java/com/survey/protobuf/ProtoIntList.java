package com.survey.protobuf;

import java.util.Arrays;

import com.google.protobuf.GeneratedMessage;
import com.google.protobuf.InvalidProtocolBufferException;
import com.survey.protobuf.proto.ToolsProto;

public class ProtoIntList implements ProtobufSerializable<ToolsProto.IntList> {

    /**
     * The array buffer into which the elements of the ArrayList are stored. The
     * capacity of the ArrayList is the length of this array buffer.
     */
    private transient int[] elementData;

    /**
     * The size of the ArrayList (the number of elements it contains).
     * 
     * @serial
     */
    protected int size;

    @SuppressWarnings("unused")
	private int modCount = 0;

    /**
     * Constructs an empty list with the specified initial capacity.
     * 
     * @param initialCapacity
     *            the initial capacity of the list
     * @exception IllegalArgumentException
     *                if the specified initial capacity is negative
     */
    public ProtoIntList(int initialCapacity) {
        super();
        if (initialCapacity < 0)
            throw new IllegalArgumentException("Illegal Capacity: " + initialCapacity);
        this.elementData = new int[initialCapacity];
    }

    /**
     * Constructs an empty list with an initial capacity of ten.
     */
    public ProtoIntList() {
        this(10);
    }

    public void trimToSize() {
        modCount++;
        int oldCapacity = elementData.length;
        if (size < oldCapacity) {
            elementData = Arrays.copyOf(elementData, size);
        }
    }

    public void ensureCapacity(int minCapacity) {
        modCount++;
        int oldCapacity = elementData.length;
        if (minCapacity > oldCapacity) {
            int newCapacity = (oldCapacity * 3) / 2 + 1;
            if (newCapacity < minCapacity)
                newCapacity = minCapacity;
            // minCapacity is usually close to size, so this is a win:
            elementData = Arrays.copyOf(elementData, newCapacity);
        }
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public boolean contains(int o) {
        return indexOf(o) >= 0;
    }

    public int indexOf(int o) {
        for (int i = 0; i < size; i++)
            if (o == elementData[i])
                return i;
        return -1;
    }

    public int lastIndexOf(int o) {
        for (int i = size - 1; i >= 0; i--)
            if (o == elementData[i])
                return i;
        return -1;
    }

    public int[] toArray() {
        return Arrays.copyOf(elementData, size);
    }

    // Positional Access Operations
    public int get(int index) {
        RangeCheck(index);

        return elementData[index];
    }

    public int set(int index, int element) {
        RangeCheck(index);

        int oldValue = elementData[index];
        elementData[index] = element;
        return oldValue;
    }

    public boolean add(int e) {
        ensureCapacity(size + 1); // Increments modCount!!
        elementData[size++] = e;
        return true;
    }

    public void add(int index, int element) {
        if (index > size || index < 0)
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);

        ensureCapacity(size + 1); // Increments modCount!!
        System.arraycopy(elementData, index, elementData, index + 1, size - index);
        elementData[index] = element;
        size++;
    }

    public int removeByIndex(int index) {
        RangeCheck(index);

        modCount++;
        int oldValue = elementData[index];

        int numMoved = size - index - 1;
        if (numMoved > 0)
            System.arraycopy(elementData, index + 1, elementData, index, numMoved);
        elementData[--size] = 0; // Let gc do its work

        return oldValue;
    }

    public boolean remove(int o) {
        for (int index = 0; index < size; index++) {
            if (o == elementData[index]) {
                fastRemove(index);
                return true;
            }
        }
        return false;
    }

    /*
     * Private remove method that skips bounds checking and does not return the
     * value removed.
     */
    private void fastRemove(int index) {
        modCount++;
        int numMoved = size - index - 1;
        if (numMoved > 0)
            System.arraycopy(elementData, index + 1, elementData, index, numMoved);
        elementData[--size] = 0; // Let gc do its work
    }

    public void clear() {
        modCount++;

        // Let gc do its work
        for (int i = 0; i < size; i++)
            elementData[i] = 0;

        size = 0;
    }


    /**
     * Removes from this list all of the elements whose index is between
     * <tt>fromIndex</tt>, inclusive, and <tt>toIndex</tt>, exclusive. Shifts
     * any succeeding elements to the left (reduces their index). This call
     * shortens the list by <tt>(toIndex - fromIndex)</tt> elements. (If
     * <tt>toIndex==fromIndex</tt>, this operation has no effect.)
     * 
     * @param fromIndex
     *            index of first element to be removed
     * @param toIndex
     *            index after last element to be removed
     * @throws IndexOutOfBoundsException
     *             if fromIndex or toIndex out of range (fromIndex &lt; 0 ||
     *             fromIndex &gt;= size() || toIndex &gt; size() || toIndex &lt;
     *             fromIndex)
     */
    protected void removeRange(int fromIndex, int toIndex) {
        modCount++;
        int numMoved = size - toIndex;
        System.arraycopy(elementData, toIndex, elementData, fromIndex, numMoved);

        // Let gc do its work
        int newSize = size - (toIndex - fromIndex);
        while (size != newSize)
            elementData[--size] = 0;
    }

    /**
     * Checks if the given index is in range. If not, throws an appropriate
     * runtime exception. This method does *not* check if the index is negative:
     * It is always used immediately prior to an array access, which throws an
     * ArrayIndexOutOfBoundsException if index is negative.
     */
    private void RangeCheck(int index) {
        if (index >= size)
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
    }

    public void sort() {
        Arrays.sort(elementData, 0, size);
    }

    @Override
    public void copyFrom(ToolsProto.IntList intList) {
        int size = intList.getElementsCount();
        this.elementData = new int[size + 3];
        for (int i = 0; i < size; i++) {
            this.add(intList.getElements(i));
        }
    }

    @Override
    public ToolsProto.IntList copyTo() {
        ToolsProto.IntList.Builder builder = ToolsProto.IntList.newBuilder();
        for (int i = 0; i < size; i++) {
            builder.addElements(this.get(i));
        }
        return builder.build();
    }

    @Override
    public void parseFrom(byte[] bytes) {
        try {
            ToolsProto.IntList intList = ToolsProto.IntList.parseFrom(bytes);
            copyFrom(intList);
        } catch (InvalidProtocolBufferException e) {
            throw new IllegalArgumentException(e);
        }
    }

    @Override
    public byte[] toByteArray() {
        ToolsProto.IntList intList = (ToolsProto.IntList) copyTo();
        return intList.toByteArray();
    }
    
    public ProtoIntList(byte[] bytes) {
        parseFrom(bytes);
    }
    
}

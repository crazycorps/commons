package com.survey.tools.util;

public class BitUtils {
	
	public static int append(int source,int index){
		return source|(1<<index);
	}
	
	public static int remove(int source,int index){
		return (source^(1<<index));
	}
	
	public static boolean isExist(int source,int index){
		int indexValue=1<<index;
		return (source&indexValue)==indexValue;
	}
	
	public static void main(String args[]){
		int source=0;
		for(int i=0;i<=3;i++){
			source=BitUtils.append(source, i);
			System.out.println(source);
			System.out.println(BitUtils.isExist(source, i));
			System.out.println(source+"......");
		}
		source=BitUtils.remove(source, 2);
		System.out.println(source);
		System.out.println(BitUtils.isExist(source, 2));
	}

}

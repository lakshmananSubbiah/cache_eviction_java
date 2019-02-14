package com.cache.eviction.strategies;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

public class FIFO {
	static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	
	static int counter = 0;
	
	public static void main(String[] args) throws IOException {
		System.out.println("\nEnter the size of the cache");
		int cacheSize = Integer.parseInt(br.readLine());
		Integer[] cache = new Integer[cacheSize];
		cache = initializeCache(cache);
		print(cache,"The Initial Cache Scenario");
		while(true) {
			System.out.println("\nWant to Insert More ?  Y/N");
			String s = br.readLine();
			if(s.equals("N")) {
				System.exit(0);
			}
			fifoCache(cache);
		}
		
	}

	private static void fifoCache(Integer[] cache) throws IOException {
		System.out.println("\nEnter the number to be given as input into the FIFO Cache");
		String numberInput = br.readLine();
		int currentElement = Integer.parseInt(numberInput);
		boolean elementExist = Arrays.stream(cache).anyMatch(x -> x == currentElement);
		if(!elementExist) {
			evictElement(cache,currentElement);
		}
		print(cache,"The current cache after adding element :"+currentElement);
	}

	private static void evictElement(Integer[] cache,int currentElement) {
		int elementToBeEvicted = cache[counter];
		cache[counter] = currentElement;
		counter = (counter+1)%(cache.length);
		System.out.println("Evicted Element: "+elementToBeEvicted);
	}

	private static void print(Integer[] cache,String content) {
		System.out.println(content);
		for(int element: cache) {
			System.out.print("\t "+element);
		}
	}

	private static Integer[] initializeCache(Integer[] cache) {
		Arrays.fill(cache, -1);
		return cache;
	}
	
	
}

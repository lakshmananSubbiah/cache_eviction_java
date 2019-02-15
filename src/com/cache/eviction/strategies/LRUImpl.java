package com.cache.eviction.strategies;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Timestamp;
import java.util.Arrays;

class LRU {

	private Integer element;
	
	private Long time;
	
	public LRU(Integer element) {
		this.element = element;
		this.time = System.currentTimeMillis();
	}
	
	public Integer getElement() {
		return this.element;
	}
	
	public Long getTime() {
		return this.time;
		
	}
	
	public void updateTime(Long time) {
		this.time = time;
	}
	
	public boolean equals(LRU lru) {
		return lru.getElement() == this.element;
	}
	
}

public class LRUImpl{
	static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	
	
	public static void main(String[] args) throws NumberFormatException, IOException {
		System.out.println("\nEnter the size of the cache");
		int cacheSize = Integer.parseInt(br.readLine());
		LRU[] lruCache = new LRU[cacheSize];
		lruCache = initializeCache(lruCache);
		print(lruCache,"The initial State");
		while(true) {
			System.out.println("\nWant to Insert More ?  Y/N");
			String s = br.readLine();
			if(s.equals("N")) {
				System.exit(0);
			}
			leastRecentlyUsedCache(lruCache);
		}
		
	}

	private static void leastRecentlyUsedCache(LRU[] lruCache) throws IOException {
		System.out.println("\nEnter the number to be given as input into the LRU Cache");
		String numberInput = br.readLine();
		LRU currentElement = new LRU(Integer.parseInt(numberInput));
		boolean elementExist = Arrays.stream(lruCache).anyMatch(x -> x.equals(currentElement));
		if(elementExist) {
			updateTime(currentElement,lruCache);
		}
		else {
			int pointer = calCulateNextPointer(lruCache);
			evict(lruCache,currentElement,pointer);
		}
		print(lruCache, "The current Cache after adding element");
	}

	private static int calCulateNextPointer(LRU[] lruCache) {
		Long time = lruCache[0].getTime();
		int pointer = 0; 
		for(int i=1;i<lruCache.length;i++) {
			if(lruCache[i].getTime()< time) {
				time = lruCache[i].getTime();
				pointer = i;
			}
		}
		return pointer;
	}

	private static void evict(LRU[] lruCache,LRU currentElement,int pointer) {
		System.out.println("Evicted Element : "+lruCache[pointer].getElement());
		lruCache[pointer] = currentElement;
	}

	private static void updateTime(LRU currentElement, LRU[] lruCache) {
		for(LRU lru:lruCache) {
			if(lru.equals(currentElement)) {
				lru.updateTime(currentElement.getTime());
			}
		}
	}

	private static void print(LRU[] lruCache, String string) {
		System.out.println(string);
		for(LRU lru : lruCache) {
			System.out.print("\nElement : "+lru.getElement());
			System.out.print("\t Last Used Time : "+new Timestamp(lru.getTime()*1000));
		}
		
	}

	private static LRU[] initializeCache(LRU[] lruCache) {
		LRU lru = new LRU(-1);
		Arrays.fill(lruCache, lru);
		return lruCache;
	}
}
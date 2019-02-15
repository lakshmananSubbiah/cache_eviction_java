package com.cache.eviction.strategies;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

class LFU{

	private Integer element;
	
	private int counter;
	
	public LFU(Integer element, int negativeCount) {
		this.element = element;
		this.counter = negativeCount;
	}
	
	public LFU(Integer element) {
		this.element = element;
		counter = 0;
	}
		
	public boolean equals(LFU anotherLfu) {
		return this.element == anotherLfu.getElement();
	}
	
	public Integer getElement() {
		return this.element;
	}
	
	public Integer getCounter() {
		return this.counter;
	}
	
	public void incrementCounter() {
		this.counter++;
	}
}

public class LFUImpl {
	static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	
	public static void main(String[] args) throws NumberFormatException, IOException {
		System.out.println("\nEnter the size of the cache");
		int cacheSize = Integer.parseInt(br.readLine());
		LFU[] lfuCache = new LFU[cacheSize];
		lfuCache = initializeCache(lfuCache);
		print(lfuCache,"The initial State");
		while(true) {
			System.out.println("\nWant to Insert More ?  Y/N");
			String s = br.readLine();
			if(s.equals("N")) {
				System.exit(0);
			}
			leastFrequentlyUsedCache(lfuCache);
		}
	}
	
	private static LFU[] initializeCache(LFU[] lfuCache) {
		LFU lfu = new LFU(-1,-1);
		Arrays.fill(lfuCache, lfu);
		return lfuCache;
	}
	
	private static void print(LFU[] lfuCache, String string) {
		System.out.println(string);
		for(LFU lfu : lfuCache) {
			System.out.print("\nElement : "+lfu.getElement());
			System.out.print("\t Counter: "+lfu.getCounter());
		}
	}	
	
	private static void leastFrequentlyUsedCache(LFU[] lfuCache) throws IOException {
		System.out.println("\nEnter the number to be given as input into the LFU Cache");
		String numberInput = br.readLine();
		LFU currentElement = new LFU(Integer.parseInt(numberInput));
		boolean elementExist = Arrays.stream(lfuCache).anyMatch(x -> x.equals(currentElement));
		if(elementExist) {
			updateCount(currentElement,lfuCache);
		}
		else {
			int pointer = calCulateNextPointer(lfuCache);
			evict(lfuCache,currentElement,pointer);
		}
		print(lfuCache, "The current Cache after adding element");
	}

	private static void evict(LFU[] lfuCache, LFU currentElement, int pointer) {
		System.out.println("Evicted Element : "+lfuCache[pointer].getElement());
		lfuCache[pointer] = currentElement;
	}

	private static int calCulateNextPointer(LFU[] lfuCache) {
		int pointer = 0; 
		int counter = lfuCache[0].getCounter();
		for(int i=0;i<lfuCache.length;i++) {
			if(counter > lfuCache[i].getCounter()) {
				pointer = i;
				counter = lfuCache[i].getCounter();
			}
		}
		return pointer;
	}

	private static void updateCount(LFU currentElement, LFU[] lfuCache) {
		for(LFU lfu:lfuCache) {
			if(lfu.equals(currentElement)) {
				lfu.incrementCounter();
			}
		}
	}
	
}

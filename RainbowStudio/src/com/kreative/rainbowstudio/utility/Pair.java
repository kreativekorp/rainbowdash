package com.kreative.rainbowstudio.utility;

public class Pair<A, B> {
	private A a;
	private B b;
	
	public Pair(A a, B b) {
		this.a = a;
		this.b = b;
	}
	
	public A getFormer() {
		return a;
	}
	
	public B getLatter() {
		return b;
	}
}

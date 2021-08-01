package com.oasis.util.security;

public class DefaultSecure implements ISecure{
	public String encrypt(String s){
		return s;
	}
	public String encrypt(String s, String k){
		return s;
	}
	
	public String decrypt(String s){
		return s;
	}
	public String decrypt(String s, String k){
		return s;
	}
	public boolean isOneWay(){
		return false;
	}
}

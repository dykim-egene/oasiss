package com.oasis.util.security;

public interface ISecure {
	String encrypt(String s);
	String encrypt(String s, String k);
	String decrypt(String s);
	String decrypt(String s, String k);
	boolean isOneWay();
}

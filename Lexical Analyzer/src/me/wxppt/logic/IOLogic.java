package me.wxppt.logic;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class IOLogic {
	
	public String keyboardInput() {
		BufferedReader bfr = new BufferedReader(new InputStreamReader(System.in));
		String input = "";
		try {
			input = bfr.readLine();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return input;
	}
	
	public void print(Object content) {
		System.out.print(content);
	}
	public void println(Object content) {
		System.out.println(content);
	}
}

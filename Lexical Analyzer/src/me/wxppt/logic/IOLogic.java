package me.wxppt.logic;

import java.io.BufferedReader;
import java.io.FileInputStream;
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
	
	public String fileInput(String path) {
		String content = " ";
		try {
			BufferedReader bfr = new BufferedReader(new InputStreamReader(new FileInputStream(path)));
			String line = null;
			while((line = bfr.readLine()) != null) {
				content += line;
			}
			bfr.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		content = content.replace(" ", "   ");
		content = content.replace("=", "  =  ");
		content = content.replace(">", "  >  ");
		content = content.replace("<", "  <  ");
		content = content.replace(">=", "  >=  ");
		content = content.replace("<=", "  <=  ");
		content = content.replace("==", "  ==  ");
		content = content.replace("!=", "  !=  ");
		content = content.replace("(", "  (  ");
		content = content.replace(")", "  )  ");
		content = content.replace("[", "  [  ");
		content = content.replace("]", "  ]  ");
		content = content.replace("{", "  {  ");
		content = content.replace("}", "  }  ");
		content = content.replace(",", ",   ");
		content = content.replace(";", ";   ");
		content = content.replace("\n","   ");
		content = content.replace("\t", "   ");
		return content;
	}
	
	public void print(Object content) {
		System.out.print(content);
	}
	public void println(Object content) {
		System.out.println(content);
	}
}

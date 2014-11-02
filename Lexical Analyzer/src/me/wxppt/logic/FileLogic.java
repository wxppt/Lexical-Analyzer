package me.wxppt.logic;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;

public class FileLogic {
	public char[] fileInput(String path) {
		String content = " ";
		try {
			BufferedReader bfr = new BufferedReader(new InputStreamReader(new FileInputStream(path)));
			String line = null;
			while((line = bfr.readLine()) != null) {
				content +="  " +  line + "  ";
			}
			bfr.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		String[] replaceChars = {" ","=",">","<",">=","<=","==","!=","(","[","]","{","}",",",";","\n","\t"};
		for(int i = 0;i<replaceChars.length;i++) {
			content = content.replace(replaceChars[i], " " + replaceChars[i] + " ");
		}
		char[] srcArr = content.toCharArray();
		return preprocess(srcArr);
	}
	
	private static char[] preprocess(char[] srcArr) {
		for (int i = 0; i < srcArr.length; i++) {
			if (srcArr[i] == '\t') {
				srcArr[i] = ' ';
			}
			if (srcArr[i] == '\n') {
				srcArr[i] = ' ';
			}
		}
		return srcArr;
	}
	
	public void saveFile(String path,String content) throws IOException {
		BufferedWriter bfw = new BufferedWriter(new FileWriter(path));
		bfw.write(content);
		bfw.close();
	}
}

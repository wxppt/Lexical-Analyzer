package me.wxppt.logic;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Stack;

import me.wxppt.adt.ReElement;
import me.wxppt.adt.Regular;

public class ReLogic {
	public ArrayList<Regular> readRe(String path) {
		ArrayList<String> content = new ArrayList<String>();
		ArrayList<Regular> res = new ArrayList<Regular>();
		try {
			BufferedReader bfr = new BufferedReader(new InputStreamReader(new FileInputStream(path)));
			String line = null;
			while((line = bfr.readLine()) != null) {
				content.add(line);
			}
			bfr.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
//		char start = 'a';
//		String atoz = "";
//		while(start <= 'z'){
//			atoz += start + "|";
//			start++;
//		}
//		atoz = atoz.substring(0,atoz.length()-1);
//		System.out.println(atoz);
//		System.out.println(atoz.toUpperCase());
//		
//		start = '0';
//		String zerotonine = "";
//		while(start <= '9'){
//			zerotonine += start + "|";
//			start++;
//		}
//		zerotonine = zerotonine.substring(0,zerotonine.length()-1);
//		System.out.println(zerotonine);
		
		for(String line :content) {
			Regular re = new Regular();
			String[] sp = line.split("======");
			re.type = sp[0].split(":")[0];
			re.priority = Integer.parseInt(sp[0].split(":")[1]);
			re.regular = sp[1];
			System.out.println(re);
			res.add(re);
		}
		return res;
	}
	
	public ArrayList<ReElement> preprocessing(String input) {
		char[] re = input.toCharArray();
		ArrayList<ReElement> reItems = new ArrayList<ReElement>();

		// 把转义字符放在一起
		try {
			for (int i = 0; i < re.length; i++) {
				// 检测是否是转义
				if (re[i] != '\\') {
					reItems.add(new ReElement(re[i], false));
				} else {
					reItems.add(new ReElement(re[i + 1], true));
					i++;
				}
			}
		} catch (Exception e) { // 数组越界或字符错误
			e.printStackTrace();
			System.out.println("ERROR RE: " + input);
		}

		// 加点
		try {
			for (int i = 0; i < reItems.size() - 1; i++) {
				if (reItems.get(i).isChar() && reItems.get(i + 1).isChar()) {
					reItems.add(i + 1, new ReElement('.', false));
					i++;
				}
				if (reItems.get(i).isChar() && reItems.get(i + 1).equals(new ReElement('(', false))) {
					reItems.add(i + 1, new ReElement('.', false));
					i++;
				}
				if (reItems.get(i).equals(new ReElement(')', false))
						&& reItems.get(i + 1).equals(new ReElement('(', false))) {
					reItems.add(i + 1, new ReElement('.', false));
					i++;
				}
				if (reItems.get(i).equals(new ReElement('*', false))
						&& reItems.get(i + 1).equals(new ReElement('(', false))) {
					reItems.add(i + 1, new ReElement('.', false));
					i++;
				}
				if (reItems.get(i).equals(new ReElement(')', false))
						&& reItems.get(i + 1).isChar()) {
					reItems.add(i + 1, new ReElement('.', false));
					i++;
				}
				if (reItems.get(i).equals(new ReElement('*', false))
						&& reItems.get(i + 1).isChar()) {
					reItems.add(i + 1, new ReElement('.', false));
					i++;
				}
			}
		} catch (Exception e) { // 不可能出错
		}

		// for (int i = 0; i < reItems.size(); i++) {
		// System.out.println(reItems.get(i));
		// }
		return reItems;
	}

	public ArrayList<ReElement> infixToPostfix(ArrayList<ReElement> reItems) {
		ArrayList<ReElement> postReItems = new ArrayList<ReElement>();
		Stack<ReElement> stack = new Stack<ReElement>();
		for (ReElement r : reItems) {
			if (r.isChar()) {
				postReItems.add(r);
			} else if (r.equals(ReElement.getReElement('('))) {
				stack.push(r);
			} else if (r.equals(ReElement.getReElement(')'))) {
				if (stack.isEmpty()) {
					System.err.println("ERROR ()");
					System.exit(-1);
				} else {
					while (!stack.peek().equals(ReElement.getReElement('('))) {
						postReItems.add(stack.pop());
					}
					stack.pop(); // 弹出括号
				}
			} else {
				while (!stack.isEmpty()
						&& !stack.peek().equals(ReElement.getReElement('('))
						&& stack.peek().getPriority() >= r.getPriority()) {
					postReItems.add(stack.pop());

				}
				stack.push(r);
			}
		}
		while (!stack.isEmpty()) {

			if (!stack.peek().equals(ReElement.getReElement('('))) {
				postReItems.add(stack.pop());
			} else {
				System.err.println("ERROR ()");
				System.exit(-1);
			}
		}
		for (int i = 0; i < postReItems.size(); i++) {
			System.out.println(postReItems.get(i));
		}
		return postReItems;
	}
}

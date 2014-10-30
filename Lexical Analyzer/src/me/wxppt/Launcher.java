package me.wxppt;

import java.util.ArrayList;
import java.util.Stack;

import me.wxppt.adt.NfaState;
import me.wxppt.adt.ReItem;
import me.wxppt.logic.IOLogic;
import me.wxppt.logic.NfaLogic;

public class Launcher {
	public static void main(String[] args) {
		IOLogic io = new IOLogic();
		String input = io.keyboardInput();
		ArrayList<ReItem> reItems = preprocessing(input);
		ArrayList<ReItem> postReItems = infixToPostfix(reItems);
		NfaState nfa = NfaLogic.create(postReItems);
		NfaLogic.print(nfa);
	}

	public static ArrayList<ReItem> preprocessing(String input) {
		char[] re = input.toCharArray();
		ArrayList<ReItem> reItems = new ArrayList<ReItem>();

		// 把转义字符放在一起
		try {
			for (int i = 0; i < re.length; i++) {
				// 检测是否是转义
				if (re[i] != '\\') {
					reItems.add(new ReItem(re[i], false));
				} else {
					reItems.add(new ReItem(re[i + 1], true));
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
					reItems.add(i + 1, new ReItem('.', false));
					i++;
				}
				if (reItems.get(i).equals(new ReItem(')', false))
						&& reItems.get(i + 1).equals(new ReItem('(', false))) {
					reItems.add(i + 1, new ReItem('.', false));
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

	public static ArrayList<ReItem> infixToPostfix(ArrayList<ReItem> reItems) {
		ArrayList<ReItem> postReItems = new ArrayList<ReItem>();
		Stack<ReItem> stack = new Stack<ReItem>();
		for (ReItem r : reItems) {
			if (r.isChar()) {
				postReItems.add(r);
			} else if (r.equals(ReItem.getReElement('('))) {
				stack.push(r);
			} else if (r.equals(ReItem.getReElement(')'))) {
				if (stack.isEmpty()) {
					System.err.println("ERROR ()");
					System.exit(-1);
				} else {
					while (!stack.peek().equals(ReItem.getReElement('('))) {
						postReItems.add(stack.pop());
					}
					stack.pop(); // 弹出括号
				}
			} else {
				while (!stack.isEmpty()
						&& !stack.peek().equals(ReItem.getReElement('('))
						&& stack.peek().getPriority() >= r.getPriority()) {
					postReItems.add(stack.pop());

				}
				stack.push(r);
			}
		}
		while (!stack.isEmpty()) {

			if (!stack.peek().equals(ReItem.getReElement('('))) {
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

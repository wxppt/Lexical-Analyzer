package me.wxppt.logic;

import java.util.ArrayList;

import me.wxppt.adt.SearchTable;

public class AnalysiserLogic {
	private String returnMsg = "";
	private String output = "";
	private int confirmedIndex = 0;
	private ArrayList<String> symbolTable = new ArrayList<String>();
	private ArrayList<String> tokenTable = new ArrayList<String>();

	public void scan(char[] srcArr, int start) {
		int state = start;
		int point = 0;
		while (point < srcArr.length) {
			Integer tmp = go(state, srcArr[point]);
			if (tmp == null) {
				if (state == 0) {
					System.err.println("ERROR CHARACTER: " + srcArr[point]);
					System.exit(-1);
				}
				String word = output.substring(0, output.length()
						- (point - confirmedIndex) + 1);
				if (returnMsg.equals("ID")) {
					if (!symbolTable.contains(word)) {
						symbolTable.add(word);
					}
					tokenTable.add("TOKEN (" + word + "," + returnMsg + ","
							+ symbolTable.indexOf(word) + ")");
				} else {
					if (!word.equals(" ")) {
						tokenTable.add("TOKEN (" + word.trim() + ","
								+ returnMsg + ",/)");
//					} else if (tokenTable.size() > 0
//							&& !tokenTable.get(tokenTable.size() - 1).equals(
//									"TOKEN ( ,BLANK,/)")) {
//						tokenTable
//								.add("TOKEN (" + word + "," + returnMsg + ",/)");
//					} else if (tokenTable.size() == 0) {
//						tokenTable.add("TOKEN ( ,BLANK,/)");
					}
				}
				state = start;
				output = "";
				point = ++confirmedIndex;
			} else {
				output += srcArr[point];
				state = tmp;
				if (SearchTable.returnTable.containsKey(state)) {
					returnMsg = SearchTable.returnTable.get(state).message;
					confirmedIndex = point;
				}
				point++;
			}
		}
		if (state != start) {
			String word = output.substring(0, output.length()
					- (point - confirmedIndex) + 1);
			if (returnMsg.equals("ID")) {
				if (!symbolTable.contains(word)) {
					symbolTable.add(word);
				}
				tokenTable.add("TOKEN (" + word + "," + returnMsg + ","
						+ symbolTable.indexOf(word) + ")");
			} else {
				if (!word.equals(" ")) {
					tokenTable.add("TOKEN (" + word.trim() + "," + returnMsg
							+ ")");
//				} else if (tokenTable.size() > 0
//						&& !tokenTable.get(tokenTable.size() - 1).equals(
//								"TOKEN ( ,BLANK)")) {
//					tokenTable.add("TOKEN (" + word + "," + returnMsg + ")");
				}
			}
			state = start;
		}
	}

	private Integer go(int state, char c) {
		return SearchTable.table.get(state).get(c);
	}

	public ArrayList<String> getSymbolTable() {
		return symbolTable;
	}

	public ArrayList<String> getTokenTable() {
		return tokenTable;
	}
	
	
}

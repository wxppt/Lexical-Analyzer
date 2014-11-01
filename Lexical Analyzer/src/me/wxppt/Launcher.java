package me.wxppt;

import java.util.ArrayList;

import me.wxppt.adt.DfaState;
import me.wxppt.adt.NfaState;
import me.wxppt.adt.ReElement;
import me.wxppt.adt.Regular;
import me.wxppt.adt.SearchTable;
import me.wxppt.adt.StateProperty;
import me.wxppt.logic.DfaLogic;
import me.wxppt.logic.IOLogic;
import me.wxppt.logic.NfaLogic;
import me.wxppt.logic.ReLogic;

public class Launcher {
	public static void main(String[] args) {
		ReLogic reLogic = new ReLogic();
		ArrayList<Regular> res = reLogic.readRe("java.re");
		System.out.println("---------------");
		NfaState start = new NfaState();
		start.property = StateProperty.START;
		NfaLogic nfaLogic = new NfaLogic();
		for (Regular re : res) {
			ArrayList<ReElement> reItems = reLogic.preprocessing(re.regular);
			ArrayList<ReElement> postReItems = reLogic.infixToPostfix(reItems);
			NfaState nfa = nfaLogic.create(postReItems);
			nfa.setReturnInfo(re.type, re.priority);
			nfaLogic.print(nfa);
			start.link(ReElement.getEmptyElement(), nfa);
		}
		nfaLogic.print(start);
		System.out.println("---------------");
		DfaLogic dfaLogic = new DfaLogic();
		DfaState dfa = dfaLogic.nfaToDfa(start);
		dfaLogic.check(dfa);
		dfaLogic.print(dfa);
		dfaLogic.constructSearchTable(dfa);
		System.out.println("---------------");
		IOLogic ioLogic = new IOLogic();
		String sourceCode = ioLogic.fileInput("SourceCode.java");
		char[] srcArr = sourceCode.toCharArray();
		srcArr = preprocess(srcArr);
		scan(srcArr, 0);
		for(int i = 0; i < tokenTable.size();i++) {
			System.out.println(tokenTable.get(i));
		}
		for(int i = 0; i < symbolTable.size();i++) {
			System.out.println(i + " - " + symbolTable.get(i));
		}
		
	}
	static String returnMsg = "";
	static String output = "";
	static int confirmedIndex = 0;
	static ArrayList<String> symbolTable = new ArrayList<String>();
	static ArrayList<String> tokenTable = new ArrayList<String>();
	
	private static void scan(char[] srcArr, int start) {
		int state = start;
		int point = 0;
		while (point < srcArr.length) {
			Integer tmp = go(state, srcArr[point]);
			if(tmp == null) {
				if(state == 0) {
					System.out.println("ERROR WORDS");
					return;
				}
				String word = output.substring(0,output.length()-(point-confirmedIndex)+1);
				if(returnMsg.equals("ID")) {
					if(!symbolTable.contains(word)) {
						symbolTable.add(word);
					}
					tokenTable.add("TOKEN (" + word + "," + returnMsg + "," + symbolTable.indexOf(word) + ")");
				} else {
					if(!word.equals(" ")) {
						tokenTable.add("TOKEN (" + word.trim() + "," + returnMsg + ")");
					} else if(tokenTable.size() > 0 && !tokenTable.get(tokenTable.size()-1).equals("TOKEN ( ,BLANK)")) {
						tokenTable.add("TOKEN (" + word + "," + returnMsg + ")");
					} else if(tokenTable.size() == 0) {
						tokenTable.add("TOKEN ( ,BLANK)");
					}
				}
				state = start;
				output = "";
				point = ++confirmedIndex;
			} else {
				output += srcArr[point];
				state = tmp;
				if(SearchTable.returnTable.containsKey(state)) {
					returnMsg = SearchTable.returnTable.get(state).message;
					confirmedIndex = point;
				}
				point++;
			}
		}
		if(state != start) {
			String word = output.substring(0,output.length()-(point-confirmedIndex)+1);
			if(returnMsg.equals("ID")) {
				if(!symbolTable.contains(word)) {
					symbolTable.add(word);
				}
				tokenTable.add("TOKEN (" + word + "," + returnMsg + "," + symbolTable.indexOf(word) + ")");
			} else {
				if(!word.equals(" ")) {
					tokenTable.add("TOKEN (" + word.trim() + "," + returnMsg + ")");
				} else if(tokenTable.size() > 0 && !tokenTable.get(tokenTable.size()-1).equals("TOKEN ( ,BLANK)")) {
					tokenTable.add("TOKEN (" + word + "," + returnMsg + ")");
				}
			}
			state = start;
		}
	}
	
	private static Integer go(int state, char c) {
		return SearchTable.table.get(state).get(c);
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
}

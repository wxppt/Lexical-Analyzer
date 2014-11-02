package me.wxppt;

import java.io.IOException;
import java.util.ArrayList;

import me.wxppt.adt.DfaState;
import me.wxppt.adt.NfaState;
import me.wxppt.adt.ReElement;
import me.wxppt.adt.Regular;
import me.wxppt.adt.SearchTable;
import me.wxppt.adt.StateProperty;
import me.wxppt.logic.AnalysiserLogic;
import me.wxppt.logic.DfaLogic;
import me.wxppt.logic.FileLogic;
import me.wxppt.logic.NfaLogic;
import me.wxppt.logic.ReLogic;

public class Launcher {
	public static void main(String[] args) {
		ReLogic reLogic = new ReLogic();
		ArrayList<Regular> res = reLogic.readRe("Regular.re");
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
		FileLogic fileLogic = new FileLogic();
		char[] srcArr = fileLogic.fileInput("SourceCode");
		AnalysiserLogic anaLogic = new AnalysiserLogic();
		anaLogic.scan(srcArr, 0);
		String symbolContent = "";
		String tokenContent = "";
		for (int i = 0; i < anaLogic.getTokenTable().size(); i++) {
			System.out.println(anaLogic.getTokenTable().get(i));
			tokenContent += anaLogic.getTokenTable().get(i) + "\r\n";
		}
		for (int i = 0; i < anaLogic.getSymbolTable().size(); i++) {
			System.out.println(i + " - " + anaLogic.getSymbolTable().get(i));
			symbolContent += i + " - " + anaLogic.getSymbolTable().get(i) + "\r\n";
		}
		try {
			fileLogic.saveFile("Token.txt", tokenContent);
			fileLogic.saveFile("Symbol.txt", symbolContent);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	

	
}

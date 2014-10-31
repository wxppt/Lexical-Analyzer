package me.wxppt.logic;

import java.util.ArrayList;
import java.util.Stack;

import me.wxppt.adt.NfaState;
import me.wxppt.adt.ReItem;
import me.wxppt.adt.StateProperty;

public class NfaLogic {
	ArrayList<Integer> record = new ArrayList<Integer>();
	// 创建单一终态的NFA
	public NfaState create(ArrayList<ReItem> res) {
		Stack<NfaState> stack = new Stack<NfaState>();
		for (ReItem re : res) {
			if (re.isChar()) {
				// 开始状态
				NfaState startState = new NfaState();
				startState.property = StateProperty.START;
				// 结束状态
				NfaState endState = new NfaState();
				endState.property = StateProperty.END;
				// 边
				startState.link(re, endState);
				// 压栈
				stack.push(startState);
			} else if (re.equals(ReItem.getReElement('.'))) {
				NfaState nfa2 = stack.pop();
				NfaState nfa1 = stack.pop();
				nfa1.addToEnd(ReItem.getEmptyElement(), nfa2);
				// 压栈
				stack.push(nfa1);
			} else if (re.equals(ReItem.getReElement('|'))) {
				NfaState nfa2 = stack.pop();
				NfaState nfa1 = stack.pop();
				NfaState startState = new NfaState();
				startState.property = StateProperty.START;
				NfaState endState = new NfaState();
				endState.property = StateProperty.END;
				nfa1.addToEnd(ReItem.getEmptyElement(), endState);
				nfa2.addToEnd(ReItem.getEmptyElement(), endState);
				startState.link(ReItem.getEmptyElement(), nfa1);
				startState.link(ReItem.getEmptyElement(), nfa2);
				stack.push(startState);
			} else if (re.equals(ReItem.getReElement('*'))) {
				NfaState nfa = stack.pop();
				NfaState startState = new NfaState();
				startState.property = StateProperty.START;
				NfaState endState = new NfaState();
				endState.property = StateProperty.END;
				nfa.cycling(nfa);
				startState.link(ReItem.getEmptyElement(), nfa);
				startState.addToEnd(ReItem.getEmptyElement(), endState);
				startState.link(ReItem.getEmptyElement(), endState);
				stack.push(startState);
			}
		}
		return stack.pop();
	}

	public void print(NfaState nfa) {

		for (int i = 0; i < nfa.edgeCnt; i++) {
			System.out.println(nfa.no + "(" + nfa.property + ") --"
					+ nfa.edge[i].getC() + "-- " + nfa.next[i].no + "("
					+ nfa.next[i].property + ")");
			record.add(nfa.no);
			if (!record.contains(nfa.next[i].no)) {
				print(nfa.next[i]);
			}
		}
	}
}

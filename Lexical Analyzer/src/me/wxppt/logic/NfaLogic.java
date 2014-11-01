package me.wxppt.logic;

import java.util.ArrayList;
import java.util.Stack;

import me.wxppt.adt.NfaState;
import me.wxppt.adt.ReElement;
import me.wxppt.adt.StateProperty;

public class NfaLogic {
	ArrayList<Integer> record = new ArrayList<Integer>();
	// 创建单一终态的NFA
	public NfaState create(ArrayList<ReElement> res) {
		Stack<NfaState> stack = new Stack<NfaState>();
		for (ReElement re : res) {
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
			} else if (re.equals(ReElement.getReElement('.'))) {
				NfaState nfa2 = stack.pop();
				NfaState nfa1 = stack.pop();
				nfa1.addToEnd(ReElement.getEmptyElement(), nfa2);
				// 压栈
				stack.push(nfa1);
			} else if (re.equals(ReElement.getReElement('|'))) {
				NfaState nfa2 = stack.pop();
				NfaState nfa1 = stack.pop();
				NfaState startState = new NfaState();
				startState.property = StateProperty.START;
				NfaState endState = new NfaState();
				endState.property = StateProperty.END;
				nfa1.addToEnd(ReElement.getEmptyElement(), endState);
				nfa2.addToEnd(ReElement.getEmptyElement(), endState);
				startState.link(ReElement.getEmptyElement(), nfa1);
				startState.link(ReElement.getEmptyElement(), nfa2);
				stack.push(startState);
			} else if (re.equals(ReElement.getReElement('*'))) {
				NfaState nfa = stack.pop();
				NfaState startState = new NfaState();
				startState.property = StateProperty.START;
				NfaState endState = new NfaState();
				endState.property = StateProperty.END;
				nfa.cycling(nfa);
				startState.link(ReElement.getEmptyElement(), nfa);
				startState.addToEnd(ReElement.getEmptyElement(), endState);
				startState.link(ReElement.getEmptyElement(), endState);
				stack.push(startState);
			}
		}
		return stack.pop();
	}

	public void print(NfaState nfa) {

		for (int i = 0; i < nfa.edgeCnt; i++) {
			if(nfa.next[i].property == StateProperty.END) {
				System.out.println(nfa.no + "(" + nfa.property + ") --"
						+ nfa.edge[i].getC() + "-- " + nfa.next[i].no + "("
						+ nfa.next[i].property + ":" + nfa.next[i].returnMessage + ":" + nfa.next[i].priority + ")");
			} else {
				System.out.println(nfa.no + "(" + nfa.property + ") --"
						+ nfa.edge[i].getC() + "-- " + nfa.next[i].no + "("
						+ nfa.next[i].property + ")");
			}
			
			record.add(nfa.no);
			if (!record.contains(nfa.next[i].no)) {
				print(nfa.next[i]);
			}
		}
	}
}

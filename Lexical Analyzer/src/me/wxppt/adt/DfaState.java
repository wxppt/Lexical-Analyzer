package me.wxppt.adt;

import java.util.ArrayList;

public class DfaState {
	public static int STATE_INDEX = 0;

	public int no;

	public StateProperty property;
	public ReElement[] edge = new ReElement[100];
	public DfaState[] next = new DfaState[100];
	//public NfaState[] closure = new NfaState[1000];
	public ArrayList<NfaState> closure = new ArrayList<NfaState>();
	
	public String returnMessage = "";
	public int priority = -1;

	public int edgeCnt = 0;
//	public int closureCnt = 0;

	public DfaState() {
		no = STATE_INDEX;
		STATE_INDEX++;
	}

	public static void RESET() {
		STATE_INDEX = 0;
	}

	public boolean addToClosure(NfaState nfa) {
		if (!isInClosure(nfa)) {
			if (nfa.property == StateProperty.END) {
				property = StateProperty.END;
				returnMessage = nfa.returnMessage;
				priority = nfa.priority;
			}
			closure.add(nfa);
//			closureCnt++;
			return true;
		}
		return false;
	}

	public boolean isInClosure(NfaState nfa) {
//		for (int i = 0; i < closureCnt; i++) {
		for (int i = 0; i < closure.size(); i++) {
			if (closure.get(i).no == nfa.no) {
				return true;
			}
		}
		return false;
	}

	private int existEdge(ReElement re) {
		for (int i = 0; i < edgeCnt; i++) {
			if (edge[i].equals(re)) {
				return i;
			}
		}
		return -1;
	}

	public void addEdge(ReElement re, NfaState nfa) {
		int pos = existEdge(re);
		if (pos == -1) {
			edge[edgeCnt] = re;
			next[edgeCnt] = new DfaState();
			if (nfa.property == StateProperty.END) {
				next[edgeCnt].property = StateProperty.END;
				next[edgeCnt].returnMessage = nfa.returnMessage;
				next[edgeCnt].priority = nfa.priority;
			} else {
				next[edgeCnt].property = StateProperty.NORMAL;
			}
			next[edgeCnt].addToClosure(nfa);
			edgeCnt++;
		} else {
			if (nfa.property == StateProperty.END) {
				next[pos].property = StateProperty.END;
				next[pos].returnMessage = nfa.returnMessage;
				next[pos].priority = nfa.priority;
			}
			next[pos].addToClosure(nfa);
		}
	}

	public void deleteEdge(int index) {
		if (index >= 0 && index < edgeCnt) {
			for (int i = index; i < edgeCnt - 1; i++) {
				edge[i] = edge[i + 1];
				next[i] = next[i + 1];
			}
			edgeCnt--;
		} else {
			System.err.println("Error delete index");
		}
	}

	@Override
	public boolean equals(Object obj) {

		if (!(obj instanceof DfaState)) {
			return false;
		} else {
			DfaState d = (DfaState) obj;
//			if (edgeCnt != d.edgeCnt || closureCnt != d.closureCnt) {
			if (edgeCnt != d.edgeCnt || closure.size() != d.closure.size()) {
				return false;
			}
			for (int i = 0; i < edgeCnt; i++) {
				if (!edge[i].equals(d.edge[i])) {
					return false;
				}
			}
			for (int i = 0; i < closure.size(); i++) {
				if (closure.get(i).no != d.closure.get(i).no) {
					return false;
				}
			}
			return true;
		}

	}

}

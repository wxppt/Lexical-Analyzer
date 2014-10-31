package me.wxppt.adt;

public class DfaState {
	public static int STATE_INDEX = 0;

	public int no;

	public StateProperty property;
	public ReItem[] edge = new ReItem[100];
	public DfaState[] next = new DfaState[100];
	public NfaState[] closure = new NfaState[100];

	public int edgeCnt = 0;
	public int closureCnt = 0;

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
			}
			closure[closureCnt] = nfa;
			closureCnt++;
			return true;
		}
		return false;
	}

	public boolean isInClosure(NfaState nfa) {
		for (int i = 0; i < closureCnt; i++) {
			if (closure[i].no == nfa.no) {
				return true;
			}
		}
		return false;
	}

	private int existEdge(ReItem re) {
		for (int i = 0; i < edgeCnt; i++) {
			if (edge[i].equals(re)) {
				return i;
			}
		}
		return -1;
	}

	public void addEdge(ReItem re, NfaState nfa) {
		int pos = existEdge(re);
		if (pos == -1) {
			edge[edgeCnt] = re;
			next[edgeCnt] = new DfaState();
			if (nfa.property == StateProperty.END) {
				next[edgeCnt].property = StateProperty.END;
			} else {
				next[edgeCnt].property = StateProperty.NORMAL;
			}
			next[edgeCnt].addToClosure(nfa);
			edgeCnt++;
		} else {
			if (nfa.property == StateProperty.END) {
				next[pos].property = StateProperty.END;
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
			if (edgeCnt != d.edgeCnt || closureCnt != d.closureCnt) {
				return false;
			}
			for (int i = 0; i < edgeCnt; i++) {
				if (!edge[i].equals(d.edge[i])) {
					return false;
				}
			}
			for (int i = 0; i < closureCnt; i++) {
				if (closure[i].no != d.closure[i].no) {
					return false;
				}
			}
			return true;
		}

	}

}

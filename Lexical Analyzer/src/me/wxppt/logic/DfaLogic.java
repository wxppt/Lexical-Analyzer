package me.wxppt.logic;

import java.util.ArrayList;
import java.util.HashMap;

import me.wxppt.adt.DfaState;
import me.wxppt.adt.NfaState;
import me.wxppt.adt.ReElement;
import me.wxppt.adt.ReturnMessage;
import me.wxppt.adt.SearchTable;
import me.wxppt.adt.StateProperty;

public class DfaLogic {

	ArrayList<Integer> record = new ArrayList<Integer>();
	ArrayList<Integer> checkNo = new ArrayList<Integer>();
	ArrayList<DfaState> check = new ArrayList<DfaState>();
	ArrayList<DfaState> dfaAlready = new ArrayList<DfaState>();

	public DfaState nfaToDfa(NfaState start) {
		// 新建I0节点，把nfa的start加入
		DfaState startDfa = new DfaState();
		startDfa.addToClosure(start);
		startDfa.property = StateProperty.START;

		toDfaProcess(startDfa);
		return startDfa;
	}

	private void toDfaProcess(DfaState dfa) {

		// 遍历闭包中所有的节点
		// for(int i = 0; i < dfa.closureCnt;i++) {
		for (int i = 0; i < dfa.closure.size(); i++) {
			// NfaState curNfa = dfa.closure[i];
			NfaState curNfa = dfa.closure.get(i);
			// 扫描nfa中的所有边
			for (int j = 0; j < curNfa.edgeCnt; j++) {
				if (curNfa.edge[j].equals(ReElement.getEmptyElement())) {
					// 如果是空边，当前DFA的闭包中加入这个边的后继节点
					dfa.addToClosure(curNfa.next[j]);
				} else {
					// 加入这条边
					dfa.addEdge(curNfa.edge[j], curNfa.next[j]);
				}
			}
		}
		if (dfaAlready.contains(dfa)) {
			// dfa.property = StateProperty.DESTORY;
			return;
		}
		dfaAlready.add(dfa);
		for (int i = 0; i < dfa.edgeCnt; i++) {
			toDfaProcess(dfa.next[i]);
		}

	}

	public void print(DfaState dfa) {
		record.clear();
		printEx(dfa);
	}

	private void printEx(DfaState dfa) {
		record.add(dfa.no);
		for (int i = 0; i < dfa.edgeCnt; i++) {
			if (dfa.next[i].property == StateProperty.END) {
				System.out.println(dfa.no + "(" + dfa.property + ") --"
						+ dfa.edge[i].getC() + "-- " + dfa.next[i].no + "("
						+ dfa.next[i].property + ":"
						+ dfa.next[i].returnMessage + ":"
						+ dfa.next[i].priority + ")");
			} else {
				System.out.println(dfa.no + "(" + dfa.property + ") --"
						+ dfa.edge[i].getC() + "-- " + dfa.next[i].no + "("
						+ dfa.next[i].property + ")");
			}
			if (!record.contains(dfa.next[i].no)) {
				printEx(dfa.next[i]);
			}
		}
	}

	public void constructSearchTable(DfaState dfa) {
		record.clear();
		constructSearchTableEx(dfa);
	}

	private void constructSearchTableEx(DfaState dfa) {
		record.add(dfa.no);
		if (dfa.property == StateProperty.END) {
			SearchTable.returnTable.put(dfa.no, new ReturnMessage(dfa.priority,
					dfa.returnMessage));
		}
		HashMap<Character, Integer> map = new HashMap<Character, Integer>();
		for (int i = 0; i < dfa.edgeCnt; i++) {
			map.put(dfa.edge[i].getC(), dfa.next[i].no);
			if (!record.contains(dfa.next[i].no)) {
				constructSearchTableEx(dfa.next[i]);
			}
		}
		SearchTable.table.put(dfa.no, map);
	}

	public void check(DfaState dfa) {
		checkNo.add(dfa.no);
		check.add(dfa);
		for (int i = 0; i < dfa.edgeCnt; i++) {
			for (DfaState d : check) {
				if (dfa.next[i].equals(d)) {
					// System.out.println(dfa.next[i].no + "->" + d.no);
					dfa.next[i].no = d.no;
				}
			}
			if (!checkNo.contains(dfa.next[i].no)) {
				check(dfa.next[i]);
			}
		}
	}
}

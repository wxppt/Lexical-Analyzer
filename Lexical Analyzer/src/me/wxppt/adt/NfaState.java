package me.wxppt.adt;

import java.util.ArrayList;


public class NfaState {
	private ArrayList<Integer> addToAlready = new ArrayList<Integer>();
	public static int STATE_INDEX = 0;
	
	public int no;

	public StateProperty property;
	public ReItem[] edge = new ReItem[100];
	public NfaState[] next = new NfaState[100];

	public int edgeCnt = 0;
	
	public NfaState() {
		no = STATE_INDEX;
		STATE_INDEX++;
	}
	
	public void link(ReItem edge, NfaState nextState) {
		this.edge[edgeCnt] = edge;
		if(nextState.edgeCnt != 0) {
			nextState.property = StateProperty.NORMAL;
		}
		this.next[edgeCnt] = nextState;
		this.edgeCnt++;
	}
	
	public void addToEnd(ReItem edge, NfaState nextState) {
		addToAlready.clear();
		addToEndEx(edge, nextState);
	}
	
	private void addToEndEx(ReItem edge, NfaState nextState) {
		if(addToAlready.contains(nextState.no)) {
			return;
		}
		addToAlready.add(nextState.no);
		
		if(this.property == StateProperty.END) {
			this.property = StateProperty.NORMAL;
			if(nextState.edgeCnt != 0) {
				nextState.property = StateProperty.NORMAL;
			}
			this.link(edge, nextState);
			return;
		} else {
			for(int i = 0; i< edgeCnt;i++) {
				this.next[i].addToEndEx(edge,nextState);
			}
		}
	}
	
	
	public void cycling(NfaState start) {
		if(this.property == StateProperty.END) {
			this.link(ReItem.getEmptyElement(), start);
			return;
		} else {
			this.next[0].cycling(start);
		}
	}
	
	public static void RESET() {
		STATE_INDEX = 0;
	}
}

package me.wxppt.adt;

import java.util.ArrayList;


public class NfaState {
	private ArrayList<Integer> addToAlready = new ArrayList<Integer>();
	public static int STATE_INDEX = 0;
	
	public int no;

	public StateProperty property;
	public ReElement[] edge = new ReElement[256];
	public NfaState[] next = new NfaState[256];

	public int edgeCnt = 0;
	
	public String returnMessage = "";
	public int priority = -1;
	
	public NfaState() {
		no = STATE_INDEX;
		STATE_INDEX++;
	}
	
	public void link(ReElement edge, NfaState nextState) {
		this.edge[edgeCnt] = edge;
		if(nextState.edgeCnt != 0) {
			nextState.property = StateProperty.NORMAL;
		}
		this.next[edgeCnt] = nextState;
		this.edgeCnt++;
	}
	
	public void addToEnd(ReElement edge, NfaState nextState) {
		addToAlready.clear();
		addToEndEx(edge, nextState);
	}
	
	private void addToEndEx(ReElement edge, NfaState nextState) {
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
		addToAlready.clear();
		cyclingEx(start);
	}
	
	public void cyclingEx(NfaState start) {
		if(addToAlready.contains(start.no)) {
			return;
		}
		addToAlready.add(start.no);
		if(this.property == StateProperty.END) {
			this.link(ReElement.getEmptyElement(), start);
			return;
		} else {
			for(int i = 0; i< edgeCnt;i++) {
				this.next[i].cyclingEx(start);
			}
		}
	}
	
	public static void RESET() {
		STATE_INDEX = 0;
	}
	
	public void setReturnInfo(String s, int priority) {
		addToAlready.clear();
		setReturnMessageEx(s,priority,this);
	}
	
	public void setReturnMessageEx(String s, int priority, NfaState nextState) {
		if(addToAlready.contains(nextState.no)) {
			return;
		}
		addToAlready.add(nextState.no);
		
		if(this.property == StateProperty.END) {
			this.returnMessage = s;
			this.priority = priority;
		} else {
			for(int i = 0; i< edgeCnt;i++) {
				this.next[i].setReturnMessageEx(s, priority,nextState);
			}
		}
	}
}

package me.wxppt.adt;


public class NfaState {
	public static int STATE_INDEX = 0;
	
	public int no;
	public enum Property {
		START,NORMAL,END
	}

	public Property property;
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
			nextState.property = NfaState.Property.NORMAL;
		}
		this.next[edgeCnt] = nextState;
		this.edgeCnt++;
	}
	
	public void addToEnd(ReItem edge, NfaState nextState) {
		if(this.property == NfaState.Property.END) {
			this.property = NfaState.Property.NORMAL;
			if(nextState.edgeCnt != 0) {
				nextState.property = NfaState.Property.NORMAL;
			}
			this.link(edge, nextState);
			return;
		} else {
			this.next[0].addToEnd(edge,nextState);
		}
	}
	
	public void cycling(NfaState start) {
		if(this.property == NfaState.Property.END) {
			this.link(ReItem.getEmptyElement(), start);
			return;
		} else {
			this.next[0].cycling(start);
		}
	}
}

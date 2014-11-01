package me.wxppt.adt;

import me.wxppt.constant.Const;

public class ReElement {

	private char c;
	private boolean isEscape = false;
	private boolean isEmpty = false;
	private int priority = 0;

	public ReElement(char c, boolean isEscape) throws Exception {
		this.c = c;
		this.isEscape = isEscape;
		// 检查转义字符的合法性
		if (isEscape) {
			boolean flag = false;
			for (int i = 0; i < Const.ESCAPABLE_CHARS.length; i++) {
				if (Const.ESCAPABLE_CHARS[i] == c) {
					flag = true;
				}
			}
			if (!flag) {
				throw new Exception(c + " 不能转义");
			}
		} else {
			switch(c) {
			case '*':
				priority = 3;
				break;
			case '|':
				priority = 1;
				break;
			case '.':
				priority = 2;
				break;
			default:
				break;
			}
		}
	}

	public boolean isChar() {
		if(isEscape) {
			return true;
		}
		for (int i = 0; i < Const.RE_ELEMENTS.length; i++) {
			if (c == Const.RE_ELEMENTS[i]) {
				return false;
			}
		}
		return true;
	}

	public char getC() {
		return c;
	}

	public void setC(char c) {
		this.c = c;
	}

	public boolean isEscape() {
		return isEscape;
	}

	public void setEscape(boolean isEscape) {
		this.isEscape = isEscape;
	}

	public int getPriority() {
		return priority;
	}
	
	public boolean isEmpty() {
		return isEmpty;
	}

	public void setEmpty(boolean isEmpty) {
		this.isEmpty = isEmpty;
	}

	public void setPriority(int priority) {
		this.priority = priority;
	}

	@Override
	public String toString() {
		return c + " " + isEscape;
	}

	@Override
	public boolean equals(Object obj) {
		return (c + " " + isEscape).equals(obj.toString());
	}

	public static ReElement getReElement(char ele) {
		try {
			return new ReElement(ele,false);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public boolean match(char charactor) {
		return c == charactor;
	}
	
	public static ReElement getEmptyElement() {
		ReElement nitem = null;
		try {
			nitem = new ReElement(' ',false);
			nitem.setEmpty(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return nitem;
	}
}

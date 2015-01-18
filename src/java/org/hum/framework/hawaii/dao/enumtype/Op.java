package org.hum.framework.hawaii.dao.enumtype;

public enum Op {

	EQUAL(0, "="),
	LIKE(1, "like"),
	GT(2, ">"),
	LT(3, "<"),
	NOT_EQUAL(4, "!="),
	IN(5, "in");

	private final int type;
	private final String operator;

	private Op(int type, String operator) {
		this.type = type;
		this.operator = operator;
	}

	public static Op getType(int type) {
		for (Op t : values()) {
			if (type == t.getType()) {
				return t;
			}
		}
		return null;
	}

	public int getType() {
		return this.type;
	}

	public String getOperator() {
		return this.operator;
	}
}

package org.hum.framework.hawaii.dao.enumtype;

public enum Sort {
	ASC("asc", "asc"), DESC("desc", "desc");

	private String key;
	private String value;

	private Sort(String key, String value) {
		this.key = key;
		this.value = value;
	}

	public static Sort getType(String key) {
		for (Sort t : values()) {
			if (key.equals(t.getKey())) {
				return t;
			}
		}
		return null;
	}

	public String getKey() {
		return this.key;
	}

	public String getValue() {
		return this.value;
	}
}

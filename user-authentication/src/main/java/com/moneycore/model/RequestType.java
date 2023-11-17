package com.moneycore.model;

public enum RequestType {

	GET("read"), POST("insert"), PUT("update"), DELETE("delete");

	private String value;

	private RequestType(String value) {
		this.value = value;
	}

	public static String getEnumValue(String name) {
		String value = "";
		for (RequestType type : RequestType.values()) {
			if (type.name().equals(name)) {
				value = type.value;
			}
		}
		return value;
	}
}

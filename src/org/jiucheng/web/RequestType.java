/*
 * Copyright (c) jiucheng.org
 */
package org.jiucheng.web;

public enum RequestType {
	REQUEST(""),POST("POST"), GET("GET");
    private String value;

    private RequestType(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return value;
    }
}

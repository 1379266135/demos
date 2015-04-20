package com.niu.demos.images.http;

public class HttpSetting {
	/** 连接超时时间 */
    public static final int HTTP_CONNECTION_TIMEOUT = 5 * 1000;
    /**
     * HTTP_SO_TIMEOUT: Set the default socket timeout (SO_TIMEOUT). in
     * milliseconds which is the timeout for waiting for data.
     */
    public static final int HTTP_SO_TIMEOUT = 7 * 1000;

    // session
    public static final String SESSION_ID = "sessionid";
    public static final String CSRF_TOKEN = "csrftoken";
    public static final String TOKEN = "token";
    public static final String CSRF_MIDDLE_WARE_TOKEN = "csrfmiddlewaretoken";
}

package com.kloudless;

public abstract class Kloudless {

	public static final String BASE_URL = "https://api.kloudless.com";
	public static final String VERSION = "0.0.1";
	public static String apiKey = "zPrd_wa5tRZaPpD2nVxG3KmkuKYYl_DcMABGJl6k4iTZJLz4";
	public static String accountId = "15590";//"Zqau9dIcFRBUWmObeicM_lVagsrbVZsKUa_u7c3_iPq6gDWk";
	public static String accountKey ="Zqau9dIcFRBUWmObeicM_lVagsrbVZsKUa_u7c3_iPq6gDWk";// apiKey;
	public static String apiVersion = "0";

	private static String apiBase = BASE_URL;

	/**
	 * (FOR TESTING ONLY) If you'd like your API requests to hit your own
	 * (mocked) server, you can set this up here by overriding the base api URL.
	 */
	public static void overrideApiBase(final String overriddenApiBase) {
		apiBase = overriddenApiBase;
	}

	public static String getApiBase() {
		return apiBase;
	}

}

package com.kloudless.net;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.net.URLStreamHandler;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.kloudless.Kloudless;
import com.kloudless.exception.APIConnectionException;
import com.kloudless.exception.APIException;
import com.kloudless.exception.AuthenticationException;
import com.kloudless.exception.InvalidRequestException;
import com.kloudless.model.Data;
import com.kloudless.model.DataDeserializer;
import com.kloudless.model.KloudlessObject;
import com.kloudless.model.KloudlessRawJsonObject;
import com.kloudless.model.KloudlessRawJsonObjectDeserializer;

public abstract class APIResource extends KloudlessObject {

	public static final Gson GSON = new GsonBuilder()
			.setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
			.registerTypeAdapter(Data.class, new DataDeserializer())
			.registerTypeAdapter(KloudlessRawJsonObject.class,
					new KloudlessRawJsonObjectDeserializer()).create();

	protected static String className(Class<?> clazz) {
		String className = clazz.getSimpleName().toLowerCase()
				.replace("$", " ");

		// TODO: Delurk this, with invoiceitem being a valid url, we can't get
		// too
		// fancy yet.
		if (className.equals("applicationfee")) {
			return "application_fee";
		} else {
			return className;
		}
	}

	protected static String singleClassURL(Class<?> clazz) {
		return className(clazz);
	}

	protected static String classURL(Class<?> clazz) {
		return String.format("%ss", singleClassURL(clazz));
	}

	protected static String instanceURL(Class<?> clazz, String id)
			throws InvalidRequestException {
		try {
			return String.format("%s/%s", classURL(clazz), urlEncode(id));
		} catch (UnsupportedEncodingException e) {
			throw new InvalidRequestException("Unable to encode parameters to "
					+ CHARSET
					+ ". Please contact support@kloudless.com for assistance.",
					null, e);
		}
	}

	protected static String detailURL(Class<?> clazz, String id)
			throws InvalidRequestException {
		try {
			return String.format("%s/%s/contents", classURL(clazz),
					urlEncode(id));
		} catch (UnsupportedEncodingException e) {
			throw new InvalidRequestException("Unable to encode parameters to "
					+ CHARSET
					+ ". Please contact support@kloudless.com for assistance.",
					null, e);
		}
	}

	public static final String CHARSET = "UTF-8";

	private static final String DNS_CACHE_TTL_PROPERTY_NAME = "networkaddress.cache.ttl";

	/*
	 * Set this property to override your environment's default
	 * URLStreamHandler; Settings the property should not be needed in most
	 * environments.
	 */
	private static final String CUSTOM_URL_STREAM_HANDLER_PROPERTY_NAME = "com.kloudless.net.customURLStreamHandler";

	protected enum RequestMethod {
		GET, PATCH, POST, PUT, DELETE
	}

	private static String urlEncode(String str)
			throws UnsupportedEncodingException {
		// Preserve original behavior that passing null for an object id will
		// lead
		// to us actually making a request to /v1/foo/null
		if (str == null) {
			return null;
		} else {
			return URLEncoder.encode(str, CHARSET);
		}
	}

	private static String urlEncodePair(String k, String v)
			throws UnsupportedEncodingException {
		return String.format("%s=%s", urlEncode(k), urlEncode(v));
	}

	static Map<String, String> getHeaders(String apiKey) {
		Map<String, String> headers = new HashMap<String, String>();
		headers.put("Accept-Charset", CHARSET);
		headers.put("User-Agent", String.format("Kloudless/v1 JavaBindings/%s",
				Kloudless.VERSION));

		if (apiKey == null) {
			apiKey = Kloudless.apiKey;
		}

		if (apiKey != null) {
			headers.put("Authorization", String.format("ApiKey %s", apiKey));
		} else {
			if (Kloudless.accountId != null && Kloudless.accountKey != null) {
				headers.put("Authorization",
						String.format("AccountKey %s", Kloudless.accountKey));
			}
		}

		// debug headers
		String[] propertyNames = { "os.name", "os.version", "os.arch",
				"java.version", "java.vendor", "java.vm.version",
				"java.vm.vendor" };
		Map<String, String> propertyMap = new HashMap<String, String>();
		for (String propertyName : propertyNames) {
			propertyMap.put(propertyName, System.getProperty(propertyName));
		}
		propertyMap.put("bindings.version", Kloudless.VERSION);
		propertyMap.put("lang", "Java");
		propertyMap.put("publisher", "Kloudless");
		headers.put("X-Kloudless-Client-User-Agent", GSON.toJson(propertyMap));
		if (Kloudless.apiVersion != null) {
			headers.put("Kloudless-Version", Kloudless.apiVersion);
		}
		return headers;
	}

	private static javax.net.ssl.HttpsURLConnection createKloudlessConnection(
			String url, String apiKey) throws IOException {
		URL kloudlessURL = null;
		String customURLStreamHandlerClassName = System.getProperty(
				CUSTOM_URL_STREAM_HANDLER_PROPERTY_NAME, null);
		if (customURLStreamHandlerClassName != null) {
			// instantiate the custom handler provided
			try {
				@SuppressWarnings("unchecked")
				Class<URLStreamHandler> clazz = (Class<URLStreamHandler>) Class
						.forName(customURLStreamHandlerClassName);
				Constructor<URLStreamHandler> constructor = clazz
						.getConstructor();
				URLStreamHandler customHandler = constructor.newInstance();
				kloudlessURL = new URL(null, url, customHandler);
			} catch (ClassNotFoundException e) {
				throw new IOException(e);
			} catch (SecurityException e) {
				throw new IOException(e);
			} catch (NoSuchMethodException e) {
				throw new IOException(e);
			} catch (IllegalArgumentException e) {
				throw new IOException(e);
			} catch (InstantiationException e) {
				throw new IOException(e);
			} catch (IllegalAccessException e) {
				throw new IOException(e);
			} catch (InvocationTargetException e) {
				throw new IOException(e);
			}
		} else {
			kloudlessURL = new URL(url);
		}
		javax.net.ssl.HttpsURLConnection conn = (javax.net.ssl.HttpsURLConnection) kloudlessURL
				.openConnection();
		conn.setConnectTimeout(30 * 1000);
		conn.setReadTimeout(80 * 1000);
		conn.setUseCaches(false);
		for (Map.Entry<String, String> header : getHeaders(apiKey).entrySet()) {
			conn.setRequestProperty(header.getKey(), header.getValue());
		}

		return conn;
	}

	private static javax.net.ssl.HttpsURLConnection createGetConnection(
			String url, String query, String apiKey) throws IOException {
		String getURL = String.format("%s?%s", url, query);
		javax.net.ssl.HttpsURLConnection conn = createKloudlessConnection(
				getURL, apiKey);
		conn.setRequestMethod("GET");
		return conn;
	}

	private static javax.net.ssl.HttpsURLConnection createPatchConnection(
			String url, Map<String, Object> params, String query, String apiKey)
			throws IOException {
		javax.net.ssl.HttpsURLConnection conn = createKloudlessConnection(url,
				apiKey);
		conn.setDoOutput(true);
		conn.setRequestMethod("POST");
		conn.setRequestProperty("Content-Type", "application/json");
		conn.setRequestProperty("X-HTTP-Method-Override", "PATCH");
		OutputStream output = null;
		try {
			output = conn.getOutputStream();
			System.out.println(GSON.toJson(params));
			output.write(GSON.toJson(params).getBytes());
		} finally {
			if (output != null) {
				output.close();
			}
		}
		return conn;
	}

	private static javax.net.ssl.HttpsURLConnection createPostConnection(
			String url, Map<String, Object> params, String query, String apiKey)
			throws IOException {
		javax.net.ssl.HttpsURLConnection conn = createKloudlessConnection(url,
				apiKey);
		conn.setDoOutput(true);
		conn.setRequestMethod("POST");

		OutputStream output = null;
		try {
			if (params.containsKey("file") && params.containsKey("metadata")) {

				Map<?, ?> json = GSON.fromJson((String) params.get("metadata"),
						Map.class);
				String name = (String) json.get("name");
				String twoHyphens = "--";
				String boundary = "*****";
				String crlf = "\r\n";

				conn.setRequestProperty("Content-Type",
						"multipart/form-data;boundary=" + boundary);
				output = conn.getOutputStream();
				output.write((crlf + twoHyphens + boundary + crlf)
						.getBytes(CHARSET));
				output.write(("Content-Disposition: form-data; name=\"file\";filename=\""
						+ name + "\"" + crlf).getBytes(CHARSET));
				output.write(("Content-Type: application/octet-stream" + crlf + crlf)
						.getBytes(CHARSET));
				output.write((byte[]) params.get("file"));
				output.write((crlf + twoHyphens + boundary + crlf)
						.getBytes(CHARSET));
				output.write(("Content-Disposition: form-data; name=\"metadata\""
						+ crlf + crlf).getBytes(CHARSET));
				output.write((((String) params.get("metadata"))
						.getBytes(CHARSET)));
				output.write((crlf + twoHyphens + boundary + crlf)
						.getBytes(CHARSET));

			} else {
				conn.setRequestProperty("Content-Type", String
						.format("application/x-www-form-urlencoded;charset=%s",
								CHARSET));
				output = conn.getOutputStream();
				output.write(query.getBytes(CHARSET));
			}
		} finally {
			if (output != null) {
				output.close();
			}
		}
		return conn;
	}

	private static javax.net.ssl.HttpsURLConnection createDeleteConnection(
			String url, String query, String apiKey) throws IOException {
		String deleteUrl = String.format("%s?%s", url, query);
		javax.net.ssl.HttpsURLConnection conn = createKloudlessConnection(
				deleteUrl, apiKey);
		conn.setRequestMethod("DELETE");
		return conn;
	}

	private static String createQuery(RequestMethod method,
			Map<String, Object> params) throws UnsupportedEncodingException,
			InvalidRequestException {
		Map<String, String> flatParams = flattenParams(params);
		StringBuilder queryStringBuffer = new StringBuilder();

		// PATCH puts the parameters into the body
		if (method != RequestMethod.PATCH) {
			for (Map.Entry<String, String> entry : flatParams.entrySet()) {
				if (queryStringBuffer.length() > 0) {
					queryStringBuffer.append("&");
				}
				queryStringBuffer.append(urlEncodePair(entry.getKey(),
						entry.getValue()));
			}
		}
		return queryStringBuffer.toString();
	}

	private static Map<String, String> flattenParams(Map<String, Object> params)
			throws InvalidRequestException {
		if (params == null) {
			return new HashMap<String, String>();
		}
		Map<String, String> flatParams = new HashMap<String, String>();
		for (Map.Entry<String, Object> entry : params.entrySet()) {
			String key = entry.getKey();
			Object value = entry.getValue();
			if (value instanceof Map<?, ?>) {
				Map<String, Object> flatNestedMap = new HashMap<String, Object>();
				Map<?, ?> nestedMap = (Map<?, ?>) value;
				for (Map.Entry<?, ?> nestedEntry : nestedMap.entrySet()) {
					flatNestedMap.put(
							String.format("%s[%s]", key, nestedEntry.getKey()),
							nestedEntry.getValue());
				}
				flatParams.putAll(flattenParams(flatNestedMap));
			} else if ("".equals(value)) {
				throw new InvalidRequestException("You cannot set '" + key
						+ "' to an empty string. "
						+ "We interpret empty strings as null in requests. "
						+ "You may set '" + key
						+ "' to null to delete the property.", key, null);
			} else if (value == null) {
				flatParams.put(key, "");
			} else if (value != null) {
				flatParams.put(key, value.toString());
			}
		}
		return flatParams;
	}

	private static class Error {
		@SuppressWarnings("unused")
		String type;

		String message;

		@SuppressWarnings("unused")
		String code;

		String param;
	}

	private static String getResponseBody(InputStream responseStream, ByteArrayOutputStream byteArrayOut)
			throws IOException {
		// \A is the beginning of
		// the stream boundary
//		String rBody = new Scanner(responseStream, CHARSET).useDelimiter("\\A")
//				.next(); //

		BufferedInputStream bufferedInputStream = new BufferedInputStream(responseStream);
		int c;
		while ((c = bufferedInputStream.read()) != -1) {
			byteArrayOut.write(c);
		}
		
		String rBody = byteArrayOut.toString(CHARSET);
		responseStream.close();

		System.out.println(rBody);

		return rBody;
	}

	private static KloudlessResponse makeURLConnectionRequest(
			APIResource.RequestMethod method, Map<String, Object> params,
			String url, String query, String apiKey)
			throws APIConnectionException {
		javax.net.ssl.HttpsURLConnection conn = null;
		try {
			switch (method) {
			case GET:
				conn = createGetConnection(url, query, apiKey);
				break;
			case PATCH:
				conn = createPatchConnection(url, params, query, apiKey);
				break;
			case POST:
				conn = createPostConnection(url, params, query, apiKey);
				break;
			case DELETE:
				conn = createDeleteConnection(url, query, apiKey);
				break;
			default:
				throw new APIConnectionException(
						String.format(
								"Unrecognized HTTP method %s. "
										+ "This indicates a bug in the Kloudless bindings. Please contact "
										+ "support@kloudless.com for assistance.",
								method));
			}
			// trigger the request
			int rCode = conn.getResponseCode();
			String rBody = null;
			Map<String, List<String>> headers;
			ByteArrayOutputStream byteArrayOut = new ByteArrayOutputStream();
			
			// convert responseBody and save responseStream
			if (rCode >= 200 && rCode < 300) {
				rBody = getResponseBody(conn.getInputStream(), byteArrayOut);
			} else {
				rBody = getResponseBody(conn.getErrorStream(), byteArrayOut);
			}

			headers = conn.getHeaderFields();
			return new KloudlessResponse(rCode, rBody, headers, byteArrayOut);

		} catch (IOException e) {
			throw new APIConnectionException(
					String.format(
							"Could not connect to Kloudless (%s). "
									+ "Please check your internet connection and try again. If this problem persists,"
									+ "you should check Kloudless's service status at https://twitter.com/KloudlessAPI,"
									+ " or let us know at support@kloudless.com.",
							Kloudless.getApiBase()), e);
		} finally {
			if (conn != null) {
				conn.disconnect();
			}
		}
	}

	protected static KloudlessResponse request(
			APIResource.RequestMethod method, String path,
			Map<String, Object> params, String apiKey)
			throws AuthenticationException, InvalidRequestException,
			APIConnectionException, APIException {
		String originalDNSCacheTTL = null;
		Boolean allowedToSetTTL = true;

		System.out.format("path: %s\n", path);

		try {
			originalDNSCacheTTL = java.security.Security
					.getProperty(DNS_CACHE_TTL_PROPERTY_NAME);
			// disable DNS cache
			java.security.Security
					.setProperty(DNS_CACHE_TTL_PROPERTY_NAME, "0");
		} catch (SecurityException se) {
			allowedToSetTTL = false;
		}

		try {
			return _request(method, path, params, apiKey);
		} finally {
			if (allowedToSetTTL) {
				if (originalDNSCacheTTL == null) {
					// value unspecified by implementation
					// DNS_CACHE_TTL_PROPERTY_NAME of -1 = cache forever
					java.security.Security.setProperty(
							DNS_CACHE_TTL_PROPERTY_NAME, "-1");
				} else {
					java.security.Security.setProperty(
							DNS_CACHE_TTL_PROPERTY_NAME, originalDNSCacheTTL);
				}
			}
		}
	}

	protected static KloudlessResponse _request(
			APIResource.RequestMethod method, String path,
			Map<String, Object> params, String apiKey)
			throws AuthenticationException, InvalidRequestException,
			APIConnectionException, APIException {
		if ((Kloudless.apiKey == null || Kloudless.apiKey.length() == 0)
				&& (apiKey == null || apiKey.length() == 0)
				&& (Kloudless.accountId == null || Kloudless.accountKey
						.length() == 0)
				&& (Kloudless.accountKey == null || Kloudless.accountKey
						.length() == 0)) {
			throw new AuthenticationException(
					"No API key or Account Key provided. (HINT: set your API key using 'Kloudless.apiKey = <API-KEY>'. "
							+ "You can generate API keys from the Kloudless web interface. "
							+ "See https://developers.kloudless.com/docs for details or email support@kloudless.com if you have questions.");
		}

		if (apiKey == null) {
			apiKey = Kloudless.apiKey;
		}

		String query;
		String url = String.format("%s/v%s/%s", Kloudless.getApiBase(),
				Kloudless.apiVersion, path);

		try {
			query = createQuery(method, params);
		} catch (UnsupportedEncodingException e) {
			throw new InvalidRequestException("Unable to encode parameters to "
					+ CHARSET
					+ ". Please contact support@kloudless.com for assistance.",
					null, e);
		}

		KloudlessResponse response;
		try {
			// HTTPSURLConnection verifies SSL cert by default
			response = makeURLConnectionRequest(method, params, url, query,
					apiKey);
		} catch (ClassCastException ce) {
			// appengine doesn't have HTTPSConnection, use URLFetch API
			String appEngineEnv = System.getProperty(
					"com.google.appengine.runtime.environment", null);
			if (appEngineEnv != null) {
				response = makeAppEngineRequest(method, url, query, apiKey);
			} else {
				// non-appengine ClassCastException
				throw ce;
			}
		}
		return response;
	}

	protected static void handleAPIError(String rBody, int rCode)
			throws InvalidRequestException, AuthenticationException,
			APIException {
		APIResource.Error error = GSON.fromJson(rBody, APIResource.Error.class);
		switch (rCode) {
		case 400:
			throw new InvalidRequestException(error.message, error.param, null);
		case 404:
			throw new InvalidRequestException(error.message, error.param, null);
		case 403:
			throw new AuthenticationException(error.message);
		case 401:
			throw new AuthenticationException(error.message);
		default:
			throw new APIException(error.message, null);
		}
	}

	/*
	 * This is slower than usual because of reflection but avoids having to
	 * maintain AppEngine-specific JAR
	 */
	private static KloudlessResponse makeAppEngineRequest(RequestMethod method,
			String url, String query, String apiKey) throws APIException {
		String unknownErrorMessage = "Sorry, an unknown error occurred while trying to use the "
				+ "Google App Engine runtime. Please contact support@kloudless.com for assistance.";
		try {
			if (method == RequestMethod.GET || method == RequestMethod.DELETE) {
				url = String.format("%s?%s", url, query);
			}
			URL fetchURL = new URL(url);

			Class<?> requestMethodClass = Class
					.forName("com.google.appengine.api.urlfetch.HTTPMethod");
			Object httpMethod = requestMethodClass.getDeclaredField(
					method.name()).get(null);

			Class<?> fetchOptionsBuilderClass = Class
					.forName("com.google.appengine.api.urlfetch.FetchOptions$Builder");
			Object fetchOptions = null;
			try {
				fetchOptions = fetchOptionsBuilderClass.getDeclaredMethod(
						"validateCertificate").invoke(null);
			} catch (NoSuchMethodException e) {
				System.err
						.println("Warning: this App Engine SDK version does not allow verification of SSL certificates;"
								+ "this exposes you to a MITM attack. Please upgrade your App Engine SDK to >=1.5.0. "
								+ "If you have questions, contact support@kloudless.com.");
				fetchOptions = fetchOptionsBuilderClass.getDeclaredMethod(
						"withDefaults").invoke(null);
			}

			Class<?> fetchOptionsClass = Class
					.forName("com.google.appengine.api.urlfetch.FetchOptions");

			// GAE requests can time out after 60 seconds, so make sure we leave
			// some time for the application to handle a slow Kloudless
			fetchOptionsClass.getDeclaredMethod("setDeadline",
					java.lang.Double.class)
					.invoke(fetchOptions, new Double(55));

			Class<?> requestClass = Class
					.forName("com.google.appengine.api.urlfetch.HTTPRequest");

			Object request = requestClass.getDeclaredConstructor(URL.class,
					requestMethodClass, fetchOptionsClass).newInstance(
					fetchURL, httpMethod, fetchOptions);

			if (method == RequestMethod.POST) {
				requestClass.getDeclaredMethod("setPayload", byte[].class)
						.invoke(request, query.getBytes());
			}

			for (Map.Entry<String, String> header : getHeaders(apiKey)
					.entrySet()) {
				Class<?> httpHeaderClass = Class
						.forName("com.google.appengine.api.urlfetch.HTTPHeader");
				Object reqHeader = httpHeaderClass.getDeclaredConstructor(
						String.class, String.class).newInstance(
						header.getKey(), header.getValue());
				requestClass.getDeclaredMethod("setHeader", httpHeaderClass)
						.invoke(request, reqHeader);
			}

			Class<?> urlFetchFactoryClass = Class
					.forName("com.google.appengine.api.urlfetch.URLFetchServiceFactory");
			Object urlFetchService = urlFetchFactoryClass.getDeclaredMethod(
					"getURLFetchService").invoke(null);

			Method fetchMethod = urlFetchService.getClass().getDeclaredMethod(
					"fetch", requestClass);
			fetchMethod.setAccessible(true);
			Object response = fetchMethod.invoke(urlFetchService, request);

			int responseCode = (Integer) response.getClass()
					.getDeclaredMethod("getResponseCode").invoke(response);
			String body = new String((byte[]) response.getClass()
					.getDeclaredMethod("getContent").invoke(response), CHARSET);
			return new KloudlessResponse(responseCode, body);
		} catch (InvocationTargetException e) {
			throw new APIException(unknownErrorMessage, e);
		} catch (MalformedURLException e) {
			throw new APIException(unknownErrorMessage, e);
		} catch (NoSuchFieldException e) {
			throw new APIException(unknownErrorMessage, e);
		} catch (SecurityException e) {
			throw new APIException(unknownErrorMessage, e);
		} catch (NoSuchMethodException e) {
			throw new APIException(unknownErrorMessage, e);
		} catch (ClassNotFoundException e) {
			throw new APIException(unknownErrorMessage, e);
		} catch (IllegalArgumentException e) {
			throw new APIException(unknownErrorMessage, e);
		} catch (IllegalAccessException e) {
			throw new APIException(unknownErrorMessage, e);
		} catch (InstantiationException e) {
			throw new APIException(unknownErrorMessage, e);
		} catch (UnsupportedEncodingException e) {
			throw new APIException(unknownErrorMessage, e);
		}
	}

}
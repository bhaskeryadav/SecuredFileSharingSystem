package com.kloudless.model;

import java.util.Map;

import com.kloudless.exception.APIConnectionException;
import com.kloudless.exception.APIException;
import com.kloudless.exception.AuthenticationException;
import com.kloudless.exception.InvalidRequestException;
import com.kloudless.net.APIResourceMixin;
import com.kloudless.net.KloudlessResponse;

public class Link extends APIResourceMixin {

	public String id;
	public String file_id;
	public String url;
	public Integer account;
	public Boolean active;
	public String created;
	public String modified;

	public static LinkCollection all(String accountId, Map<String, Object> params)
			throws APIException, AuthenticationException,
			InvalidRequestException, APIConnectionException {
		String path = String.format("%s/%s",
				instanceURL(Account.class, accountId), classURL(Link.class));
		return all(path, params, LinkCollection.class, null);
	}

	public static Link retrieve(String id, String accountId,
			Map<String, Object> params) throws APIException,
			AuthenticationException, InvalidRequestException,
			APIConnectionException {
		String path = String
				.format("%s/%s", instanceURL(Account.class, accountId),
						instanceURL(Link.class, id));
		return retrieve(path, params, Link.class, null);
	}

	public static Link save(String id, String accountId,
			Map<String, Object> params) throws APIException,
			AuthenticationException, InvalidRequestException,
			APIConnectionException {
		String path = String.format("%s/%s",
				instanceURL(Account.class, accountId),
				instanceURL(Link.class, id));
		return save(path, params, Link.class, null);
	}

	public static Link create(String accountId, Map<String, Object> params)
			throws APIException, AuthenticationException,
			InvalidRequestException, APIConnectionException {
		String path = String.format("%s/%s",
				instanceURL(Account.class, accountId), classURL(Link.class));
		return create(path, params, Link.class, null);
	}

	// TODO: figure out how to return a null response for a 204 DELETED response
	public static KloudlessResponse delete(String id, Map<String, Object> params)
			throws APIException, AuthenticationException,
			InvalidRequestException, APIConnectionException {
		return delete(instanceURL(Link.class, id), params, null);
	}

}

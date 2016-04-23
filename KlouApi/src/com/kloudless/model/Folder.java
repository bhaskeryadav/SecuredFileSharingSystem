package com.kloudless.model;

import java.util.Map;

import com.kloudless.exception.APIConnectionException;
import com.kloudless.exception.APIException;
import com.kloudless.exception.AuthenticationException;
import com.kloudless.exception.InvalidRequestException;
import com.kloudless.net.KloudlessResponse;

public class Folder extends Metadata {

	public static MetadataCollection contents(String id, String accountId,
			Map<String, Object> params) throws APIException,
			AuthenticationException, InvalidRequestException,
			APIConnectionException {
		String path = String.format("%s/%s",
				instanceURL(Account.class, accountId),
				detailURL(Folder.class, id));
		KloudlessResponse response = request(RequestMethod.GET, path, params,
				null);
		int rCode = response.getResponseCode();
		String rBody = response.getResponseBody();
		if (rCode < 200 || rCode >= 300) {
			handleAPIError(rBody, rCode);
		}
		return GSON.fromJson(rBody, MetadataCollection.class);
	}

	public static Folder retrieve(String id, String accountId,
			Map<String, Object> params) throws APIException,
			AuthenticationException, InvalidRequestException,
			APIConnectionException {
		String path = String.format("%s/%s",
				instanceURL(Account.class, accountId),
				instanceURL(Folder.class, id));
		return retrieve(path, params, Folder.class, null);
	}

	public static Folder save(String id, String accountId,
			Map<String, Object> params) throws APIException,
			AuthenticationException, InvalidRequestException,
			APIConnectionException {
		String path = String.format("%s/%s",
				instanceURL(Account.class, accountId),
				instanceURL(Folder.class, id));
		return save(path, params, Folder.class, null);
	}

	public static Folder create(String accountId, Map<String, Object> params)
			throws APIException, AuthenticationException,
			InvalidRequestException, APIConnectionException {
		String path = String.format("%s/%s",
				instanceURL(Account.class, accountId), classURL(Folder.class));
		return create(path, params, Folder.class, null);
	}

	public static KloudlessResponse delete(String id, String accountId,
			Map<String, Object> params) throws APIException,
			AuthenticationException, InvalidRequestException,
			APIConnectionException {
		String path = String.format("%s/%s",
				instanceURL(Account.class, accountId),
				instanceURL(Folder.class, id));
		return delete(path, params, null);
	}

}

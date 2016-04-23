package com.kloudless.model;

import java.util.Map;

import com.kloudless.exception.APIConnectionException;
import com.kloudless.exception.APIException;
import com.kloudless.exception.AuthenticationException;
import com.kloudless.exception.InvalidRequestException;
import com.kloudless.net.APIResourceMixin;
import com.kloudless.net.KloudlessResponse;

public class Account extends APIResourceMixin {

	public String id;
	public String account;
	public Boolean active;
	public String service;
	public String created;
	public String modified;

	/**
	 * The all() method returns an AccountCollection object that contains a list of Account objects.
	 * Use this method to retrieve all accounts associated with your application.
	 *
	 * @param params - query parameters that include page, pageCount, and active.
	 * @return AccountCollection
	 * @throws APIException
	 * @throws AuthenticationException
	 * @throws InvalidRequestException
	 * @throws APIConnectionException
	 */
	public static AccountCollection all(Map<String, Object> params)
			throws APIException, AuthenticationException,
			InvalidRequestException, APIConnectionException {
		return all(classURL(Account.class), params, AccountCollection.class, "zPrd_wa5tRZaPpD2nVxG3KmkuKYYl_DcMABGJl6k4iTZJLz4");
	}


	/**
	 * The retrieve() method returns an Account object that is associated with a connected cloud
	 * storage account. Use this method to retrieve a specific account.
	 * 
	 * @param id - the account id (ex: 42)
	 * @param params - query parameters that include active.
	 * @return
	 * @throws APIException
	 * @throws AuthenticationException
	 * @throws InvalidRequestException
	 * @throws APIConnectionException
	 */
	public static Account retrieve(String id, Map<String, Object> params)
			throws APIException, AuthenticationException,
			InvalidRequestException, APIConnectionException {
		return retrieve(instanceURL(Account.class, id), params, Account.class, null);
	}

	/**
	 * The delete() method returns a KloudlessResponse where you can check to see the 204 status code
	 * of a successful delete.  Use this method to delete a specific account.
	 * 
	 * @param id - the account id (ex: 42)
	 * @param params - query parameters
	 * @return
	 * @throws APIException
	 * @throws AuthenticationException
	 * @throws InvalidRequestException
	 * @throws APIConnectionException
	 */
	public static KloudlessResponse delete(String id, Map<String, Object> params) throws APIException,
			AuthenticationException, InvalidRequestException,
			APIConnectionException {
		return delete(instanceURL(Account.class, id), params, null);
	}
}

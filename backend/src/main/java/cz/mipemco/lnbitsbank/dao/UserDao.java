package cz.mipemco.lnbitsbank.dao;

import cz.mipemco.lnbitsbank.api.LnbitsApi;
import cz.mipemco.lnbitsbank.dto.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author Michal Pemčák
 */
@Component
public class UserDao
{

	private final LnbitsApi lnbitsApi;
	private Map<String, UserDto> userDetails;
	private Map<String, UserDao> userDaos = new HashMap<>();
	private UserApiKeysDto apiKeys;

	public UserDao(LnbitsApi lnbitsApi,@Value("${app.lnbits.admin:}") String apiKey) throws Exception
	{

		this.lnbitsApi = lnbitsApi;

		apiKeys = lnbitsApi.login(apiKey);
		userDetails = lnbitsApi.getUsers(apiKeys).stream()
					.collect(Collectors.toMap(UserDto::getName, Function.identity()));

	}

	public WalletDto getWallet() throws Exception
	{
		return lnbitsApi.getWallet(apiKeys);
	}

	public Collection<UserDto> getUsers()
	{
		return userDetails.values();
	}

	public List<PaymentDto> getPayments() throws Exception
	{
		return lnbitsApi.getPayments(apiKeys);
	}

	public InvoiceResponse createInvoice(InvoiceRequestDto invoiceRequestDto) throws Exception
	{
		return lnbitsApi.createInvoice(apiKeys,invoiceRequestDto);
	}

	public UserDto getUserByName(String name)
	{
		return userDetails.get(name);
	}

	public WalletDto getUserWalletByName(String name) throws Exception
	{
		if(userDetails.containsKey(name))
		{
			return determineUserDao(name).getWallet();
		}
		throw new Exception("Can't find user " + name);
	}

	private UserDao determineUserDao(String name) throws Exception
	{
		if(userDaos.containsKey(name))
			return userDaos.get(name);
		synchronized (userDaos)
		{
			userDaos.put(name, new UserDao(lnbitsApi, userDetails.get(name).id));
		}
		return userDaos.get(name);
	}

	public List<PaymentDto> getUserPayments(String name) throws Exception
	{
		if(userDetails.containsKey(name))
		{
			return determineUserDao(name).getPayments();
		}
		throw new Exception("Can't find user " + name);
	}

	public InvoiceResponse createInvoice(String name, InvoiceRequestDto invoiceRequestDto) throws Exception
	{
		return determineUserDao(name).createInvoice(invoiceRequestDto);
	}

}

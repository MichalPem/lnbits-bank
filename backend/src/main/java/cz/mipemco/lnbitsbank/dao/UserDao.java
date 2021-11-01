package cz.mipemco.lnbitsbank.dao;

import cz.mipemco.lnbitsbank.api.LnbitsApi;
import cz.mipemco.lnbitsbank.dto.UserApiKeysDto;
import cz.mipemco.lnbitsbank.dto.UserDetailsDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

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
	private Map<String, UserDetailsDto> userDetails;
	private UserApiKeysDto adminApiKeys;

	public UserDao(LnbitsApi lnbitsApi,@Value("${app.lnbits.admin:}")String lbitsAdmin) throws Exception
	{

		this.lnbitsApi = lnbitsApi;

		adminApiKeys = lnbitsApi.login(lbitsAdmin);
		userDetails = lnbitsApi.getUsersDetails(adminApiKeys).stream()
					.collect(Collectors.toMap(UserDetailsDto::getName, Function.identity()));

	}

	public UserDetailsDto getUserByName(String name)
	{
		return userDetails.get(name);
	}
}

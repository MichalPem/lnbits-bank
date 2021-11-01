package cz.mipemco.lnbitsbank.rest;

import cz.mipemco.lnbitsbank.dao.UserDao;
import cz.mipemco.lnbitsbank.dto.UserDetailsDto;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Michal Pemčák
 */
@RestController
public class UserRestController
{

	private final UserDao userDao;

	public UserRestController(UserDao userDao)
	{
		this.userDao = userDao;
	}

	@GetMapping("/api/user")
	public UserDetailsDto getUser() throws Exception
	{
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		if (principal instanceof UserDetails) {
			String username = ((UserDetails)principal).getUsername();
			return userDao.getUserByName(username);
		}
		throw new Exception();
	}


}

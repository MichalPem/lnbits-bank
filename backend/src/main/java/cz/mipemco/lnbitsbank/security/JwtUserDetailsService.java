package cz.mipemco.lnbitsbank.security;

import cz.mipemco.lnbitsbank.dao.UserDao;
import cz.mipemco.lnbitsbank.dto.UserDto;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Collections;

/**
 * @author Michal Pemčák
 */
@Component
public class JwtUserDetailsService implements UserDetailsService
{


	private final UserDao userDao;

	public JwtUserDetailsService(UserDao userDao)
	{
		this.userDao = userDao;
	}

	@Override public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException
	{
		UserDto u = userDao.getUserByName(s);
		if(u!=null)
		{
			return new User(u.name,u.password, Collections.singletonList(new SimpleGrantedAuthority("USER")));
		}
		throw new UsernameNotFoundException("USername " + s + " not found");
	}
}

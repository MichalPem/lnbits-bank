package cz.mipemco.lnbitsbank.rest;

import cz.mipemco.lnbitsbank.dao.UserDao;
import cz.mipemco.lnbitsbank.dto.InvoiceRequestDto;
import cz.mipemco.lnbitsbank.dto.InvoiceResponse;
import cz.mipemco.lnbitsbank.dto.Profile;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
	public Profile getProfile() throws Exception
	{
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		if (principal instanceof UserDetails) {
			String username = ((UserDetails)principal).getUsername();
			Profile profile = new Profile();
			profile.user = userDao.getUserByName(username);
			profile.wallet = userDao.getUserWalletByName(username);
			profile.payments = userDao.getUserPayments(username);
			return profile;
		}
		throw new Exception();
	}

	@PostMapping("/api/invoice")
	public InvoiceResponse createInvoice(@RequestBody InvoiceRequestDto invoiceRequestDto) throws Exception
	{
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		if (principal instanceof UserDetails) {
			String username = ((UserDetails)principal).getUsername();
			return userDao.createInvoice(username,invoiceRequestDto);
		}
		throw new Exception();
	}


}

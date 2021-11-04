package cz.mipemco.lnbitsbank.dto;

import java.util.Date;

/**
 * @author Michal Pemčák
 */
public class PaymentDto
{

	public Integer amount;
	public String memo;
	public Date date;
	public Boolean pending;
}

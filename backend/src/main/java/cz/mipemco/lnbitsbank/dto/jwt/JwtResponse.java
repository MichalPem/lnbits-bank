package cz.mipemco.lnbitsbank.dto.jwt;

/**
 * @author Michal Pemčák
 */
public class JwtResponse
{
	private String jwttoken;

	public JwtResponse()
	{
	}

	public JwtResponse(String jwttoken)
	{
		this.jwttoken = jwttoken;
	}

	public String getJwttoken()
	{
		return jwttoken;
	}

	public void setJwttoken(String jwttoken)
	{
		this.jwttoken = jwttoken;
	}
}

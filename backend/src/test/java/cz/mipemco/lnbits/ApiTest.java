package cz.mipemco.lnbits;

import cz.mipemco.lnbitsbank.ApplicationConfiguration;
import cz.mipemco.lnbitsbank.api.LnbitsApi;
import cz.mipemco.lnbitsbank.api.WalletDetailsDto;
import cz.mipemco.lnbitsbank.dto.UserApiKeysDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = {ApplicationConfiguration.class})
public class ApiTest {


    @Autowired
    private LnbitsApi lnbitsApi;

    @Value("${app.lnbits.admin:}")
    private String lbitsAdmin;

    @Test
    public void test() throws Exception {

        UserApiKeysDto a = lnbitsApi.login(lbitsAdmin);
        Assertions.assertNotNull(a);
        WalletDetailsDto b = lnbitsApi.getWalletDetails(a);
        System.out.println();
    }

}

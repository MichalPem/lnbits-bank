package cz.mipemco.lnbitsbank.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import cz.mipemco.lnbitsbank.dto.UserApiKeysDto;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

@Component
@ConfigurationProperties(prefix = "app.datasource")
public class LnbitsApi {

    @Value("${app.lnbits.host:}")
    private String lnbitsHost;

    private final ObjectMapper objectMapper;

    public static final String BASE_URL = "/wallet";
    public static final String WALLET_DETAILS = "/api/v1/wallet";

    public LnbitsApi(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public UserApiKeysDto login(String user) throws Exception {

            Document doc  = Jsoup.connect(lnbitsHost+BASE_URL)
                    .data("usr", user)
                    .timeout(3000)
                    .get();
            Element el = doc.getElementsByTag("q-card-section")
                    .stream()
                    .filter(e -> e.text()
                            .contains("Wallet ID:")).findFirst()
                    .orElse(null);
            if(el != null)
            {
                Elements data = el.getElementsByTag("em");
                if(data.size() == 3)
                {
                    UserApiKeysDto userApiKeysDto = new UserApiKeysDto();
                    userApiKeysDto.walletId = data.get(0).text();
                    userApiKeysDto.adminKey = data.get(1).text();
                    userApiKeysDto.invReadKey = data.get(2).text();
                    return  userApiKeysDto;
                }
            }
        throw new Exception("Unable to login for user");
    }

    public WalletDetailsDto getWalletDetails(UserApiKeysDto userApiKeys) throws Exception {
        try {
            return objectMapper.readValue(readUrl(lnbitsHost+WALLET_DETAILS,"GET",userApiKeys.invReadKey),WalletDetailsDto.class);
        } catch (IOException e) {
            throw new Exception("Unable to read user wallet details",e);
        }
    }

    private String readUrl(String url,String method,String apiKey) throws IOException {
      Connection.Response r= Jsoup.connect(url)
                .header("X-Api-Key", apiKey)
                .ignoreHttpErrors(true)
                .ignoreContentType(true)
                .method(Connection.Method.valueOf(method)).execute();

        return  r.body();

    }


}

package cz.mipemco.lnbitsbank.api;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import cz.mipemco.lnbitsbank.dto.*;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Component
//@ConfigurationProperties(prefix = "app.datasource")
public class LnbitsApi {

    @Value("${app.lnbits.host:}")
    private String lnbitsHost;

    private final ObjectMapper objectMapper;

    public static final String BASE_URL = "/wallet";
    public static final String WALLET = "/api/v1/wallet";
    public static final String USERS = "/usermanager/api/v1/users";
    public static final String PAYMENTS = "/api/v1/payments";

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

    public WalletDto getWallet(UserApiKeysDto userApiKeys) throws Exception {
        try {
            return objectMapper.readValue(readUrl(lnbitsHost+ WALLET,"GET",userApiKeys.invReadKey), WalletDto.class);
        } catch (IOException e) {
            throw new Exception("Unable to read user wallet details",e);
        }
    }

    public InvoiceResponse createInvoice(UserApiKeysDto userApiKeys, InvoiceRequestDto invoiceRequest) throws Exception {
        try {
            invoiceRequest.out = false;
            return objectMapper.readValue(readUrl(lnbitsHost+ PAYMENTS,"POST",userApiKeys.invReadKey,objectMapper
                    .writeValueAsString(invoiceRequest)), InvoiceResponse.class);
        } catch (IOException e) {
            throw new Exception("Unable to read user wallet details",e);
        }
    }

    public List<UserDto> getUsers(UserApiKeysDto userApiKeys) throws Exception {
        try {
            return objectMapper
                    .readValue(
                            readUrl(lnbitsHost+ USERS,"GET",userApiKeys.invReadKey),
                            new TypeReference<List<UserDto>>(){});
        } catch (IOException e) {
            throw new Exception("Unable to read user wallet details",e);
        }
    }

    public List<PaymentDto> getPayments(UserApiKeysDto userApiKeys) throws Exception {
        try {
            List<List<Object>>  ret  = objectMapper
                    .readValue(readUrl(lnbitsHost+PAYMENTS,"GET",userApiKeys.invReadKey),
                            new TypeReference<List<List<Object>>>(){});
            return ret.stream().map(v -> {
                PaymentDto t = new PaymentDto();
                t.amount = (Integer) v.get(2);
                t.memo = (String) v.get(4);
                t.date = new Date((((Integer)(v.get(5)))*1000L));
                t.pending = ((Integer)v.get(1))==1;
                return t;
            }).collect(Collectors.toList());
        } catch (IOException e) {
            throw new Exception("Unable to read user wallet details",e);
        }
    }

    private String readUrl(String url,String method,String apiKey) throws IOException {
        return readUrl(url,method,apiKey,null);
    }
    private String readUrl(String url,String method,String apiKey,String body) throws IOException {
      Connection d = Jsoup.connect(url)
                .header("X-Api-Key", apiKey)
                .ignoreHttpErrors(true)
                .ignoreContentType(true)
                .method(Connection.Method.valueOf(method));
      if(body!=null)
      {
          d.requestBody(body);
          d.header("Content-type","application/json");
      }
      Connection.Response r= d.execute();
      if(r.statusCode() > 205) throw new IOException(r.statusCode() + " " + r.statusMessage());
      return  r.body();

    }


}

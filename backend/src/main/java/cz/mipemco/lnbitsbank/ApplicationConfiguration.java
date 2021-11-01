package cz.mipemco.lnbitsbank;

import cz.mipemco.lnbitsbank.api.LnbitsApi;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration(proxyBeanMethods = false)
@ComponentScan
public class ApplicationConfiguration {


    @Bean
    @ConfigurationProperties(prefix = "app.datasource")
    public DataSource dataSource() {
        return DataSourceBuilder.create().build();
    }


}

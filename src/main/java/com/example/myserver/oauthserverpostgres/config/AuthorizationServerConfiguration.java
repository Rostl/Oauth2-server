package com.example.myserver.oauthserverpostgres.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JdbcTokenStore;

import javax.sql.DataSource;

@Configuration
public class AuthorizationServerConfiguration implements AuthorizationServerConfigurer {

    @Autowired
   private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Autowired
    DataSource dataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("org.postgresql.Driver");
        dataSource.setUrl("jdbc:postgresql://localhost:5432/oauth2_postgres");
        dataSource.setUsername("postgres");
        dataSource.setPassword("root");
        dataSource.setSchema("oauth_server");
        return dataSource;
    }

    @Autowired
   private AuthenticationManager authenticationManager;

    @Bean
    TokenStore jdbcTokenStore() {
        return new JdbcTokenStore(dataSource());
    }

    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
        security.tokenKeyAccess("permitAll()")
                .checkTokenAccess("isAuthenticated()");
    }

    @Override
    public void configure(ClientDetailsServiceConfigurer cliets) throws Exception {
        cliets.jdbc(dataSource()).passwordEncoder(passwordEncoder);
        System.out.println();
    }

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoint) throws Exception {
        endpoint.tokenStore(jdbcTokenStore());
        endpoint.authenticationManager(authenticationManager);
    }
}
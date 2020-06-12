package com.cvinicius.springcomaws.configuration;

import com.cvinicius.springcomaws.domain.user.auth.UserAuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.InMemoryTokenStore;

@Configuration
@EnableAuthorizationServer
public class AuthorizeServerConfiguration extends AuthorizationServerConfigurerAdapter {

    static final int REFRESH_TOKEN_SECONDS  = 30000;
    static final String RESOURCE_IDS        = "restService";
    static final int ACCESS_TOKEN_SECONDS   = 50000;
    static final String CLIENT              = "client";
    static final String SECRET              = "123";

    private final TokenStore tokenStore = new InMemoryTokenStore();

    @Autowired
    @Qualifier("authenticationManager")
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserAuthService userAuthService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer configurer)
    throws Exception {

        configurer.tokenStore(tokenStore)
                .authenticationManager(authenticationManager)
                .userDetailsService(userAuthService);
    }

    @Bean
    @Primary
    public DefaultTokenServices tokenServices(){

        DefaultTokenServices token = new DefaultTokenServices();

        token.setSupportRefreshToken(true);
        token.setTokenStore(this.tokenStore);

        return token;
    }

    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {

        clients.inMemory()
                .withClient(CLIENT)
                .authorizedGrantTypes("password", "authorization_code", "refresh_token")
                .scopes("all")
                .refreshTokenValiditySeconds(REFRESH_TOKEN_SECONDS)
                .resourceIds(RESOURCE_IDS)
                .secret(passwordEncoder.encode(SECRET))
                .accessTokenValiditySeconds(ACCESS_TOKEN_SECONDS);
    }
}
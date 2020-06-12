package com.cvinicius.springcomaws.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;

import static com.cvinicius.springcomaws.configuration.AuthorizeServerConfiguration.RESOURCE_IDS;

@Configuration
@EnableResourceServer
public class OAuthServerConfiguration extends ResourceServerConfigurerAdapter{

    @Override
    public void configure(ResourceServerSecurityConfigurer resources){

        resources.resourceId(RESOURCE_IDS);
    }

    public void configure(HttpSecurity httpSecurity)
    throws Exception{

        httpSecurity.logout()
                .invalidateHttpSession(true)
                .clearAuthentication(true)
                .and()
                .authorizeRequests()
                .anyRequest().fullyAuthenticated()
                .antMatchers(HttpMethod.OPTIONS, "/**")
                .permitAll();
    }
}

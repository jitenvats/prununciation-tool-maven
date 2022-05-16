package com.wellsfargo.hackathon.config;

import com.okta.spring.boot.oauth.Okta;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.oauth2.client.oidc.web.logout.OidcClientInitiatedLogoutSuccessHandler;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;

import java.net.URI;

@EnableWebSecurity
public class OktaOAuth2Config extends WebSecurityConfigurerAdapter {

    @Autowired
    ClientRegistrationRepository clientRegistrationRepository;

    OidcClientInitiatedLogoutSuccessHandler oidcLogoutSuccessHandler() {
        OidcClientInitiatedLogoutSuccessHandler successHandler = new OidcClientInitiatedLogoutSuccessHandler(clientRegistrationRepository);
        successHandler.setPostLogoutRedirectUri("http://localhost:8080/index.html");
        return successHandler;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.authorizeRequests()
                // allow anonymous access to the root page
                .antMatchers("/").permitAll()
                // all other requests
                .anyRequest().authenticated()
                // After we logout, redirect to root page, by default Spring will send you to /login?logout
                .and().logout().logoutSuccessUrl("/index.html")

                // RP-initiated logout
                .and().logout().logoutSuccessHandler(oidcLogoutSuccessHandler())
                // enable OAuth2/OIDC
                .and().oauth2Client()
                .and().oauth2Login();
        http.csrf().disable();
        /*http.logout()
                .invalidateHttpSession(true)
                .deleteCookies("JSESSIONID")
                .logoutSuccessUrl("/index.html");*/
        // Send a 401 message to the browser (w/o this, you'll see a blank page)
        //Okta.configureResourceServer401ResponseBody(http);
    }
}

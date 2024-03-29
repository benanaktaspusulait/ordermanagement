package com.cgi.ordermanagement.config;

import com.cgi.ordermanagement.security.oauth.AjaxLogoutSuccessHandler;
import com.cgi.ordermanagement.security.oauth.Http401UnauthorizedEntryPoint;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.approval.ApprovalStore;
import org.springframework.security.oauth2.provider.approval.JdbcApprovalStore;
import org.springframework.security.oauth2.provider.code.AuthorizationCodeServices;
import org.springframework.security.oauth2.provider.code.JdbcAuthorizationCodeServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JdbcTokenStore;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.filter.CorsFilter;

import javax.sql.DataSource;

@Configuration
public class OAuth2ServerConfiguration {

    private final DataSource dataSource;

    public OAuth2ServerConfiguration(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Bean
    public JdbcTokenStore tokenStore() {
        return new JdbcTokenStore(dataSource);
    }


    @Configuration
    @EnableResourceServer
    protected static class ResourceServerConfiguration extends ResourceServerConfigurerAdapter {

        private final TokenStore tokenStore;

        private final Http401UnauthorizedEntryPoint http401UnauthorizedEntryPoint;

        private final AjaxLogoutSuccessHandler ajaxLogoutSuccessHandler;

        private final CorsFilter corsFilter;


        public ResourceServerConfiguration(TokenStore tokenStore, Http401UnauthorizedEntryPoint http401UnauthorizedEntryPoint,
                                           AjaxLogoutSuccessHandler ajaxLogoutSuccessHandler, CorsFilter corsFilter) {

            this.tokenStore = tokenStore;
            this.http401UnauthorizedEntryPoint = http401UnauthorizedEntryPoint;
            this.ajaxLogoutSuccessHandler = ajaxLogoutSuccessHandler;
            this.corsFilter = corsFilter;
        }

        @Override
        public void configure(HttpSecurity http) throws Exception {
            http
                    .exceptionHandling()
                    .authenticationEntryPoint(http401UnauthorizedEntryPoint)
                    .and()
                    .logout()
                    .logoutUrl("/api/logout")
                    .logoutSuccessHandler(ajaxLogoutSuccessHandler)
                    .and()
                    .csrf()
                    .disable()
                    .addFilterBefore(corsFilter, UsernamePasswordAuthenticationFilter.class)
                    .headers()
                    .frameOptions().disable()
                    .and()
                    .authorizeRequests()
                    .antMatchers("/api/v1/merchants/signup").permitAll()
                    .antMatchers("/oauth/token").permitAll()
                    .antMatchers("/v2/api-docs/**").permitAll()
                    .antMatchers("/swagger-resources/configuration/ui").permitAll()
                    .antMatchers("/swagger-ui.html").permitAll()
                    .antMatchers("/api/webhook/**").permitAll()
                    .antMatchers("/api/**").authenticated();
        }

        @Override
        public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
            resources.resourceId("res_todoApp").tokenStore(tokenStore);
        }
    }

    @Configuration
    @EnableAuthorizationServer
    protected static class AuthorizationServerConfiguration extends AuthorizationServerConfigurerAdapter {

        private final CustomAuthenticationManager authenticationManager;

        private final TokenStore tokenStore;

        private final DataSource dataSource;

        private ClientDetailsService customClientDetailsService;


        public AuthorizationServerConfiguration(CustomAuthenticationManager authenticationManager,
                                                TokenStore tokenStore, DataSource dataSource) {

            this.authenticationManager = authenticationManager;
            this.tokenStore = tokenStore;
            this.dataSource = dataSource;
        }

        @Bean
        protected AuthorizationCodeServices authorizationCodeServices() {
            return new JdbcAuthorizationCodeServices(dataSource);
        }

        @Bean
        public ApprovalStore approvalStore() {
            return new JdbcApprovalStore(dataSource);
        }

        @Override
        public void configure(AuthorizationServerEndpointsConfigurer endpoints)
                throws Exception {
            endpoints
                    .authorizationCodeServices(authorizationCodeServices())
                    .approvalStore(approvalStore())
                    .tokenStore(tokenStore)
                    .authenticationManager(authenticationManager)
                    ;
        }

        @Override
        public void configure(AuthorizationServerSecurityConfigurer oauthServer) throws Exception {
            oauthServer.allowFormAuthenticationForClients();
        }

        @Override
        public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
            clients.withClientDetails(customClientDetailsService);
            clients.jdbc(dataSource);
        }
    }


}

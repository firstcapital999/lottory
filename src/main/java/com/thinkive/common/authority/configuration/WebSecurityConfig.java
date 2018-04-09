package com.thinkive.common.authority.configuration;

import com.thinkive.common.authority.filter.ValidateCodeFilter;
import com.thinkive.common.authority.handler.LotteryAuthenticationSuccessHandler;
import com.thinkive.common.authority.service.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.token.KeyBasedPersistenceTokenService;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

import javax.annotation.Resource;
import javax.sql.DataSource;


/**
 * @Describe 配置security拦截器
 * @Author dengchangneng
 * @CreateTime 2017年10月9日16:52:14
 */
@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    @Autowired
    private LotteryAuthenticationSuccessHandler lotteryAuthenticationSuccessHandler;

    @Autowired
    private AuthenticationFailureHandler lottoryAuthenticationFailureHandler;

    @Resource
    private DataSource dataSource;

    @Autowired
    private UserDetailsService userDetailsService;


    @Bean
    public PersistentTokenRepository persistenceTokenRepository(){
        JdbcTokenRepositoryImpl tokenRepository = new JdbcTokenRepositoryImpl();
        tokenRepository.setDataSource(dataSource);
        //tokenRepository.setCreateTableOnStartup(true);
        return tokenRepository;
    }



    //http://localhost:8080/login 输入正确的用户名密码 并且选中remember-me 则登陆成功，转到 index页面
    //再次访问index页面无需登录直接访问
    //访问http://localhost:8080/home 不拦截，直接访问，
    //访问http://localhost:8080/hello 需要登录验证后，且具备 “ADMIN”权限hasAuthority("ADMIN")才可以访问
    @Override
    protected void configure(HttpSecurity http) throws Exception {

        ValidateCodeFilter validateCodeFilter = new ValidateCodeFilter();
        validateCodeFilter.setAuthenticationFailureHandler(lottoryAuthenticationFailureHandler);

        http.addFilterBefore(validateCodeFilter, UsernamePasswordAuthenticationFilter.class)
                .authorizeRequests()
                .antMatchers("/", "/home",
                        "/register",
                        "/regist/**",
                        "/testShort",
                        "/yourls/*",
                        "/images/**",
                        "/css/**",
                        "/scripts/**",
                        "/**/*.html",
                        "/webjars/**", "/js/**",
                        "/checkUser/*",
                        "/getLatestAwardList/**",
                        "/code/image").permitAll()//访问：/home 无需登录认证权限
                .anyRequest().authenticated() //其他所有资源都需要认证，登陆后访问
                .antMatchers("/hello").hasAuthority("ADMIN") //登陆后之后拥有“ADMIN”权限才可以访问/hello方法，否则系统会出现“403”权限不足的提示
                .and()
                .formLogin()
                .loginPage("/login")//指定登录页是”/login”
                .failureUrl("/login?error=true")
                .permitAll()
                .successHandler(lotteryAuthenticationSuccessHandler) //登录成功后可使用loginSuccessHandler()存储用户信息，可选。
                .and()
                .logout()
                /* .logoutSuccessUrl("/home") //退出登录后的默认网址是”/home”*/
                .permitAll()
                .invalidateHttpSession(true)
                .and()
                .rememberMe()//登录后记住用户，下次自动登录,数据库中必须存在名为persistent_logins的表
                .tokenRepository(persistenceTokenRepository())
                .tokenValiditySeconds(1209600)
                .userDetailsService(userDetailsService)
                .and().csrf().disable();//跨站防护关闭
    }

    /* @Autowired
     public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
 //指定密码加密所使用的加密器为passwordEncoder()
 //需要将密码加密后写入数据库
         *//*auth.userDetailsService(customUserDetailsService).passwordEncoder(passwordEncoder());
        auth.eraseCredentials(false);*//*
        auth
                .inMemoryAuthentication()
                .withUser("user").password("password").roles("USER");
    }
*/
    @Bean(name = "passwordEncoder")
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

  /*  @Bean
    public LoginSuccessHandler loginSuccessHandler(){
        return new LoginSuccessHandler();
    }*/


    public static void main(String[] args) {
        WebSecurityConfig webSecurityConfig = new WebSecurityConfig();
        String pwd = webSecurityConfig.passwordEncoder().encode("123456");
        System.out.print(pwd);
    }
}
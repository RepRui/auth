package com.li.auth.config;

import com.li.auth.basic.AccessInfo;
import jakarta.annotation.Resource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Collections;

@Configuration
@EnableWebSecurity  //启用Spring Security
//@EnableGlobalMethodSecurity(prePostEnabled = true)//启用基于注解的权限控制方案。
@EnableMethodSecurity(prePostEnabled = true,securedEnabled = true)//启用基于注解的权限控制方案。替换可以替换Spring boot 3.0中的@EnableGlobalMethodSecurity默认情况下是 true
public class SecurityConfig {

    @Resource
    private CustomerUserDetailsService customerUserDetailsService;

    @Resource
    private JWTValidationFilter jwtValidationFilter;

    @Resource
    private LoginAccessDefineHandler loginAccessDefineHandler;
    @Resource
    private LoginAuthenticationHandler loginAuthenticationHandler;


    /**
     * 获取AuthenticationManager（认证管理器），登录时认证使用
     */
    @Bean
    public AuthenticationManager authenticationManager() {
        DaoAuthenticationProvider authenticationProvider = daoAuthenticationProvider();
        return new ProviderManager(authenticationProvider);
    }
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }


    /**
     * 允许抛出用户不存在的异常
     * @return DaoAuthenticationProvider
     */
   // @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider(PasswordEncoder passwordEncoder) {
        final DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(customerUserDetailsService);
        //注释使用security默认密码验证器 provider.setUserDetailsPasswordService(customerUserDetailsService);//customerUserDetailsService需要实现接口UserDetailsPasswordService
        provider.setHideUserNotFoundExceptions(false);
        provider.setPasswordEncoder(passwordEncoder);//使用BCrypt加密
        return provider;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                //.cors(AbstractHttpConfigurer::disable)//跨域
                .cors((cors) -> cors.configurationSource(configurationSource()))//跨域

                .csrf(AbstractHttpConfigurer::disable)//使用JWT可以禁用csrf保护
                .formLogin(AbstractHttpConfigurer::disable)//禁用Security自带表单登录页面
                //.formLogin(Customizer.withDefaults())
                .logout(AbstractHttpConfigurer::disable)//禁用Security自带表单登出页面
                //.logout(Customizer.withDefaults())//禁用Security自带表单登出页面
                .httpBasic(AbstractHttpConfigurer::disable)//关闭HttpBasic认证
                //.httpBasic(Customizer.withDefaults())
                //.sessionManagement(AbstractHttpConfigurer::disable)//禁用session
                .sessionManagement((sessionManagement) -> sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS))// 配置了会话管理策略为 STATELESS（无状态）。在无状态的会话管理策略下，应用程序不会创建或使用 HTTP 会话，每个请求都是独立的，服务器不会在请求之间保留任何状态信息。

                .authorizeHttpRequests(authorizeHttpRequests -> {//访问白名单
                    authorizeHttpRequests.requestMatchers(AccessInfo.WHITE_URI).permitAll()
                        .anyRequest().authenticated();
                })


                .headers((headers) -> headers.frameOptions((HeadersConfigurer.FrameOptionsConfig::disable)))
                .headers((headers) -> headers.frameOptions((HeadersConfigurer.FrameOptionsConfig::sameOrigin)))

                .addFilterBefore(jwtValidationFilter, UsernamePasswordAuthenticationFilter.class)
                .exceptionHandling((exceptionHandling) -> exceptionHandling
                        .accessDeniedHandler(loginAccessDefineHandler)  // 无权限处理
                        .authenticationEntryPoint(loginAuthenticationHandler)//匿名处理
                )
        ;

        return http.build();
    }


    /**
     * 跨域配置
     */
    CorsConfigurationSource configurationSource() {
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.setAllowedHeaders(Collections.singletonList("*"));
        corsConfiguration.setAllowedMethods(Collections.singletonList("*"));
        corsConfiguration.setAllowedOrigins(Collections.singletonList("*"));
        corsConfiguration.setMaxAge(3600L);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", corsConfiguration);
        return source;
    }

}

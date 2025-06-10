package com.example.productmgmt.config;

import com.example.productmgmt.security.CustomUserDetailsService;
import com.example.productmgmt.security.JwtAuthenticationFilter;
import com.example.productmgmt.security.handler.CustomOAuth2SuccessHandler;
import com.example.productmgmt.security.oauth.CustomOAuth2UserService;
import com.example.productmgmt.service.UserService;
import com.example.productmgmt.service.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)
@RequiredArgsConstructor
public class SecurityConfig {

    private final CustomUserDetailsService customUserDetailsService;
    private final JwtAuthenticationFilter jwtAuthFilter;
    private final CustomOAuth2UserService customOAuth2UserService;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                // 關閉預設保護機制，改由 JWT 控制權限
                .csrf(csrf -> csrf.disable())
                .formLogin(form -> form.disable())
                .httpBasic(basic -> basic.disable())

                // 定義路由授權規則
                .authorizeHttpRequests(auth -> auth
                        // 開放登入、註冊、OAuth2 流程與 Swagger 文件
                        .requestMatchers("/auth/**", "/oauth2/**", "/login/**").permitAll()
                        .requestMatchers("/v3/api-docs/**", "/swagger-ui/**", "/swagger-ui.html").permitAll()

                        // 權限路由：根據角色控制資源存取
                        .requestMatchers("/api/users/**", "/products/**").hasAnyAuthority("MERCHANT", "STAFF")
                        .requestMatchers("/admin/**").hasAuthority("STAFF")

                        // 其他請求需經身份驗證
                        .anyRequest().authenticated()
                )

                // 設定 OAuth2 成功登入處理與用戶資料服務
                .oauth2Login(oauth -> oauth
                        .userInfoEndpoint(userInfo -> userInfo.userService(customOAuth2UserService))
                        .successHandler(customOAuth2SuccessHandler(null, null)) // JWT 發發發
                )

                // 使用自定的帳密驗證機制
                .authenticationProvider(authenticationProvider())

                // 加入 JWT 驗證過濾器
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public CustomOAuth2SuccessHandler customOAuth2SuccessHandler(UserService userService, JwtService jwtService) {
        return new CustomOAuth2SuccessHandler(userService, jwtService);
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(customUserDetailsService);
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }
}

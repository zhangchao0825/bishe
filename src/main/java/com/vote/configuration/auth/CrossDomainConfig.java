package com.vote.configuration.auth;

import com.vote.util.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.Collections;

@Configuration
public class CrossDomainConfig {

    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    /**
     *
     * @return 基于URL的跨域配置信息
     */
    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        //添加cors配置信息
        CorsConfiguration cores=new CorsConfiguration();
        //允许客户端携带认证信息
        cores.setAllowCredentials(true);
        //springBoot 2.4.1版本之后，不可以用 * 号设置允许的Origin,如果不降低版本，则在跨域设置时使用setAllowedOriginPatterns方法
        cores.setAllowedOriginPatterns(Collections.singletonList("*"));
        //允许哪些请求方式可以访问
        cores.setAllowedMethods(Arrays.asList("GET","POST","DELETE","PUT","UPDATE"));
        //允许服务端访问的客户端请求头
        cores.setAllowedHeaders(Collections.singletonList("*"));
        // 暴露哪些头部信息（因为跨域访问默认不能获取全部头部信息）
        cores.addExposedHeader(jwtTokenUtil.getHeader());
        // 注册跨域配置
        // 也可以使用CorsConfiguration 类的 applyPermitDefaultValues()方法使用默认配置
        source.registerCorsConfiguration("/**",cores.applyPermitDefaultValues());
        return source;
    }

}

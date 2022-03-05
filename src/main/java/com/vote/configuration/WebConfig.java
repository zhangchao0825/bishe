package com.vote.configuration;

import com.vote.util.FilesUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.web.firewall.HttpFirewall;
import org.springframework.security.web.firewall.StrictHttpFirewall;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author ZhangChaoEr
 * @version 1.0
 * @date 2022/2/11 18:31
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Autowired
    private FilesUtil filesUtil;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {

        registry.addResourceHandler(filesUtil.getRelativePath()+"**").addResourceLocations("file:/"+filesUtil.getAbsolutePath());

    }
}

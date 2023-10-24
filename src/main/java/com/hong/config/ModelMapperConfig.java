package com.hong.config;

import com.hong.wrapper.CustomMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelMapperConfig {

    @Bean
    public CustomMapper customMapper(){
        CustomMapper customMapper = new CustomMapper();
        customMapper.getConfiguration()
                .setFieldAccessLevel(org.modelmapper.config.Configuration.AccessLevel.PRIVATE)
                .setFieldMatchingEnabled(true);
        return customMapper;
    }

}

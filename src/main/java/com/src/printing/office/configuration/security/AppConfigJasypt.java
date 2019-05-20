package com.src.printing.office.configuration.security;

import org.springframework.context.annotation.Configuration;

import com.ulisesbocchio.jasyptspringboot.annotation.EncryptablePropertySource;

@Configuration
@EncryptablePropertySource("encrypted.properties")
public class AppConfigJasypt {

}

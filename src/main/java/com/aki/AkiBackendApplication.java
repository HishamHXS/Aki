package com.aki;

import com.aki.config.RsaKeyProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@EnableConfigurationProperties(RsaKeyProperties.class)
@SpringBootApplication
public class AkiBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(AkiBackendApplication.class, args);
	}

}

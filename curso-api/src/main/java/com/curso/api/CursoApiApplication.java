package com.curso.api;

import java.util.TimeZone;

import javax.annotation.PostConstruct;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import com.curso.api.config.property.CursoApiProperty;

@SpringBootApplication
@EnableConfigurationProperties(CursoApiProperty.class)
public class CursoApiApplication {


	@PostConstruct
	void started() {
		TimeZone.setDefault(TimeZone.getTimeZone("TimeZone"));
	}
	public static void main(String[] args) {
		SpringApplication.run(CursoApiApplication.class, args);
	}

}

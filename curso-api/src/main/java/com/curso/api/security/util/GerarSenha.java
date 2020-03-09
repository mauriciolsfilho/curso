package com.curso.api.security.util;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class GerarSenha {

	public static void main(String[] args) {
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		System.out.println(encoder.encode("user"));
	}
}

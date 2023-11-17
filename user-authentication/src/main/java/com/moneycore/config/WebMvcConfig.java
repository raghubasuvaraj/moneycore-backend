package com.moneycore.config;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.AcceptHeaderLocaleResolver;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

@Configuration
public class WebMvcConfig extends AcceptHeaderLocaleResolver implements WebMvcConfigurer {

	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
		return bCryptPasswordEncoder;
	}
	
	@Bean
	public RestTemplate getRestTemplate() {
		return new RestTemplate();
	}

	@Override
	public void addCorsMappings(CorsRegistry registry) {
		registry.addMapping("/**").allowedMethods("GET", "POST", "PUT", "DELETE", "HEAD", "OPTIONS").allowedOrigins("*")
				.allowedHeaders("Authorization", "Cache-Control", "Content-Type", "Accept", "X-Requested-With",
						"Access-Control-Allow-Origin", "Access-Control-Allow-Headers", "Origin", "Accept-Language")
				.exposedHeaders("Access-Control-Expose-Headers", "Authorization", "Cache-Control", "Content-Type",
						"Access-Control-Allow-Origin", "Access-Control-Allow-Headers", "Origin", "Accept-Language");
	}

	List<Locale> LOCALES = Arrays.asList(
			new Locale("en"),
			new Locale("fr"));

	@Override
	public Locale resolveLocale(HttpServletRequest request) {
		String headerLang = request.getHeader("Accept-Language");
		return headerLang == null || headerLang.isEmpty()
				? Locale.getDefault()
				: Locale.lookup(Locale.LanguageRange.parse(headerLang), LOCALES);
	}

	@Bean("messageSource")
	public MessageSource messageSource() {
		ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
		messageSource.setBasenames("language/messages");
//		messageSource.setDefaultEncoding("UTF-8");
//		messageSource.setUseCodeAsDefaultMessage(true);
		return messageSource;
	}

//	@Override
//	public void addInterceptors(InterceptorRegistry interceptorRegistry) {
//		interceptorRegistry.addInterceptor(localeChangeInterceptor());
//	}
//
//	@Bean
//	public LocaleChangeInterceptor localeChangeInterceptor() {
//		LocaleChangeInterceptor localeChangeInterceptor = new LocaleChangeInterceptor();
//		// Defaults to "locale" if not set
//		localeChangeInterceptor.setParamName("Accept-Language");
//		return localeChangeInterceptor;
//	}
}

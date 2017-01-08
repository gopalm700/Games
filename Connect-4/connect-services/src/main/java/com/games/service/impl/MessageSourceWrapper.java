package com.games.service.impl;

import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;

@Service
public class MessageSourceWrapper {

	@Autowired
	private MessageSource messageSource;

	public String getMessage(String key, Object[] args) {
		Locale locale = LocaleContextHolder.getLocale();
		return messageSource.getMessage(key, args, locale);
	}

	
	public String getMessage(String key) {
		Locale locale = LocaleContextHolder.getLocale();
		return messageSource.getMessage(key, null, locale);
	}
	
	public String getMessage(String key, Object[] args, Locale locale) {
		return messageSource.getMessage(key, null, locale);
	}
	
}

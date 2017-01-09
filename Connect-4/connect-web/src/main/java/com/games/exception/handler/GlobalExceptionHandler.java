package com.games.exception.handler;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import com.games.dto.BaseResponse;
import com.games.exception.ApplicationException;
import com.games.service.impl.MessageSourceWrapper;

@ControllerAdvice
public class GlobalExceptionHandler {

	Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

	@Autowired
	private MessageSourceWrapper messageSourceWrapper;

	@ExceptionHandler(value = ApplicationException.class)
	@ResponseBody
	public BaseResponse handleApplicationExceptionException(ApplicationException ex, HttpServletResponse response) {
		logger.error("GlobalExceptionHandler handler executed", ex);
		BaseResponse error = new BaseResponse();
		error.setError(true);
		if (ex.getErrorCode() == null) {
			response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
			error.setErrorMessage("Exception occurled");
		} else {

			int errorCode = ex.getErrorCode();
			if (errorCode >= 100 && errorCode < 200) {
				response.setStatus(HttpStatus.BAD_REQUEST.value());
			}
			error.setError(true);
			error.setErrorMessage(messageSourceWrapper.getMessage(ex.getErrorCode().toString(), ex.getArgs()));
		}
		return error;
	}
	
	@ExceptionHandler(value = HttpRequestMethodNotSupportedException.class)
	@ResponseBody
	public BaseResponse handleMethodNotSupportedException(Exception ex, HttpServletResponse response) {
		logger.error("GlobalExceptionHandler handler executed", ex);
		BaseResponse error = new BaseResponse();
		error.setError(true);
		error.setErrorMessage(ex.getMessage());
		response.setStatus(HttpStatus.METHOD_NOT_ALLOWED.value());
		return error;
	}
	
	@ExceptionHandler(value = Exception.class)
	@ResponseBody
	public BaseResponse handleException(Exception ex, HttpServletResponse response) {
		logger.error("GlobalExceptionHandler handler executed", ex);
		BaseResponse error = new BaseResponse();
		error.setError(true);
		error.setErrorMessage(ex.getMessage());
		response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
		return error;
	}

}

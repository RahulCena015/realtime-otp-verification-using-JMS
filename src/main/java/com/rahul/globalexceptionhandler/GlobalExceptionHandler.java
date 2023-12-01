package com.rahul.globalexceptionhandler;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.rahul.dto.ErrorDto;
import com.rahul.dto.ServiceResponse;
import com.rahul.exception.LoginBusinessException;

@RestControllerAdvice
public class GlobalExceptionHandler {
	
	@ExceptionHandler(LoginBusinessException.class)
    public ServiceResponse handleServiceException(LoginBusinessException exception){
        ServiceResponse serviceResponse = new ServiceResponse();
        List<ErrorDto> errorDTOList = new ArrayList<>();
        errorDTOList.add(new ErrorDto(exception.getMessage(),LocalDateTime.now()));
        serviceResponse.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
        serviceResponse.setError(errorDTOList);
        return serviceResponse;
    }

}

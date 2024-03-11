package com.elpidoroun.financialportfolio.controller;


import com.elpidoroun.financialportfolio.controller.command.AbstractRequest;
import com.elpidoroun.financialportfolio.controller.command.Command;
import com.google.common.collect.ImmutableMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import javax.validation.ValidationException;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.NOT_FOUND;

public class MainController {

    private static final Logger logger = LoggerFactory.getLogger(MainController.class);

    private static final Map<Class<?>, HttpStatus> exceptionStatuses = new ImmutableMap.Builder<Class<?>, HttpStatus>()
            .put(ValidationException.class, BAD_REQUEST)
            .build();

    protected <RequestT extends AbstractRequest, ResponseT> ResponseEntity<?> execute(
            Command<RequestT, ResponseT> command, RequestT request){
        return execute(command, request, ResponseEntity::ok);
    }

    protected <RequestT extends AbstractRequest, ResponseT> ResponseEntity<?> execute(
            Command<RequestT, ResponseT> command, RequestT request,
            Function<ResponseT, ResponseEntity<?>> responseBuilder){
        try{
            return responseBuilder.apply(command.execute(request));
        } catch (Exception exception){
            logger.error(exception.getMessage());

            return ResponseEntity.status(httpStatusByException(exception))
                    .body(exception.getMessage());
        }
    }

    private HttpStatus httpStatusByException(Exception exception){
        return Optional.ofNullable(exceptionStatuses.get(exception.getClass())).orElse(INTERNAL_SERVER_ERROR);
    }


}
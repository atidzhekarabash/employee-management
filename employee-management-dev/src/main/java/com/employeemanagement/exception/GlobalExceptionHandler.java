package com.employeemanagement.exception;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.employeemanagement.exception.common.DuplicateEntryException;
import com.employeemanagement.exception.common.InvalidArgumentException;
import com.employeemanagement.exception.common.NotFoundException;
import com.employeemanagement.exception.common.OverlappingLeaveException;
import com.employeemanagement.exception.common.RelatedEntityNotFoundException;
import com.employeemanagement.exception.common.TeamNotEmptyException;
import com.employeemanagement.util.MessageUtil;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@ControllerAdvice
public class GlobalExceptionHandler {

  private final MessageUtil messageUtil;
  private final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

  public GlobalExceptionHandler(MessageUtil messageUtil) {
    this.messageUtil = messageUtil;
  }

  @ExceptionHandler(value = {RelatedEntityNotFoundException.class})
  public ResponseEntity<Object> handleRelatedEntityNotFoundException(
      RelatedEntityNotFoundException e) {
    logger.warn(e.getMessage(), e);
    return new ResponseEntity<>(e.getMessage(), HttpStatus.UNPROCESSABLE_ENTITY);
  }

  @ExceptionHandler(value = {
      TeamNotEmptyException.class,
      DuplicateEntryException.class,
      InvalidArgumentException.class
  })
  public ResponseEntity<Object> handleConflictExceptions(RuntimeException e) {
    logger.info(e.getMessage(), e);
    return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
  }

  @ExceptionHandler(value = {OverlappingLeaveException.class})
  public ResponseEntity<Object> handleOverlappingLeaveException(OverlappingLeaveException e) {
    logger.info(e.getMessage(), e);

    StringBuilder errorMessage = new StringBuilder(
        messageUtil.getMessage("exception.leave_overlap"));
    for (LocalDate[] period : e.getOverlappingPeriods()) {
      errorMessage.append(String.format("[%s - %s]", period[0], period[1]));
    }

    return new ResponseEntity<>(errorMessage.toString(), HttpStatus.CONFLICT);
  }

  @ExceptionHandler(value = {NotFoundException.class})
  public ResponseEntity<Object> handleNotFoundException(NotFoundException e) {
    logger.warn(e.getMessage(), e);
    return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<String> handleMethodArgumentNotValidException(
      MethodArgumentNotValidException e) {
    logger.info(e.getMessage(), e);

    List<String> errorMessages = new ArrayList<>();
    e.getBindingResult()
        .getAllErrors()
        .forEach(error -> errorMessages.add(formatValidationError(error)));

    return new ResponseEntity<>(String.join("\n", errorMessages), HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(MethodArgumentTypeMismatchException.class)
  public ResponseEntity<String> handleMethodArgumentTypeMismatchException(
      MethodArgumentTypeMismatchException e) {
    logger.info(e.getMessage(), e);
    String errorMessage = messageUtil.getMessage(
        "exception.invalid_format",
        e.getValue(),
        e.getName());

    return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(value = {HttpMessageNotReadableException.class})
  public ResponseEntity<Object> handleHttpMessageNotReadableException(
      HttpMessageNotReadableException e) {
    logger.info(e.getMessage(), e);
    if (e.getCause() instanceof InvalidFormatException invalidFormatException) {
      String errorMessage = messageUtil.getMessage(
          "exception.invalid_format",
          invalidFormatException.getValue(),
          invalidFormatException.getPath().getFirst().getFieldName());

      return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
    }

    return new ResponseEntity<>(messageUtil.getMessage("exception.malformed_json"),
        HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(value = {Exception.class})
  public ResponseEntity<Object> handleException(Exception e) {
    logger.error(e.getMessage(), e);
    return new ResponseEntity<>(messageUtil.getMessage("exception.general_error"),
        HttpStatus.INTERNAL_SERVER_ERROR);
  }

  private static String formatValidationError(ObjectError error) {
    return ((FieldError) error).getField() + " " + error.getDefaultMessage();
  }
}

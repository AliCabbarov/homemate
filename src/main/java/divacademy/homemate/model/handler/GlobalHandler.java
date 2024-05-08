package divacademy.homemate.model.handler;

import divacademy.homemate.model.exception.ApplicationException;
import divacademy.homemate.model.exception.NotFoundException;
import divacademy.homemate.model.exception.UsernameNotFoundException;
import divacademy.homemate.util.MessageUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.servlet.error.DefaultErrorAttributes;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.context.request.WebRequest;
import divacademy.homemate.model.enums.Exceptions;
import org.springframework.security.access.AccessDeniedException;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
@RequiredArgsConstructor
public class GlobalHandler extends DefaultErrorAttributes {

    private final MessageUtil messageUtil;

    @ExceptionHandler(ApplicationException.class)
    public ResponseEntity<Map<String, Object>> handle(ApplicationException ex,
                                                      WebRequest webRequest) {
        return of(ex,webRequest);
    }
    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<Map<String, Object>> handle(NotFoundException ex,
                                                      WebRequest webRequest) {
        return of(ex,webRequest);
    }
    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<Map<String, Object>> handle(UsernameNotFoundException ex,
                                                      WebRequest webRequest) {
        return of(ex,webRequest);
    }
    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<Map<String, Object>> handle(BadCredentialsException ex,
                                                      WebRequest webRequest) {
        return of(ex,webRequest);
    }
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<Map<String, Object>> handle(DataIntegrityViolationException ex,
                                                      WebRequest webRequest) {
        return of(ex,webRequest);
    }
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> handle(MethodArgumentNotValidException ex,
                                                      WebRequest webRequest) {
        return of(ex,webRequest);
    }
    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<Map<String, Object>> handle(AccessDeniedException ex,
                                                      WebRequest webRequest) {
        return of(ex,webRequest);
    }
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, Object>> handle(Exception ex,
                                                      WebRequest webRequest) {
        return of(ex, webRequest);
    }

    private Map<String, Object> buildExceptionResponse(String message,
                                                       WebRequest webRequest,
                                                       HttpStatus http) {

        Map<String, Object> errorAttributes = getErrorAttributes(webRequest, ErrorAttributeOptions.defaults());
        errorAttributes.put("error", message);
        errorAttributes.put("status", http.value());
        errorAttributes.put("timestamp", LocalDateTime.now());
        errorAttributes.put("path", ((ServletRequestAttributes) webRequest).getRequest().getServletPath());

        return errorAttributes;
    }

    private Map<String, Object> buildExceptionResponse(MethodArgumentNotValidException ex,
                                                       WebRequest webRequest) {

        Map<String,Object> invalidFields = new HashMap<>();
        Map<String, Object> errorAttributes = getErrorAttributes(webRequest, ErrorAttributeOptions.defaults());


        ex.getFieldErrors().forEach(fieldError -> invalidFields.put(fieldError.getField(),messageUtil.getMessage(fieldError.getDefaultMessage())));

        errorAttributes.put("error", invalidFields);
        errorAttributes.put("status", HttpStatus.BAD_REQUEST.value());
        errorAttributes.put("timestamp", LocalDateTime.now());
        errorAttributes.put("path", ((ServletRequestAttributes) webRequest).getRequest().getServletPath());

        return errorAttributes;
    }

    private ResponseEntity<Map<String,Object>> of(ApplicationException ex,WebRequest webRequest){
        return new ResponseEntity<>(
                buildExceptionResponse(messageUtil.getMessage(ex.getResponse().getMessage()),
                        webRequest,
                        ex.getResponse().getStatus()),
                ex.getResponse().getStatus());
    }
    private ResponseEntity<Map<String,Object>> of(MethodArgumentNotValidException ex,WebRequest webRequest){
        return new ResponseEntity<>(
                buildExceptionResponse(ex,
                        webRequest
                ),
                HttpStatus.BAD_REQUEST);
    }
    private ResponseEntity<Map<String,Object>> of(AccessDeniedException ex,WebRequest webRequest){
        return new ResponseEntity<>(
                buildExceptionResponse(messageUtil.getMessage(Exceptions.ACCESS_DENIED_EXCEPTION.getMessage()),
                        webRequest,
                        Exceptions.ACCESS_DENIED_EXCEPTION.getStatus()),
                Exceptions.ACCESS_DENIED_EXCEPTION.getStatus());
    }
    private ResponseEntity<Map<String,Object>> of(Exception ex,WebRequest webRequest){
        return new ResponseEntity<>(
                buildExceptionResponse(ex.getMessage(),
                        webRequest,
                        HttpStatus.BAD_REQUEST),
                HttpStatus.BAD_REQUEST);
    }
    private ResponseEntity<Map<String,Object>> of(DataIntegrityViolationException ex,WebRequest webRequest){
        return new ResponseEntity<>(
                buildExceptionResponse(messageUtil.getMessage(Exceptions.USERNAME_IS_UNAVAILABLE_EXCEPTION.getMessage()),
                        webRequest,
                        Exceptions.USERNAME_IS_UNAVAILABLE_EXCEPTION.getStatus()),
                Exceptions.USERNAME_IS_UNAVAILABLE_EXCEPTION.getStatus());
    }

    private ResponseEntity<Map<String,Object>> of(BadCredentialsException ex,
                                                  WebRequest webRequest){
        return new ResponseEntity<>(
                buildExceptionResponse(messageUtil.getMessage(Exceptions.BAD_CREDENTIALS_EXCEPTION.getMessage()),
                        webRequest,
                        Exceptions.BAD_CREDENTIALS_EXCEPTION.getStatus()),
                Exceptions.BAD_CREDENTIALS_EXCEPTION.getStatus());
    }
    private ResponseEntity<Map<String,Object>> of(NotFoundException ex, WebRequest webRequest){
        return new ResponseEntity<>(buildExceptionResponse(
                messageUtil.getMessage(ex.getResponse().getMessage(),ex.getDynamicKey()),
                webRequest,
                ex.getResponse().getStatus()),ex.getResponse().getStatus());
    }
    private ResponseEntity<Map<String,Object>> of(UsernameNotFoundException ex, WebRequest webRequest){
        return new ResponseEntity<>(buildExceptionResponse(
                messageUtil.getMessage(ex.getResponse().getMessage()),
                webRequest,
                ex.getResponse().getStatus()),ex.getResponse().getStatus());
    }
}

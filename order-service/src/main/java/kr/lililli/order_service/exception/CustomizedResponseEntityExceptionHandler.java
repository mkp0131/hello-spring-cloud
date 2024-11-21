package kr.lililli.order_service.exception;

import java.util.Date;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import org.springframework.web.servlet.resource.NoResourceFoundException;
import kr.lililli.order_service.vo.ApiResponse;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@RestControllerAdvice
public class CustomizedResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {


    @ExceptionHandler(Exception.class)
    public final ResponseEntity<?> handleAllExceptions(Exception ex, WebRequest request) {
        log.error("@@@@@@@@@@@@handleAllExceptions");
        return new ResponseEntity<>(ApiResponse.error(ex.getMessage()),
                org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(UserNotFoundException.class)
    public final ResponseEntity<?> handleUserNotFoundException(Exception ex, WebRequest request) {
        log.error("@@@@@@@@@@@@handleUserNotFoundException", ex);
        return new ResponseEntity<>(ApiResponse.error(ex.getMessage()), HttpStatus.NOT_FOUND);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status,
            WebRequest request) {
        StringBuilder details = new StringBuilder();
        ex.getBindingResult().getFieldErrors().forEach(error -> {
            details.append(error.getDefaultMessage());
        });

        log.error("@@@@@@@@@@@@handleMethodArgumentNotValid");
        return new ResponseEntity<>(ApiResponse.error(details.toString().trim()),
                HttpStatus.BAD_REQUEST);
    }

    @Override
    protected ResponseEntity<Object> handleNoResourceFoundException(NoResourceFoundException ex,
            HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        log.error("@@@@@@@@@@@@페이지를 찾을 수 없습니다.");
        return new ResponseEntity<>(ApiResponse.error("페이지를 찾을 수 없습니다."), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(JdbcSQLIntegrityConstraintViolationException.class)
    public final ResponseEntity<?> handleSQLIntegrityConstraintViolationException(
            JdbcSQLIntegrityConstraintViolationException ex, WebRequest request) {
        log.error("@@@@@@@@@@@@handleSQLIntegrityConstraintViolationException", ex);
        return new ResponseEntity<>(ApiResponse.error("이미 등록된 이메일입니다."), HttpStatus.CONFLICT);
    }
}

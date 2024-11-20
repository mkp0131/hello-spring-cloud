package kr.lililli.user_service.exception;

public class JdbcSQLIntegrityConstraintViolationException extends RuntimeException {
    public JdbcSQLIntegrityConstraintViolationException(String message) {
        super(message);
    }
}

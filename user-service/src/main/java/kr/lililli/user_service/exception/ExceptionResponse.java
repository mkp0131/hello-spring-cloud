package kr.lililli.user_service.exception;

import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExceptionResponse {
    private boolean ok = false;
    // private Date timestamp;
    private String error;
    // private String details;
}

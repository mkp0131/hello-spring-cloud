package kr.lililli.user_service.vo;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL) // null 값은 포함하지 않음
public class ResponseUser {
    private String userId;
    private String email;
    private String name;

    private List<ResponseOrder> orders;
}

package kr.lililli.user_service.service;

import java.util.List;
import kr.lililli.user_service.dto.UserDto;

public interface UserService {
    UserDto createUser(UserDto userDto);

    UserDto getUserByUserId(String userId);

    List<UserDto> getUserByAll();
}

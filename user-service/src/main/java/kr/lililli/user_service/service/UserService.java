package kr.lililli.user_service.service;

import kr.lililli.user_service.dto.UserDto;

public interface UserService {
    UserDto createUser(UserDto userDto);
}

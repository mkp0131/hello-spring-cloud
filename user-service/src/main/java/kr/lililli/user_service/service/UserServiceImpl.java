package kr.lililli.user_service.service;

import java.util.UUID;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.stereotype.Service;
import kr.lililli.user_service.dto.UserDto;
import kr.lililli.user_service.jpa.UserEntity;
import kr.lililli.user_service.jpa.UserRepository;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDto createUser(UserDto userDto) {
        userDto.setUserId(UUID.randomUUID().toString());
        ModelMapper modelMapper = new ModelMapper();
        // STRICT 매칭 전략 설정
        // - 소스와 대상의 필드명이 정확히 일치해야 매핑됨
        // - 더 엄격한 타입 검사를 수행
        // - 모호한 매핑을 허용하지 않음
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        UserEntity userEntity = modelMapper.map(userDto, UserEntity.class);
        // TODO: 비밀번호 암호화
        userEntity.setEncryptedPwd("encrypted_password");

        userRepository.save(userEntity);

        return null;
    }

}

package kr.lililli.user_service.service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import kr.lililli.user_service.dto.UserDto;
import kr.lililli.user_service.exception.UserNotFoundException;
import kr.lililli.user_service.jpa.UserEntity;
import kr.lililli.user_service.jpa.UserRepository;
import kr.lililli.user_service.vo.ResponseOrder;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final BCryptPasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, ModelMapper modelMapper,
            BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDto createUser(UserDto userDto) {
        userDto.setUserId(UUID.randomUUID().toString());
        String encryptedPwd = passwordEncoder.encode(userDto.getPwd());
        userDto.setEncryptedPwd(encryptedPwd);

        UserEntity userEntity = modelMapper.map(userDto, UserEntity.class);
        UserEntity savedEntity = userRepository.save(userEntity);
        UserDto returnUserDto = modelMapper.map(savedEntity, UserDto.class);

        return returnUserDto;
    }

    @Override
    public List<UserDto> getUserByAll() {
        // TODO Auto-generated method stub
        Iterable<UserEntity> userEntities = userRepository.findAll();
        List<UserDto> returnUserDtoList = new ArrayList<>();

        userEntities.forEach(v -> {
            returnUserDtoList.add(modelMapper.map(v, UserDto.class));
        });

        return returnUserDtoList;
    }

    @Override
    public UserDto getUserByUserId(String userId) {
        UserEntity userEntity = userRepository.findByUserId(userId);

        if (userEntity == null) {
            throw new UserNotFoundException("유저가 없습니다.");
        }

        UserDto userDto = modelMapper.map(userEntity, UserDto.class);

        List<ResponseOrder> orders = new ArrayList<>();
        userDto.setOrders(orders);

        return userDto;
    }



}

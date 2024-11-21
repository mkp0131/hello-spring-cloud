package kr.lililli.user_service.controller;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import jakarta.validation.Valid;
import kr.lililli.user_service.dto.UserDto;
import kr.lililli.user_service.jpa.UserEntity;
import kr.lililli.user_service.service.UserService;
import kr.lililli.user_service.vo.ApiResponse;
import kr.lililli.user_service.vo.RequestUser;
import kr.lililli.user_service.vo.ResponseUser;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;



@RestController
@RequestMapping("/user-service")
public class UserController {
    private final UserService userService;
    private final ModelMapper modelMapper;

    public UserController(UserService userService, ModelMapper modelMapper) {
        this.userService = userService;
        this.modelMapper = modelMapper;
    }

    @GetMapping({"", "/"})
    public String getHello() {
        return "Hello User Service";
    }

    @PostMapping("/users")
    public ResponseEntity<?> postMethodName(@Valid @RequestBody RequestUser user) {
        UserDto userDto = modelMapper.map(user, UserDto.class);
        UserDto createdUser = userService.createUser(userDto);
        ResponseUser responseUser = modelMapper.map(createdUser, ResponseUser.class);

        // URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
        // .buildAndExpand(responseUser.getId()).toUri();

        return ResponseEntity.created(null).body(ApiResponse.success(responseUser));
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<?> getUserById(@PathVariable("id") String id) {
        UserDto userDto = userService.getUserByUserId(id);
        ResponseUser responseUser = modelMapper.map(userDto, ResponseUser.class);
        return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.success(responseUser));
    }

    @GetMapping("/users")
    public ResponseEntity<?> getUserByAll() {
        List<UserDto> userDtoList = userService.getUserByAll();
        List<ResponseUser> responseUserList = new ArrayList<>();

        userDtoList.forEach(v -> {
            responseUserList.add(modelMapper.map(v, ResponseUser.class));
        });


        return ResponseEntity.status(HttpStatus.OK).body(ApiResponse.success(responseUserList));
    }

}

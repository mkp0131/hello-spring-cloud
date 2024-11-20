package kr.lililli.user_service.controller;

import org.springframework.web.bind.annotation.RestController;
import jakarta.validation.Valid;
import kr.lililli.user_service.dto.UserDto;
import kr.lililli.user_service.service.UserService;
import kr.lililli.user_service.vo.RequestUser;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;



@RestController
@RequestMapping("/user")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping({"", "/"})
    public String getHello() {
        return "Hello User Service";
    }

    @PostMapping({"", "/"})
    public ResponseEntity<?> postMethodName(@Valid @RequestBody RequestUser user) {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        UserDto userDto = modelMapper.map(user, UserDto.class);
        UserDto createdUser = userService.createUser(userDto);

        return ResponseEntity.ok(createdUser);
    }

}

package kr.lililli.user_service.service;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import kr.lililli.user_service.exception.UserNotFoundException;
import kr.lililli.user_service.jpa.UserEntity;
import kr.lililli.user_service.jpa.UserRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity userEntity = userRepository.findByEmail(username);
        if (userEntity == null) {
            throw new UserNotFoundException("User not found: " + username);
        }

        return User.builder().username(userEntity.getEmail()).password(userEntity.getEncryptedPwd())
                .roles("USER").build();
    }
}

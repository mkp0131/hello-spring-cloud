package kr.lililli.user_service.jpa;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
    UserEntity findByUserId(String userId); // String 타입으로 선언

    UserEntity findByEmail(String email);
}

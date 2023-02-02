package com.hosu.sns.service;

import com.hosu.sns.exception.SnsApplicationException;
import com.hosu.sns.model.User;
import com.hosu.sns.model.entity.UserEntity;
import com.hosu.sns.repository.UserEntityRepository;
import com.hosu.sns.util.JwtTokenUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.hosu.sns.exception.ErrorCode.*;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserEntityRepository userEntityRepository;
    private final BCryptPasswordEncoder encoder;

    @Value("${jwt.secret-key}") private String key;
    @Value("${jwt.token.expired-time-ms}") private long expiredTimeMs;

    //    TODO : implement

    public User loadUserByUserName(String userName){
        return userEntityRepository.findByUserName(userName).map(User::fromEntity).orElseThrow(
                () -> new SnsApplicationException(USER_NOT_FOUND, String.format("%s not founded",userName));
        );
    }

    @Transactional
    public User join(String userName, String password){
        userEntityRepository.findByUserName(userName).ifPresent(it ->{
            throw new SnsApplicationException(DUPLICATED_USER_NAME, String.format("%s is duplicated", userName));
        });

        UserEntity userEntity = userEntityRepository.save(UserEntity.of(userName, encoder.encode(password)));

        return User.fromEntity(userEntity);
    }

    //TODO : implement
    public String login(String userName, String password){

        //회원가입여부
        UserEntity userEntity = userEntityRepository.findByUserName(userName)
                .orElseThrow(() -> new SnsApplicationException(USER_NOT_FOUND, String.format("%s not found", userName)));

        //비밀번호체크
        if(!encoder.matches(password, userEntity.getPassword())){
            throw new SnsApplicationException(INVALID_PASSWORD);
        }

        //토큰생성
        String token = JwtTokenUtils.generateToken(userName, key, expiredTimeMs);

        return token;
    }

}

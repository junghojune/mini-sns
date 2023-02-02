package com.hosu.sns.service;

import com.hosu.sns.exception.SnsApplicationException;
import com.hosu.sns.model.User;
import com.hosu.sns.model.entity.UserEntity;
import com.hosu.sns.repository.UserEntityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import static com.hosu.sns.exception.ErrorCode.DUPLICATED_USER_NAME;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserEntityRepository userEntityRepository;
    private final BCryptPasswordEncoder encoder;

    //    TODO : implement
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
        UserEntity userEntity = userEntityRepository.findByUserName(userName).orElseThrow(() -> new SnsApplicationException(DUPLICATED_USER_NAME, ""));

        //비밀번호채크
        if(!userEntity.getPassword().equals(password)){
            throw new SnsApplicationException(DUPLICATED_USER_NAME, "");
        }
        //토큰생성
        return "";
    }

}

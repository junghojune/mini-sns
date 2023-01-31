package com.hosu.sns.service;

import com.hosu.sns.exception.SnsApplicationException;
import com.hosu.sns.model.User;
import com.hosu.sns.model.entity.UserEntity;
import com.hosu.sns.repository.UserEntityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserEntityRepository userEntityRepository;
//    TODO : implement
    public User join(String userName, String password){
        Optional<UserEntity> userEntity =
                userEntityRepository.findByUserName(userName);

        userEntityRepository.save(new UserEntity());
        return new User();
    }

    //TODO : implement
    public String login(String userName, String password){

        //회원가입여부
        UserEntity userEntity = userEntityRepository.findByUserName(userName).orElseThrow(() -> new SnsApplicationException());

        //비밀번호채크
        if(!userEntity.getPassword().equals(password)){
            throw new SnsApplicationException();
        }
        //토큰생성
        return "";
    }

}

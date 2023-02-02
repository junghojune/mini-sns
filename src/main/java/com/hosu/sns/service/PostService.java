package com.hosu.sns.service;

import com.hosu.sns.exception.SnsApplicationException;
import com.hosu.sns.model.entity.PostEntity;
import com.hosu.sns.model.entity.UserEntity;
import com.hosu.sns.repository.PostEntityRepository;
import com.hosu.sns.repository.UserEntityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.hosu.sns.exception.ErrorCode.USER_NOT_FOUND;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostEntityRepository postEntityRepository;
    private final UserEntityRepository userEntityRepository;

    @Transactional
    public void create(String title, String body, String userName) {
        // user find
        UserEntity userEntity = userEntityRepository.findByUserName(userName)
                .orElseThrow(() -> new SnsApplicationException(USER_NOT_FOUND, String.format("%S not founded", userName)));
        // post save
        postEntityRepository.save(new PostEntity());
        // return
    }
}

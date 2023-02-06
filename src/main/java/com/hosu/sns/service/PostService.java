package com.hosu.sns.service;

import com.hosu.sns.exception.SnsApplicationException;
import com.hosu.sns.model.Post;
import com.hosu.sns.model.entity.PostEntity;
import com.hosu.sns.model.entity.UserEntity;
import com.hosu.sns.repository.PostEntityRepository;
import com.hosu.sns.repository.UserEntityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.hosu.sns.exception.ErrorCode.*;

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
        PostEntity saved = postEntityRepository.save(PostEntity.of(title, body, userEntity));

        // return
    }

    @Transactional
    public Post modify(String title, String body, String userName, Integer postId) {
        // user find
        UserEntity userEntity = userEntityRepository.findByUserName(userName)
                .orElseThrow(() -> new SnsApplicationException(USER_NOT_FOUND, String.format("%S not founded", userName)));
        // post exist
        PostEntity postEntity = postEntityRepository.findById(postId)
                .orElseThrow(() -> new SnsApplicationException(POST_NOT_FOUND, String.format("%s not founded", postId)));
        // post permission
        if (postEntity.getUser() != userEntity) {
            throw new SnsApplicationException(INVALID_PERMISSION, String.format("%s has no permission with %s", userName, postId));
        }

        postEntity.setTitle(title);
        postEntity.setBody(body);

        return Post.fromEntity(postEntityRepository.saveAndFlush(postEntity));
    }

    @Transactional
    public void delete(String userName, Integer postId){
        // user find
        UserEntity userEntity = userEntityRepository.findByUserName(userName)
                .orElseThrow(() -> new SnsApplicationException(USER_NOT_FOUND, String.format("%S not founded", userName)));
        // post exist
        PostEntity postEntity = postEntityRepository.findById(postId)
                .orElseThrow(() -> new SnsApplicationException(POST_NOT_FOUND, String.format("%s not founded", postId)));
        // post permission
        if (postEntity.getUser() != userEntity) {
            throw new SnsApplicationException(INVALID_PERMISSION, String.format("%s has no permission with %s", userName, postId));
        }

        postEntityRepository.delete(postEntity);
    }

    public Page<Post> list(Pageable pageable){
        return postEntityRepository.findAll(pageable).map(Post::fromEntity);
    }

    public Page<Post> my(String userName, Pageable pageable){
        UserEntity userEntity = userEntityRepository.findByUserName(userName)
                .orElseThrow(() -> new SnsApplicationException(USER_NOT_FOUND, String.format("%S not founded", userName)));

        return postEntityRepository.findAllByUser(userEntity, pageable).map(Post::fromEntity);
    }
}

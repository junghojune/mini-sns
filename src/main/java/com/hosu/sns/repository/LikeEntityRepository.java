package com.hosu.sns.repository;

import com.hosu.sns.model.entity.LikeEntity;
import com.hosu.sns.model.entity.PostEntity;
import com.hosu.sns.model.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LikeEntityRepository extends JpaRepository<LikeEntity, Integer> {

    Optional<LikeEntity> findByUserAndPost(UserEntity user, PostEntity post);

    @Query(value = "SELECT COUNT(*) FROM LikeEntity entity WHERE entity.post = :post")
    Integer countByPost(@Param("post") PostEntity post);
}

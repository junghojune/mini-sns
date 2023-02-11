package com.hosu.sns.repository;

import com.hosu.sns.model.entity.LikeEntity;
import com.hosu.sns.model.entity.PostEntity;
import com.hosu.sns.model.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface LikeEntityRepository extends JpaRepository<LikeEntity, Integer> {

    Optional<LikeEntity> findByUserAndPost(UserEntity user, PostEntity post);

//    @Query(value = "SELECT COUNT(*) FROM LikeEntity entity WHERE entity.post = :post")
//    Integer countByPost(@Param("post") PostEntity post);

    long countByPost(PostEntity post);

    // JPA에서 제공되는 delete 문제점은 삭제 쿼리만을 날리는 것이 아닌 데이터를 가지고 온 후 삭제를 하는 문제점이 있다
    // 그래서 직접 쿼리를 작성하여 데이터를 가져오는 작업을 없애고 바로 삭제만 해준다.
    @Transactional
    @Modifying
    @Query("UPDATE LikeEntity entity SET deleted_at = NOW() where entity.post = :post")
    void deleteByPost(@Param("post") PostEntity post);
}

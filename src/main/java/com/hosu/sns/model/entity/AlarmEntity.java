package com.hosu.sns.model.entity;

import com.hosu.sns.model.AlarmArgs;
import com.hosu.sns.model.AlarmType;
import com.vladmihalcea.hibernate.type.json.JsonBinaryType;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.Instant;

@Entity
@Table(name = "\"alarm\"", indexes = {
        @Index(name = "user_id_idx", columnList = "user_id")
})
@Getter
@Setter
@TypeDef(name = "jsonb", typeClass = JsonBinaryType.class) //josnb를 사용하기 위해 type을 정의해준다.
@SQLDelete(sql = "UPDATE \"alarm\" SET deleted_at = NOW() WHERE id=?")
@Where(clause = "deleted_at is NULL")
public class AlarmEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    // 받는 사람의 정보
    @ManyToOne(fetch = FetchType.LAZY)
    //fecth default = EAGER -> 사용하지 않아도 쿼리를 날려 불러오는 형식
    // LAZY -> user가 필요한 순간 쿼리를 날려 가져오는 형식 하지만 N+1문제를 근본적으로 해결하는건 아
    // N+1를 해결하는 방법은 실제 쿼리를 작성하여 날리면 된다.
    @JoinColumn(name = "user_id")
    private UserEntity user;

    @Enumerated(EnumType.STRING) private AlarmType alarmType;

    // postgresql에서는 json을 받는 컬럼을 설정할수있다 -> 데이터를 변경하는데 용이하고 변경이 자주 일어나는 컬럼을 좀 더  편리하게 사용할 수있다.
    // josn, josnb가 있느데 jsonb는 압축을해서 저장을 하고 index를 걸수 있다.

    @Type(type = "jsonb") @Column(columnDefinition = "json") private AlarmArgs args;
    @Column(name = "registered_at") private Timestamp registeredAt;

    @Column(name = "updated_at") private Timestamp updatedAt;

    @Column(name = "deleted_at") private Timestamp deletedAt;

    @PrePersist
    void registeredAt() {
        this.registeredAt = Timestamp.from(Instant.now());
    }

    @PreUpdate
    void updatedAt() {
        this.updatedAt = Timestamp.from(Instant.now());
    }

    public static AlarmEntity of(UserEntity userEntity, AlarmType alarmType, AlarmArgs args) {
        AlarmEntity entity = new AlarmEntity();
        entity.setUser(userEntity);
        entity.setAlarmType(alarmType);
        entity.setArgs(args);

        return entity;
    }
}

package com.hosu.sns.model;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * 알람에 관한 많은 데이터들이 있는데 이를 모두 컬럼화 시킨다면 null이 들어간 컬럼이 있을경우 데이터를 낭비하고
 * 좋지 않다. 그래서 args를 성정하여 좀 더 유연하게 데이터를 처리할 수 있다.
 */
@Data
@AllArgsConstructor
public class AlarmArgs {

    // 알람을 발생시킨 유저아이디
    private Integer fromUserId;
    // 알람을 받는 유저아이디
    private Integer targetId;
}

package com.hosu.sns.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum AlarmType {
    NEW_COMMENT_ON_POST("New comment!"),
    NEW_LIKE_ON_POST("New like!"),
    ;

    private final String alarmText;
}


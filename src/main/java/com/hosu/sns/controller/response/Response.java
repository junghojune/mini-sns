package com.hosu.sns.controller.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 프론트에 내려줄때 획일화된 정보를 내려준다.
 * 결과 code와 결과값을 가지고 있는 result를 보내줌 *
 * @param <T>
 */
@Getter
@AllArgsConstructor
public class Response<T> {

    private String resultCode;
    private T result;

    public static Response<Void> error(String errorCode){
        return new Response<>(errorCode, null);
    }

    public static Response<Void> success(){
        return new Response<>("SUCCESS", null);
    }

    public static <T> Response<T> success(T result){
        return new Response<>("SUCCESS", result);
    }

    public String toStream(){
        if(result == null){
            return "{" +
                    "\"resultCode\":" + "\"" + resultCode + "\"," +
                    "\"result\":" + null + "}";
        }
        return  "{" +
                "\"resultCode\":" + "\"" + resultCode + "\"," +
                "\"result\":" + "\"" + result + "\"" + "}";
    }
}

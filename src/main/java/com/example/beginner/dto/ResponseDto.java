package com.example.beginner.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ResponseDto<T> {
  private boolean success;
  private T data;
  private Error error;

  public static <T> ResponseDto<T> success(T data) {
    return new ResponseDto<>(true, data, null);
  }

  //static으로 선언할 경우 제네릭은<T> 로지정 ResponseDto도 <T>로 데이터타입을 지정
 // success란 클래스를 만들고 타입을 ResponseDto<T>로 정의
  // 기본적으로 T data를 받으면 true, data, null을 반환해 준다.

  public static <T> ResponseDto<T> fail(String code, String message) {
    return new ResponseDto<>(false, null, new Error(code, message));
  }
 // fail란 클래스를 만들고 타입을 ResponseDto<T>로 정의
  // String code, String message을 받고 false, null, new Error(code, message)를 반환해 준다.


  @Getter
  @AllArgsConstructor
  static class Error {
    private String code;
    private String message;
  }

}

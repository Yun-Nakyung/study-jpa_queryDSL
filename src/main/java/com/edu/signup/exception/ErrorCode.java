package com.edu.signup.exception;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.CONFLICT;
import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.HttpStatus.NO_CONTENT;
import static org.springframework.http.HttpStatus.UNAUTHORIZED;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Getter
public enum ErrorCode {

    /* 400 BAD_REQUEST : 잘못된 요청 */
    INVALID_REFRESH_TOKEN(BAD_REQUEST, "001", "토큰이 유효하지 않습니다"),
    MISMATCH_REFRESH_TOKEN(BAD_REQUEST, "002", "리프레시 토큰의 유저 정보가 일치하지 않습니다"),
    CANNOT_FOLLOW_MYSELF(BAD_REQUEST, "003", "자기 자신은 팔로우 할 수 없습니다"),
    INCONSISTENCY_PASSWORD(BAD_REQUEST, "004","비밀번호가 일치하지 않습니다."),
    INCONSISTENCY_UNLOCK_ACCOUNT(BAD_REQUEST, "005","인증 문자가 일치하지 않습니다."),
    LOCK_ACCOUNT(BAD_REQUEST, "006","로그인 시도 횟수 초과입니다. 계정 잠금을 해제하세요."),

    /* 401 UNAUTHORIZED : 인증되지 않은 사용자 */
    INVALID_AUTH_TOKEN(UNAUTHORIZED, "007","권한 정보가 없는 토큰입니다."),
    UNAUTHORIZED_MEMBER(UNAUTHORIZED, "008","현재 내 계정 정보가 존재하지 않습니다"),

    /* 403 */
    INVALID_ACCOUNT(FORBIDDEN, "009","로그인되지 않은 계정입니다."),
    INVALID_TOKEN(FORBIDDEN, "010", "토큰 인가 실패"),

    /* 404 NOT_FOUND : Resource 를 찾을 수 없음 */
    MEMBER_NOT_FOUND(NOT_FOUND, "011","해당 유저 정보를 찾을 수 없습니다"),
    REFRESH_TOKEN_NOT_FOUND(NOT_FOUND, "012","로그아웃 된 사용자입니다"),
    NOT_FOLLOW(NOT_FOUND, "013","팔로우 중이지 않습니다"),
    NOT_FOUND_LOGIN_HISTORY(NOT_FOUND, "014","로그인 이력이 없습니다."),

    /* 409 CONFLICT : Resource 의 현재 상태와 충돌. 보통 중복된 데이터 존재 */
    DUPLICATE_RESOURCE(CONFLICT, "015","데이터가 이미 존재합니다"),


    /* 204 NO CONTENT : */
    DUPLICATE_EMAIL(BAD_REQUEST, "101","이미 존재하는 이메일 입니다."),
    NOT_FOUND_EMAIL(NOT_FOUND, "102","존재하지 않는 사용자입니다."),

    INTERNAL(INTERNAL_SERVER_ERROR, "500","관리자에게 문의하세요."),

    /* 토큰 관련 */

    ;

    private final HttpStatus httpStatus;
    private String code;
    private final String detail;
}

package com.boot.api.globals.common.enums;

import lombok.Getter;

import java.util.Collections;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Getter
public enum ErrorCode {
    INVALID_INPUT_VALUE(400, "C001", "유효하지 않은 파라미터"),
    INVALID_TYPE_VALUE(400, "C002", "유효하지 않은 파라미터 타입"),
    HANDLE_ACCESS_DENIED(403, "C003", "API 인증 실패"),
    METHOD_NOT_ALLOWED(405, "C005", "허용되지 않은 메소드 입니다."),
    ENTITY_NOT_FOUND(400, "C007", "데이터 조회 실패"),
    WRONG_APPROACH(401, "C008", "잘못된 접근"),
    LOGGING_FAIL(400, "C009", "로그 기록 실패"),
    INVALID_TOKEN(403, "C011", "유효하지 않은 토큰입니다."),
    EXPIRED_TOKEN(403, "C012", "만료된 토큰 입니다."),
    NO_REFRESH_TOKEN(403, "C013", "토큰이 존재하지않습니다"),
    PERMISSION_DENIED(403, "C014", "접근 권한이 없습니다."),
    INTERNAL_SERVER_ERROR(500, "C100", "Server Error"),
    ADMIN_USER_ALREADY_EXISTS(409, "AU01", "존재하는 관리자입니다."),
    ADMIN_USER_NOT_FOUND(404, "AU02", "존재하지 않는 관리자입니다."),
    ADMIN_USER_INCORRECT_PW(409, "AU03", "비밀번호가 일치하지 않습니다."),
    ADMIN_USER_FORBIDDEN(403, "AU04", "Expired access token. Please login again."),
    ADMIN_USER_UNAUTHORIZED(401, "AU05", "Unauthorized."),
    ADMIN_USER_FORBIDDEN_JWT_EXP(403, "AU06", "JWT verify error - or expired."),
    ADMIN_USER_FORBIDDEN_NO_AT(403, "AU07", "No access token. Please login again."),
    ADMIN_USER_FORBIDDEN_NO_RT(403, "AU08", "No refresh token. Please login again."),
    ADMIN_USER_PW_NOT_SWITCHED(401, "AU09", "비밀번호를 변경하셔야합니다."),
    ADMIN_USER_DELETED(403, "AU10", "삭제된 관리자입니다."),
    ADMIN_USER_BLOCKED(403, "AU11", "차단된 관리자입니다."),
    ADMIN_USER_PW_UNACCEPTABLE(409, "AU12", "기존에 등록한 비밀번호와 일치합니다. 다른 비밀번호로 변경해주세요."),
    ADMIN_USER_CANNOT_DELETE_OWN(409, "AU13", "본인 계정은 삭제할 수 없습니다."),
    AGENCY_KEY_TWO_LIMIT(409, "A021", "대행사 키 2개 이상 등록 못합니다. 하나 삭제하고 등록해주세요."),
    AGENCY_NOT_ACTIVE(409, "A022", "대행사 비활성화/삭제 상태입니다."),
    AGENCY_KEY_NOT_FOUND(404, "A023", "존재하지 않는 대행사 키입니다."),
    ADMIN_CM_ALREADY_EXISTS(409, "ACM01", "에이전시 - 골프장 이미 매핑되어 있습니다."),
    ADMIN_CM_NOT_FOUND(404, "ACM02", "No code mapper detected.");

    private int status;
    private String code;
    private String message;
    private static final Map<String, String> codeMap = Collections.unmodifiableMap((Map) Stream.of(values()).collect(Collectors.toMap(ErrorCode::getCode, Enum::name)));

    private ErrorCode(int status, String code, String message) {
        this.status = status;
        this.code = code;
        this.message = message;
    }

    public int getStatus() {
        return this.status;
    }

    public String getCode() {
        return this.code;
    }

    public String getMessage() {
        return this.message;
    }

    public static ErrorCode of(final String code) {
        return valueOf((String)codeMap.get(code));
    }
}

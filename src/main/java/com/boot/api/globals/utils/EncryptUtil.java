package com.boot.api.globals.utils;

import org.springframework.security.crypto.bcrypt.BCrypt;

public class EncryptUtil {
    /**
     * 문자열 암호화
     * @param originString
     * @return String
     */
    public static String hashString(String originString) {
        //10 라운드의 salt 생성 및 해성
        return BCrypt.hashpw(originString, BCrypt.gensalt(10));
    }

    /**
     * 문자열 검증
     * @param originString
     * @param encryptedString
     * @return Boolean
     */
    public static boolean validateString(String originString, String encryptedString) {
        //평문 문자열과 해싱된 문자열 비교
        return BCrypt.checkpw(originString, encryptedString);
    }
}

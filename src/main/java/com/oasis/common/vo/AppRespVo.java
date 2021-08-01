package com.oasis.common.vo;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@Builder
public class AppRespVo<T> {
    // 정상
    public static String STATUS_SUCCESS = "000";
    // 실패
    public static String STATUS_FAIL = "001";
    
    /**
     * 결과 코드, 정상, 에러
     */
    private String status = "000";

    /**
     * 에러코드 LIT001
     */
    private String error_code = "LIT000";

    private String message;

    private T data;    
}
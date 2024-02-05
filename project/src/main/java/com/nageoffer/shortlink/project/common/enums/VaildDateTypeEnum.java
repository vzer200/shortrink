package com.nageoffer.shortlink.project.common.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum VaildDateTypeEnum {

    /**
     * 永久有效期
     */
    PERMANENT(0),

    /**
     *
     * 自定义有效期
     */
    CUSTOM(1);

    @Getter
    private final int type;

}

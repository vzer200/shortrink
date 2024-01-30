package com.nageoffer.shortlink.admin.remote.dto.resp;

import lombok.Data;

/**
 * 短链接分页返回参数
 */
@Data
public class ShortLinkPageRespDTO {

    /**
     * 历史PV
     */
    private Integer totalPv;

    /**
     * 今日PV
     */
    private Integer todayPv;

    /**
     * 历史UV
     */
    private Integer totalUv;

    /**
     * 今日UV
     */
    private Integer todayUv;

    /**
     * 历史UIP
     */
    private Integer totalUip;

    /**
     * 今日UIP
     */
    private Integer todayUip;


}

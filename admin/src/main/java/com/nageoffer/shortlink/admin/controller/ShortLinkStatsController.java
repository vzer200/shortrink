package com.nageoffer.shortlink.admin.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.nageoffer.shortlink.admin.common.convention.result.Result;
import com.nageoffer.shortlink.admin.remote.ShortLinkRemoteService;
import com.nageoffer.shortlink.admin.remote.dto.req.ShortLinkStatsAccessRecordReqDTO;
import com.nageoffer.shortlink.admin.remote.dto.req.ShortLinkStatsReqDTO;
import com.nageoffer.shortlink.admin.remote.dto.resp.ShortLinkStatsAccessRecordRespDTO;
import com.nageoffer.shortlink.admin.remote.dto.resp.ShortLinkStatsRespDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ShortLinkStatsController {

    ShortLinkRemoteService shortLinkRemoteService = new ShortLinkRemoteService(){

    };

    /**
     *访问单个短链接指定时间内监控数据
     */
    @GetMapping("/api/shortlink/admin/v1/stats")
    public Result<ShortLinkStatsRespDTO> shortLinkStats(ShortLinkStatsReqDTO requestParam){
        return shortLinkRemoteService.oneShortLinkStats(requestParam);
    }

    /**
     *访问单个短链接指定时间内访问记录监控数据
     */
    @GetMapping("/api/shortlink/admin/v1/stats/access-record")
    public Result<IPage<ShortLinkStatsAccessRecordRespDTO>> shortLinkStatsAccessRecord(ShortLinkStatsAccessRecordReqDTO requestParam) {
        return shortLinkRemoteService.shortLinkStatsAccessRecord(requestParam);
    }


}

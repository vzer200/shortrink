package com.nageoffer.shortlink.project.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.nageoffer.shortlink.project.common.convention.result.Result;
import com.nageoffer.shortlink.project.common.convention.result.Results;
import com.nageoffer.shortlink.project.dto.req.ShortLinkCreatReqDTO;
import com.nageoffer.shortlink.project.dto.req.ShortLinkPageReqDTO;
import com.nageoffer.shortlink.project.dto.resp.ShortLinkCreatRespDTO;
import com.nageoffer.shortlink.project.dto.resp.ShortLinkGroupCountQueryRespDTO;
import com.nageoffer.shortlink.project.dto.resp.ShortLinkPageRespDTO;
import com.nageoffer.shortlink.project.service.ShortLinkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 短链接控制层
 */
@RestController
public class ShortLinkController {

    @Autowired
    private ShortLinkService shortLinkService;


    /**
     * 创建短链接
     * @param requestParam
     * @return
     */
    @PostMapping("/api/shortlink/v1/create")
    public Result<ShortLinkCreatRespDTO> creatShortLink(@RequestBody ShortLinkCreatReqDTO requestParam) {

        return Results.success(shortLinkService.createShortLink(requestParam));
    }

    /**
     * 分页查询短链接
     * @param requestParam
     * @return
     */
    @GetMapping("/api/shortlink/v1/page")
    public Result<IPage<ShortLinkPageRespDTO>> pageShortLink(ShortLinkPageReqDTO requestParam){
        return Results.success(shortLinkService.pageShortLink(requestParam));
    }


    /**
     * 统计短链接数量
     * @param requestParam
     * @return
     */
    @GetMapping("/api/shortlink/v1/count")
    public Result<List<ShortLinkGroupCountQueryRespDTO>> listGroupShortLinkCount(@RequestParam("requestParam")  List<String> requestParam){
        return Results.success(shortLinkService.listGroupShortLinkCount(requestParam));
    }

}
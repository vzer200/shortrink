package com.nageoffer.shortlink.project.handler;

import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.nageoffer.shortlink.project.common.convention.result.Result;
import com.nageoffer.shortlink.project.dto.req.ShortLinkCreatReqDTO;
import com.nageoffer.shortlink.project.dto.resp.ShortLinkCreatRespDTO;

/**
 * 自定义流控策略
 */
public class CustomBlockHandler {

    public static Result<ShortLinkCreatRespDTO> createShortLinkBlockHandlerMethod(ShortLinkCreatReqDTO requestParam, BlockException exception) {
        return new Result<ShortLinkCreatRespDTO>().setCode("B100000").setMessage("当前访问网站人数过多，请稍后再试...");
    }
}

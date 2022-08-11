/*
 *  Copyright 2008-2022 Shopfly.cloud Group.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package cloud.shopfly.b2c.api.buyer.promotion;

import cloud.shopfly.b2c.core.promotion.PromotionErrorCode;
import cloud.shopfly.b2c.core.promotion.seckill.model.dto.SeckillQueryParam;
import cloud.shopfly.b2c.core.promotion.seckill.model.vo.TimeLineVO;
import cloud.shopfly.b2c.core.promotion.seckill.service.SeckillGoodsManager;
import cloud.shopfly.b2c.core.promotion.seckill.service.SeckillRangeManager;
import cloud.shopfly.b2c.framework.database.Page;
import cloud.shopfly.b2c.framework.exception.ServiceException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import java.util.List;

/**
 * 限时抢购相关API
 *
 * @author Snow create in 2018/7/23
 * @version v2.0
 * @since v7.0.0
 */
@RestController
@RequestMapping("/promotions/seckill")
@Api(tags = "限时抢购相关API")
@Validated
public class SeckillBuyerController {

    @Autowired
    private SeckillGoodsManager seckillApplyManager;

    @Autowired
    private SeckillRangeManager seckillRangeManager;

    @ApiOperation(value = "读取秒杀时刻")
    @ResponseBody
    @GetMapping(value = "/time-line")
    public List<TimeLineVO> readTimeLine() {
        List<TimeLineVO> timeLineVOList = this.seckillRangeManager.readTimeList();
        return timeLineVOList;
    }


    @ApiOperation(value = "根据参数读取限时抢购的商品列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "range_time", value = "时刻", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "page_no", value = "页码", dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "page_size", value = "条数", dataType = "int", paramType = "query")
    })
    @GetMapping("/goods-list")
    public Page goodsList(@ApiIgnore Integer rangeTime, @ApiIgnore Integer pageSize, @ApiIgnore Integer pageNo) {

        if (rangeTime == null) {
            throw new ServiceException(PromotionErrorCode.E400.code(), "Time cannot be empty");
        }

        if (rangeTime > 24) {
            throw new ServiceException(PromotionErrorCode.E400.code(), "The moment must be0~24The integer");
        }

        SeckillQueryParam param = new SeckillQueryParam();
        param.setPageNo(pageNo);
        param.setPageSize(pageSize);

        List list = this.seckillApplyManager.getSeckillGoodsList(rangeTime, pageNo, pageSize);
        long dataTotal = 0;
        if (list != null && !list.isEmpty()) {
            dataTotal = list.size();
        }

        Page page = new Page();
        page.setData(list);
        page.setPageNo(pageNo);
        page.setPageSize(pageSize);
        page.setDataTotal(dataTotal);
        return page;

    }


}

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
package cloud.shopfly.b2c.api.seller.distribution;

import cloud.shopfly.b2c.core.distribution.exception.DistributionErrorCode;
import cloud.shopfly.b2c.core.distribution.exception.DistributionException;
import cloud.shopfly.b2c.core.distribution.model.vo.DistributionOrderVO;
import cloud.shopfly.b2c.core.distribution.model.vo.DistributionSellbackOrderVO;
import cloud.shopfly.b2c.core.distribution.service.DistributionOrderManager;
import cloud.shopfly.b2c.framework.database.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

/**
 * 分销订单订单
 *
 * @author Chopper
 * @version v1.0
 * @Description:
 * @since v7.0
 * 2018/5/28 In the morning11:12
 */
@Api(tags = "分销订单")
@RestController
@RequestMapping("/seller/distribution/order")
public class DistributionOrderSellerController {
    @Autowired
    private DistributionOrderManager distributionOrderManager;

    protected final Log logger = LogFactory.getLog(this.getClass());

    @ApiOperation("结算单 分销订单查询")
    @GetMapping()
    @ApiImplicitParams({
            @ApiImplicitParam(name = "bill_id", value = "会员结算单id", required = false, paramType = "query", dataType = "int", allowMultiple = false),
            @ApiImplicitParam(name = "member_id", value = "会员id", required = false, paramType = "query", dataType = "int", allowMultiple = false),
            @ApiImplicitParam(name = "page_size", value = "页码大小", required = false, paramType = "query", dataType = "int", allowMultiple = false),
            @ApiImplicitParam(name = "page_no", value = "页码", required = false, paramType = "query", dataType = "int", allowMultiple = false),
    })
    public Page<DistributionOrderVO> billOrder(@ApiIgnore Integer pageNo, @ApiIgnore Integer pageSize, @ApiIgnore Integer billId, @ApiIgnore Integer memberId) throws Exception {
        try {
            return distributionOrderManager.pageDistributionOrder(pageSize, pageNo, memberId, billId);
        } catch (DistributionException e) {
            throw e;
        } catch (Exception e) {
            this.logger.error("查询结算单-》订单异常 ", e);
            throw new DistributionException(DistributionErrorCode.E1000.code(), DistributionErrorCode.E1000.des());
        }
    }


    @ApiOperation("结算单 分销退款订单查询")
    @GetMapping("/sellback")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "bill_id", value = "结算单id", required = false, paramType = "query", dataType = "int", allowMultiple = false),
            @ApiImplicitParam(name = "member_id", value = "会员id", required = false, paramType = "query", dataType = "int", allowMultiple = false),
            @ApiImplicitParam(name = "page_size", value = "页码大小", required = false, paramType = "query", dataType = "int", allowMultiple = false),
            @ApiImplicitParam(name = "page_no", value = "页码", required = false, paramType = "query", dataType = "int", allowMultiple = false),
    })
    public Page<DistributionSellbackOrderVO> billSellbackOrder(@ApiIgnore Integer pageNo, @ApiIgnore Integer pageSize, @ApiIgnore Integer billId, @ApiIgnore Integer memberId) {
        try {
            return distributionOrderManager.pageSellBackOrder(pageSize, pageNo, memberId, billId);
        } catch (DistributionException e) {
            throw e;
        } catch (Exception e) {
            this.logger.error("查询结算单-》退款单异常 ", e);
            throw new DistributionException(DistributionErrorCode.E1000.code(), DistributionErrorCode.E1000.des());
        }
    }


}

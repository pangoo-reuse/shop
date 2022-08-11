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
package cloud.shopfly.b2c.api.buyer.aftersale;

import cloud.shopfly.b2c.core.aftersale.AftersaleErrorCode;
import cloud.shopfly.b2c.core.aftersale.model.dto.RefundDTO;
import cloud.shopfly.b2c.core.aftersale.model.dto.RefundDetailDTO;
import cloud.shopfly.b2c.core.aftersale.model.vo.BuyerCancelOrderVO;
import cloud.shopfly.b2c.core.aftersale.model.vo.BuyerRefundApplyVO;
import cloud.shopfly.b2c.core.aftersale.model.vo.RefundApplyVO;
import cloud.shopfly.b2c.core.aftersale.model.vo.RefundQueryParamVO;
import cloud.shopfly.b2c.core.aftersale.service.AfterSaleManager;
import cloud.shopfly.b2c.framework.context.UserContext;
import cloud.shopfly.b2c.framework.database.Page;
import cloud.shopfly.b2c.framework.exception.ServiceException;
import cloud.shopfly.b2c.framework.security.model.Buyer;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

/**
 * @author zjp
 * @version v7.0
 * @Description 售后相关API
 * @ClassName AfterSaleController
 * @since v7.0 In the afternoon8:10 2018/5/9
 */
@Api(tags = "售后相关API")
@RestController
@RequestMapping("/after-sales")
@Validated
public class AfterSaleBuyerController {

    @Autowired
    private AfterSaleManager afterSaleManager;

    @ApiOperation(value = "退款申请数据获取", response = RefundApplyVO.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "order_sn", value = "订单号", dataType = "String", paramType = "path"),
            @ApiImplicitParam(name = "sku_id", value = "货品id", required = false, dataType = "int", paramType = "query")
    })
    @GetMapping(value = "/refunds/apply/{order_sn}")
    public RefundApplyVO refundApply(@PathVariable("order_sn") String orderSn, @ApiIgnore Integer skuId) {
        return afterSaleManager.refundApply(orderSn, skuId);
    }

    @ApiOperation(value = "买家申请退款", response = BuyerRefundApplyVO.class)
    @PostMapping(value = "/refunds/apply")
    public BuyerRefundApplyVO refund(@Valid BuyerRefundApplyVO refundApply) {
        afterSaleManager.applyRefund(refundApply);
        return refundApply;
    }

    @ApiOperation(value = "买家申请退货", response = BuyerRefundApplyVO.class)
    @PostMapping(value = "/return-goods/apply")
    public BuyerRefundApplyVO returnGoods(@Valid BuyerRefundApplyVO refundApply) {
        afterSaleManager.applyGoodsReturn(refundApply);
        return refundApply;
    }


    @ApiOperation(value = "买家对已付款的订单取消操作")
    @PostMapping(value = "/refunds/cancel-order")
    public String cancelOrder(@Valid BuyerCancelOrderVO buyerCancelOrderVO) {
        afterSaleManager.cancelOrder(buyerCancelOrderVO);
        return "";
    }

    @ApiOperation(value = "买家查看退款(货)列表", response = RefundDTO.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page_no", value = "页码", required = true, dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "page_size", value = "分页数", required = true, dataType = "int", paramType = "query")
    })
    @GetMapping(value = "/refunds")
    public Page refundDetail(@ApiIgnore @NotNull(message = "The page number cannot be blank") Integer pageNo, @ApiIgnore @NotNull(message = "The number of pages cannot be empty") Integer pageSize) {

        Buyer buyer = UserContext.getBuyer();
        RefundQueryParamVO queryParam = new RefundQueryParamVO();
        queryParam.setPageNo(pageNo);
        queryParam.setPageSize(pageSize);
        queryParam.setMemberId(buyer.getUid());
        return this.afterSaleManager.query(queryParam);
    }

    @ApiOperation(value = "买家查看退款(货)详细", response = RefundDetailDTO.class)
    @ApiImplicitParam(name = "sn", value = "退款(货)编号", required = true, dataType = "String", paramType = "path")
    @GetMapping(value = "/refund/{sn}")
    public RefundDetailDTO sellerDetail(@PathVariable("sn") String sn) {
        Buyer buyer = UserContext.getBuyer();
        RefundDetailDTO detail = this.afterSaleManager.getDetail(sn);
        if (!detail.getRefund().getMemberId().equals(buyer.getUid())) {
            throw new ServiceException(AftersaleErrorCode.E603.name(), "Refund slip does not exist");
        }
        return detail;
    }
}

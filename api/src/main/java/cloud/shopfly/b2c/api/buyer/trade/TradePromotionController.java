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
package cloud.shopfly.b2c.api.buyer.trade;

import cloud.shopfly.b2c.core.promotion.tool.model.enums.PromotionTypeEnum;
import cloud.shopfly.b2c.core.trade.cart.model.enums.CartType;
import cloud.shopfly.b2c.core.trade.cart.model.vo.CartVO;
import cloud.shopfly.b2c.core.trade.cart.model.vo.CartView;
import cloud.shopfly.b2c.core.trade.cart.service.CartPromotionManager;
import cloud.shopfly.b2c.core.trade.cart.service.cartbuilder.CartBuilder;
import cloud.shopfly.b2c.core.trade.cart.service.cartbuilder.CartPriceCalculator;
import cloud.shopfly.b2c.core.trade.cart.service.cartbuilder.CartSkuRenderer;
import cloud.shopfly.b2c.core.trade.cart.service.cartbuilder.CheckDataRebderer;
import cloud.shopfly.b2c.core.trade.cart.service.cartbuilder.impl.DefaultCartBuilder;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.constraints.NotNull;

/**
 * 购物车价格计算接口
 *
 * @author Chopper
 * @version v1.0
 * @since v7.0
 * 2018-12-01 In the afternoon8:26
 */
@Api(tags = "购物车价格计算API")
@RestController
@RequestMapping("/trade/promotion")
@Validated
public class TradePromotionController {


    @Autowired
    private CartPromotionManager promotionManager;


    /**
     * 购物车价格计算器
     */
    @Autowired
    private CartPriceCalculator cartPriceCalculator;
    /**
     * 数据校验
     */
    @Autowired
    private CheckDataRebderer checkDataRebderer;

    /**
     * 购物车sku数据渲染器
     */
    @Autowired
    private CartSkuRenderer cartSkuRenderer;

    @ApiOperation(value = "选择要参与的促销活动")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "sku_id", value = "产品id", required = true, dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "activity_id", value = "产品id", required = true, dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "promotion_type", value = "活动类型", required = true, dataType = "String", paramType = "query"),})
    @RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.POST)
    public void setPromotion(@ApiIgnore Integer skuId, @ApiIgnore Integer activityId, @ApiIgnore String promotionType) {
        promotionManager.usePromotion(skuId, activityId, PromotionTypeEnum.valueOf(promotionType));
    }


    @ApiOperation(value = "取消参与促销")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "sku_id", value = "产品id", required = true, dataType = "int", paramType = "query")
    })
    @RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.DELETE)
    public void promotionCancel(Integer skuId) {
        promotionManager.delete(new Integer[]{skuId});
    }


    @ApiOperation(value = "设置优惠券", notes = "使用优惠券的时候分为三种情况：前2种情况couponId 不为0,不为空。第3种情况couponId为0," +
            "1、使用优惠券:在刚进入订单结算页，为使用任何优惠券之前。" +
            "2、切换优惠券:在1、情况之后，当用户切换优惠券的时候。" +
            "3、取消已使用的优惠券:用户不想使用优惠券的时候。")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "mc_id", value = "优惠券ID", required = true, dataType = "int", paramType = "path")
    })
    @PostMapping(value = "/{mc_id}/coupon")
    public void setCoupon(@NotNull(message = "coupons id cant be empty") @PathVariable("mc_id") Integer mcId) {

        CartBuilder cartBuilder = new DefaultCartBuilder(CartType.CART, cartSkuRenderer, null, cartPriceCalculator, checkDataRebderer);

        CartView cartView = cartBuilder.renderSku().countPrice().build();


        CartVO cart = cartView.getCartList().get(0);
        double goodsPrice = cart.getPrice().getGoodsPrice();
        promotionManager.useCoupon(mcId, goodsPrice);
    }


}

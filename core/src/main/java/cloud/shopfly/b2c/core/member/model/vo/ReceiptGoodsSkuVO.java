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
package cloud.shopfly.b2c.core.member.model.vo;

import cloud.shopfly.b2c.core.trade.sdk.model.OrderSkuDTO;
import io.swagger.annotations.ApiModelProperty;

/**
 * productskuVo Each newskuHow much is the discount
 *
 * @author zh
 * @version v7.0
 * @date 18/7/24 In the afternoon4:57
 * @since v7.0
 */
public class ReceiptGoodsSkuVO  extends OrderSkuDTO {
    /**
     * eachskuDiscount amount
     */
    @ApiModelProperty(value = "Discount amount")
    private Double discount;


    public Double getDiscount() {
        return discount;
    }

    public void setDiscount(Double discount) {
        this.discount = discount;
    }

    @Override
    public String toString() {
        return "ReceiptGoodsSkuVO{" +
                "discount=" + discount +
                '}';
    }
}

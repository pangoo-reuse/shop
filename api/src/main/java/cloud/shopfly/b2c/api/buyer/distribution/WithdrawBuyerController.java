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
package cloud.shopfly.b2c.api.buyer.distribution;

import cloud.shopfly.b2c.core.base.model.vo.SuccessMessage;
import cloud.shopfly.b2c.core.distribution.exception.DistributionErrorCode;
import cloud.shopfly.b2c.core.distribution.exception.DistributionException;
import cloud.shopfly.b2c.core.distribution.model.vo.BankParamsVO;
import cloud.shopfly.b2c.core.distribution.model.vo.WithdrawApplyVO;
import cloud.shopfly.b2c.core.distribution.service.DistributionManager;
import cloud.shopfly.b2c.core.distribution.service.WithdrawManager;
import cloud.shopfly.b2c.framework.context.UserContext;
import cloud.shopfly.b2c.framework.database.Page;
import cloud.shopfly.b2c.framework.security.model.Buyer;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

/**
 * 提现api
 *
 * @author Chopper
 * @version v1.0
 * @Description:
 * @since v7.0
 * 2018/5/24 In the morning7:09
 */

@RestController
@RequestMapping("/distribution/withdraw")
@Api(tags = "提现api")
public class WithdrawBuyerController {

    protected final Log logger = LogFactory.getLog(this.getClass());
    @Autowired
    private WithdrawManager withdrawManager;
    @Autowired
    private DistributionManager distributionManager;

    @ApiOperation("保存提现参数")
    @PutMapping(value = "/params")
    public BankParamsVO saveWithdrawWay(BankParamsVO bankParamsVO) {

        Buyer buyer = UserContext.getBuyer();
        if (buyer == null) {
            throw new DistributionException(DistributionErrorCode.E1001.code(), DistributionErrorCode.E1001.des());
        }
        try {
            withdrawManager.saveWithdrawWay(bankParamsVO);
            return bankParamsVO;
        } catch (Exception e) {
            logger.error("保存失败", e);
            throw new DistributionException(DistributionErrorCode.E1000.code(), DistributionErrorCode.E1000.des());
        }
    }


    @ApiOperation("获取提现参数")
    @GetMapping(value = "/params")
    public BankParamsVO getWithdrawWay() {
        Buyer buyer = UserContext.getBuyer();
        if (buyer == null) {
            throw new DistributionException(DistributionErrorCode.E1001.code(), DistributionErrorCode.E1001.des());
        }
        try {
            return withdrawManager.getWithdrawSetting(buyer.getUid());
        } catch (Exception e) {
            logger.error("获取失败", e);
            throw new DistributionException(DistributionErrorCode.E1000.code(), DistributionErrorCode.E1000.des());
        }
    }

    @ApiOperation("提现申请")
    @PostMapping(value = "/apply-withdraw")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "apply_money", value = "申请金额", required = true, paramType = "query", dataType = "double"),
            @ApiImplicitParam(name = "remark", value = "备注", required = false, paramType = "query", dataType = "String"),

    })
    public SuccessMessage applyWithdraw(@ApiIgnore Double applyMoney, @ApiIgnore String remark) {
        Buyer buyer = UserContext.getBuyer();
        if (buyer == null) {
            throw new DistributionException(DistributionErrorCode.E1001.code(), DistributionErrorCode.E1001.des());
        }
        if (applyMoney == null) {
            throw new DistributionException(DistributionErrorCode.E1011.code(), DistributionErrorCode.E1011.des());
        }
        try {
            double rebate = distributionManager.getCanRebate(buyer.getUid());

            // 如果提现申请
            if (applyMoney <= 0) {
                throw new DistributionException(DistributionErrorCode.E1006.code(), DistributionErrorCode.E1006.des());
            }
            /**
             * 如果申请金额小于当前可提现金额
             */
            if (applyMoney <= rebate) {
                this.withdrawManager.applyWithdraw(buyer.getUid(), applyMoney, remark);
                return new SuccessMessage("Apply submitted");

            } else {
                throw new DistributionException(DistributionErrorCode.E1003.code(), DistributionErrorCode.E1003.des());
            }
        } catch (DistributionException e) {
            throw e;
        } catch (Exception e) {
            logger.error("提现申请错误：", e);
            throw new DistributionException(DistributionErrorCode.E1000.code(), DistributionErrorCode.E1000.des());
        }

    }

    @ApiOperation("Withdrawal record")
    @GetMapping(value = "/apply-history")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page_no", value = "The page number", required = true, paramType = "query", dataType = "int"),
            @ApiImplicitParam(name = "page_size", value = "Page size", required = true, paramType = "query", dataType = "int"),
    })
    public Page<WithdrawApplyVO> applyWithdraw(@ApiIgnore Integer pageNo, @ApiIgnore Integer pageSize) {
        Buyer buyer = UserContext.getBuyer();
        if (buyer == null) {
            throw new DistributionException(DistributionErrorCode.E1001.code(), DistributionErrorCode.E1001.des());
        }
        return withdrawManager.pageWithdrawApply(buyer.getUid(), pageNo, pageSize);
    }


    @ApiOperation("Withdrawal amount")
    @GetMapping(value = "/can-rebate")
    public SuccessMessage canRebate() {
        Buyer buyer = UserContext.getBuyer();
        if (buyer == null) {
            throw new DistributionException(DistributionErrorCode.E1001.code(), DistributionErrorCode.E1001.des());
        }
        return new SuccessMessage(withdrawManager.getRebate(buyer.getUid()));
    }


}

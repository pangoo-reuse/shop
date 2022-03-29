/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package com.enation.app.javashop.core.aftersale.service;

import com.enation.app.javashop.core.aftersale.model.enums.RefundOperateEnum;
import com.enation.app.javashop.core.aftersale.model.enums.RefundStatusEnum;
import com.enation.app.javashop.core.aftersale.model.enums.RefuseTypeEnum;
import com.enation.app.javashop.core.trade.order.model.enums.PaymentTypeEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;


/**
 * @author zjp
 * @version v7.0
 * @Description 操作被允许的情况
 * @ClassName AfterSaleOperateAllowable
 * @since v7.0 上午11:32 2018/5/8
 */
@ApiModel(description = "操作被允许的情况")
public class AfterSaleOperateAllowable implements Serializable {

    private static final long serialVersionUID = -6083914452276811925L;

    public AfterSaleOperateAllowable() {
    }

    private RefuseTypeEnum type;
    private RefundStatusEnum status;
    private PaymentTypeEnum paymentType;

    public AfterSaleOperateAllowable(RefuseTypeEnum type, RefundStatusEnum status, PaymentTypeEnum paymentType) {
        this.type = type;
        this.status = status;
        this.paymentType = paymentType;
    }

    @ApiModelProperty(value = "是否允许被取消", name = "allow_cancel")
    private boolean allowCancel;

    @ApiModelProperty(value = "是否允许申请", name = "allow_apply")
    private boolean allowApply;

    @ApiModelProperty(value = "是否允许退货入库", name = "allow_stock_in")
    private boolean allowStockIn;

    @ApiModelProperty(value = "是否管理员退款", name = "allow_admin_refund")
    private boolean allowAdminRefund;


    public boolean getAllowCancel() {
        allowCancel = RefundOperateChecker.checkAllowable(type, paymentType, status, RefundOperateEnum.CANCEL);
        return allowCancel;
    }


    public boolean getAllowApply() {
        allowApply = RefundOperateChecker.checkAllowable(type, paymentType, status, RefundOperateEnum.APPLY);
        return allowApply;
    }


    public boolean getAllowStockIn() {
        allowStockIn = RefundOperateChecker.checkAllowable(type, paymentType, status, RefundOperateEnum.STOCK_IN);
        return allowStockIn;
    }


    public boolean getAllowAdminRefund() {
        allowAdminRefund = RefundOperateChecker.checkAllowable(type, paymentType, status, RefundOperateEnum.ADMIN_REFUND);
        return allowAdminRefund;
    }



}

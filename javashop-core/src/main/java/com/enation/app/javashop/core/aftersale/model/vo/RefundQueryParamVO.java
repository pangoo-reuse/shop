/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package com.enation.app.javashop.core.aftersale.model.vo;

import io.swagger.annotations.ApiModelProperty;

/**
 * @author zjp
 * @version v7.0
 * @since v7.0 上午11:19 2018/5/2
 */
public class RefundQueryParamVO {
    @ApiModelProperty(value = "页码", name = "page_no", hidden = true)
    private Integer pageNo;

    @ApiModelProperty(value = "分页大小", name = "page_size", hidden = true)
    private Integer pageSize;

    @ApiModelProperty(allowableValues = "APPLY,PASS,REFUSE,STOCK_IN,WAIT_FOR_MANUAL,CANCEL,REFUNDING,REFUNDFAIL,COMPLETED", value = "退货(款)单状态: APPLY/申请中,PASS/申请通过,REFUSE/审核拒绝,STOCK_IN/退货入库,WAIT_FOR_MANUAL/待人工处理,CANCEL/申请取消,REFUNDING/退款中,REFUNDFAIL/退款失败,COMPLETED/完成", name = "refund_status", required = false)
    private String refundStatus;

    @ApiModelProperty(value = "售后类型: CANCEL_ORDER 取消订单,AFTER_SALE 申请售后", name = "refund_type", allowableValues = "CANCEL_ORDER,AFTER_SALE", required = false)
    private String refundType;

    @ApiModelProperty(value = "退货(款)单编号", name = "sn", required = false)
    private String sn;

    @ApiModelProperty(hidden = true, value = "会员id")
    private Integer memberId;

    @ApiModelProperty(value = "订单编号", name = "order_sn", required = false)
    private String orderSn;

    @ApiModelProperty(name = "refuse_type", value = "类型:退款 RETURN_MONEY,退货 RETURN_GOODS", allowableValues = "RETURN_MONEY,RETURN_GOODS", required = false)
    private String refuseType;

    @ApiModelProperty(value = "起始时间", name = "start_time", required = false)
    private String startTime;

    @ApiModelProperty(value = "结束时间", name = "end_time", required = false)
    private String endTime;

    @ApiModelProperty(value = "退款方式 OFFLINE 线下支付，ORIGINAL 原路退回", name = "refund_way", allowableValues = "OFFLINE,ORIGINAL")
    private String refundWay;


    public String getRefundStatus() {
        return refundStatus;
    }

    public void setRefundStatus(String refundStatus) {
        this.refundStatus = refundStatus;
    }

    public String getSn() {
        return sn;
    }

    public void setSn(String sn) {
        this.sn = sn;
    }

    public Integer getMemberId() {
        return memberId;
    }

    public void setMemberId(Integer memberId) {
        this.memberId = memberId;
    }

    public String getOrderSn() {
        return orderSn;
    }

    public void setOrderSn(String orderSn) {
        this.orderSn = orderSn;
    }

    public String getRefuseType() {
        return refuseType;
    }

    public void setRefuseType(String refuseType) {
        this.refuseType = refuseType;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public Integer getPageNo() {
        return pageNo;
    }

    public void setPageNo(Integer pageNo) {
        this.pageNo = pageNo;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public String getRefundType() {
        return refundType;
    }

    public void setRefundType(String refundType) {
        this.refundType = refundType;
    }

    public String getRefundWay() {
        return refundWay;
    }

    public void setRefundWay(String refundWay) {
        this.refundWay = refundWay;
    }

    @Override
    public String toString() {
        return "RefundQueryParamVO{" +
                "pageNo=" + pageNo +
                ", pageSize=" + pageSize +
                ", refundStatus='" + refundStatus + '\'' +
                ", refundType='" + refundType + '\'' +
                ", sn='" + sn + '\'' +
                ", memberId=" + memberId +
                ", orderSn='" + orderSn + '\'' +
                ", refuseType='" + refuseType + '\'' +
                ", startTime='" + startTime + '\'' +
                ", endTime='" + endTime + '\'' +
                '}';
    }
}

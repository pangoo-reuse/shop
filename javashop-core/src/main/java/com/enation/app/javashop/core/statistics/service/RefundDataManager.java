/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package com.enation.app.javashop.core.statistics.service;


import com.enation.app.javashop.core.statistics.model.dto.RefundData;

/**
 * 退货收集manager
 *
 * @author chopper
 * @version v1.0
 * @Description:
 * @since v7.0
 * 2018/5/8 下午4:10
 */

public interface RefundDataManager {

    /**
     * 退款消息写入
     *
     * @param refundData
     */
    void put(RefundData refundData);

}

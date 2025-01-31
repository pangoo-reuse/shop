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
package cloud.shopfly.b2c.api.seller.aftersale;

import cloud.shopfly.b2c.core.aftersale.model.dto.RefundDTO;
import cloud.shopfly.b2c.core.aftersale.model.dto.RefundDetailDTO;
import cloud.shopfly.b2c.core.aftersale.model.vo.AdminRefundApprovalVO;
import cloud.shopfly.b2c.core.aftersale.model.vo.ExportRefundExcelVO;
import cloud.shopfly.b2c.core.aftersale.model.vo.FinanceRefundApprovalVO;
import cloud.shopfly.b2c.core.aftersale.model.vo.RefundQueryParamVO;
import cloud.shopfly.b2c.core.aftersale.service.AfterSaleManager;
import cloud.shopfly.b2c.core.goods.model.enums.Permission;
import cloud.shopfly.b2c.framework.database.Page;
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
import java.util.List;

/**
 * @author zjp
 * @version v7.0
 * @Description 售后相关API
 * @ClassName AfterSaleSellerController
 * @since v7.0 In the morning9:38 2018/5/10
 */
@Api(tags = "售后相关API")
@RestController
@RequestMapping("/seller/after-sales")
@Validated
public class AfterSaleSellerController {

    @Autowired
    private AfterSaleManager afterSaleManager;

    @ApiOperation(value = "审核退款/退货", response = AdminRefundApprovalVO.class)
    @PostMapping(value = "/audits/{sn}")
    @ApiImplicitParam(name = "sn", value = "退款单sn", required = true, dataType = "String", paramType = "path")
    public AdminRefundApprovalVO audit(@Valid AdminRefundApprovalVO refundApproval, @PathVariable("sn") String sn) {
        refundApproval.setSn(sn);
        afterSaleManager.approval(refundApproval, Permission.ADMIN);
        return refundApproval;
    }

    @ApiOperation(value = "入库操作")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "sn", value = "退款单编号", required = true, dataType = "String", paramType = "path"),
            @ApiImplicitParam(name = "remark", value = "入库备注", required = false, dataType = "String", paramType = "query")
    })
    @PostMapping(value = "/stock-ins/{sn}")
    public String stockIn(@PathVariable("sn") String sn, String remark) {
        afterSaleManager.stockIn(sn, remark);
        return "";
    }

    @ApiOperation(value = "退款", response = FinanceRefundApprovalVO.class)
    @PostMapping(value = "/refunds/{sn}")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "sn", value = "退款(货)编号", required = true, dataType = "String", paramType = "path"),
            @ApiImplicitParam(name = "remark", value = "退款备注", required = false, dataType = "String", paramType = "query")
    })
    public String sellerRefund(@Valid FinanceRefundApprovalVO refundApply, @PathVariable("sn") @ApiIgnore String sn) {
        refundApply.setSn(sn);
        this.afterSaleManager.approval(refundApply);
        return "";
    }

    @ApiOperation(value = "退款备注", response = RefundDetailDTO.class)
    @ApiImplicitParam(name = "sn", value = "退款(货)编号", required = true, dataType = "String", paramType = "path")
    @GetMapping(value = "/refunds/{sn}")
    public RefundDetailDTO sellerDetail(@PathVariable("sn") String sn) {

        return this.afterSaleManager.getDetail(sn);
    }

    @ApiOperation(value = "查看退款(货)列表", response = RefundDTO.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page_no", value = "页码", required = true, dataType = "int", paramType = "query"),
            @ApiImplicitParam(name = "page_size", value = "分页数", required = true, dataType = "int", paramType = "query")
    })
    @GetMapping(value = "/refunds")
    public Page sellerDetail(RefundQueryParamVO queryParam, @ApiIgnore @NotNull(message = "The page number cannot be blank") Integer pageNo, @ApiIgnore @NotNull(message = "The number of pages cannot be empty") Integer pageSize) {

        queryParam.setPageNo(pageNo);
        queryParam.setPageSize(pageSize);
        return this.afterSaleManager.query(queryParam);
    }

    @ApiOperation(value = "退款单导出excel",response = ExportRefundExcelVO.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "start_time" , value = "开始时间" , required = true , dataType = "long", paramType = "query"),
            @ApiImplicitParam(name = "end_time" , value = "结束时间" , required = true , dataType = "long", paramType = "query")
    })
    @GetMapping(value = "/exports/excel")
    public List<ExportRefundExcelVO> exportExcel(@ApiIgnore @NotNull(message = "The start time cannot be empty") long startTime, @ApiIgnore @NotNull(message = "The end time cannot be empty") long endTime){

        return afterSaleManager.exportExcel(startTime,endTime);
    }
}

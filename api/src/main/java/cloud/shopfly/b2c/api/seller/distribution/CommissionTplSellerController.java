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
import cloud.shopfly.b2c.core.distribution.model.dos.CommissionTpl;
import cloud.shopfly.b2c.core.distribution.service.CommissionTplManager;
import cloud.shopfly.b2c.framework.database.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.annotation.Resource;
import javax.validation.Valid;

/**
 * 模版控制器
 *
 * @author Chopper
 * @version v1.0
 * @Description:
 * @since v7.0
 * 2018/5/24 In the afternoon3:15
 */
@RestController
@RequestMapping("/seller/distribution/commission-tpl")
@Api(tags = "模版")
public class CommissionTplSellerController {
    protected final Log logger = LogFactory.getLog(this.getClass());


    @Resource
    private CommissionTplManager commissionTplManager;

    @ApiOperation("添加模版")
    @PostMapping
    public CommissionTpl save(@Valid CommissionTpl commissionTpl) {
        try {
            return this.commissionTplManager.add(commissionTpl);
        } catch (DistributionException e) {
            throw e;
        } catch (Exception e) {
            logger.error("添加模版异常：", e);
            throw new DistributionException(DistributionErrorCode.E1000.code(), DistributionErrorCode.E1000.des());
        }
    }

    @ApiOperation("修改模版")
    @PutMapping(value = "/{tplId}")
    @ApiImplicitParam(name = "tplId", value = "模版id", required = false, paramType = "path", dataType = "int", allowMultiple = false)
    public CommissionTpl saveEdit(@PathVariable @ApiIgnore Integer tplId, @Valid CommissionTpl commissionTpl) {

        //如果要将默认模版中默认修改为不默认则提示
        if (commissionTpl.getIsDefault() == 0) {
            CommissionTpl com = this.commissionTplManager.getModel(tplId);
            if (com.getIsDefault() == 1) {
                throw new DistributionException(DistributionErrorCode.E1013.code(), DistributionErrorCode.E1013.des());
            }
        }
        // edit
        try {
            return this.commissionTplManager.edit(commissionTpl);
        } catch (DistributionException e) {
            throw e;
        } catch (Exception e) {
            logger.error("修改模版失败：", e);
            throw new DistributionException(DistributionErrorCode.E1000.code(), DistributionErrorCode.E1000.des());
        }

    }


    @ApiOperation("模板列表")
    @GetMapping
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page_size", value = "页码大小", required = false, paramType = "query", dataType = "int", allowMultiple = false),
            @ApiImplicitParam(name = "page_no", value = "页码", required = false, paramType = "query", dataType = "int", allowMultiple = false),
    })
    public Page<CommissionTpl> listJson(@ApiIgnore Integer pageNo, @ApiIgnore Integer pageSize) {
        return this.commissionTplManager.page(pageNo, pageSize);
    }


    @ApiOperation("获取模板")
    @GetMapping(value = "/{tplId}")
    @ApiImplicitParam(name = "tplId", value = "模版id", required = false, paramType = "path", dataType = "int", allowMultiple = false)
    public CommissionTpl getModel(@PathVariable Integer tplId) {
        return this.commissionTplManager.getModel(tplId);
    }


    @ApiOperation("删除模板")
    @DeleteMapping(value = "/{tplId}")
    @ApiImplicitParam(name = "tplId", value = "模版id", required = false, paramType = "path", dataType = "int", allowMultiple = false)
    public void delete(@PathVariable Integer tplId) {
        try {
            this.commissionTplManager.delete(tplId);

        } catch (DistributionException e) {
            throw e;
        } catch (Exception e) {
            logger.error("删除模版失败：", e);
            throw new DistributionException(DistributionErrorCode.E1000.code(), DistributionErrorCode.E1000.des());
        }

    }
}

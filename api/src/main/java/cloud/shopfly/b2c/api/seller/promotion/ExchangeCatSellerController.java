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
package cloud.shopfly.b2c.api.seller.promotion;

import cloud.shopfly.b2c.core.promotion.exchange.model.dos.ExchangeCat;
import cloud.shopfly.b2c.core.promotion.exchange.service.ExchangeCatManager;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.Valid;
import java.util.List;

/**
 * 积分分类相关API
 *
 * @author Snow create in 2018/7/24
 * @version v2.0
 * @since v7.0.0
 */
@RestController
@RequestMapping("/seller/promotion/exchange-cats")
@Api(tags = "积分分类相关API")
@Validated
public class ExchangeCatSellerController {

    @Autowired
    private ExchangeCatManager exchangeCatManager;

    @ApiOperation(value = "查询某分类下的子分类列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "parent_id", value = "父id，顶级为0", required = true, dataType = "int", paramType = "path")
    })
    @GetMapping(value = "/{parent_id}/children")
    public List list(@ApiIgnore @PathVariable("parent_id") Integer parentId) {
        return	this.exchangeCatManager.list(parentId);
    }

    @ApiOperation(value	= "添加积分兑换分类", response = ExchangeCat.class)
    @PostMapping
    public ExchangeCat add(@Valid ExchangeCat exchangeCat)	{

        this.exchangeCatManager.add(exchangeCat);

        return	exchangeCat;
    }

    @PutMapping(value = "/{id}")
    @ApiOperation(value	= "修改积分兑换分类", response = ExchangeCat.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name	= "id",	value =	"A primary key",	required = true, dataType = "int",	paramType =	"path")
    })
    public	ExchangeCat edit(@Valid ExchangeCat exchangeCat, @PathVariable Integer id) {

        this.exchangeCatManager.edit(exchangeCat,id);

        return	exchangeCat;
    }


    @DeleteMapping(value = "/{id}")
    @ApiOperation(value	= "删除积分兑换分类")
    @ApiImplicitParams({
            @ApiImplicitParam(name	= "id",	value =	"The points to be deleted are converted into classified primary keys",	required = true, dataType = "int",	paramType =	"path")
    })
    public	String	delete(@PathVariable Integer id) {

        this.exchangeCatManager.delete(id);

        return "";
    }


    @GetMapping(value =	"/{id}")
    @ApiOperation(value	= "查询一个积分兑换分类")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id",	value = "The integral exchange classification primary key to query",	required = true, dataType = "int",	paramType = "path")
    })
    public	ExchangeCat get(@PathVariable	Integer	id)	{

        ExchangeCat exchangeCat = this.exchangeCatManager.getModel(id);

        return	exchangeCat;
    }

}

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
package cloud.shopfly.b2c.api.base;

import cloud.shopfly.b2c.core.pagedata.model.Article;
import cloud.shopfly.b2c.core.pagedata.model.vo.ArticleDetail;
import cloud.shopfly.b2c.core.pagedata.service.ArticleManager;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 文章控制器
 * Article controller
 *
 * @author fk
 * @version v1.0
 * @since v7.0.0
 * 2018-06-12 10:43:18
 */
@RestController
@RequestMapping("/pages")
@Api(tags = "文章相关API")
public class ArticleBuyerController {

    @Autowired
    private ArticleManager articleManager;


    @ApiOperation(value = "查询某个位置的文章列表", response = ArticleDetail.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "position", value = "文章显示位置:注册协议，入驻协议，平台联系方式，团购活动协议,其他", required = true, dataType = "string", paramType = "query", allowableValues = "REGISTRATION_AGREEMENT,COOPERATION_AGREEMENT,CONTACT_INFORMATION,GROUP_BUY_AGREEMENT,OTHER")
    })
    @GetMapping("/articles")
    public List<Article> list(String position) {

        return this.articleManager.listByPosition(position);
    }

    @GetMapping(value = "/articles/{id}")
    @ApiOperation(value = "查询一个文章")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "要查询的文章主键", required = true, dataType = "int", paramType = "path")
    })
    public Article get(@PathVariable Integer id) {

        Article article = this.articleManager.getModel(id);

        return article;
    }

    @ApiOperation(value = "查询某个位置的一个文章", response = ArticleDetail.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "position", value = "文章显示位置,注册协议，入驻协议，平台联系方式，其他", required = true, dataType = "string", paramType = "path", allowableValues = "REGISTRATION_AGREEMENT,COOPERATION_AGREEMENT,CONTACT_INFORMATION,OTHER")
    })
    @GetMapping("/{position}/articles")
    public Article getOne(@PathVariable String position) {

        List<Article> list = this.articleManager.listByPosition(position);
        return list.get(0);
    }

    @ApiOperation(value = "查询某个分类类型下的文章列表", response = ArticleDetail.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "category_type", value = "分类类型,帮助中心，商城公告，固定位置，商城促销，其他", required = true, dataType = "string", paramType = "path", allowableValues = "HELP,NOTICE,POSITION,PROMOTION,OTHER"),
    })
    @GetMapping("/article-categories/{category_type}/articles")
    public List<Article> listByCategoryType(@PathVariable("category_type") String categoryType) {

        return this.articleManager.listByCategoryType(categoryType);
    }

}


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

import cloud.shopfly.b2c.core.base.SettingGroup;
import cloud.shopfly.b2c.core.base.service.SettingManager;
import cloud.shopfly.b2c.core.system.model.vo.SiteSetting;
import cloud.shopfly.b2c.framework.util.JsonUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 站点信息首页展示控制器
 *
 * @author zh
 * @version v7.0
 * @date 18/7/13 In the morning11:21
 * @since v7.0
 */
@RestController
@RequestMapping("/site-show")
@Api(tags = "站点展示")
public class SiteShowBaseController {

    @Autowired
    private SettingManager settingManager;

    @GetMapping
    @ApiOperation(value = "获取站点设置")
    public SiteSetting getSiteSetting() {

        String siteJson = settingManager.get(SettingGroup.SITE);

        return JsonUtil.jsonToObject(siteJson,SiteSetting.class);
    }
}

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
package cloud.shopfly.b2c.core.member.plugin.weibo;

import cloud.shopfly.b2c.core.member.model.dos.Member;
import cloud.shopfly.b2c.core.member.model.enums.ConnectTypeEnum;
import cloud.shopfly.b2c.core.member.model.enums.WeiboConnectConfigGroupEnum;
import cloud.shopfly.b2c.core.member.model.enums.WeiboConnectConfigItemEnum;
import cloud.shopfly.b2c.core.member.model.vo.Auth2Token;
import cloud.shopfly.b2c.core.member.model.vo.ConnectSettingConfigItem;
import cloud.shopfly.b2c.core.member.model.vo.ConnectSettingParametersVO;
import cloud.shopfly.b2c.core.member.model.vo.ConnectSettingVO;
import cloud.shopfly.b2c.core.member.service.impl.AbstractConnectLoginPlugin;
import cloud.shopfly.b2c.framework.context.ThreadContextHolder;
import cloud.shopfly.b2c.framework.util.HttpUtils;
import cloud.shopfly.b2c.framework.util.JsonUtil;
import cloud.shopfly.b2c.framework.util.StringUtil;
import net.sf.json.JSONObject;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author zjp
 * @version v7.0
 * @Description Micro-blog trust login plug-in class
 * @ClassName WeiboAbstractConnectLoginPlugin
 * @since v7.0 In the morning11:37 2018/6/5
 */
@Component
public class WeiboAbstractConnectLoginPlugin extends AbstractConnectLoginPlugin {

    public WeiboAbstractConnectLoginPlugin() {
        super();
    }

    @Override
    public String getLoginUrl() {

        // To obtain parameters
        Map map =  initConnectSetting();

        // Store the callback address in Redis
        String callBack = this.getCallBackUrl(ConnectTypeEnum.WEIBO.value());
        return "https://api.weibo.com/oauth2/authorize?" +
                "client_id=" + StringUtil.toString(map.get("weibo_pc_app_key")) +
                "&redirect_uri=" + callBack +
                "&scope=all";
    }

    @Override
    public Auth2Token loginCallback() {

        debugger.log("Enter theWeiboAbstractConnectLoginPlugin The callback");

        // To obtain parameters
        Map map =  initConnectSetting();
        HttpServletRequest request = ThreadContextHolder.getHttpRequest();

        // Access code
        String code = request.getParameter("code");
        // Obtain the access_token and OpenID by code
        String callBack = this.getCallBackUrl(ConnectTypeEnum.WEIBO.value());
        String url = "https://api.weibo.com/oauth2/access_token";

        Map newMap = new HashMap<>(8);
        newMap.put("client_id", StringUtil.toString(map.get("weibo_pc_app_key")));
        newMap.put("client_secret", StringUtil.toString(map.get("weibo_pc_app_secret")));
        newMap.put("grant_type", "authorization_code");
        newMap.put("code", code);
        newMap.put("redirect_uri", callBack);

        debugger.log("toweiboMake a request, request address is：",url);

        String content = HttpUtils.doPost(url, newMap, "UTF-8", 1000, 1000);

        debugger.log("The return result is:",content);

        // To obtain the openid
        JSONObject json = JSONObject.fromObject(content);

        String openid = json.getString("uid");
        String accessToken = json.getString("access_token");

        Auth2Token auth2Token = new Auth2Token();
        auth2Token.setUnionid(openid);
        auth2Token.setAccessToken(accessToken);

        return auth2Token;
    }

    @Override
    public Member fillInformation(Auth2Token auth2Token, Member member) {
        // To obtain parameters
        Map map =  initConnectSetting();
        HttpServletRequest request = ThreadContextHolder.getHttpRequest();

        String url = "https://api.weibo.com/2/users/show.json?" +
                "access_token=" + auth2Token.getAccessToken() +
                "&uid=" + auth2Token.getUnionid();
        // Obtain userInfo from openID
        String content = HttpUtils.doGet(url, "UTF-8", 1000, 1000);
        JSONObject jsonObject = JSONObject.fromObject(content);
        member.setNickname(jsonObject.getString("screen_name"));
        member.setFace(jsonObject.getString("profile_image_url"));
        String sex = jsonObject.getString("gender");
        if ("m".equals(sex)) {
            member.setSex(1);
        } else {
            member.setSex(0);
        }
        return member;
    }

    @Override
    public ConnectSettingVO assembleConfig() {
        ConnectSettingVO connectSetting = new ConnectSettingVO();
        List<ConnectSettingParametersVO> list = new ArrayList<>();
        for (WeiboConnectConfigGroupEnum weiboConnectConfigGroupEnum : WeiboConnectConfigGroupEnum.values()) {
            ConnectSettingParametersVO connectSettingParametersVO = new ConnectSettingParametersVO();
            List<ConnectSettingConfigItem> lists = new ArrayList<>();
            for (WeiboConnectConfigItemEnum weiboConnectConfigItemEnum : WeiboConnectConfigItemEnum.values()) {
                ConnectSettingConfigItem connectSettingConfigItem = new ConnectSettingConfigItem();
                connectSettingConfigItem.setKey("weibo_" + weiboConnectConfigGroupEnum.value() + "_" + weiboConnectConfigItemEnum.value());
                connectSettingConfigItem.setName(weiboConnectConfigItemEnum.getText());
                lists.add(connectSettingConfigItem);
            }
            connectSettingParametersVO.setConfigList(lists);
            connectSettingParametersVO.setName(weiboConnectConfigGroupEnum.getText());
            list.add(connectSettingParametersVO);
        }
        connectSetting.setName("Configure weibo parameters");
        connectSetting.setType(ConnectTypeEnum.WEIBO.value());
        connectSetting.setConfig(JsonUtil.objectToJson(list));
        return connectSetting;
    }
}

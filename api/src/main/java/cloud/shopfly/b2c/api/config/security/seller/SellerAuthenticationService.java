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
package cloud.shopfly.b2c.api.config.security.seller;


import cloud.shopfly.b2c.framework.auth.AuthUser;
import cloud.shopfly.b2c.framework.security.impl.AbstractAuthenticationService;
import cloud.shopfly.b2c.framework.security.model.Admin;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Component;

/**
 * jwt鉴权管理
 * <p>
 * Created by kingapex on 2018/3/12.
 *
 * @author kingapex
 * @version 1.0
 * @since 7.0.0
 * 2018/3/12
 */
@SuppressWarnings("AlibabaUndefineMagicConstant")
@Component
public class SellerAuthenticationService  extends AbstractAuthenticationService {

    protected final Log logger = LogFactory.getLog(this.getClass());

    /**
     *将token解析为Clerk
     *
     * @param token
     * @return
     */
    @Override
    protected AuthUser parseToken(String token) {
        AuthUser authUser=  tokenManager.parse(Admin.class, token);
//        User user = (User) authUser;
//        checkUserDisable(Role.CLERK, user.getUid());
        return authUser;
    }


}

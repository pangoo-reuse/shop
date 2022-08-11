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

import cloud.shopfly.b2c.core.base.CachePrefix;
import cloud.shopfly.b2c.core.client.system.RoleClient;
import cloud.shopfly.b2c.framework.cache.Cache;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * 权限数据源提供者<br/>
 * 提供此资源需要的角色集合
 *
 * @author kingapex
 * @version 1.0
 * @since 7.0.0
 * 2018/3/27
 */
public class SellerSecurityMetadataSource implements org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource {

    private RoleClient roleClient;

    private Cache cache;

    public SellerSecurityMetadataSource(RoleClient roleClient, Cache cache) {
        this.roleClient = roleClient;
        this.cache = cache;
    }


    @Override
    public Collection<ConfigAttribute> getAttributes(Object object) throws IllegalArgumentException {
        FilterInvocation fi = (FilterInvocation) object;
        String url = fi.getRequestUrl();

        return this.buildAttributes(url);

    }


    private Collection<ConfigAttribute> buildAttributes(String url) {
        List<String> roleList = new ArrayList<>();
        //从缓存中获取各个角色分别对应的菜单权限，缓存中获取不到从数据库获取，放入缓存。
        Map<String, List<String>> roleMap = (Map<String, List<String>>) cache.get(CachePrefix.ADMIN_URL_ROLE.getPrefix());
        if (roleMap == null) {
            roleMap = roleClient.getRoleMap();
        }

        if (roleMap != null) {
            for (String role : roleMap.keySet()) {
                List<String> urlList = roleMap.get(role);
                if (matchUrl(urlList, url)) {
                    roleList.add("ROLE_" + role);
                }
            }
        }

        if (roleList.isEmpty()) {
            //没有匹配到,默认是要超级管理员才能访问
            return SecurityConfig.createList("ROLE_SUPER_ADMIN");

        } else {
            return SecurityConfig.createList(roleList.toArray(new String[roleList.size()]));
        }

    }


    /**
     * 看一个list 中是否匹配某个url
     * Take a look at alist Is matched to aurl
     *
     * @param patternList 一个含有ant表达式的list
     * @param url         要匹配的Url
     * @return 是否有可以匹配此url的表达式, 有返回true
     */
    private boolean matchUrl(List<String> patternList, String url) {
        // Traverse permission
        for (String expression : patternList) {

            // Match the permissions
            boolean isMatch = Pattern.matches(expression, url);

            if (isMatch) {
                return true;
            }
        }
        return false;
    }


    @Override
    public Collection<ConfigAttribute> getAllConfigAttributes() {
        return null;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return FilterInvocation.class.isAssignableFrom(clazz);
    }
}

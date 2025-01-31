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
package cloud.shopfly.b2c.core.member.model.dto;



import cloud.shopfly.b2c.core.member.model.enums.ConnectTypeEnum;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * Request landingmodel
 *
 * @author cs
 * @version v1.0
 * @since v7.2.2
 * 2020-09-24
 */
@ApiModel
@JsonNaming(value = PropertyNamingStrategy.SnakeCaseStrategy.class)
public class LoginUserDTO implements Serializable {

    private static final long serialVersionUID = -1232483319436590972L;

    @ApiModelProperty(name = "uuid", value = "This login random number", required = false)
    private String uuid;

    @ApiModelProperty(name = "tokenOutTime", value = "tokenExpiration time", required = false)
    private Integer tokenOutTime;

    @ApiModelProperty(name = "refreshTokenOutTime", value = "refreshTokenExpiration time", required = false)
    private Integer refreshTokenOutTime;

    @ApiModelProperty(name = "openid", value = "openid", required = true)
    private String openid;

    @ApiModelProperty(name = "openType", value = "openidtype", required = false)
    private ConnectTypeEnum openType;

    @ApiModelProperty(name = "unionid", value = "unionid", required = true)
    private String unionid;

    @ApiModelProperty(name = "unionType", value = "unionidtype", required = false)
    private ConnectTypeEnum unionType;


    @ApiModelProperty(name = "headimgurl", value = "Head portrait", required = false,hidden = true)
    private String headimgurl;

    @ApiModelProperty(name = "nickName", value = "The user nickname", required = false)
    private String nickName;

    @ApiModelProperty(name = "sex", value = "gender：1:male;0:female", required = false)
    private Integer sex;

    @ApiModelProperty(name = "country", value = "Country name", required = false)
    private String country;

    @ApiModelProperty(name = "city", value = "city", required = false)
    private String city;

    @ApiModelProperty(name = "oldUuid", value = "Before the redirectuuid（Distribution using）", required = false)
    private String oldUuid;


    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public Integer getTokenOutTime() {
        return tokenOutTime;
    }

    public void setTokenOutTime(Integer tokenOutTime) {
        this.tokenOutTime = tokenOutTime;
    }

    public Integer getRefreshTokenOutTime() {
        return refreshTokenOutTime;
    }

    public void setRefreshTokenOutTime(Integer refreshTokenOutTime) {
        this.refreshTokenOutTime = refreshTokenOutTime;
    }


    public ConnectTypeEnum getOpenType() {
        return openType;
    }

    public void setOpenType(ConnectTypeEnum openType) {
        this.openType = openType;
    }

    public ConnectTypeEnum getUnionType() {
        return unionType;
    }

    public void setUnionType(ConnectTypeEnum unionType) {
        this.unionType = unionType;
    }

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }

    public String getHeadimgurl() {
        return headimgurl;
    }

    public void setHeadimgurl(String headimgurl) {
        this.headimgurl = headimgurl;
    }

    public String getUnionid() {
        return unionid;
    }

    public void setUnionid(String unionid) {
        this.unionid = unionid;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getOldUuid() {
        return oldUuid;
    }

    public void setOldUuid(String oldUuid) {
        this.oldUuid = oldUuid;
    }
}

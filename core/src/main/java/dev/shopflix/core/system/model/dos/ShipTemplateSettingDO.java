/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package dev.shopflix.core.system.model.dos;

import dev.shopflix.framework.database.annotation.Column;
import dev.shopflix.framework.database.annotation.Id;
import dev.shopflix.framework.database.annotation.PrimaryKeyField;
import dev.shopflix.framework.database.annotation.Table;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiParam;

import java.io.Serializable;


/**
 * 模版详细配置
 *
 * @author fk
 * @version v1.0
 * @since v7.0.0
 * 2018-08-22 15:10:51
 */
@ApiModel
@JsonNaming(value = PropertyNamingStrategy.SnakeCaseStrategy.class)
@Table(name = "es_ship_template_setting")
public class ShipTemplateSettingDO implements Serializable {

    /**
     * <p>
     * Title:
     * </p>
     * <p>
     * Description:
     * </p>
     */
    private static final long serialVersionUID = -2310849247997108107L;

    @Id
    @ApiModelProperty(hidden = true)
    private Integer id;

    @ApiModelProperty(hidden = true)
    @Column(name = "template_id")
    private Integer templateId;

    @ApiModelProperty(hidden = true)
    @Column(name = "rate_area_id")
    private Integer rateAreaId;

    @ApiParam("价格类型：absolute:绝对值；percentage:百分比")
    @Column(name = "amt_type")
    private String amtType;

    @ApiParam("价格")
    @Column(name = "amt")
    private Double amt;

    @ApiParam("条件类型：price:价格;weight:重量;items:数量")
    @Column(name = "conditions_type")
    private String conditionsType;


    @ApiParam("区间开始")
    @Column(name = "region_start")
    private Double regionStart;

    @ApiParam("区间结束")
    @Column(name = "region_end")
    private Double regionEnd;


    @ApiParam("序号")
    @Column(name = "sort")
    private Integer sort;


    public Integer getRateAreaId() {
        return rateAreaId;
    }

    public void setRateAreaId(Integer rateAreaId) {
        this.rateAreaId = rateAreaId;
    }

    public String getAmtType() {
        return amtType;
    }

    public void setAmtType(String amtType) {
        this.amtType = amtType;
    }

    public Double getAmt() {
        return amt;
    }

    public void setAmt(Double amt) {
        this.amt = amt;
    }

    public String getConditionsType() {
        return conditionsType;
    }

    public void setConditionsType(String conditionsType) {
        this.conditionsType = conditionsType;
    }

    public Double getRegionStart() {
        return regionStart;
    }

    public void setRegionStart(Double regionStart) {
        this.regionStart = regionStart;
    }

    public Double getRegionEnd() {
        return regionEnd;
    }

    public void setRegionEnd(Double regionEnd) {
        this.regionEnd = regionEnd;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public static long getSerialversionuid() {
        return serialVersionUID;
    }

    @PrimaryKeyField
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getTemplateId() {
        return templateId;
    }

    public void setTemplateId(Integer templateId) {
        this.templateId = templateId;
    }
}
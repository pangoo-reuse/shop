/*
 * 易族智汇（北京）科技有限公司 版权所有。
 * 未经许可，您不得使用此文件。
 * 官方地址：www.javamall.com.cn
*/
package cloud.shopfly.b2c.core.client.goods.impl;

import cloud.shopfly.b2c.core.client.goods.GoodsQuantityClient;
import cloud.shopfly.b2c.core.goods.model.vo.GoodsQuantityVO;
import cloud.shopfly.b2c.core.goods.service.GoodsQuantityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 库存操作实现
 *
 * @author zh
 * @version v7.0
 * @date 18/9/20 下午7:33
 * @since v7.0
 */
@Service
@ConditionalOnProperty(value = "shopfly.product", havingValue = "stand")
public class GoodsQuantityClientDefaultImpl implements GoodsQuantityClient {

    @Autowired
    private GoodsQuantityManager goodsQuantityManager;


    @Override
    public boolean updateSkuQuantity( List<GoodsQuantityVO> goodsQuantityList) {

        return goodsQuantityManager.updateSkuQuantity(goodsQuantityList);
    }

}

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
package cloud.shopfly.b2c.framework.rabbitmq;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionalEventListener;

/**
 * 对ApplicationEventPublisher的publishEvent的监听，默认在事务提交后执行
 *
 * @author fk
 * @version v7.2.0
 * @since v7.2.0
 * 2020-06-15 21:50:52
 */
@Component
public class TransactionalMessageListener {

    @Autowired
    private AmqpTemplate amqpTemplate;

    /**
     * 默认在事务提交后执行
     * @param message
     */
    @TransactionalEventListener(fallbackExecution = true)
    public void handleSupplierBillPush(MqMessage message){
        this.amqpTemplate.convertAndSend(message.getExchange(), message.getRoutingKey(), message.getMessage());

    }
}

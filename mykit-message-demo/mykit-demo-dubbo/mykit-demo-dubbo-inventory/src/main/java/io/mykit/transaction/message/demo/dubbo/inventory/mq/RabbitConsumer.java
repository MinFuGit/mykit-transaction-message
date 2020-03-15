/**
 * Copyright 2020-9999 the original author or authors.
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.mykit.transaction.message.demo.dubbo.inventory.mq;

import io.mykit.transaction.message.core.service.MykitMqReceiveService;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

/**
 * @author binghe
 * @version 1.0.0
 * @description Rabbit消费者
 */
@Component
@ConditionalOnProperty(prefix = "spring.rabbitmq", name = "host")
@RabbitListener(queues = "inventory",containerFactory = "myContainerFactory")
public class RabbitConsumer {

    private final MykitMqReceiveService mykitMqReceiveService;

    /**
     * Instantiates a new Rabbit consumer.
     *
     * @param mykitMqReceiveService the mykit mq receive service
     */
    @Autowired
    public RabbitConsumer(MykitMqReceiveService mykitMqReceiveService) {
        this.mykitMqReceiveService = mykitMqReceiveService;
    }


    /**
     * Process.
     *
     * @param msg the msg
     */
    @RabbitHandler
    public void process(byte[] msg) {
        mykitMqReceiveService.processMessage(msg);
    }

}

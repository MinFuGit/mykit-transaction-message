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
package io.mykit.transaction.message.core.service.handler;

import io.mykit.transaction.message.common.bean.context.MykitTransactionMessageContext;
import io.mykit.transaction.message.common.enums.MykitTransactionMessageStatusEnum;
import io.mykit.transaction.message.core.concurrent.threadlocal.TransactionContextLocal;
import io.mykit.transaction.message.core.service.MykitTransactionHandler;
import io.mykit.transaction.message.core.service.engine.MykitTransactionEngine;
import org.aspectj.lang.ProceedingJoinPoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author binghe
 * @version 1.0.0
 * @description StartMykitTransactionHandler
 */
@Component
public class StartMykitTransactionHandler implements MykitTransactionHandler {

    private final MykitTransactionEngine mykitTransactionEngine;

    @Autowired
    public StartMykitTransactionHandler(final MykitTransactionEngine mykitTransactionEngine) {
        this.mykitTransactionEngine = mykitTransactionEngine;
    }

    @Override
    public Object handler(ProceedingJoinPoint point, MykitTransactionMessageContext mykitTransactionMessageContext) throws Throwable {
        try {
            mykitTransactionEngine.begin(point);
            final Object proceed = point.proceed();
            mykitTransactionEngine.updateStatus(MykitTransactionMessageStatusEnum.COMMIT.getCode());
            return proceed;
        } catch (Throwable throwable) {
            //update error log
            mykitTransactionEngine.failTransaction(throwable.getMessage());
            throw throwable;
        } finally {
            //send message to mq.
            mykitTransactionEngine.sendMessage();
            mykitTransactionEngine.cleanThreadLocal();
            TransactionContextLocal.getInstance().remove();
        }
    }
}

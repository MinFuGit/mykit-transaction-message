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
package io.mykit.transaction.message.rpc.springcloud.fegin;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import io.mykit.transaction.message.common.bean.context.MykitTransactionMessageContext;
import io.mykit.transaction.message.core.concurrent.threadlocal.TransactionContextLocal;
import io.mykit.transaction.message.core.mediator.RpcMediator;
import org.springframework.context.annotation.Configuration;

/**
 * @author binghe
 * @version 1.0.0
 * @description MykitFeignInterceptor
 */
@Configuration
public class MykitFeignInterceptor implements RequestInterceptor {

    @Override
    public void apply(final RequestTemplate requestTemplate) {
        final MykitTransactionMessageContext mythTransactionContext = TransactionContextLocal.getInstance().get();
        RpcMediator.getInstance().transmit(requestTemplate::header, mythTransactionContext);
    }

}
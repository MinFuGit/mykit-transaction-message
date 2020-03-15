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
package io.mykit.transaction.message.core.disruptor.handler;

import com.lmax.disruptor.WorkHandler;
import io.mykit.transaction.message.common.bean.entity.MykitTransaction;
import io.mykit.transaction.message.common.enums.EventTypeEnum;
import io.mykit.transaction.message.core.coordinator.MykitCoordinatorService;
import io.mykit.transaction.message.core.disruptor.event.MykitTransactionEvent;

import java.util.concurrent.Executor;

/**
 * @author binghe
 * @version 1.0.0
 * @description MykitTransactionEventHandler
 */
public class MykitTransactionEventHandler implements WorkHandler<MykitTransactionEvent> {

    private final MykitCoordinatorService mykitCoordinatorService;

    private Executor executor;

    public MykitTransactionEventHandler(final MykitCoordinatorService mykitCoordinatorService,
                                       final Executor executor) {
        this.mykitCoordinatorService = mykitCoordinatorService;
        this.executor = executor;
    }

    @Override
    public void onEvent(final MykitTransactionEvent mykitTransactionEvent) {
        executor.execute(() -> {
            if (mykitTransactionEvent.getType() == EventTypeEnum.SAVE.getCode()) {
                mykitCoordinatorService.save(mykitTransactionEvent.getMykitTransaction());
            } else if (mykitTransactionEvent.getType() == EventTypeEnum.UPDATE_PARTICIPANT.getCode()) {
                mykitCoordinatorService.updateParticipant(mykitTransactionEvent.getMykitTransaction());
            } else if (mykitTransactionEvent.getType() == EventTypeEnum.UPDATE_STATUS.getCode()) {
                final MykitTransaction mykitTransaction = mykitTransactionEvent.getMykitTransaction();
                mykitCoordinatorService.updateStatus(mykitTransaction.getTransId(), mykitTransaction.getStatus());
            } else if (mykitTransactionEvent.getType() == EventTypeEnum.UPDATE_FAIR.getCode()) {
                mykitCoordinatorService.updateFailTransaction(mykitTransactionEvent.getMykitTransaction());
            }
            mykitTransactionEvent.clear();
        });
    }
}

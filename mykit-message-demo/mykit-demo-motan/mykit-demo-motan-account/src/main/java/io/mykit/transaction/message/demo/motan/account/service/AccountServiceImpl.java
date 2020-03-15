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
package io.mykit.transaction.message.demo.motan.account.service;

import io.mykit.transaction.message.annotation.MykitTransactionMessage;
import io.mykit.transaction.message.demo.motan.account.api.dto.AccountDto;
import io.mykit.transaction.message.demo.motan.account.api.entity.AccountDo;
import io.mykit.transaction.message.demo.motan.account.api.service.AccountService;
import io.mykit.transaction.message.demo.motan.account.mapper.AccountMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author binghe
 * @version 1.0.0
 * @description 账户业务实现
 */
public class AccountServiceImpl implements AccountService {
    /**
     * logger
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(AccountServiceImpl.class);


    private final AccountMapper accountMapper;

    /**
     * Instantiates a new Account service.
     *
     * @param accountMapper the account mapper
     */
    @Autowired(required = false)
    public AccountServiceImpl(AccountMapper accountMapper) {
        this.accountMapper = accountMapper;
    }

    /**
     * 扣款支付 真实的场景请保证幕等性。
     *
     * @param accountDTO 参数dto
     * @return true
     */
    @Override
    @MykitTransactionMessage(destination = "account")
    public boolean payment(AccountDto accountDTO) {
        LOGGER.debug("========================motan 框架 开始执行扣款服务===");
        final AccountDo accountDO = accountMapper.findByUserId(accountDTO.getUserId());
        if (accountDO.getBalance().compareTo(BigDecimal.valueOf(accountDTO.getAmount())) <= 0) {
            throw new RuntimeException("资金不足！");
        }
        accountDO.setBalance(accountDO.getBalance().subtract(
                BigDecimal.valueOf(accountDTO.getAmount())));
        accountDO.setUpdateTime(new Date());
        final int update = accountMapper.update(accountDO);
        if (update != 1) {
            throw new RuntimeException("资金不足！");
        }
        return Boolean.TRUE;
    }

    /**
     * 获取用户资金信息
     *
     * @param userId 用户id
     * @return AccountDO
     */
    @Override
    public AccountDo findByUserId(String userId) {
        return  accountMapper.findByUserId(userId);
    }
}

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
package io.mykit.transaction.message.demo.dubbo.inventory.api.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * @author binghe
 * @version 1.0.0
 * @description 库存实体类
 */
@Data
public class Inventory implements Serializable {
    private static final long serialVersionUID = 1151540062746906025L;

    private Integer id;

    /**
     * 商品id
     */
    private Integer productId;

    /**
     * 总库存
     */
    private Integer totalInventory;
}

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
package io.mykit.transaction.message.common.exception;

/**
 * @author binghe
 * @version 1.0.0
 * @description MykitException
 */
public class MykitException extends RuntimeException{
    private static final long serialVersionUID = 8464859216526611255L;
    /**
     * Instantiates a new Myth exception.
     */
    public MykitException() {
    }

    /**
     * Instantiates a new Myth exception.
     *
     * @param message the message
     */
    public MykitException(final String message) {
        super(message);
    }

    /**
     * Instantiates a new Myth exception.
     *
     * @param message the message
     * @param cause   the cause
     */
    public MykitException(final String message, final Throwable cause) {
        super(message, cause);
    }

    /**
     * Instantiates a new Myth exception.
     *
     * @param cause the cause
     */
    public MykitException(final Throwable cause) {
        super(cause);
    }
}

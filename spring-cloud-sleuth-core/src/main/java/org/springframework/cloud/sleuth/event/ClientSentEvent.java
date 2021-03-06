/*
 * Copyright 2013-2015 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.springframework.cloud.sleuth.event;

import lombok.Data;
import lombok.EqualsAndHashCode;

import org.springframework.cloud.sleuth.Span;
import org.springframework.context.ApplicationEvent;

/**
 * @author Dave Syer
 *
 */
@Data
@EqualsAndHashCode(callSuper = false)
@SuppressWarnings("serial")
public class ClientSentEvent extends ApplicationEvent {

	private final Span span;

	public ClientSentEvent(Object source, Span span) {
		super(source);
		this.span = span;
	}
}
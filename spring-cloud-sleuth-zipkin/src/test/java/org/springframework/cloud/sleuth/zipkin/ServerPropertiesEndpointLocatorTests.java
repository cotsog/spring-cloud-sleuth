/*
 * Copyright 2015 the original author or authors.
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

package org.springframework.cloud.sleuth.zipkin;

import java.net.InetAddress;
import java.net.UnknownHostException;
import org.junit.Test;
import org.springframework.boot.autoconfigure.web.ServerProperties;

import static org.assertj.core.api.Assertions.assertThat;

public class ServerPropertiesEndpointLocatorTests {

  @Test
  public void portDefaultsTo8080() {
    ServerPropertiesEndpointLocator locator = new ServerPropertiesEndpointLocator(new ServerProperties(), "unknown");

    assertThat(locator.local().port).isEqualTo((short) 8080);
  }

  @Test
  public void portFromServerProperties() {
    ServerProperties properties = new ServerProperties();
    properties.setPort(1234);

    ServerPropertiesEndpointLocator locator = new ServerPropertiesEndpointLocator(properties, "unknown");

    assertThat(locator.local().port).isEqualTo((short) 1234);
  }

  @Test
  public void portDefaultsToLocalhost() {
    ServerPropertiesEndpointLocator locator = new ServerPropertiesEndpointLocator(new ServerProperties(), "unknown");

    assertThat(locator.local().ipv4)
        .isEqualTo(127 << 24 | 1);
  }

  @Test
  public void hostFromServerPropertiesIp() throws UnknownHostException {
    ServerProperties properties = new ServerProperties();
    properties.setAddress(InetAddress.getByAddress(new byte[]{1, 2, 3, 4}));

    ServerPropertiesEndpointLocator locator = new ServerPropertiesEndpointLocator(properties, "unknown");

    assertThat(locator.local().ipv4)
        .isEqualTo(1 << 24 | 2 << 16 | 3 << 8 | 4);
  }
}

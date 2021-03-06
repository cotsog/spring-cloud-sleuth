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
package integration;

import example.ZipkinStreamServerApplication;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.WebIntegrationTest;
import org.springframework.cloud.stream.test.binder.TestSupportBinderAutoConfiguration;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.JdkIdGenerator;
import tools.AbstractIntegrationTest;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = { SampleApp.Config.class,
		AbstractIntegrationTest.WaitUntilZipkinIsUpConfig.class,
		TestSupportBinderAutoConfiguration.class,
		ZipkinStreamServerApplication.class })
@WebIntegrationTest
@Slf4j
@ActiveProfiles("test")
public class ZipkinStreamTests extends AbstractIntegrationTest {

	private static int port = 9411;
	private static String sampleAppUrl = "http://localhost:" + port;

	@Test
	@SneakyThrows
	public void should_propagate_spans_to_zipkin() {
		await().until(zipkinServerIsUp());
		String traceId = new JdkIdGenerator().generateId().toString();

		await().until(httpMessageWithTraceIdInHeadersIsSuccessfullySent(sampleAppUrl + "/hi2", traceId));

		await().until(allSpansWereRegisteredInZipkinWithTraceIdEqualTo(traceId));
	}

}

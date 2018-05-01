/*
 * Zed Attack Proxy (ZAP) and its related class files.
 *
 * ZAP is an HTTP/HTTPS proxy for assessing web application security.
 *
 * Copyright 2018 The ZAP Development Team
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.zaproxy.zap.extension.pscanrulesAlpha;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

import org.junit.Test;

import org.zaproxy.zap.extension.pscan.PluginPassiveScanner;

public class SourceCodeDisclosureScannerUnitTest extends PassiveScannerTest {

	@Override
	protected PluginPassiveScanner createScanner() {
		return new SourceCodeDisclosureScanner();
	}

	@Test
	public void scannerNameShouldMatch() {
		// Quick test to verify scanner name which is used in the policy dialog but not 
		// alone in alerts

		// Given
		PluginPassiveScanner thisScanner = createScanner();
		// Then
		assertThat(thisScanner.getName(), equalTo("Source Code Disclosure"));
	}
	
}
/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package org.apache.maven.plugins.dependency;

import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import java.io.File;
import org.apache.maven.plugin.Mojo;
import org.apache.maven.plugin.logging.Log;
import org.mockito.ArgumentCaptor;

public class TestSkip extends AbstractDependencyMojoTestCase {
    public void testSkipAnalyze() throws Exception {
        doTest("analyze");
    }

    public void testSkipAnalyzeDepMgt() throws Exception {
        doTest("analyze-dep-mgt");
    }

    public void testSkipAnalyzeOnly() throws Exception {
        doTest("analyze-only");
    }

    public void testSkipAnalyzeReport() throws Exception {
        doSpecialTest("analyze-report");
    }

    public void testSkipAnalyzeDuplicate() throws Exception {
        doTest("analyze-duplicate");
    }

    public void testSkipBuildClasspath() throws Exception {
        doTest("build-classpath");
    }

    public void testSkipCopy() throws Exception {
        doTest("copy");
    }

    public void testSkipCopyDependencies() throws Exception {
        doTest("copy-dependencies");
    }

    public void testSkipGet() throws Exception {
        doSpecialTest("get");
    }

    public void testSkipGoOffline() throws Exception {
        doTest("go-offline");
    }

    public void testSkipList() throws Exception {
        doTest("list");
    }

    public void testSkipProperties() throws Exception {
        doTest("properties");
    }

    public void testSkipPurgeLocalRepository() throws Exception {
        doSpecialTest("purge-local-repository");
    }

    public void testSkipResolve() throws Exception {
        doTest("resolve");
    }

    public void testSkipResolvePlugins() throws Exception {
        doTest("resolve-plugins");
    }

    public void testSkipSources() throws Exception {
        doTest("sources");
    }

    public void testSkipTree() throws Exception {
        doTest("tree");
    }

    public void testSkipUnpack() throws Exception {
        doTest("unpack");
    }

    public void testSkipUnpackDependencies() throws Exception {
        doTest("unpack-dependencies");
    }

    protected void doTest(String mojoName) throws Exception {
        doConfigTest(mojoName, "plugin-config.xml");
    }

    protected void doSpecialTest(String mojoName) throws Exception {
        doConfigTest(mojoName, "plugin-" + mojoName + "-config.xml");
    }

    private void doConfigTest(String mojoName, String configFile) throws Exception {
        File testPom = new File(getBasedir(), "target/test-classes/unit/skip-test/" + configFile);
        Mojo mojo = lookupMojo(mojoName, testPom);
        assertNotNull(mojo);
        Log log = mock(Log.class);
        mojo.setLog(log);
        mojo.execute();

        ArgumentCaptor<String> captor = ArgumentCaptor.forClass(String.class);
        verify(log, atLeastOnce()).info(captor.capture());
        assertTrue(captor.getValue().contains("Skipping plugin execution"));
    }
}

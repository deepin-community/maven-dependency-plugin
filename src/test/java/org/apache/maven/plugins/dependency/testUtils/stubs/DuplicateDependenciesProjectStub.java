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
package org.apache.maven.plugins.dependency.testUtils.stubs;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import org.apache.maven.model.Model;
import org.apache.maven.model.io.xpp3.MavenXpp3Reader;
import org.apache.maven.plugin.testing.stubs.MavenProjectStub;
import org.codehaus.plexus.util.xml.pull.XmlPullParserException;

/**
 * very simple stub of maven project, going to take a lot of work to make it useful as a stub though
 */
public class DuplicateDependenciesProjectStub extends MavenProjectStub {
    public DuplicateDependenciesProjectStub() {
        File pom = new File(getBasedir(), "plugin-config.xml");
        MavenXpp3Reader pomReader = new MavenXpp3Reader();

        try (FileInputStream in = new FileInputStream(pom)) {
            Model model = pomReader.read(in);
            setModel(model);

            setGroupId(model.getGroupId());
            setArtifactId(model.getArtifactId());
            setVersion(model.getVersion());
            setName(model.getName());
            setUrl(model.getUrl());
            setPackaging(model.getPackaging());
            setFile(pom);
        } catch (IOException | XmlPullParserException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * @see org.apache.maven.plugin.testing.stubs.MavenProjectStub#getBasedir()
     */
    public File getBasedir() {
        return new File(super.getBasedir() + "/src/test/resources/unit/duplicate-dependencies");
    }
}

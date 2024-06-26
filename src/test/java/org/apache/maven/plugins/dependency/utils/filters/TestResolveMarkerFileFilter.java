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
package org.apache.maven.plugins.dependency.utils.filters;

/**
 *
 */
import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import junit.framework.TestCase;
import org.apache.commons.io.FileUtils;
import org.apache.maven.artifact.Artifact;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.logging.Log;
import org.apache.maven.plugin.testing.SilentLog;
import org.apache.maven.plugins.dependency.testUtils.DependencyArtifactStubFactory;
import org.apache.maven.plugins.dependency.utils.markers.SourcesFileMarkerHandler;
import org.apache.maven.shared.artifact.filter.collection.ArtifactFilterException;

/**
 * @author brianf
 */
public class TestResolveMarkerFileFilter extends TestCase {
    Set<Artifact> artifacts = new HashSet<>();

    Log log = new SilentLog();

    File outputFolder;

    DependencyArtifactStubFactory fact;

    protected void setUp() throws Exception {
        super.setUp();

        outputFolder = new File("target/markers/");
        FileUtils.deleteDirectory(outputFolder);
        assertFalse(outputFolder.exists());

        this.fact = new DependencyArtifactStubFactory(outputFolder, false);
        artifacts = fact.getReleaseAndSnapshotArtifacts();
    }

    protected void tearDown() throws IOException {
        FileUtils.deleteDirectory(outputFolder);
    }

    public void testResolveFile() throws IOException, ArtifactFilterException, MojoExecutionException {
        SourcesFileMarkerHandler handler = new SourcesFileMarkerHandler(outputFolder);

        Artifact artifact = fact.getReleaseArtifact();
        handler.setArtifact(artifact);

        ResolveFileFilter filter = new ResolveFileFilter(handler);

        assertTrue(filter.isArtifactIncluded(artifact));
        handler.setMarker();
        assertFalse(filter.isArtifactIncluded(artifact));
    }
}

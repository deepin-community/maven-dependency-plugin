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
package org.apache.maven.plugins.dependency.analyze;

import java.util.Iterator;
import java.util.ResourceBundle;
import org.apache.maven.artifact.Artifact;
import org.apache.maven.doxia.sink.Sink;
import org.apache.maven.shared.dependency.analyzer.ProjectDependencyAnalysis;

/**
 * This is the view part of the analyze-report mojo. It generates the HTML report for the project website. The HTML
 * output is same as the CLI output.
 */
public class AnalyzeReportView {
    /**
     * Generates the HTML report.
     *
     * @param analysis {@link ProjectDependencyAnalysis}
     * @param sink {@link Sink}
     * @param bundle {@link ResourceBundle}
     */
    public void generateReport(ProjectDependencyAnalysis analysis, Sink sink, ResourceBundle bundle) {
        sink.head();
        sink.title();
        sink.text(bundle.getString("analyze.report.header"));
        sink.title_();
        sink.head_();
        sink.body();

        // Generate title
        sink.section1();
        sink.sectionTitle1();
        sink.text(bundle.getString("analyze.report.mainTitle"));
        sink.sectionTitle1_();

        // Generate Used Declared dependencies:
        sink.section2();
        sink.sectionTitle2();
        sink.text(bundle.getString("analyze.report.UsedDeclaredDependencies"));
        sink.sectionTitle2_();
        if (analysis.getUsedDeclaredArtifacts().isEmpty()) {
            sink.paragraph();
            sink.text(bundle.getString("analyze.report.noDependency"));
            sink.paragraph_();
            sink.horizontalRule();
        } else {
            generateDependenciesTable(sink, analysis.getUsedDeclaredArtifacts().iterator());
        }
        sink.section2_();

        // Generate Used Undeclared dependencies:
        sink.section2();
        sink.sectionTitle2();
        sink.text(bundle.getString("analyze.report.UsedUndeclaredDependencies"));
        sink.sectionTitle2_();
        if (analysis.getUsedUndeclaredArtifacts().isEmpty()) {
            sink.paragraph();
            sink.text(bundle.getString("analyze.report.noDependency"));
            sink.paragraph_();
            sink.horizontalRule();
        } else {
            generateDependenciesTable(
                    sink, analysis.getUsedUndeclaredArtifacts().iterator());
        }
        sink.section2_();

        // Generate Unused declared dependencies:
        sink.section2();
        sink.sectionTitle2();
        sink.text(bundle.getString("analyze.report.UnusedDeclaredDependencies"));
        sink.sectionTitle2_();
        if (analysis.getUnusedDeclaredArtifacts().isEmpty()) {
            sink.paragraph();
            sink.text(bundle.getString("analyze.report.noDependency"));
            sink.paragraph_();
            sink.horizontalRule();
        } else {
            generateDependenciesTable(
                    sink, analysis.getUnusedDeclaredArtifacts().iterator());
        }
        sink.section2_();

        // Generate Non-Test Scoped Test Dependencies:
        sink.section2();
        sink.sectionTitle2();
        sink.text("Compile Scoped Test Dependencies");
        sink.sectionTitle2_();
        if (analysis.getTestArtifactsWithNonTestScope().isEmpty()) {
            sink.paragraph();
            sink.text("None");
            sink.paragraph_();
            sink.horizontalRule();
        } else {
            generateDependenciesTable(
                    sink, analysis.getTestArtifactsWithNonTestScope().iterator());
        }
        sink.section2_();

        sink.section1_();

        // Closing the report
        sink.body_();
        sink.flush();
        sink.close();
    }

    /**
     * Generate a table for the given dependencies iterator.
     *
     * @param sink {@link Sink}
     * @param iter {@link Artifact}
     */
    public void generateDependenciesTable(Sink sink, Iterator<Artifact> iter) {
        sink.table();
        sink.tableRows(null, false);

        sink.tableRow();
        sink.tableCell();
        sink.bold();
        sink.text("GroupId");
        sink.bold_();
        sink.tableCell_();

        sink.tableCell();
        sink.bold();
        sink.text("ArtifactId");
        sink.bold_();
        sink.tableCell_();

        sink.tableCell();
        sink.bold();
        sink.text("Version");
        sink.bold_();
        sink.tableCell_();

        sink.tableCell();
        sink.bold();
        sink.text("Scope");
        sink.bold_();
        sink.tableCell_();

        sink.tableCell();
        sink.bold();
        sink.text("Classifier");
        sink.bold_();
        sink.tableCell_();

        sink.tableCell();
        sink.bold();
        sink.text("Type");
        sink.bold_();
        sink.tableCell_();

        sink.tableCell();
        sink.bold();
        sink.text("Optional");
        sink.bold_();
        sink.tableCell_();

        sink.tableRow_();
        while (iter.hasNext()) {
            Artifact artifact = iter.next();

            sink.tableRow();
            sink.tableCell();
            sink.text(artifact.getGroupId());
            sink.tableCell_();
            sink.tableCell();
            sink.text(artifact.getArtifactId());
            sink.tableCell_();
            sink.tableCell();
            sink.text(artifact.getVersion());
            sink.tableCell_();
            sink.tableCell();
            sink.text(artifact.getScope());
            sink.tableCell_();
            sink.tableCell();
            sink.text(artifact.getClassifier());
            sink.tableCell_();
            sink.tableCell();
            sink.text(artifact.getType());
            sink.tableCell_();
            sink.tableCell();
            if (artifact.isOptional()) {
                sink.text("");
            } else {
                sink.text("false");
            }

            sink.tableCell_();
            sink.tableRow_();
        }

        sink.tableRows_();
        sink.table_();
        sink.horizontalRule();
    }
}

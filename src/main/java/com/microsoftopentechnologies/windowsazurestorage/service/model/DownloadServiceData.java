/*
 Copyright 2016 Microsoft, Inc.

 Licensed under the Apache License, Version 2.0 (the "License");
 you may not use this file except in compliance with the License.
 You may obtain a copy of the License at

 http://www.apache.org/licenses/LICENSE-2.0

 Unless required by applicable law or agreed to in writing, software
 distributed under the License is distributed on an "AS IS" BASIS,
 WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 See the License for the specific language governing permissions and
 limitations under the License.
 */

package com.microsoftopentechnologies.windowsazurestorage.service.model;

import com.microsoftopentechnologies.windowsazurestorage.beans.StorageAccountInfo;
import hudson.EnvVars;
import hudson.FilePath;
import hudson.Launcher;
import hudson.Util;
import hudson.model.Result;
import hudson.model.Run;
import hudson.model.TaskListener;
import hudson.plugins.copyartifact.BuildSelector;
import org.apache.commons.lang.StringUtils;

import java.io.IOException;

public class DownloadServiceData extends ServiceData {
    private String includeFilesPattern;
    private String excludeFilesPattern;
    private String containerName;
    private String downloadDirLoc;
    private boolean flattenDirectories;
    private boolean deleteFromAzureAfterDownload;
    private String downloadType;
    private String projectName;
    private BuildSelector buildSelector;
    private boolean includeArchiveZips;

    public DownloadServiceData(final Run<?, ?> run,
                               final FilePath workspace,
                               final Launcher launcher,
                               final TaskListener taskListener,
                               final StorageAccountInfo storageAccountInfo) {
        super(run, workspace, launcher, taskListener, storageAccountInfo);
    }

    public FilePath getDownloadDir() {
        FilePath downloadDir = getRemoteWorkspace();
        try {
            if (!StringUtils.isBlank(downloadDirLoc)) {
                final EnvVars envVars = getRun().getEnvironment(getTaskListener());
                downloadDir = new FilePath(getRemoteWorkspace(), Util.replaceMacro(downloadDirLoc, envVars));
            }

            if (!downloadDir.exists()) {
                downloadDir.mkdirs();
            }
        } catch (IOException | InterruptedException e) {
            getRun().setResult(Result.UNSTABLE);
        }

        return downloadDir;
    }

    public String getIncludeFilesPattern() {
        return includeFilesPattern;
    }

    public void setIncludeFilesPattern(final String includeFilesPattern) {
        this.includeFilesPattern = includeFilesPattern;
    }

    public String getExcludeFilesPattern() {
        return excludeFilesPattern;
    }

    public void setExcludeFilesPattern(final String excludeFilesPattern) {
        this.excludeFilesPattern = excludeFilesPattern;
    }

    public String getDownloadDirLoc() {
        return downloadDirLoc;
    }

    public void setDownloadDirLoc(final String downloadDirLoc) {
        this.downloadDirLoc = downloadDirLoc;
    }

    public boolean isFlattenDirectories() {
        return flattenDirectories;
    }

    public void setFlattenDirectories(final boolean flattenDirectories) {
        this.flattenDirectories = flattenDirectories;
    }

    public String getContainerName() {
        return containerName;
    }

    public void setContainerName(final String containerName) {
        this.containerName = containerName;
    }

    public boolean isDeleteFromAzureAfterDownload() {
        return deleteFromAzureAfterDownload;
    }

    public void setDeleteFromAzureAfterDownload(final boolean deleteFromAzureAfterDownload) {
        this.deleteFromAzureAfterDownload = deleteFromAzureAfterDownload;
    }

    public String getDownloadType() {
        return downloadType;
    }

    public void setDownloadType(final String downloadType) {
        this.downloadType = downloadType;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(final String projectName) {
        this.projectName = projectName;
    }

    public BuildSelector getBuildSelector() {
        return buildSelector;
    }

    public void setBuildSelector(final BuildSelector buildSelector) {
        this.buildSelector = buildSelector;
    }

    public boolean isIncludeArchiveZips() {
        return includeArchiveZips;
    }

    public void setIncludeArchiveZips(final boolean includeArchiveZips) {
        this.includeArchiveZips = includeArchiveZips;
    }
}

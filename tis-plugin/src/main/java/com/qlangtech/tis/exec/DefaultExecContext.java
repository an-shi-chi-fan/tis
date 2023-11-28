/**
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.qlangtech.tis.exec;

import com.google.common.collect.Maps;
import com.qlangtech.tis.cloud.ITISCoordinator;
import com.qlangtech.tis.datax.IDataxProcessor;
import com.qlangtech.tis.datax.impl.DataxProcessor;
import com.qlangtech.tis.fs.ITISFileSystem;
import com.qlangtech.tis.fullbuild.indexbuild.RemoteTaskTriggers;
import com.qlangtech.tis.fullbuild.phasestatus.PhaseStatusCollection;
import com.qlangtech.tis.job.common.JobCommon;
import com.qlangtech.tis.order.center.IAppSourcePipelineController;
import com.qlangtech.tis.sql.parser.TabPartitions;
import org.apache.commons.lang.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Supplier;

/**
 * @author 百岁 (baisui@qlangtech.com)
 * @date 2023/11/18
 */
public class DefaultExecContext implements IExecChainContext {

    private final long ps;
    private Integer workflowId;
    private ExecutePhaseRange executePhaseRange;
    private final String dataXName;
    private boolean dryRun;
    private ITISCoordinator coordinator;
    private PhaseStatusCollection latestPhaseStatusCollection;

    public DefaultExecContext(String dataXName, Long triggerTimestamp) {
        this.ps = triggerTimestamp;
        this.dataXName = dataXName;
        ExecChainContextUtils.setDependencyTablesPartitions(this, new TabPartitions(Maps.newHashMap()));
    }

    public void setCoordinator(ITISCoordinator coordinator) {
        this.coordinator = coordinator;
    }

    public void setExecutePhaseRange(ExecutePhaseRange executePhaseRange) {
        this.executePhaseRange = executePhaseRange;
    }

    public void setDryRun(boolean dryRun) {
        this.dryRun = dryRun;
    }


    public void setWorkflowId(Integer workflowId) {
        this.workflowId = workflowId;
    }

    @Override
    public final IDataxProcessor getProcessor() {
        return DataxProcessor.load(null, this.dataXName);
    }

    @Override
    public void addAsynSubJob(AsynSubJob jobName) {

    }

    @Override
    public List<AsynSubJob> getAsynSubJobs() {
        return null;
    }

    @Override
    public boolean containAsynJob() {
        return false;
    }

    private RemoteTaskTriggers tskTriggers;

    @Override
    public void setTskTriggers(RemoteTaskTriggers tskTriggers) {
        this.tskTriggers = tskTriggers;
    }

    @Override
    public RemoteTaskTriggers getTskTriggers() {
        return Objects.requireNonNull(this.tskTriggers, "tskTriggers can not be null");
    }

    @Override
    public void cancelTask() {
        throw new UnsupportedOperationException();
    }

    @Override
    public ITISCoordinator getZkClient() {
        return this.coordinator;
    }

    @Override
    public Integer getWorkflowId() {
        return this.workflowId;
    }

    @Override
    public String getWorkflowName() {
        throw new UnsupportedOperationException();
    }

    @Override
    public ITISFileSystem getIndexBuildFileSystem() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void rebindLoggingMDCParams() {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean isDryRun() {
        return this.dryRun;
    }

    @Override
    public int getIndexShardCount() {
        throw new UnsupportedOperationException();
    }

    private final Map<String, Object> attribute = new HashMap<>();

    @Override
    public <T> T getAttribute(String key) {
        return (T) this.attribute.get(key);
    }

    @Override
    public <T> T getAttribute(String key, Supplier<T> creator) {
        synchronized (attribute) {
            T attr = getAttribute(key);
            if (attr == null) {
                attr = creator.get();
                this.setAttribute(key, attr);
            }
            return attr;
        }
    }

    //  private Map<String, Object> attrs = new HashMap<>();

    @Override
    public void setAttribute(String key, Object v) {
        this.attribute.put(key, v);
    }

    @Override
    public IAppSourcePipelineController getPipelineController() {
        return null;
    }

    @Override
    public int getTaskId() {
        Integer taskId = Objects.requireNonNull(getAttribute(JobCommon.KEY_TASK_ID),
                JobCommon.KEY_TASK_ID + " can " + "not be null");
        return taskId;
    }

    @Override
    public String getIndexName() {
        return this.dataXName;
    }

    @Override
    public boolean hasIndexName() {
        return StringUtils.isNotEmpty(this.dataXName);
    }

    @Override
    public ExecutePhaseRange getExecutePhaseRange() {
        return this.executePhaseRange;
    }

    @Override
    public String getString(String key) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean getBoolean(String key) {
        throw new UnsupportedOperationException();
    }

    @Override
    public int getInt(String key) {
        throw new UnsupportedOperationException();
    }

    @Override
    public long getLong(String key) {
        throw new UnsupportedOperationException();
    }

    @Override
    public long getPartitionTimestampWithMillis() {
        return ps;
    }

    public void setLatestPhaseStatusCollection(PhaseStatusCollection latestPhaseStatusCollection) {
        this.latestPhaseStatusCollection = latestPhaseStatusCollection;
    }

    @Override
    public PhaseStatusCollection loadPhaseStatusFromLatest() {
        return this.latestPhaseStatusCollection;
    }
}
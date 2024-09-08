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

package com.qlangtech.tis.manage.common;

import com.qlangtech.tis.pubhook.common.RunEnvironment;

/**
 *
 */
public class AppAndRuntime {

    private static ThreadLocal<AppAndRuntime> appAndRuntimeLocal = new ThreadLocal<AppAndRuntime>();
    private String appName;

    private RunEnvironment runtime;

//    public static void setAppAndRuntimeLocal(AppAndRuntime appAndRuntime) {
//        appAndRuntimeLocal.set(appAndRuntime);
//    }

    // public static void setThreadRequest(AdapterHttpRequest request) {
    // requestLocal.set(request);
    // }
    public static AppAndRuntime getAppAndRuntime() {
        return appAndRuntimeLocal.get();
    }

    public static void setAppAndRuntime(AppAndRuntime appAndRuntime) {
        appAndRuntimeLocal.set(appAndRuntime);
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public void setRuntime(RunEnvironment runtime) {
        this.runtime = runtime;
    }

    public String getAppName() {
        return appName;
    }

    public RunEnvironment getRuntime() {
        return runtime;
    }
}

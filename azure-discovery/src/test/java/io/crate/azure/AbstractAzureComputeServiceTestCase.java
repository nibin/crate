/*
 * Licensed to Crate under one or more contributor license agreements.
 * See the NOTICE file distributed with this work for additional
 * information regarding copyright ownership.  Crate licenses this file
 * to you under the Apache License, Version 2.0 (the "License"); you may
 * not use this file except in compliance with the License.  You may
 * obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or
 * implied.  See the License for the specific language governing
 * permissions and limitations under the License.
 *
 * However, if you have executed another commercial license agreement
 * with Crate these terms will supersede the license and you may use the
 * software solely pursuant to the terms of the relevant commercial
 * agreement.
 */

package io.crate.azure;

import org.elasticsearch.action.admin.cluster.node.info.NodesInfoResponse;
import io.crate.azure.management.AzureComputeService.*;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.test.ESIntegTestCase;


public abstract class AbstractAzureComputeServiceTestCase extends ESIntegTestCase {

    @Override
    protected Settings nodeSettings(int nodeOrdinal) {
        Settings.Builder builder = Settings.settingsBuilder()
            .put(super.nodeSettings(nodeOrdinal))
            .put("node.mode", "network")
            .put("discovery.type", "azure")
            .put(Management.SUBSCRIPTION_ID, "fake")
            .put(Discovery.REFRESH, "5s")
            .put(Management.APP_ID, "dummy")
            .put(Management.TENANT_ID, "dummy")
            .put(Management.APP_SECRET, "dummy")
            .put(Management.RESOURCE_GROUP_NAME, "dummy");
        return builder.build();
    }

    protected void checkNumberOfNodes(int expected) {
        NodesInfoResponse nodeInfos = client().admin().cluster().prepareNodesInfo().execute().actionGet();
        assertNotNull(nodeInfos);
        assertNotNull(nodeInfos.getNodes());
        assertEquals(expected, nodeInfos.getNodes().length);
    }
}

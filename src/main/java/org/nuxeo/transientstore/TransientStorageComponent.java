/*
 * (C) Copyright 2015 Nuxeo SAS (http://nuxeo.com/) and contributors.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Lesser General Public License
 * (LGPL) version 2.1 which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/lgpl.html
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * Contributors:
 * Nuxeo - initial API and implementation
 */

package org.nuxeo.transientstore;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.nuxeo.runtime.model.ComponentContext;
import org.nuxeo.runtime.model.ComponentInstance;
import org.nuxeo.runtime.model.DefaultComponent;
import org.nuxeo.transientstore.api.TransientStore;
import org.nuxeo.transientstore.api.TransientStoreConfig;
import org.nuxeo.transientstore.api.TransientStoreService;

/**
 * Component exposing the {@link TransientStoreService} and managing the unerlying extension point
 *
 * @author <a href="mailto:tdelprat@nuxeo.com">Tiry</a>
 * @since 7.2
 */
public class TransientStorageComponent extends DefaultComponent implements TransientStoreService {

    protected Map<String, TransientStoreConfig> configs = new HashMap<String, TransientStoreConfig>();

    protected Map<String, TransientStore> stores = new HashMap<String, TransientStore>();

    public static final String EP_STORE = "store";

    @Override
    public TransientStore getStore(String name) {
        return stores.get(name);
    }

    @Override
    public TransientStoreConfig getStoreConfig(String name) throws IOException {
        TransientStore store = getStore(name);
        if (store != null) {
            return store.getConfig();
        }
        return null;
    }

    public TransientStore registerStore(TransientStoreConfig config) {
        TransientStore store = null;
        if (config.isCluster()) {
            store = new ClusterAwareTransientStore(config);
        } else {
            store = new SimpleTransientStore(config);
        }
        stores.put(config.getName(), store);
        return store;
    }

    @Override
    public void registerContribution(Object contribution, String extensionPoint, ComponentInstance contributor) {
        if (EP_STORE.equals(extensionPoint)) {
            TransientStoreConfig config = (TransientStoreConfig) contribution;
            // XXX merge
            configs.put(config.getName(), config);
        }
    }

    @Override
    public void applicationStarted(ComponentContext context) {
        for (TransientStoreConfig config : configs.values()) {
            registerStore(config);
        }
    }

    public void doGC() {
        for (TransientStore store : stores.values()) {
            store.doGC();
        }
    }

}

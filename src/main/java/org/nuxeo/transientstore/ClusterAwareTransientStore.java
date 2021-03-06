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

import org.nuxeo.transientstore.api.StorageEntry;
import org.nuxeo.transientstore.api.TransientStore;
import org.nuxeo.transientstore.api.TransientStoreConfig;

/**
*
* Redis implementation (i.e. Cluster Aware) implementation of the {@link TransientStore}
*
* @author <a href="mailto:tdelprat@nuxeo.com">Tiry</a>
* @since 7.2
*/

public class ClusterAwareTransientStore extends AbstractTransientStore {

    ClusterAwareTransientStore(TransientStoreConfig config) {
        super(config);
    }

    @Override
    protected void incrementStorageSize(StorageEntry entry) {
        // XXX
    }

    @Override
    protected void decrementStorageSize(StorageEntry entry) {
        // XXX
    }

    @Override
    protected long getStorageSize() {
        return 0;
    }

    @Override
    protected void setStorageSize(long newSize) {
        // XXX
    }

}

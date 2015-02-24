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

import java.util.concurrent.atomic.AtomicLong;

import org.nuxeo.transientstore.api.StorageEntry;
import org.nuxeo.transientstore.api.TransientStore;
import org.nuxeo.transientstore.api.TransientStoreConfig;

/**
 *
 * Default implementation (i.e, not Cluster Aware) implementation of the {@link TransientStore}
 *
 * @author <a href="mailto:tdelprat@nuxeo.com">Tiry</a>
 * @since 7.2
 */
public class SimpleTransientStore extends AbstractTransientStore {

    protected AtomicLong storageSize = new AtomicLong(0);

    SimpleTransientStore(TransientStoreConfig config) {
        super(config);
    }

    @Override
    protected void incrementStorageSize(StorageEntry entry) {
        storageSize.addAndGet(entry.getSize());
    }

    @Override
    protected void decrementStorageSize(StorageEntry entry) {
        storageSize.addAndGet(-entry.getSize());
    }

    @Override
    protected void setStorageSize(long newSize) {
        storageSize.set(newSize);
    }

    @Override
    protected long getStorageSize() {
        return (int) storageSize.get();
    }

    @Override
    public int getStorageSizeMB() {
        return (int) getStorageSize() / (1024 * 1024);
    }

}

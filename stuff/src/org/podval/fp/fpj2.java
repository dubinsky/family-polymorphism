package org.podval.fp;

// Introduce RootFolder and NonRootFolder types

interface fpj2 {

    // Interfaces

    interface Connection {
        RootFolder rootFolder();
    }

    interface Folder {
        boolean isRoot();
        RootFolder asRootFolder();
        NonRootFolder asNonRootFolder();
    }

    interface RootFolder extends Folder {
    }

    interface NonRootFolder extends Folder {
        Folder parent();
    }


    // GDrive

    interface GDriveConnection extends Connection {
        // GDrive-specific methods
    }

    interface GDriveFolder extends Folder {
        // GDrive-specific methods
    }


    final class GDriveConnectionImpl implements GDriveConnection {
        @Override public RootFolder rootFolder() { return null; } // Not really :)
    }

    abstract class GDriveFolderImpl implements GDriveFolder {
        // GDrive-specific implementation
    }

    final class GDriveRootFolderImpl extends GDriveFolderImpl implements RootFolder {
        @Override public boolean isRoot() { return true; }
        @Override public RootFolder asRootFolder() { return this; }
        @Override public NonRootFolder asNonRootFolder() { throw new ClassCastException(); }
    }

    final class GDriveNonRootFolderImpl extends GDriveFolderImpl implements NonRootFolder {
        public GDriveNonRootFolderImpl(final Folder parent_) {
            this.parent_ = parent_;
        }
        @Override public boolean isRoot() { return false; }
        @Override public RootFolder asRootFolder() { throw new ClassCastException(); }
        @Override public NonRootFolder asNonRootFolder() { return this; }
        @Override public Folder parent() { return parent_; }
        private final Folder parent_;
    }


    // Problem: isRoot(), as*() and parent() have to be implemented for DropBox, file system etc.
    // in exactly the same way (not concise)
}

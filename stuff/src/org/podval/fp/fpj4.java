package org.podval.fp;

// Use covariant return types (available since Java 1.5 (2004)) to make return types more precise;
// parent() needs to be pulled down the hierarchy so that it can be overridden.

interface fpj4 {

    // Interfaces

    interface Connection {
        RootFolder rootFolder();
    }

    interface Folder {
        boolean isRoot();
        RootFolder asRootFolder();
        NonRootFolder asNonRootFolder();
    }

    abstract class RootFolder implements Folder {
        @Override public final boolean isRoot() { return true; }
        @Override public final RootFolder asRootFolder() { return this; }
        @Override public final NonRootFolder asNonRootFolder() { throw new ClassCastException(); }
    }

    abstract class NonRootFolder implements Folder {
        @Override public final boolean isRoot() { return false; }
        @Override public final RootFolder asRootFolder() { throw new ClassCastException(); }
        @Override public final NonRootFolder asNonRootFolder() { return this; }
        public abstract Folder parent();
    }


    // GDrive

    interface GDriveConnection extends Connection {
        @Override GDriveRootFolderImpl rootFolder();
        // GDrive-specific methods
    }

    interface GDriveFolder extends Folder {
        // GDrive-specific methods
    }


    final class GDriveConnectionImpl implements GDriveConnection {
        @Override public GDriveRootFolderImpl rootFolder() { return null; } // Not really :)
    }

    final class GDriveRootFolderImpl extends RootFolder implements GDriveFolder {
        // GDrive-specific implementation
    }

    final class GDriveNonRootFolderImpl extends NonRootFolder implements GDriveFolder {
        public GDriveNonRootFolderImpl(final GDriveFolder parent_) {
            this.parent_ = parent_;
        }
        @Override public GDriveFolder parent() { return parent_; }
        private final GDriveFolder parent_;
        // GDrive-specific implementation
    }

    // Problem: rootFolder() and parent() have to be overridden (and parent() has to be implemented)
    // in exactly the same way for DropBox, file system etc. (not concise).

    // Problem: GDrive-specific implementation can not be shared between root and non-root folders
    // and has to be duplicated (not concise).

    // Problem: as*() return Folder, but should return specific subtype of Folder (not precise).
}

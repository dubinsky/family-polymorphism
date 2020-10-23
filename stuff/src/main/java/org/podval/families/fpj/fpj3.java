package org.podval.families.fpj;

// Push implementation of isRoot(), as*() and parent() up the hierarchy;
// Make RootFolder and NonRootFolder need to be abstract classes, not interfaces.

interface fpj3 {

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
        public NonRootFolder(final Folder parent_) {
            this.parent_ = parent_;
        }
        @Override public final boolean isRoot() { return false; }
        @Override public final RootFolder asRootFolder() { throw new ClassCastException(); }
        @Override public final NonRootFolder asNonRootFolder() { return this; }
        public final Folder parent() { return parent_; }
        private final Folder parent_;
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

    final class GDriveRootFolderImpl extends RootFolder implements GDriveFolder {
        // GDrive-specific implementation
    }

    final class GDriveNonRootFolderImpl extends NonRootFolder implements GDriveFolder {
        public GDriveNonRootFolderImpl(final Folder parent_) {
            super(parent_);
        }
        // GDrive-specific implementation
    }


    // Problem: GDrive-specific implementation can not be shared between root and non-root folders
    // and has to be duplicated (not concise).


    // Problem: GDriveNonRootFolder.parent returns Folder, but should return GDriveFolder (not precise).
}

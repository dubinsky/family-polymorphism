package org.podval.families.fpj;

// REALLY use generics: system of inequalities over types

interface fpj6 {

    // Interfaces

    interface Connection<
            C extends Connection<C,F,R,N>,
            F extends Folder<C,F,R,N>,
            R extends RootFolder<C,F,R,N>,
            N extends NonRootFolder<C,F,R,N>>
    {
        R rootFolder();
    }

    interface Folder<
            C extends Connection<C,F,R,N>,
            F extends Folder<C,F,R,N>,
            R extends RootFolder<C,F,R,N>,
            N extends NonRootFolder<C,F,R,N>>
    {
        boolean isRoot();
        R asRootFolder();
        N asNonRootFolder();
    }

    interface RootFolder<
            C extends Connection<C,F,R,N>,
            F extends Folder<C,F,R,N>,
            R extends RootFolder<C,F,R,N>,
            N extends NonRootFolder<C,F,R,N>>
            extends Folder<C,F,R,N>
    {
    }

    interface NonRootFolder<
            C extends Connection<C,F,R,N>,
            F extends Folder<C,F,R,N>,
            R extends RootFolder<C,F,R,N>,
            N extends NonRootFolder<C,F,R,N>>
            extends Folder<C,F,R,N>
    {
        F parent();
    }


    // GDrive

    interface GDriveConnection extends Connection<
            GDriveConnection,
            GDriveFolder,
            GDriveRootFolderImpl,
            GDriveNonRootFolderImpl>
    {
        // GDrive-specific methods
    }

    interface GDriveFolder extends Folder<
            GDriveConnection,
            GDriveFolder,
            GDriveRootFolderImpl,
            GDriveNonRootFolderImpl>
    {
        // GDrive-specific methods
    }


    final class GDriveConnectionImpl implements GDriveConnection {
        @Override public GDriveRootFolderImpl rootFolder() { return null; } // Not really :)
    }

    abstract class GDriveFolderImpl implements GDriveFolder {
        // GDrive-specific implementation
    }

    final class GDriveRootFolderImpl extends GDriveFolderImpl implements RootFolder<
            GDriveConnection,
            GDriveFolder,
            GDriveRootFolderImpl,
            GDriveNonRootFolderImpl>
    {
        @Override public boolean isRoot() { return true; }
        @Override public GDriveRootFolderImpl asRootFolder() { return this; }
        @Override public GDriveNonRootFolderImpl asNonRootFolder() { throw new ClassCastException(); }
    }

    final class GDriveNonRootFolderImpl extends GDriveFolderImpl implements NonRootFolder<
            GDriveConnection,
            GDriveFolder,
            GDriveRootFolderImpl,
            GDriveNonRootFolderImpl>
    {
        public GDriveNonRootFolderImpl(final GDriveFolder parent_) {
            this.parent_ = parent_;
        }
        @Override public boolean isRoot() { return false; }
        @Override public GDriveRootFolderImpl asRootFolder() { throw new ClassCastException(); }
        @Override public GDriveNonRootFolderImpl asNonRootFolder() { return this; }
        @Override public GDriveFolder parent() { return parent_; }
        private final GDriveFolder parent_;
    }


    // Problem: root and non-root parameters have to be supplied in a particular way for specific operations to be
    // available: F can not be specified (and so can not be enforced) as an upper bound on R and N (not precise).

    // Problem: type inequalities are unreadable and repetitive (not concise).

    // Problem: isRoot(), as*() and parent() have to be implemented for DropBox, file system etc.
    // in exactly the same way (not concise).
}

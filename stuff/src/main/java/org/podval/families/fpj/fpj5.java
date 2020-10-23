package org.podval.families.fpj;

// Use generics (available since Java 1.5 (2004)) to make base interface aware of the specific Folder subtype.

interface fpj5 {

    // Interfaces

    interface Connection<F extends Folder<F>> {
        F rootFolder();
    }

    interface Folder<F extends Folder<F>> {
        boolean isRoot();
        RootFolder asRootFolder();
        NonRootFolder asNonRootFolder();
    }

    abstract class RootFolder<F extends Folder<F>> implements Folder<F> {
        @Override public final boolean isRoot() { return true; }
        @Override public final RootFolder asRootFolder() { return this; }
        @Override public final NonRootFolder asNonRootFolder() { throw new ClassCastException(); }
    }

    abstract class NonRootFolder<F extends Folder<F>> implements Folder<F> {
        public NonRootFolder(final F parent_) {
            this.parent_ = parent_;
        }
        @Override public final boolean isRoot() { return false; }
        @Override public final RootFolder asRootFolder() { throw new ClassCastException(); }
        @Override public final NonRootFolder asNonRootFolder() { return this; }
        public final F parent() { return parent_; };
        private final F parent_;
    }


    // GDrive

    interface GDriveConnection extends Connection<GDriveFolder> {
        @Override GDriveRootFolderImpl rootFolder();
        // GDrive-specific methods
    }

    interface GDriveFolder extends Folder<GDriveFolder> {
        // GDrive-specific methods
    }


    final class GDriveConnectionImpl implements GDriveConnection {
        @Override public GDriveRootFolderImpl rootFolder() { return null; } // Not really :)
    }

    final class GDriveRootFolderImpl extends RootFolder<GDriveFolder> implements GDriveFolder {
        // GDrive-specific implementation
    }

    final class GDriveNonRootFolderImpl extends NonRootFolder<GDriveFolder> implements GDriveFolder {
        public GDriveNonRootFolderImpl(final GDriveFolder parent_) {
            super(parent_);
        }
        // GDrive-specific implementation
    }


    // Problem: GDrive-specific implementation can not be shared between root and non-root folders
    // and has to be duplicated (not concise).

    // Problem: as*() return Folder, but should return specific subtype of Folder (not precise).

    // Problem: Connection.rootFolder() is no longer a RootFolder subclass
    // (only interfaces can be used as upper bounds, and interfaces can't be mixed into return types)
}

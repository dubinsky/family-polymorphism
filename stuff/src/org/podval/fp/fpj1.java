package org.podval.fp;

// With interface wrapper, all code can be put in one file to simplify the presentation

interface fpj1 {

    // Interfaces

    interface Connection {
        Folder rootFolder();
    }

    interface Folder {
        boolean isRoot();
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
        public @Override Folder rootFolder() { return null; } // Not really :)
    }

    final class GDriveFolderImpl implements GDriveFolder {
        @Override public boolean isRoot() { return false; } // Not really
        @Override public Folder parent() { return null; } // Not really :)
    }


    // Problem: parent() is defined on the root folder (not precise)
}

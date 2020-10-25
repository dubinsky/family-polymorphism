package org.podval.families.fps

// Use traits as scopes to define type members so that they apply across the family and self-types
// to make types precise and eliminate casts.
// Use a little type arithmetic to make code more concise.

// Note: this scope is not just for presentation!

trait StorageSystem {
  type C <: Connection
  type F <: Folder

  final type RF = F with RootFolder
  final type NF = F with NonRootFolder

  trait Connection {
    def rootFolder: RF
  }

  trait Folder {
    def isRoot: Boolean
    def asRootFolder: RF
    def asNonRootFolder: NF
  }

  trait RootFolder extends Folder { this: F =>
    final override def isRoot: Boolean = true
    final override def asRootFolder: RF = this
    final override def asNonRootFolder: NF = throw new ClassCastException
  }

  trait NonRootFolder extends Folder { this: F =>
    final override def isRoot: Boolean = false
    final override def asRootFolder: RF = throw new ClassCastException
    final override def asNonRootFolder: NF = this
    val parent: F
  }
}


object GDriveStorageSystem extends StorageSystem {
  override type C = GDriveConnection
  override type F = GDriveFolder

  trait GDriveConnection extends Connection {
    // GDrive-specific methods
  }

  trait GDriveFolder extends Folder {
    // GDrive-specific methods
  }


  final class GDriveConnectionImpl extends GDriveConnection {
    def rootFolder: GDriveRootFolderImpl = null // Not really :)
  }

  trait GDriveFolderImpl extends GDriveFolder {
    // GDrive-specific implementation
  }

  final class GDriveRootFolderImpl extends GDriveFolderImpl with RootFolder
  final class GDriveNonRootFolderImpl(override val parent: GDriveFolder) extends GDriveFolderImpl with NonRootFolder
}


// [Problem: parent parameter has to be overridden in a specific way (traits do not take parameters... yet).]


// Problem: all the types of the the family have to be declared (and defined) in one scope (hence, in one file).

package org.podval.families.fps

// Use type members to make code more readable.

object fps4 {

  // Interfaces

  trait Connection {
    type C <: Connection
    type F <: Folder

    def rootFolder: F with RootFolder
  }

  trait Folder {
    type C <: Connection
    type F <: Folder

    def isRoot: Boolean

    def asRootFolder: F with RootFolder

    def asNonRootFolder: F with NonRootFolder
  }

  trait RootFolder extends Folder {
    type C <: Connection
    type F <: Folder

    final override def isRoot: Boolean = true

    final override def asRootFolder: F with RootFolder = this.asInstanceOf[F with RootFolder]

    final override def asNonRootFolder: F with NonRootFolder = throw new ClassCastException
  }

  trait NonRootFolder extends Folder {
    type C <: Connection
    type F <: Folder

    final override def isRoot: Boolean = false

    final override def asRootFolder: F with RootFolder = throw new ClassCastException

    final override def asNonRootFolder: F with NonRootFolder = this.asInstanceOf[F with NonRootFolder]

    val parent: F
  }


  // GDrive

  trait GDriveConnection extends Connection {
    override type C = GDriveConnection
    override type F = GDriveFolder
    // GDrive-specific methods
  }

  trait GDriveFolder extends Folder {
    override type C = GDriveConnection
    override type F = GDriveFolder
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


  // Problem: casts are needed in as*() methods (not precise).


  // [Problem: in more complicated cases, selection returns types that are too general (not precise).]
}
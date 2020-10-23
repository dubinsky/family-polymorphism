package org.podval.families.fps

// Use mix-ins with self-types to cut down number of type parameters (and remove casts).

object fps3 {

  // Interfaces

  trait Connection[
    C <: Connection[C, F],
    F <: Folder[C, F]] {
    def rootFolder: F with RootFolder[C, F]
  }

  trait Folder[
    C <: Connection[C, F],
    F <: Folder[C, F]] {
    this: F =>
    def isRoot: Boolean

    def asRootFolder: F with RootFolder[C, F]

    def asNonRootFolder: F with NonRootFolder[C, F]
  }

  trait RootFolder[
    C <: Connection[C, F],
    F <: Folder[C, F]] extends Folder[C, F] {
    this: F with RootFolder[C, F] =>
    final override def isRoot: Boolean = true

    final override def asRootFolder: F with RootFolder[C, F] = this

    final override def asNonRootFolder: F with NonRootFolder[C, F] = throw new ClassCastException
  }

  trait NonRootFolder[
    C <: Connection[C, F],
    F <: Folder[C, F]] extends Folder[C, F] {
    this: F with NonRootFolder[C, F] =>
    final override def isRoot: Boolean = false

    final override def asRootFolder: F with RootFolder[C, F] = throw new ClassCastException

    final override def asNonRootFolder: F with NonRootFolder[C, F] = this

    val parent: F
  }


  // GDrive

  trait GDriveConnection extends Connection[GDriveConnection, GDriveFolder] {
    // GDrive-specific methods
  }

  trait GDriveFolder extends Folder[GDriveConnection, GDriveFolder] {
    // GDrive-specific methods
  }


  final class GDriveConnectionImpl extends GDriveConnection {
    def rootFolder: GDriveRootFolderImpl = null // Not really :)
  }

  trait GDriveFolderImpl extends GDriveFolder {
    // GDrive-specific implementation
  }

  final class GDriveRootFolderImpl extends GDriveFolderImpl
    with RootFolder[GDriveConnection, GDriveFolder]

  final class GDriveNonRootFolderImpl(override val parent: GDriveFolder) extends GDriveFolderImpl
    with NonRootFolder[GDriveConnection, GDriveFolder]


  // Problem: type inequalities are unreadable and repetitive (not concise).

  // [Problem: in more complicated cases, casts are needed (not precise).]
}
package org.podval.families.fps

// Scala translation of fpj6

object fps1 {


  // Interfaces

  trait Connection[
    C <: Connection[C, F, R, N],
    F <: Folder[C, F, R, N],
    R <: Folder[C, F, R, N] with RootFolder[C, F, R, N],
    N <: Folder[C, F, R, N] with NonRootFolder[C, F, R, N]] {
    def rootFolder: R
  }

  trait Folder[
    C <: Connection[C, F, R, N],
    F <: Folder[C, F, R, N],
    R <: Folder[C, F, R, N] with RootFolder[C, F, R, N],
    N <: Folder[C, F, R, N] with NonRootFolder[C, F, R, N]] {
    def isRoot: Boolean

    def asRootFolder: R

    def asNonRootFolder: N
  }

  trait RootFolder[
    C <: Connection[C, F, R, N],
    F <: Folder[C, F, R, N],
    R <: Folder[C, F, R, N] with RootFolder[C, F, R, N],
    N <: Folder[C, F, R, N] with NonRootFolder[C, F, R, N]] extends Folder[C, F, R, N] {
  }

  trait NonRootFolder[
    C <: Connection[C, F, R, N],
    F <: Folder[C, F, R, N],
    R <: Folder[C, F, R, N] with RootFolder[C, F, R, N],
    N <: Folder[C, F, R, N] with NonRootFolder[C, F, R, N]] extends Folder[C, F, R, N] {
    def parent: F
  }


  // GDrive

  trait GDriveConnection extends Connection[GDriveConnection, GDriveFolder, GDriveRootFolderImpl, GDriveNonRootFolderImpl] {
    // GDrive-specific methods
  }

  trait GDriveFolder extends Folder[GDriveConnection, GDriveFolder, GDriveRootFolderImpl, GDriveNonRootFolderImpl] {
    // GDrive-specific methods
  }

  final class GDriveConnectionImpl extends GDriveConnection {
    def rootFolder: GDriveRootFolderImpl = null // Not really :)
  }

  abstract class GDriveFolderImpl extends GDriveFolder {
    // GDrive-specific implementation
  }

  final class GDriveRootFolderImpl extends GDriveFolderImpl
    with RootFolder[GDriveConnection, GDriveFolder, GDriveRootFolderImpl, GDriveNonRootFolderImpl] {
    def isRoot: Boolean = true

    def asRootFolder: GDriveRootFolderImpl = this

    def asNonRootFolder: GDriveNonRootFolderImpl = throw new ClassCastException
  }

  final class GDriveNonRootFolderImpl(override val parent: GDriveFolder) extends GDriveFolderImpl
    with NonRootFolder[GDriveConnection, GDriveFolder, GDriveRootFolderImpl, GDriveNonRootFolderImpl] {
    def isRoot: Boolean = false

    def asRootFolder: GDriveRootFolderImpl = throw new ClassCastException

    def asNonRootFolder: GDriveNonRootFolderImpl = this
  }


  // Problem: root and non-root parameters have to be supplied in a particular way for specific operations to be
  // available: F can not be specified as a bound on R and N (not precise).

  // Problem: type inequalities are unreadable and repetitive (not concise).

  // Problem: isRoot(), as*() and parent() have to be implemented for DropBox, file system etc.
  // in exactly the same way (not concise).
}
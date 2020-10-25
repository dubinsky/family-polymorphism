package org.podval.families.fps

// Use traits with method/member definitions to cut down duplications and mark parent as immutable.

object fps2 {

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
    final override def isRoot: Boolean = true

    final override def asRootFolder: R = this.asInstanceOf[R]

    final override def asNonRootFolder: N = throw new ClassCastException
  }

  trait NonRootFolder[
    C <: Connection[C, F, R, N],
    F <: Folder[C, F, R, N],
    R <: Folder[C, F, R, N] with RootFolder[C, F, R, N],
    N <: Folder[C, F, R, N] with NonRootFolder[C, F, R, N]] extends Folder[C, F, R, N] {
    final override def isRoot: Boolean = false

    final override def asRootFolder: R = throw new ClassCastException

    final override def asNonRootFolder: N = this.asInstanceOf[N]

    val parent: F
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
  }

  final class GDriveNonRootFolderImpl(override val parent: GDriveFolder) extends GDriveFolderImpl
    with NonRootFolder[GDriveConnection, GDriveFolder, GDriveRootFolderImpl, GDriveNonRootFolderImpl] {
  }

  // Problem: root and non-root parameters have to be supplied in a particular way for specific operations to be
  // available: F can not be specified as a bound on R and N (not precise).

  // Problem: type inequalities are unreadable and repetitive (not concise).

  // Problem: Casts are necessary in the as*() methods (not precise).
}
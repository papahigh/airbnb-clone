package storage.filesystem

import storage.BlobStorage

fun fileSystemBlobStorage(init: FileSystemBlobStorage.Builder.() -> Unit): BlobStorage {
    return FileSystemBlobStorage.builder().also(init).build()
}

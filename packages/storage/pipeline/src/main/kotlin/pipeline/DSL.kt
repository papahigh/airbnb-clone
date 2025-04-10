package pipeline

fun tempBlob(fileName: String, init: TempBlob.Builder.() -> Unit): TempBlob {
    return TempBlob.builder(fileName).apply(init).build()
}

package search.dsl.compose

import search.dsl.DslBuilder


class BranchDslBuilder<T> internal constructor(
    private val options: Options<T>
) : DslBuilder<T> {

    override fun build(input: T) = if (options.testIf(input)) {
        options.thenDo.build(input)
    } else {
        options.orElse?.build(input)
    }

    class Options<T> {
        var testIf = { _: T -> true }
        var thenDo = DslBuilder.noop<T>()
        var orElse: DslBuilder<T>? = null
    }
}

fun <T> branch(fn: BranchDslBuilder.Options<T>.() -> Unit): DslBuilder<T> {
    return BranchDslBuilder(BranchDslBuilder.Options<T>().apply(fn))
}

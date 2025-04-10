package search.dsl.compose

import search.dsl.DslBuilder


class BranchDslBuilder<Props> internal constructor(
    private val options: Options<Props>
) : DslBuilder<Props> {

    override fun build(props: Props) = if (options.testIf(props)) {
        options.thenDo.build(props)
    } else {
        options.orElse?.build(props)
    }

    class Options<Props> {
        var testIf = { _: Props -> true }
        var thenDo = DslBuilder.noop<Props>()
        var orElse: DslBuilder<Props>? = null
    }
}

fun <Props> branch(init: BranchDslBuilder.Options<Props>.() -> Unit): DslBuilder<Props> {
    return BranchDslBuilder(BranchDslBuilder.Options<Props>().apply(init))
}

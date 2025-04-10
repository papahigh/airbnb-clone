package search.dsl.compose

import org.json.JSONObject
import search.dsl.DslBuilder


class NamedDslBuilder<Props> internal constructor(
    private val options: Options<Props>,
) : DslBuilder<Props> {

    override fun build(props: Props): JSONObject? {
        return options.then.build(props)?.let { JSONObject().put(options.name, it) }
    }

    class Options<Props> {
        var name: String = "undefined"
        var then: DslBuilder<Props> = DslBuilder.noop()
    }
}

fun <Props> named(init: NamedDslBuilder.Options<Props>.() -> Unit): DslBuilder<Props> {
    return NamedDslBuilder(NamedDslBuilder.Options<Props>().apply(init))
}

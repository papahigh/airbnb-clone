package search.dsl.compose

import org.json.JSONObject
import search.dsl.DslBuilder


class NamedDslBuilder<T> internal constructor(private val options: Options<T>) : DslBuilder<T> {
    override fun build(input: T): JSONObject? {
        return options.then.build(input)?.let { JSONObject().put(options.name, it) }
    }

    class Options<T> {
        var name: String = "undefined"
        var then: DslBuilder<T> = DslBuilder.noop()
    }
}

fun <T> named(fn: NamedDslBuilder.Options<T>.() -> Unit): DslBuilder<T> {
    return NamedDslBuilder(NamedDslBuilder.Options<T>().apply(fn))
}

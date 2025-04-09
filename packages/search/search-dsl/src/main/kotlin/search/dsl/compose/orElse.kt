package search.dsl.compose

import org.json.JSONObject
import search.dsl.DslBuilder


fun <T> DslBuilder<T>.orElse(fallback: DslBuilder<T>): DslBuilder<T> {
    val target = this
    return object : DslBuilder<T> {
        override fun build(input: T): JSONObject? {
            return target.build(input) ?: fallback.build(input)
        }
    }
}

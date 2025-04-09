package search.dsl.query.term

import org.json.JSONObject
import search.dsl.DslBuilder


class IdsQueryDslBuilder<T> internal constructor(private val options: Options<T>) : DslBuilder<T> {

    override fun build(input: T) = options.values(input)?.let {
        if (it.isNotEmpty()) {
            JSONObject()
                .put(
                    "ids", JSONObject()
                        .put("values", it)
                        .put("boost", options.boost)
                )
        } else null
    }

    class Options<T> {
        var values: (input: T) -> Collection<*>? = { null }
        var boost: Double = 1.0
    }
}

// https://www.elastic.co/guide/en/elasticsearch/reference/current/query-dsl-ids-query.html
// https://opensearch.org/docs/latest/query-dsl/term/ids/
fun <T> ids(fn: IdsQueryDslBuilder.Options<T>.() -> Unit): DslBuilder<T> {
    return IdsQueryDslBuilder(IdsQueryDslBuilder.Options<T>().apply(fn))
}

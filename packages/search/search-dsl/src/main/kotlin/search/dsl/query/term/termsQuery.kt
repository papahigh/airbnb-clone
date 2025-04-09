package search.dsl.query.term

import org.json.JSONObject
import search.dsl.DslBuilder


class TermsQueryDslBuilder<T> internal constructor(private val options: Options<T>) : DslBuilder<T> {

    override fun build(input: T) = options.values(input)?.let {
        if (it.isNotEmpty()) {
            JSONObject().put(
                "terms", JSONObject()
                    .put(options.field, it)
                    .put("boost", options.boost)
            )
        } else null
    }

    class Options<T> {
        var values: (input: T) -> Collection<*>? = { null }
        var field: String = "undefined"
        var boost: Double = 1.0
    }
}

// https://www.elastic.co/guide/en/elasticsearch/reference/current/query-dsl-terms-query.html
// https://opensearch.org/docs/latest/query-dsl/term/terms/
fun <T> terms(fn: TermsQueryDslBuilder.Options<T>.() -> Unit): DslBuilder<T> {
    return TermsQueryDslBuilder(TermsQueryDslBuilder.Options<T>().apply(fn))
}

package search.dsl.query.term

import org.json.JSONObject
import search.dsl.DslBuilder


class TermQueryDslBuilder<T> internal constructor(private val options: Options<T>) : DslBuilder<T> {

    override fun build(input: T) = options.value(input)?.let {
        JSONObject().put(
            "term", JSONObject()
                .put("value", it)
                .put("boost", options.boost)
                .put("case_insensitive", options.caseInsensitive)
        )
    }

    class Options<T> {
        var value: (input: T) -> String? = { "undefined" }
        var boost: Double = 1.0
        var caseInsensitive = false
    }
}

// https://www.elastic.co/guide/en/elasticsearch/reference/current/query-dsl-term-query.html
// https://opensearch.org/docs/latest/query-dsl/term/term/
fun <T> term(fn: TermQueryDslBuilder.Options<T>.() -> Unit): DslBuilder<T> {
    return TermQueryDslBuilder(TermQueryDslBuilder.Options<T>().apply(fn))
}

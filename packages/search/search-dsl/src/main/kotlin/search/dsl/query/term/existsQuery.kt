package search.dsl.query.term

import org.json.JSONObject
import search.dsl.DslBuilder


class ExistsQueryDslBuilder<T> internal constructor(val options: Options) : DslBuilder<T> {
    override fun build(input: T): JSONObject? {
        return JSONObject()
            .put(
                "exists", JSONObject()
                    .put("field", options.field)
                    .put("boost", options.boost)
            )
    }

    class Options {
        var field: String = "undefined"
        var boost: Double = 1.0
    }
}


// https://www.elastic.co/guide/en/elasticsearch/reference/current/query-dsl-exists-query.html
// https://opensearch.org/docs/latest/query-dsl/term/exists
fun <T> exists(fn: ExistsQueryDslBuilder.Options.() -> Unit): DslBuilder<T> {
    return ExistsQueryDslBuilder(ExistsQueryDslBuilder.Options().apply(fn))
}

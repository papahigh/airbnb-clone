package search.dsl.query.term

import org.json.JSONObject
import search.dsl.DslBuilder


// https://www.elastic.co/guide/en/elasticsearch/reference/current/query-dsl-exists-query.html
// https://opensearch.org/docs/latest/query-dsl/term/exists
class ExistsQueryDslBuilder<Props> internal constructor(
    private val options: Options
) : DslBuilder<Props> {

    override fun build(props: Props): JSONObject? {
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

fun <Props> exists(init: ExistsQueryDslBuilder.Options.() -> Unit): DslBuilder<Props> {
    return ExistsQueryDslBuilder(ExistsQueryDslBuilder.Options().apply(init))
}

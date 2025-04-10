package search.dsl.query.term

import org.json.JSONObject
import search.dsl.DslBuilder


// https://www.elastic.co/guide/en/elasticsearch/reference/current/query-dsl-term-query.html
// https://opensearch.org/docs/latest/query-dsl/term/term/
class TermQueryDslBuilder<Props> internal constructor(
    private val options: Options<Props>
) : DslBuilder<Props> {

    override fun build(props: Props) = options.value(props)?.let {
        JSONObject().put(
            "term", JSONObject()
                .put(
                    options.field, JSONObject()
                        .put("value", it)
                        .put("boost", options.boost)
                        .put("case_insensitive", options.caseInsensitive)
                )
        )
    }

    class Options<Props> {
        var field: String = "undefined"
        var boost: Double = 1.0
        var value: (props: Props) -> String? = { null }
        var caseInsensitive = false
    }
}

fun <Props> term(init: TermQueryDslBuilder.Options<Props>.() -> Unit): DslBuilder<Props> {
    return TermQueryDslBuilder(TermQueryDslBuilder.Options<Props>().apply(init))
}

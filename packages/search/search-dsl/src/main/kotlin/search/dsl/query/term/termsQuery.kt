package search.dsl.query.term

import org.json.JSONObject
import search.dsl.DslBuilder


// https://www.elastic.co/guide/en/elasticsearch/reference/current/query-dsl-terms-query.html
// https://opensearch.org/docs/latest/query-dsl/term/terms/
class TermsQueryDslBuilder<Props> internal constructor(
    private val options: Options<Props>
) : DslBuilder<Props> {

    override fun build(props: Props) = options.values(props)?.let {
        if (it.isNotEmpty()) {
            JSONObject().put(
                "terms", JSONObject()
                    .put(options.field, it)
                    .put("boost", options.boost)
            )
        } else null
    }

    class Options<Props> {
        var field: String = "undefined"
        var boost: Double = 1.0
        var values: (props: Props) -> Collection<*>? = { null }
    }
}

fun <Props> terms(init: TermsQueryDslBuilder.Options<Props>.() -> Unit): DslBuilder<Props> {
    return TermsQueryDslBuilder(TermsQueryDslBuilder.Options<Props>().apply(init))
}

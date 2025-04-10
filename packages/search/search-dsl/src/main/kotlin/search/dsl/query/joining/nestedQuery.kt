package search.dsl.query.joining

import org.json.JSONObject
import search.dsl.DslBuilder


// https://www.elastic.co/guide/en/elasticsearch/reference/current/query-dsl-nested-query.html
// https://opensearch.org/docs/latest/query-dsl/joining/nested/
class NestedQueryDslBuilder<Props> internal constructor(
    private val options: Options<Props>,
) : DslBuilder<Props> {

    override fun build(props: Props) = options.query.build(props)?.let { query ->
        JSONObject()
            .put(
                "nested", JSONObject()
                    .put("path", options.path)
                    .put("query", query)
                    .put("ignore_unmapped", options.ignoreUnmapped)
            )
    }

    class Options<Props> {
        var path: String = "undefined"
        var query: DslBuilder<Props> = DslBuilder.noop()
        var ignoreUnmapped = false
    }
}

fun <Props> nested(init: NestedQueryDslBuilder.Options<Props>.() -> Unit): DslBuilder<Props> {
    return NestedQueryDslBuilder(NestedQueryDslBuilder.Options<Props>().apply(init))
}

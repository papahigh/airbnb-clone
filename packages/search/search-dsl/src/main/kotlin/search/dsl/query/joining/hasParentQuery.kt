package search.dsl.query.joining

import org.json.JSONObject
import search.dsl.DslBuilder


// https://www.elastic.co/guide/en/elasticsearch/reference/current/query-dsl-has-parent-query.html
// https://opensearch.org/docs/latest/query-dsl/joining/has-parent/
class HasParentQueryDslBuilder<Props> internal constructor(
    private val options: Options<Props>,
) : DslBuilder<Props> {

    override fun build(props: Props) = options.query.build(props)?.let { query ->
        JSONObject()
            .put(
                "has_parent", JSONObject()
                    .put("query", query)
                    .put("parent_type", options.type)
                    .put("score", options.score)
                    .put("ignore_unmapped", options.ignoreUnmapped)
            )
    }

    class Options<Props> {
        var type = "undefined"
        var query: DslBuilder<Props> = DslBuilder.noop()
        var score = false
        var ignoreUnmapped = false
    }
}

fun <Props> hasParent(init: HasParentQueryDslBuilder.Options<Props>.() -> Unit): DslBuilder<Props> {
    return HasParentQueryDslBuilder(HasParentQueryDslBuilder.Options<Props>().apply(init))
}

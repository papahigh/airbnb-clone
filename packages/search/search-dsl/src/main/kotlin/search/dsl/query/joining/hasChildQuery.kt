package search.dsl.query.joining

import org.json.JSONObject
import search.dsl.DslBuilder


// https://www.elastic.co/guide/en/elasticsearch/reference/current/query-dsl-has-child-query.html
// https://opensearch.org/docs/latest/query-dsl/joining/has-child/
class HasChildQueryDslBuilder<Props> internal constructor(
    private val options: Options<Props>,
) : DslBuilder<Props> {

    override fun build(props: Props) = options.query.build(props)?.let { query ->
        JSONObject()
            .put(
                "has_child", JSONObject()
                    .put("query", query)
                    .put("type", options.type)
                    .put("max_children", options.maxChildren(props))
                    .put("min_children", options.minChildren(props))
                    .put("ignore_unmapped", options.ignoreUnmapped)
            )
    }

    class Options<Props> {
        var query: DslBuilder<Props> = DslBuilder.noop()
        var type = "undefined"
        var maxChildren: (props: Props) -> Int? = { null }
        var minChildren: (props: Props) -> Int? = { null }
        var ignoreUnmapped = false
    }
}

fun <Props> hasChild(init: HasChildQueryDslBuilder.Options<Props>.() -> Unit): DslBuilder<Props> {
    return HasChildQueryDslBuilder(HasChildQueryDslBuilder.Options<Props>().apply(init))
}

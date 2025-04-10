package search.dsl.query.joining

import org.json.JSONObject
import search.dsl.DslBuilder


// https://www.elastic.co/guide/en/elasticsearch/reference/current/query-dsl-parent-id-query.html
// https://opensearch.org/docs/latest/query-dsl/joining/parent-id/
class ParentQueryDslBuilder<Props> internal constructor(
    private val options: Options<Props>,
) : DslBuilder<Props> {

    override fun build(props: Props) = options.id(props)?.let { id ->
        JSONObject()
            .put(
                "parent_id", JSONObject()
                    .put("id", id)
                    .put("type", options.type)
                    .put("ignore_unmapped", options.ignoreUnmapped)
            )
    }

    class Options<Props> {
        var type: String = "undefined"
        var id: (props: Props) -> Any? = { null }
        var ignoreUnmapped = false
    }
}

fun <Props> parentId(init: ParentQueryDslBuilder.Options<Props>.() -> Unit): DslBuilder<Props> {
    return ParentQueryDslBuilder(ParentQueryDslBuilder.Options<Props>().apply(init))
}

package search.dsl.query.term

import org.json.JSONObject
import search.dsl.DslBuilder


// https://www.elastic.co/guide/en/elasticsearch/reference/current/query-dsl-ids-query.html
// https://opensearch.org/docs/latest/query-dsl/term/ids/
class IdsQueryDslBuilder<Props> internal constructor(
    private val options: Options<Props>
) : DslBuilder<Props> {

    override fun build(props: Props) = options.values(props)?.let {
        if (it.isNotEmpty()) {
            JSONObject()
                .put(
                    "ids", JSONObject()
                        .put("values", it)
                        .put("boost", options.boost)
                )
        } else null
    }

    class Options<Props> {
        var values: (props: Props) -> Collection<*>? = { null }
        var boost: Double = 1.0
    }
}

fun <Props> ids(init: IdsQueryDslBuilder.Options<Props>.() -> Unit): DslBuilder<Props> {
    return IdsQueryDslBuilder(IdsQueryDslBuilder.Options<Props>().apply(init))
}

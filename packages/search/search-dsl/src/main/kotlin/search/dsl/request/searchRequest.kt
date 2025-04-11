package search.dsl.request

import org.json.JSONObject
import search.dsl.DslBuilder


// https://www.elastic.co/guide/en/elasticsearch/reference/current/search-your-data.html
// https://opensearch.org/docs/latest/search-plugins/searching-data/index/
class SearchRequestDslBuilder<Props> internal constructor(
    private val options: Options<Props>,
) : DslBuilder<Props> {
    override fun build(props: Props): JSONObject {
        return JSONObject()
            .put("query", options.query.build(props) ?: MATCH_ALL)
            .put("from", options.from(props))
            .put("size", options.size(props))
    }

    class Options<Props> {
        var query: DslBuilder<Props> = DslBuilder.noop()
        var from: (props: Props) -> Int? = { null }
        var size: (props: Props) -> Int? = { null }
    }

    companion object {
        private val MATCH_ALL = JSONObject().put("match_all", JSONObject())
    }
}

fun <Props> searchRequest(init: SearchRequestDslBuilder.Options<Props>.() -> Unit): SearchRequestDslBuilder<Props> {
    return SearchRequestDslBuilder(SearchRequestDslBuilder.Options<Props>().apply(init))
}

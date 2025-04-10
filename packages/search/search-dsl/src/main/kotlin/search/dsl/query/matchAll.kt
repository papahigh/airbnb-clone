package search.dsl.query

import org.json.JSONObject
import search.dsl.DslBuilder


// https://www.elastic.co/guide/en/elasticsearch/reference/current/query-dsl-match-all-query.html
// https://opensearch.org/docs/latest/query-dsl/match-all/
fun <Props> matchAll(): DslBuilder<Props> = object : DslBuilder<Props> {
    override fun build(props: Props): JSONObject? {
        return JSONObject().put("match_all", JSONObject())
    }
}


// https://www.elastic.co/guide/en/elasticsearch/reference/current/query-dsl-match-all-query.html
// https://opensearch.org/docs/latest/query-dsl/match-all/
fun <Props> matchNone(): DslBuilder<Props> = object : DslBuilder<Props> {
    override fun build(props: Props): JSONObject? {
        return JSONObject().put("match_none", JSONObject())
    }
}

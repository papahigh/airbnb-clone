package search.dsl.query

import org.json.JSONObject
import search.dsl.DslBuilder


internal class MatchAllQueryDslBuilder<T> : DslBuilder<T> {
    override fun build(input: T): JSONObject? {
        return JSONObject().put("match_all", JSONObject())
    }
}

// https://opensearch.org/docs/latest/query-dsl/match-all/
fun <T> matchAll(): DslBuilder<T> = MatchAllQueryDslBuilder<T>()


internal class MatchNoneQueryDslBuilder<T> : DslBuilder<T> {
    override fun build(input: T): JSONObject? {
        return JSONObject().put("match_none", JSONObject())
    }
}

// https://opensearch.org/docs/latest/query-dsl/match-all/
fun <T> matchNone(): DslBuilder<T> = MatchNoneQueryDslBuilder<T>()

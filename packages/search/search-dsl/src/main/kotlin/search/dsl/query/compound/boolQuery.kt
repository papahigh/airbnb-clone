package search.dsl.query.compound

import org.json.JSONArray
import org.json.JSONObject
import search.dsl.DslBuilder


// https://opensearch.org/docs/latest/query-dsl/compound/bool/
// https://www.elastic.co/guide/en/elasticsearch/reference/current/query-dsl-bool-query.html
class BoolQueryDslBuilder<Props> internal constructor(private val options: Options<Props>) : DslBuilder<Props> {

    override fun build(props: Props): JSONObject? {

        var bool = TouchableJSONObject()
            .putOpt("filter", build(props, options.filter))
            .putOpt("should", build(props, options.should))
            .putOpt("must_not", build(props, options.mustNot))
            .putOpt("must", build(props, options.must))

        return if (bool.touched) {
            JSONObject()
                .put(
                    "bool", bool
                        .put("boost", options.boost)
                        .put("minimum_should_match", options.minimumShouldMatch)
                )
        } else null
    }

    private fun build(props: Props, builders: MutableList<DslBuilder<Props>>): JSONArray? {
        return builders.mapNotNull { it.build(props) }.takeIf { it.isNotEmpty() }?.let { JSONArray(it) }
    }

    private class TouchableJSONObject : JSONObject() {
        var touched = false
        override fun putOpt(key: String, value: Any?): TouchableJSONObject {
            if (value != null) {
                put(key, value)
                touched = true
            }
            return this
        }
    }

    class Options<R> {
        var boost: Double? = null
        var minimumShouldMatch: Int? = null

        var must = mutableListOf<DslBuilder<R>>()
        var mustNot = mutableListOf<DslBuilder<R>>()
        var should = mutableListOf<DslBuilder<R>>()
        var filter = mutableListOf<DslBuilder<R>>()

        fun must(fn: () -> DslBuilder<R>) = must.add(fn())
        fun mustNot(fn: () -> DslBuilder<R>) = mustNot.add(fn())
        fun should(fn: () -> DslBuilder<R>) = should.add(fn())
        fun filter(fn: () -> DslBuilder<R>) = filter.add(fn())
    }
}


fun <R> bool(fn: BoolQueryDslBuilder.Options<R>.() -> Unit): DslBuilder<R> {
    return BoolQueryDslBuilder<R>(BoolQueryDslBuilder.Options<R>().apply(fn))
}

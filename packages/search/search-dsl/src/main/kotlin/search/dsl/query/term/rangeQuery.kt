package search.dsl.query.term

import org.json.JSONObject
import search.dsl.DslBuilder


class RangeQueryDslBuilder<T> internal constructor(private val options: Options<T>) : DslBuilder<T> {

    override fun build(input: T): JSONObject? {

        val field = TouchableJSONObject()
            .putOpt("gt", options.gt?.invoke(input))
            .putOpt("gte", options.gte?.invoke(input))
            .putOpt("lt", options.lt?.invoke(input))
            .putOpt("lte", options.lte?.invoke(input))

        return if (field.touched)
            JSONObject().put(
                "range", JSONObject()
                    .put(
                        options.field, field
                            .putOpt("boost", options.boost)
                            .putOpt("format", options.format)
                            .putOpt("relation", options.relation)
                            .putOpt("time_zone", options.timeZone)
                    )
            ) else null
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

    class Options<T> {
        var field = "undefined"
        var boost: Double = 1.0
        var gt: ((input: T) -> Any)? = null
        var gte: ((input: T) -> Any)? = null
        var lt: ((input: T) -> Any)? = null
        var lte: ((input: T) -> Any)? = null
        var format: String? = null
        var relation: String? = null
        var timeZone: String? = null
    }
}

// https://www.elastic.co/guide/en/elasticsearch/reference/current/query-dsl-range-query.html
// https://opensearch.org/docs/latest/query-dsl/term/range/
fun <T> range(fn: RangeQueryDslBuilder.Options<T>.() -> Unit): DslBuilder<T> {
    return RangeQueryDslBuilder(RangeQueryDslBuilder.Options<T>().apply(fn))
}

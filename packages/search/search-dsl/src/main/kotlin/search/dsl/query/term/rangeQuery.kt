package search.dsl.query.term

import org.json.JSONObject
import search.dsl.DslBuilder


// https://www.elastic.co/guide/en/elasticsearch/reference/current/query-dsl-range-query.html
// https://opensearch.org/docs/latest/query-dsl/term/range/
class RangeQueryDslBuilder<Props> internal constructor(
    private val options: Options<Props>
) : DslBuilder<Props> {

    override fun build(props: Props): JSONObject? {

        val field = TouchableJSONObject()
            .putOpt("gt", options.gt?.invoke(props))
            .putOpt("gte", options.gte?.invoke(props))
            .putOpt("lt", options.lt?.invoke(props))
            .putOpt("lte", options.lte?.invoke(props))

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

    class Options<Props> {
        var field = "undefined"
        var boost: Double = 1.0
        var gt: ((props: Props) -> Any?)? = null
        var gte: ((props: Props) -> Any?)? = null
        var lt: ((props: Props) -> Any?)? = null
        var lte: ((props: Props) -> Any?)? = null
        var format: String? = null
        var relation: String? = null
        var timeZone: String? = null
    }
}

fun <Props> range(init: RangeQueryDslBuilder.Options<Props>.() -> Unit): DslBuilder<Props> {
    return RangeQueryDslBuilder(RangeQueryDslBuilder.Options<Props>().apply(init))
}

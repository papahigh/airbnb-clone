package search.dsl.query.geo

import org.json.JSONObject
import search.dsl.DslBuilder
import search.dsl.GeoBoundingBox


// https://www.elastic.co/guide/en/elasticsearch/reference/current/query-dsl-geo-bounding-box-query.html
// https://opensearch.org/docs/latest/query-dsl/geo-and-xy/geo-bounding-box/
class GeoBoundingBoxQueryDslBuilder<Props> internal constructor(
    private val options: Options<Props>
) : DslBuilder<Props> {

    override fun build(props: Props) = options.value(props)?.let { bbox ->
        JSONObject()
            .put(
                "geo_bounding_box", JSONObject()
                    .put(options.field, bbox.toJSON())
                    .put("ignore_unmapped", options.ignoreUnmapped)
            )
    }

    class Options<Props> {
        var field: String = "undefined"
        var value: (props: Props) -> GeoBoundingBox? = { null }
        var ignoreUnmapped = false
    }
}

fun <Props> geoBoundingBox(init: GeoBoundingBoxQueryDslBuilder.Options<Props>.() -> Unit): DslBuilder<Props> {
    return GeoBoundingBoxQueryDslBuilder(GeoBoundingBoxQueryDslBuilder.Options<Props>().apply(init))
}

package search.dsl.query.geo

import org.json.JSONObject
import search.dsl.DslBuilder
import search.dsl.GeoPoint


// https://www.elastic.co/guide/en/elasticsearch/reference/current/query-dsl-geo-distance-query.html
// https://opensearch.org/docs/latest/query-dsl/geo-and-xy/geodistance/
class GeoDistanceQueryDslBuilder<Props> internal constructor(
    private val options: Options<Props>,
) : DslBuilder<Props> {

    override fun build(props: Props) = options.location(props)?.let { point ->
        options.distance(props)?.let { distance ->
            JSONObject()
                .put(
                    "geo_distance", JSONObject()
                        .put(options.field, point.toJSON())
                        .put("distance", distance)
                        .put("ignore_unmapped", options.ignoreUnmapped)
                )
        }
    }

    class Options<Props> {
        var field: String = "undefined"
        var location: (props: Props) -> GeoPoint? = { null }
        var distance: (props: Props) -> String? = { "undefined" }
        var ignoreUnmapped = false
    }
}

fun <Props> geoDistance(init: GeoDistanceQueryDslBuilder.Options<Props>.() -> Unit): DslBuilder<Props> {
    return GeoDistanceQueryDslBuilder(GeoDistanceQueryDslBuilder.Options<Props>().apply(init))
}

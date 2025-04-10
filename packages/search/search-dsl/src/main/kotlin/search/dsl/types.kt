package search.dsl

import org.json.JSONObject

/**
 * Defines a DSL builder contract for ElasticSearch compliant JSON request.
 */
interface DslBuilder<Props> {

    fun build(props: Props): JSONObject?

    companion object {
        fun <Props> noop(): DslBuilder<Props> = object : DslBuilder<Props> {
            override fun build(props: Props): JSONObject? = null
        }
    }
}


data class GeoPoint(val lat: Double, val lon: Double) {
    fun toJSON(): JSONObject = JSONObject().put("lat", lat).put("lon", lon)
}

data class GeoBoundingBox(val topLeft: GeoPoint, val bottomRight: GeoPoint) {
    fun toJSON(): JSONObject = JSONObject()
        .put("top_left", topLeft.toJSON())
        .put("bottom_right", bottomRight.toJSON())
}

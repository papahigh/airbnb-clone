package search.query.geo

import search.assertJson
import search.dsl.GeoBoundingBox
import search.dsl.GeoPoint
import search.dsl.query.geo.geoBoundingBox
import kotlin.test.Test
import kotlin.test.assertNull


class GeoBoundingBoxQueryDslBuilderTest {

    class Props(val bbox: GeoBoundingBox? = null)

    val builder = geoBoundingBox<Props> {
        field = "pin.location"
        value = { it.bbox }
        ignoreUnmapped = true
    }

    @Test
    fun `should build a geo bounding box query`() {
        val bbox = GeoBoundingBox(topLeft = GeoPoint(40.73, -74.1), bottomRight = GeoPoint(40.01, -71.12))
        assertJson(
            builder.build(Props(bbox)),
            """
            {   
                "geo_bounding_box": {
                    "pin.location": {
                        "top_left": {
                            "lat": 40.73,
                            "lon": -74.1
                        },
                        "bottom_right": {
                            "lat": 40.01,
                            "lon": -71.12
                        }
                    },
                    "ignore_unmapped": true
                }
            }
            """
        )
    }

    @Test
    fun `should not build query when bbox is not set`() {
        assertNull(builder.build(Props()))
    }
}

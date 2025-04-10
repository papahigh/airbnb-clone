package search.query.geo

import org.junit.jupiter.api.Test
import search.assertJson
import search.dsl.GeoPoint
import search.dsl.query.geo.geoDistance
import kotlin.test.assertNull


class GeoDistanceQueryDslBuilderTest {

    class Props(val point: GeoPoint? = null, val distance: String? = null)

    val builder = geoDistance<Props> {
        field = "some.geo.field"
        location = { it.point }
        distance = { it.distance }
        ignoreUnmapped = true
    }

    @Test
    fun `should build a geo distance query`() {
        assertJson(
            builder.build(Props(point = GeoPoint(40.0, -70.1), distance = "200km")),
            """
            {
                "geo_distance": {
                    "distance": "200km",
                    "ignore_unmapped": true,
                    "some.geo.field": {
                        "lat": 40,
                        "lon": -70.1
                    }
                }
            }
            """
        )
    }

    @Test
    fun `should not build query if props not set`() {
        assertNull(builder.build(Props()))
        assertNull(builder.build(Props(distance = "200km")))
        assertNull(builder.build(Props(point = GeoPoint(40.0, -70.1))))
    }
}

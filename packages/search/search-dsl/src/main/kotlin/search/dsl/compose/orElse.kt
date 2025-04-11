package search.dsl.compose

import org.json.JSONObject
import search.dsl.DslBuilder


fun <Props> DslBuilder<Props>.orElse(fallback: () -> DslBuilder<Props>): DslBuilder<Props> {
    val primary = this
    val secondary = fallback()
    return object : DslBuilder<Props> {
        override fun build(props: Props): JSONObject? {
            return primary.build(props) ?: secondary.build(props)
        }
    }
}

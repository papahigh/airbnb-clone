package search.dsl.compose

import org.json.JSONObject
import search.dsl.DslBuilder


fun <Props> DslBuilder<Props>.orElse(fallback: DslBuilder<Props>): DslBuilder<Props> {
    val target = this
    return object : DslBuilder<Props> {
        override fun build(props: Props): JSONObject? {
            return target.build(props) ?: fallback.build(props)
        }
    }
}

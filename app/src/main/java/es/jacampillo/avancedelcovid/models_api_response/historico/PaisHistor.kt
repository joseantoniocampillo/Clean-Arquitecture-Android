package es.jacampillo.avancedelcovid.models_api_response.historico


import androidx.annotation.Keep
import com.google.gson.JsonObject
import com.squareup.moshi.Json
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.JsonClass
import com.squareup.moshi.Types
import org.json.JSONObject
import java.lang.reflect.Type
import kotlin.reflect.jvm.internal.impl.load.kotlin.JvmType


@Keep
@JsonClass(generateAdapter = true)
data class PaisHistor(
    @Json(name = "country")
    val country: String,
    @Json(name = "provinces")
    val provinces: List<String>,
    @Json(name = "timeline")
    val timeline: Timeline
) {
    @Keep
    @JsonClass(generateAdapter = true)
    data class Timeline(
        @Json(name = "cases")
        val cases: Casos,
        @Json(name = "deaths")
        val deaths: Deaths,
        @Json(name = "recovered")
        val recovered: Recovered
    ) {
        @Keep
        @JsonClass(generateAdapter = true)
        class Cases(
        )

        @Keep
        @JsonClass(generateAdapter = true)
        class Deaths(
            @Json(name = "3/16/20")
            val x31620: Int
            //val list: List<JsonObject>
        )

        @Keep
        @JsonClass(generateAdapter = true)
        class Recovered(
        )
    }
}
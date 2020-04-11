package es.jacampillo.avancedelcovid.models_api_response.historico


import android.util.Log
import androidx.annotation.Keep
import com.squareup.moshi.FromJson
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import org.json.JSONObject


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
        val cases: Cases,
        @Json(name = "deaths")
        val deaths: Deaths,
        @Json(name = "recovered")
        val recovered: Recovered
    ) {
        @Keep
        @JsonClass(generateAdapter = true)
        class Cases(val lista: Map<String, Int>)

        @JsonClass(generateAdapter = true)
        class Deaths(val lista: Map<String, Int>)

        @JsonClass(generateAdapter = true)
        class Recovered(val lista: Map<String, Int>)

        class CasesAdapter {
            @FromJson
            fun fromJson(json: Map<String, Int>): Cases {
                return Cases(json)
            }
        }
        class DeathsAdapter {
            @FromJson
            fun fromJson(json: Map<String, Int>): Deaths {
                return Deaths(json)
            }
        }
        class RecoveredAdapter {
            @FromJson
            fun fromJson(json: Map<String, Int>): Recovered {
                return Recovered(json)
            }
        }

    }

}


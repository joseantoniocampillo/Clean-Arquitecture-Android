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

        @JsonClass(generateAdapter = false)
        class Deaths(val lista: Map<String, Int>)

//        @Keep
//        class Deaths(
//            val fechas: Fechas
//            //val list: List<JsonObject>
//        )

        @Keep
        @JsonClass(generateAdapter = true)
        class Recovered(
        )
    }
}

class FechasAdapter {
    @FromJson
    fun fromJson(json: Map<String, Int>): PaisHistor.Timeline.Deaths {
        //Log.d("xxx", "got $json")
        return PaisHistor.Timeline.Deaths(json)
    }
}
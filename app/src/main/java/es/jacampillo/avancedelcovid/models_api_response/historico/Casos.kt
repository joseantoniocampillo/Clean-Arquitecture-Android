package es.jacampillo.avancedelcovid.models_api_response.historico


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import androidx.annotation.Keep

@Keep
@JsonClass(generateAdapter = true)
data class Casos(
    @Json(name = "3/12/20")
    val x31220: Int,
    @Json(name = "3/13/20")
    val x31320: Int,
    @Json(name = "3/14/20")
    val x31420: Int,
    @Json(name = "3/15/20")
    val x31520: Int,
    @Json(name = "3/16/20")
    val x31620: Int,
    @Json(name = "3/17/20")
    val x31720: Int,
    @Json(name = "3/18/20")
    val x31820: Int,
    @Json(name = "3/19/20")
    val x31920: Int,
    @Json(name = "3/20/20")
    val x32020: Int,
    @Json(name = "3/21/20")
    val x32120: Int,
    @Json(name = "3/22/20")
    val x32220: Int,
    @Json(name = "3/23/20")
    val x32320: Int,
    @Json(name = "3/24/20")
    val x32420: Int,
    @Json(name = "3/25/20")
    val x32520: Int,
    @Json(name = "3/26/20")
    val x32620: Int,
    @Json(name = "3/27/20")
    val x32720: Int,
    @Json(name = "3/28/20")
    val x32820: Int,
    @Json(name = "3/29/20")
    val x32920: Int,
    @Json(name = "3/30/20")
    val x33020: Int,
    @Json(name = "3/31/20")
    val x33120: Int,
    @Json(name = "4/10/20")
    val x41020: Int,
    @Json(name = "4/1/20")
    val x4120: Int,
    @Json(name = "4/2/20")
    val x4220: Int,
    @Json(name = "4/3/20")
    val x4320: Int,
    @Json(name = "4/4/20")
    val x4420: Int,
    @Json(name = "4/5/20")
    val x4520: Int,
    @Json(name = "4/6/20")
    val x4620: Int,
    @Json(name = "4/7/20")
    val x4720: Int,
    @Json(name = "4/8/20")
    val x4820: Int,
    @Json(name = "4/9/20")
    val x4920: Int
)
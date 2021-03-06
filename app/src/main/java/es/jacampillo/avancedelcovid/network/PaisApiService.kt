package es.jacampillo.avancedelcovid.network

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import es.jacampillo.avancedelcovid.models_api_response.Pais
import es.jacampillo.avancedelcovid.models_api_response.historico.PaisHistor
import kotlinx.coroutines.Deferred
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path

private const val BASE_URL = "https://corona.lmao.ninja"

private val moshi = Moshi.Builder()
    .add(PaisHistor.Timeline.CasesAdapter())
    .add(PaisHistor.Timeline.DeathsAdapter())
    .add(PaisHistor.Timeline.RecoveredAdapter())
    .add(KotlinJsonAdapterFactory())
    .build()

private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .addCallAdapterFactory(CoroutineCallAdapterFactory())
    .baseUrl(BASE_URL)
    .build()

interface PaisApiService {

    @GET("countries")
    fun getCountriesAsync():Deferred<List<Pais>>

    @GET("v2/historical/{pais}")
    fun getDataGraficosAsync(@Path("pais") paisId: String): Deferred<PaisHistor>
}

object PaisesApi {
    val retrofitService : PaisApiService by lazy {
        retrofit.create(PaisApiService::class.java)
    }
}
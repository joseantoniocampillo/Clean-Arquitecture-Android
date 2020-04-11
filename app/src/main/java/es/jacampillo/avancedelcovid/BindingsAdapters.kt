package es.jacampillo.avancedelcovid

import android.net.Uri
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import es.jacampillo.avancedelcovid.models_api_response.DatosGraph
import es.jacampillo.avancedelcovid.models_api_response.Pais
import es.jacampillo.avancedelcovid.ui.main.MainFragment
import es.jacampillo.avancedelcovid.ui.main.PaisesAdapter
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList


@BindingAdapter("imgUrl")
fun bindImage(imageView: ImageView, urlImg: String?) {
    urlImg?.let {
        val imgUri = Uri.parse(urlImg).buildUpon().scheme("https").build()
        Glide.with(imageView.context)
            .load(imgUri)
//            .apply(
//                RequestOptions()
//                    .placeholder(R.drawable.ic_launcher_background)
//                    .error(R.drawable.ic_launcher_background))
            .into(imageView)
    }
}

@BindingAdapter("listDatos")
fun bindRecyclerView(recyclerView: RecyclerView, data: List<Pais>?) {
    // atención esta maravilla requiere bindeo en el fragment ..
    // así: binding.photosRecyclerView.adapter = PhotoGridAdapter()
    val adapter = recyclerView.adapter as PaisesAdapter
    adapter.submitList(data)
}

@BindingAdapter("positivos")
fun bindPositivos(tv: TextView, pais: Pais?) {
    pais?.apply {
        tv.text = String.format("Positivos al test: %d", pais.cases)
    }

}

@BindingAdapter("enNegrita")
fun enNegrita(tv: TextView, esEste: Boolean?) {
    //tv.setTypeface(tv.getTypeface(), Typeface.BOLD);
    esEste?.let {
        if (esEste) {
            tv.apply {
                textSize = 16f
                setTextColor(resources.getColor(R.color.texto_resaltado))
            }
        } else {
            tv.apply {
                textSize = 12f
                setTextColor(resources.getColor(R.color.texto_pricipal))
            }
        }
    }
}

@BindingAdapter("contenido")
fun contenidoChart(chart: LineChart, dataObjects: Array<DatosGraph>){
    val entries = ArrayList<Entry>()
    for (dato in dataObjects){
        entries.add(Entry(dato.ejeX, dato.ejeY))
    }
    val dataSet = LineDataSet(entries, "Esta es el label")
    dataSet.color = R.color.texto_pricipal
    dataSet.setValueTextColor(R.color.secondaryColor); // styling, ..
    val lineData = LineData(dataSet)
    chart.data = lineData
    chart.invalidate() // refresh

}




/*------------------------------ UTILS ......................................*/

//SimpleDateFormat("yyyy.MM.dd HH:mm")
fun Long.toDateFormat(): String {
    val date = Date(this)
    val format = SimpleDateFormat("HH:mm dd.MM.yy ")
    return format.format(date)
}

fun orden(codigo: Int?): String {
    return when (codigo) {
        MainFragment.FALLECIDOS -> "👓 Fallecidos"
        MainFragment.POSITIVOS -> "\uD83D\uDC53 Casos"
        MainFragment.FALLECIDOS_HOY -> "🎚 Hoy"
        MainFragment.RECUPERADOS -> "\uD83D\uDC53 ♥ Recuperados"
        MainFragment.GRAVES -> "💊 Graves"
        MainFragment.TEST -> "🧪 Test"
        MainFragment.TEST_POR_MILLON -> "\uD83E\uDDEA Test por 1M"
        else -> " "
    }
}
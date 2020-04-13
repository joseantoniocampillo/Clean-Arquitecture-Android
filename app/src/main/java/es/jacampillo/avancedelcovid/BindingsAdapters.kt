package es.jacampillo.avancedelcovid

import android.annotation.SuppressLint
import android.graphics.Color
import android.net.Uri
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.components.AxisBase
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.formatter.ValueFormatter
import es.jacampillo.avancedelcovid.models_api_response.Pais
import es.jacampillo.avancedelcovid.models_api_response.historico.PaisHistor
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
                @Suppress("DEPRECATION")
                setTextColor(resources.getColor(R.color.texto_resaltado))
            }
        } else {
            tv.apply {
                textSize = 12f
                @Suppress("DEPRECATION")
                setTextColor(resources.getColor(R.color.texto_pricipal))
            }
        }
    }
}

//@BindingAdapter("etiqueta","contenido")
//fun contenidoChart(chart: LineChart, etiqueta: String? = "", contenido: ArrayList<DatosGraph>? = null){
//    val entries = ArrayList<Entry>()
//    contenido?.let{
//        for (dato in contenido){
//            entries.add(Entry(dato.ejeX, dato.ejeY))
//        }
//        val dataSet = LineDataSet(entries, etiqueta)
//        dataSet.color = R.color.negro
//        dataSet.setValueTextColor(R.color.secondaryColor); // styling, ..
//        val lineData = LineData(dataSet)
//        chart.data = lineData
//        chart.description.setEnabled(false);
//        chart.invalidate() // refresh
//    }
//}

@BindingAdapter("historical")
fun historicalToChart(chart: BarChart, historical: PaisHistor?){
    historical?.let {
        val listaFallecidos = it.timeline.deaths.lista
//        listaFallecidos.forEach{
//            Log.d("ttt_iter", "fallecidos: ${it}")
//        }
        val diarios: ArrayList<Float> = ArrayList()
        //para que coindidan con los items de las fechas
        diarios.add(0f)
        var control = 0
        for (item in listaFallecidos){
            if (control != 0)
                diarios.add( (item.value - control).toFloat())
            control = item.value
        }
//        diarios.forEach{
//            Log.d("ttt_iter", "filtrados: ${it}")
//        }
        val diariosFloat = diarios.toFloatArray()

        val indexes: List<String> =
            ArrayList<String>(listaFallecidos.keys)

        val listaBarEntryFallecidos =
            listaFallecidos.map { BarEntry(indexes.indexOf(it.key).toFloat(), diariosFloat.get(indexes.indexOf(it.key)))}


        val fechas = listaFallecidos.map { it.key }.toTypedArray()
        val formatter: ValueFormatter =
            object : ValueFormatter() {
                override fun getAxisLabel(value: Float, axis: AxisBase): String {
                    return fechas[(value).toInt()]
                }
            }
        val xAxis = chart.xAxis
        xAxis.granularity = 1f // minimum axis-step (interval) is 1
        xAxis.valueFormatter = formatter
        val barDataSetFallecidos = BarDataSet(listaBarEntryFallecidos, "Fallecidos Al día")
        barDataSetFallecidos.color = Color.RED

        val data = BarData(barDataSetFallecidos)
        data.barWidth = 0.9f // set custom bar width
        chart.axisLeft.isEnabled = false
        chart.data = data
        chart.setFitBars(true) // make the x-axis fit exactly all bars
        chart.description.isEnabled = false
        chart.invalidate()
    }
}

@BindingAdapter("contenido")
fun contenidoChart(chart: BarChart, historical: PaisHistor?) {
    historical?.let {
        val listaFallecidos = it.timeline.deaths.lista
        val listaCurados = it.timeline.recovered.lista

        val indexes: List<String> =
            ArrayList<String>(listaFallecidos.keys) // <== Set to List

        val listaBarEntryFallecidos =
            listaFallecidos.map { BarEntry(indexes.indexOf(it.key).toFloat(), it.value.toFloat()) }
        val listaBarEntryCurados =
            listaCurados.map { BarEntry(indexes.indexOf(it.key).toFloat(), it.value.toFloat()) }
        val fechas = listaFallecidos.map { it.key }.toTypedArray()

        val formatter: ValueFormatter =
            object : ValueFormatter() {
                override fun getAxisLabel(value: Float, axis: AxisBase): String {
                    return fechas[(value).toInt()]
                }
            }
        val xAxis = chart.xAxis
        xAxis.granularity = 1f // minimum axis-step (interval) is 1
        xAxis.valueFormatter = formatter

        val barDataSetFallecidos = BarDataSet(listaBarEntryFallecidos, "Fallecidos")
        val barDataSetCurados = BarDataSet(listaBarEntryCurados, "Recuperados")

        barDataSetFallecidos.color = Color.BLACK
        barDataSetCurados.color = Color.GREEN

        val groupSpace = 0.06f
        val barSpace = 0.02f // x2 dataset
        val barWidth = 0.45f // x2 dataset

        // (0.02 + 0.45) * 2 + 0.06 = 1.00 -> interval per "group"
        // (0.02 + 0.45) * 2 + 0.06 = 1.00 -> interval per "group"
        val barData = BarData(barDataSetFallecidos, barDataSetCurados)
        barData.barWidth = barWidth // set the width of each bar

        chart.apply {
            axisLeft.isEnabled = false
            description.isEnabled = false
            setData(barData)
            groupBars(1f, groupSpace, barSpace) // perform the "explicit" grouping
            invalidate() // refresh
        }
    }
}


/*------------------------------ UTILS ......................................*/

//SimpleDateFormat("yyyy.MM.dd HH:mm")
@SuppressLint("SimpleDateFormat")
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
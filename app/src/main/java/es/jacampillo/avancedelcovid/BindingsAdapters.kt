package es.jacampillo.avancedelcovid

import android.net.Uri
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.data.*
import es.jacampillo.avancedelcovid.models_api_response.DatosGraph
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

@BindingAdapter("etiqueta","contenido")
fun contenidoChart(chart: LineChart, etiqueta: String? = "", historical: PaisHistor? = null){
    val entries = ArrayList<Entry>()
    historical?.let{

        for (dato in contenido){
            entries.add(Entry(dato.ejeX, dato.ejeY))
        }
        val dataSet = LineDataSet(entries, etiqueta)
        dataSet.color = R.color.negro
        dataSet.setValueTextColor(R.color.secondaryColor); // styling, ..

        val dataSet2 = LineDataSet(entries, etiqueta)
        dataSet2.color = R.color.negro
        dataSet2.setValueTextColor(R.color.secondaryColor); // styling, ..

        val dataSet3 = LineDataSet(entries, etiqueta)
        dataSet3.color = R.color.negro
        dataSet3.setValueTextColor(R.color.secondaryColor); // styling, ..


        val lineData = LineData(dataSet)
        chart.data = lineData
        chart.description.setEnabled(false);
        chart.invalidate() // refresh
    }
}



@BindingAdapter("contenidoBarChart")
fun contenidoChartBarras(chart: BarChart, dataObjects: ArrayList<DatosGraph>?){
    val entries = ArrayList<BarEntry>()
    dataObjects?.let{
        for (dato in dataObjects){
            entries.add(BarEntry(dato.ejeX, dato.ejeY))
        }
//        chart.setDrawBarShadow(false);
//        chart.setDrawValueAboveBar(true);
//        chart.getDescription().setEnabled(false);
//
//        // scaling can now only be done on x- and y-axis separately
//        chart.setPinchZoom(false);
//
//        chart.setDrawGridBackground(false);
//        val l = chart.legend
//        l.verticalAlignment = Legend.LegendVerticalAlignment.BOTTOM
//        l.horizontalAlignment = Legend.LegendHorizontalAlignment.LEFT
//        l.orientation = Legend.LegendOrientation.HORIZONTAL
//        l.setDrawInside(false)
//        l.form = LegendForm.SQUARE
//        l.formSize = 9f
//        l.textSize = 11f
//        l.xEntrySpace = 4f


        var set1 = BarDataSet(entries, "Fallecidos cada día")

        //-------
        val data = BarData(set1)
        data.setValueTextSize(10f)
        //data.setValueTypeface(tfLight)
        data.barWidth = 0.9f


        chart.data = data
    }
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
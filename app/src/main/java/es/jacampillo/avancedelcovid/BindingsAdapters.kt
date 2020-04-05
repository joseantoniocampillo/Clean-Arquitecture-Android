package es.jacampillo.avancedelcovid

import android.net.Uri
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import es.jacampillo.avancedelcovid.model.Pais
import es.jacampillo.avancedelcovid.ui.main.PaisesAdapter

@BindingAdapter("imgUrl")
fun bindImage(imageView: ImageView, urlImg: String?) {
    urlImg?.let {
        val imgUri = Uri.parse(urlImg).buildUpon().scheme("https").build()
        Glide.with(imageView.context)
            .load(imgUri)
//            .apply(
//                RequestOptions()
//                    .placeholder(R.drawable.loading_animation)
//                    .error(R.drawable.ic_broken_image))
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
fun bindPositivos(tv : TextView, pais: Pais?){
    pais?.apply {
        tv.text = String.format("Positivos al test: %d", pais.cases)
    }

}
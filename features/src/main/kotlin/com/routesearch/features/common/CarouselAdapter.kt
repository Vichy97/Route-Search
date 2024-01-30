package com.routesearch.features.common

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.size.Scale
import coil.transform.RoundedCornersTransformation
import com.google.android.material.carousel.MaskableFrameLayout
import com.google.android.material.shape.ShapeAppearanceModel
import com.routesearch.features.R

class CarouselAdapter(
  private val urls: List<String>,
) : RecyclerView.Adapter<CarouselAdapter.ViewHolder>() {

  inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private val maskable: MaskableFrameLayout = itemView.findViewById(R.id.maskable)
    private val imageView: ImageView = itemView.findViewById(R.id.carouselImageView)

    fun bind(url: String) {
      maskable.shapeAppearanceModel = ShapeAppearanceModel().toBuilder()
        .setAllCornerSizes(80f)
        .build()

      imageView.load(url) {
        crossfade(true)
        scale(Scale.FILL)
        transformations(RoundedCornersTransformation(80f))
      }
    }
  }

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
    val view = LayoutInflater.from(parent.context).inflate(R.layout.carousel_layout, parent, false)
    return ViewHolder(view)
  }

  override fun getItemCount() = urls.size

  override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind(urls[position])
}

package training.cg.com.kotlin.util

import android.databinding.BindingAdapter
import android.graphics.Typeface
import android.graphics.drawable.PictureDrawable
import android.net.Uri
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.model.StreamEncoder
import com.bumptech.glide.load.resource.file.FileToStreamDecoder
import com.caverock.androidsvg.SVG
import java.io.InputStream


@BindingAdapter("showProgress") fun showProgress(progressBar: ProgressBar, loading: Boolean) {
    progressBar.alpha = if (loading) 0f else 1f
    progressBar.animate()
            .alpha(if (loading) 1f else 0f)
            .setDuration(500L)
            .start()
}

@BindingAdapter("loadImage") fun loadImage(imageView: ImageView, url: String?) {

    val requestBuilder = Glide.with(imageView.context)
            .using(Glide.buildStreamModelLoader(Uri::class.java, imageView.context), InputStream::class.java)
            .from(Uri::class.java)
            .`as`(SVG::class.java)
            .transcode(SvgDrawableTranscoder(), PictureDrawable::class.java)
            .sourceEncoder(StreamEncoder())
            .cacheDecoder( FileToStreamDecoder<SVG>( SvgDecoder()))
            .decoder(SvgDecoder())
            .animate(android.R.anim.fade_in)
            .listener(SvgSoftwareLayerSetter<Uri>())


    val uri = Uri.parse(url)
    requestBuilder
            .diskCacheStrategy(DiskCacheStrategy. SOURCE)
            // SVG cannot be serialized so it's not worth to cache it
            .load(uri)
            .into(imageView)

}

@BindingAdapter("setFont") fun setFont(textView: TextView, font: String) {
    textView.typeface = Typeface.createFromAsset(textView.context.assets, "fonts/$font")
}
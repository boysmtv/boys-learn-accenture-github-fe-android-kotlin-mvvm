@file:JvmName("Utils")

package com.boys.assets.accenture.utils

import android.content.Context
import android.graphics.drawable.Drawable
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.text.Editable
import android.text.TextWatcher
import android.util.DisplayMetrics
import android.util.TypedValue
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import androidx.annotation.DrawableRes
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SnapHelper
import com.bumptech.glide.Glide
import com.bumptech.glide.load.model.GlideUrl
import org.imaginativeworld.whynotimagecarousel.model.CarouselItem

fun Context.isNetworkAvailable(): Boolean {
    val connectivityManager = this.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.Q) {
        val networkInfo = connectivityManager.activeNetworkInfo
        return networkInfo != null && networkInfo.isConnected
    }

    //Internet connectivity check in Android Q
    val networks = connectivityManager.allNetworks
    var hasInternet = false
    if (networks.isNotEmpty()) {
        for (network in networks) {
            val networkCapabilities = connectivityManager.getNetworkCapabilities(network)
            if (networkCapabilities?.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET) == true) hasInternet = true
        }
    }
    return hasInternet
}

/**
 * This method converts device specific pixels to density independent pixels.
 */
fun Int.pxToDp(context: Context): Int {
    return (this / (context.resources.displayMetrics.densityDpi.toFloat() / DisplayMetrics.DENSITY_DEFAULT)).toInt()
}

/**
 * This method converts dp unit to equivalent pixels, depending on device density.
 */
fun Int.dpToPx(context: Context): Int {
    return TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP,
        this.toFloat(),
        context.resources.displayMetrics
    ).toInt()
}

/**
 * This method converts sp unit to equivalent pixels, depending on device density.
 */
fun Int.spToPx(context: Context): Int {
    return TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_SP,
        this.toFloat(),
        context.resources.displayMetrics
    ).toInt()
}

/**
 * Get current snap item position of a recyclerView.
 *
 * @param layoutManager Target recyclerView
 * @return Position of the item or RecyclerView.NO_POSITION (-1)
 */
fun SnapHelper.getSnapPosition(layoutManager: RecyclerView.LayoutManager?): Int {
    if (layoutManager == null) {
        return RecyclerView.NO_POSITION
    }
    val snapView: View = this.findSnapView(layoutManager) ?: return RecyclerView.NO_POSITION
    return layoutManager.getPosition(snapView)
}

/**
 * Set image to the carouse image view.
 *
 * @param item The carousel item.
 */
fun ImageView.setImage(item: CarouselItem) {
    this.setImage(
        item = item,
        placeholderDrawable = null,
        placeholderDrawableResourceId = null,
    )
}

/**
 * Set image to the carouse image view.
 *
 * @param item The carousel item.
 * @param placeholderDrawableResourceId The id of the resource to use as a placeholder.
 */
fun ImageView.setImage(
    item: CarouselItem,
    @DrawableRes placeholderDrawableResourceId: Int
) {
    this.setImage(
        item = item,
        placeholderDrawable = null,
        placeholderDrawableResourceId = placeholderDrawableResourceId,
    )
}

/**
 * Set image to the carouse image view.
 *
 * @param item The carousel item.
 * @param placeholderDrawable The drawable to display as a placeholder.
 */
fun ImageView.setImage(
    item: CarouselItem,
    placeholderDrawable: Drawable? = null,
) {
    this.setImage(
        item = item,
        placeholderDrawable = placeholderDrawable,
        placeholderDrawableResourceId = null,
    )
}

/**
 * Set image to the carouse image view.
 *
 * @param item The carousel item.
 * @param placeholderDrawable The drawable to display as a placeholder.
 * @param placeholderDrawableResourceId The id of the resource to use as a placeholder.
 */
fun ImageView.setImage(
    item: CarouselItem,
    placeholderDrawable: Drawable? = null,
    @DrawableRes placeholderDrawableResourceId: Int? = null
) {
    val glide = Glide.with(context.applicationContext)

    var requestBuilder = when {
        item.imageUrl != null && item.headers == null -> {
            glide.load(item.imageUrl)
        }
        item.headers != null -> {
            glide.load(GlideUrl(item.imageUrl.toString()) { item.headers })
        }
        else -> {
            glide.load(item.imageDrawable)
        }
    }

    requestBuilder = when {
        placeholderDrawable != null -> {
            requestBuilder.placeholder(placeholderDrawable)
        }
        placeholderDrawableResourceId != null -> {
            requestBuilder.placeholder(placeholderDrawableResourceId)
        }
        else -> requestBuilder
    }

    requestBuilder.into(this)
}

/**
 * Extension function to simplify setting an afterTextChanged action to EditText components.
 */
fun EditText.afterTextChanged(afterTextChanged: (String) -> Unit) {
    this.addTextChangedListener(object : TextWatcher {
        override fun afterTextChanged(editable: Editable?) {
            afterTextChanged.invoke(editable.toString())
        }

        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}

        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
    })
}

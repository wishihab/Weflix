package com.wishihab.weflix.utils.movie

import android.app.Activity
import androidx.core.app.ShareCompat
import com.wishihab.weflix.R

// next tambah jangan lupa
fun share(activity: Activity, type: String, id: String) {

    val title = when (type) {
        "movie" -> activity.getString(R.string.sharemovie)
        else -> activity.getString(R.string.share)
    }

    val mimeType = "text/plain"
    ShareCompat.IntentBuilder.from(activity).apply {
        setType(mimeType)
        setChooserTitle(title)
        setText("https://www.themoviedb.org/${type}/${id}")
        startChooser()
    }
}
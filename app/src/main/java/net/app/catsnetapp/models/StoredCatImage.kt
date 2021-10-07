package net.app.catsnetapp.models

import android.graphics.Bitmap

data class StoredCatImage(
    val bitmap: Bitmap?,
    val url: String? = null,
    val name: String?,
    val ext: String?
)

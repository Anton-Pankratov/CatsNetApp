package net.app.catsnetapp.data.storage

import androidx.annotation.StringRes
import net.app.catsnetapp.R

enum class SaveImageState(@StringRes val message: Int? = null) {
    NONE,
    SUCCESS(R.string.save_image_success),
    FAILURE(R.string.save_image_failure)
}
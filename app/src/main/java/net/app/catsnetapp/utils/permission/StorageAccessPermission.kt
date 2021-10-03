package net.app.catsnetapp.utils.permission

import androidx.activity.result.ActivityResultLauncher
import androidx.appcompat.app.AppCompatActivity

interface StorageAccessPermission {

    val permissionRequest: ActivityResultLauncher<String>?

    fun setPermissionCallback()

    fun checkPermission()
}
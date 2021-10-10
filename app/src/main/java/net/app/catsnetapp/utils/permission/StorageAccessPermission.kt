package net.app.catsnetapp.utils.permission

import androidx.activity.result.ActivityResultLauncher

interface StorageAccessPermission {

    val permissionRequest: ActivityResultLauncher<String>?

    fun setPermissionCallback(action: () -> Unit)

    fun checkPermission(action: () -> Unit)
}
package net.app.catsnetapp.utils.permission

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import net.app.catsnetapp.utils.showPermissionRequestDialog

class StorageAccessPermissionImpl(private val activity: AppCompatActivity) :
    StorageAccessPermission {

    private val writeStoragePermission = Manifest.permission.WRITE_EXTERNAL_STORAGE

    private var _permissionRequest: ActivityResultLauncher<String>? = null

    override val permissionRequest get() = _permissionRequest

    override fun setPermissionCallback(action: () -> Unit) {
        _permissionRequest = activity.registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { isGranted ->
            if (isGranted) {
                action.invoke()
            }
        }
    }

    override fun checkPermission(action: () -> Unit) {
        activity.apply {
            when {
                ContextCompat.checkSelfPermission(
                    activity,
                    writeStoragePermission
                ) == PackageManager.PERMISSION_GRANTED -> {
                    action.invoke()
                }
                Build.VERSION.SDK_INT >= 23 &&
                    shouldShowRequestPermissionRationale(writeStoragePermission) -> {
                    showPermissionRequestDialog(
                        "",
                        ""
                    ) {
                        permissionRequest?.launch(writeStoragePermission)
                    }
                }
                else -> permissionRequest?.launch(writeStoragePermission)
            }
        }
    }
}
package net.app.catsnetapp.ui.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import net.app.catsnetapp.databinding.ActivityMainBinding
import net.app.catsnetapp.models.StoredCatImage
import net.app.catsnetapp.ui.cat.CatFragment
import net.app.catsnetapp.ui.main.view.CatsView
import net.app.catsnetapp.utils.configureSystemBars
import net.app.catsnetapp.utils.getCatImage
import net.app.catsnetapp.utils.permission.StorageAccessPermission
import net.app.catsnetapp.utils.toast
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class MainActivity : AppCompatActivity() {

    private val viewModel: MainViewModel by viewModel()

    private val permission: StorageAccessPermission by inject {
        parametersOf(this)
    }

    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding?.root?.apply {
            listenOnCatViewClickEvents()
            observeCats(viewModel.cats)
            collectSaveStateEvent()

            permission.setPermissionCallback {
                viewModel.saveCatImage(
                    contentResolver, StoredCatImage(
                        getCatImage(viewModel.keptCat?.url, viewModel.imageLoader),
                        viewModel.keptCat?.url, ""
                    )
                )
            }
        })
    }

    override fun onResume() {
        super.onResume()
        configureSystemBars()
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    private fun CatsView.listenOnCatViewClickEvents() {
        setCatClickEventListener {
            viewModel.keepSelectedCat(it)
            CatFragment.create().show(
                supportFragmentManager, "cat"
            )
        }
    }

    private fun collectSaveStateEvent() {
        lifecycleScope.launch {
            viewModel.saveStateEvent.collect {
                it.message.apply {
                    if (this != null) {
                        toast(this)
                    }
                }
            }
        }
    }
}
package net.app.catsnetapp.ui.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
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
        setOnDeniedPermission()
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding?.root?.apply {
            listenOnCatViewClickEvents()
            lifecycleScope.collectCats(viewModel.catsFlow)
            addPagination()
            collectSaveStateEvent()
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
            viewModel.saveStateEvent.observe(this@MainActivity) {
                it.message.apply {
                    if (this != null) {
                        toast(this)
                    }
                }
            }
        }
    }

    private fun CatsView.addPagination() {
        addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                if (dy > 0) {
                    layoutManager?.let { manager ->
                        manager.apply {
                            val pastVisibleItems =
                                (this as GridLayoutManager).findFirstVisibleItemPosition()

                            if ((childCount + pastVisibleItems) >= itemCount) {
                                viewModel.fetchCatsImages()
                            }
                        }
                    }
                }
            }
        })
    }

    private fun setOnDeniedPermission() {
        viewModel.keptCat?.apply {
            permission.setPermissionCallback {
                lifecycleScope.launch {
                    viewModel.apply {
                        viewModel.saveCatImage(
                            contentResolver, formStoredCat()
                        )
                    }
                }
            }
        }
    }
}
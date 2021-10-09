package net.app.catsnetapp.ui.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import net.app.catsnetapp.databinding.ActivityMainBinding
import net.app.catsnetapp.ui.cat.CatFragment
import net.app.catsnetapp.ui.main.view.CatsView
import net.app.catsnetapp.utils.*
import net.app.catsnetapp.utils.permission.StorageAccessPermission
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

    private val progressDialog by lazy {
        createProgressDialog(viewModel.glide)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setOnDeniedPermission()
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding?.root)
        binding?.catsView?.configure()
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
        super.onRequestPermissionsResult(
            requestCode,
            permissions,
            grantResults
        )
    }

    private fun CatsView.configure() {
        listenOnCatViewClickEvents()
        lifecycleScope.collectCats(viewModel.catsFlow)
        addPagination()
        collectSaveStateEvent()
        collectProgressState()
    }

    private fun CatsView.listenOnCatViewClickEvents() {
        setCatClickEventListener {
            viewModel.keepSelectedCat(it)
            CatFragment.create().show(
                supportFragmentManager,
                "cat"
            )
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

    private fun collectSaveStateEvent() {
        lifecycleScope.launch {
            viewModel.saveStateEvent.observe(
                this@MainActivity
            ) {
                it.message.apply {
                    if (this != null) {
                        toast(this)
                    }
                }
            }
        }
    }

    private fun collectProgressState() {
        lifecycleScope.launch {
            viewModel.catsFetching.collect { isFetching ->
                useProgressViewWithCheck(isFetching)
            }
        }
    }

    private fun useProgressViewWithCheck(isFetching: Boolean) {
        with(binding?.root) {
            progressDialog.apply {
                if (isFetching) {
                    this@with?.addView(
                        apply {
                            startAlphaAnimation(
                                ANIM_DURATION_PROGRESS
                            )
                        }
                    )
                } else {
                    this@with?.removeView(
                        apply {
                            startAlphaAnimationReverse(
                                ANIM_DURATION_PROGRESS
                            )
                        }
                    )
                }
            }
        }
    }

    private fun setOnDeniedPermission() {
        viewModel.keptCat?.apply {
            permission.setPermissionCallback {
                lifecycleScope.launch {
                    viewModel.apply {
                        viewModel.saveCatImage(
                            contentResolver,
                            formStoredCat()
                        )
                    }
                }
            }
        }
    }
}
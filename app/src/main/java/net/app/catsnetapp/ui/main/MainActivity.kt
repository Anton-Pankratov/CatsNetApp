package net.app.catsnetapp.ui.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import net.app.catsnetapp.databinding.ActivityMainBinding
import net.app.catsnetapp.ui.cat.CatFragment
import net.app.catsnetapp.ui.main.view.CatsView
import net.app.catsnetapp.utils.configureSystemBars
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {

    private val viewModel: MainViewModel by viewModel()

    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding?.root?.apply {
            listenOnCatViewClickEvents()
            observeCats(viewModel.cats)
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

    private fun CatsView.listenOnCatViewClickEvents() {
        setCatClickEventListener {
            viewModel.keepCatDrawable(it)
            CatFragment.create().show(
                supportFragmentManager, "cat"
            )
        }
    }
}
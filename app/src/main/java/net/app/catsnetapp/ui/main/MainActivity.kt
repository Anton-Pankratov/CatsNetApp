package net.app.catsnetapp.ui.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import net.app.catsnetapp.databinding.ActivityMainBinding
import net.app.catsnetapp.ui.cat.CatFragment
import net.app.catsnetapp.ui.list.CatsAdapter
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {

    private val viewModel: MainViewModel by viewModel()

    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding

    private val catsAdapter: CatsAdapter by lazy {
        CatsAdapter(viewModel).apply {
            setOnCatClickListener {
                CatFragment.create().show(supportFragmentManager, "cat")
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding?.root)
        configureCatsRecyclerView()
        collectCats()
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }

    private fun configureCatsRecyclerView() {
        binding?.root?.apply {
            layoutManager = GridLayoutManager(this@MainActivity, 3)
            adapter = catsAdapter
        }
    }

    private fun collectCats() {
        lifecycleScope.launch {
            viewModel.cats.collect {
                catsAdapter.submitList(it)
            }
        }
    }
}
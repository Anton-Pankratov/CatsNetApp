package net.app.catsnetapp.ui.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import net.app.catsnetapp.databinding.ActivityMainBinding
import net.app.catsnetapp.ui.list.CatsAdapter
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {

    private val viewModel: MainViewModel by viewModel()

    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding

    private val catsAdapter: CatsAdapter by lazy {
        CatsAdapter(viewModel.catsDiffCallback)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        viewModel.callTest()
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }

    private fun setCatsIntoList() {

    }
}
package net.app.catsnetapp.ui.main.view

import android.content.Context
import android.util.AttributeSet
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import net.app.catsnetapp.models.Cat
import net.app.catsnetapp.ui.list.CatsAdapter
import net.app.catsnetapp.ui.main.MainViewModel
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class CatsView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null) :
    RecyclerView(context, attrs), KoinComponent {

    private val viewModel: MainViewModel by inject()

    private var onCatViewCatEvent: CatsViewClickEvent? = null

    private val catsAdapter: CatsAdapter by lazy {
        CatsAdapter(
            viewModel.diffCallback,
            viewModel.glide
        ).apply {
            setOnCatClickListener { url ->
                onCatViewCatEvent?.onCatClick(url)
            }
        }
    }

    init {
        layoutManager = GridLayoutManager(context, 3)
        adapter = catsAdapter
    }

    fun setCatClickEventListener(listener: CatsViewClickEvent) {
        onCatViewCatEvent = listener
    }

    fun CoroutineScope.collectCats(cats: StateFlow<List<Cat>>) {
        launch {
            cats.collect {
                catsAdapter.submitList(it)
            }
        }
    }
}
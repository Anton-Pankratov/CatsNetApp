package net.app.catsnetapp.ui.cat

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import coil.imageLoader
import net.app.catsnetapp.R
import net.app.catsnetapp.databinding.FragmentCatBinding

class CatFragment : DialogFragment() {

    private var _binding: FragmentCatBinding? = null
    private val binding get() = _binding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCatBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return AlertDialog.Builder(activity, R.style.CatFragmentStyle).apply {
            context.imageLoader
        }.create()
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    companion object {
        fun create(): CatFragment {
            return CatFragment()
        }
    }
}


// https://stackoverflow.com/questions/13402782/show-dialogfragment-with-animation-growing-from-a-point
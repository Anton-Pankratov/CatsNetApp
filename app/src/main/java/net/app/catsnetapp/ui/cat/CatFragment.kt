package net.app.catsnetapp.ui.cat

import android.app.Dialog
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import net.app.catsnetapp.databinding.FragmentCatBinding
import net.app.catsnetapp.utils.setCatImage
import net.app.catsnetapp.utils.startFlipAnimation
import org.koin.androidx.viewmodel.ext.android.viewModel

class CatFragment : DialogFragment() {

    private val viewModel: CatViewModel by viewModel()

    private var _binding: FragmentCatBinding? = null
    private val binding get() = _binding

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        _binding = FragmentCatBinding.inflate(layoutInflater)
        return binding?.buildDialog() as Dialog
    }

    override fun onStart() {
        super.onStart()
        dialog?.window?.apply {
            setBackgroundDrawable(ColorDrawable(android.graphics.Color.TRANSPARENT))
            decorView.startFlipAnimation()
        }
    }

    private fun FragmentCatBinding?.buildDialog() =
        AlertDialog.Builder(requireActivity()).apply {
            with(viewModel) {
                this@buildDialog?.apply {
                    setView(root.apply {
                        catImageView.setCatImage(keptCatImage, imageLoader)
                    })
                }
            }
        }.create()

    companion object {
        fun create() = CatFragment()
    }
}
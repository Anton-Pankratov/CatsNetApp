package net.app.catsnetapp.ui.cat

import android.app.Dialog
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch
import net.app.catsnetapp.databinding.FragmentCatBinding
import net.app.catsnetapp.utils.*
import net.app.catsnetapp.utils.permission.StorageAccessPermission
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class CatFragment : DialogFragment() {

    private val viewModel: CatViewModel by viewModel()

    private val permission: StorageAccessPermission by inject {
        parametersOf(activity)
    }

    private var _binding: FragmentCatBinding? = null
    private val binding get() = _binding

    private val cat get() = viewModel.keptCat

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        _binding = FragmentCatBinding.inflate(layoutInflater)
        return binding?.buildDialog() as Dialog
    }

    override fun onStart() {
        super.onStart()
        dialog?.configureDialog()
        binding?.configureDownloadIcon()
    }

    private fun FragmentCatBinding?.buildDialog() =
        AlertDialog.Builder(requireActivity()).apply {
            with(viewModel) {
                this@buildDialog?.apply {
                    setView(
                        root.apply {
                            catImageView.setImage(
                                cat?.url,
                                cat?.ext,
                                glide
                            )
                        }
                    )
                }
            }
        }.create()

    private fun Dialog.configureDialog() {
        window?.apply {
            setBackgroundDrawable(
                ColorDrawable(android.graphics.Color.TRANSPARENT)
            )
            decorView.startFlipAnimation()
        }
    }

    private fun FragmentCatBinding.configureDownloadIcon() {
        saveInGalleryView.apply {
            startAlphaAnimation(ANIM_DURATION_ICON)
            setOnDownloadClick()
            setDownloadIcon()
        }
    }

    private fun ImageView.setDownloadIcon() {
        with(viewModel) {
            setDownloadIcon(cat?.url, glide)
        }
    }

    private fun ImageView.setOnDownloadClick() {
        setOnClickListener {
            permission.checkPermission {
                lifecycleScope.launch {
                    viewModel.apply {
                        saveCatImage(
                            activity?.contentResolver,
                            formStoredCat()
                        )
                    }
                }
            }
        }
    }

    companion object {
        fun create() = CatFragment()
    }
}
package net.app.catsnetapp.ui.cat

import android.app.Dialog
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import net.app.catsnetapp.databinding.FragmentCatBinding
import net.app.catsnetapp.models.StoredCatImage
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

    private val storedCat by lazy {
        StoredCatImage(
            bitmap = requireContext().getCatImage(cat?.url, viewModel.imageLoader),
            name = cat?.getBreed()?.getShowName(viewModel.timeStamp) ?: viewModel.timeStamp,
            url = cat?.url
        )
    }

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
                    setView(root.apply {
                        catImageView.setImage(
                            cat?.url, imageLoader
                        )
                    })
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
            startAlphaAnimation()
            setIconByCheckOf(catImageView)
            setOnDownloadClick()
        }
    }

    private fun ImageView.setIconByCheckOf(sourceView: ImageView) {
        with(viewModel) {
            sourceView.setDownloadIcon(
                cat?.url, this@setIconByCheckOf, imageLoader
            )
        }
    }

    private fun ImageView.setOnDownloadClick() {
        setOnClickListener {
            permission.checkPermission {
                viewModel.saveCatImage(
                    activity?.contentResolver, storedCat
                )
            }
        }
    }

    companion object {
        fun create() = CatFragment()
    }
}
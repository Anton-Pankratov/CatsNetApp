package net.app.catsnetapp.ui.cat

import androidx.fragment.app.DialogFragment

class CatFragment : DialogFragment() {


    companion object {
        fun create(): CatFragment {
            return CatFragment()
        }
    }
}
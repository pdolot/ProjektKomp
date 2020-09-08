package com.example.myapplication.base

import android.os.Bundle
import androidx.fragment.app.FragmentManager
import com.example.myapplication.R
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

open class BaseBottomSheet : BottomSheetDialogFragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NO_TITLE, R.style.BottomSheet)
    }

    open fun show(fragmentManager: FragmentManager) {
        val ft = fragmentManager.beginTransaction()
        val prev = fragmentManager.findFragmentByTag("bottomSheet")
        if (prev != null) {
            ft.remove(prev)
        }
        ft.addToBackStack(null)
        super.show(ft, "baseBottomSheet")
    }
}
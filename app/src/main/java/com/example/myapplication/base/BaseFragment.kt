package com.example.myapplication.base

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.example.myapplication.R
import com.example.myapplication.view.activity.CoreFragmentInterface
import com.example.myapplication.view.customView.TopBar

abstract class BaseFragment : Fragment() {

    private var core: CoreFragmentInterface? = null
    var topBar: TopBar? = null
    abstract var sideMenuEnabled: Boolean
    abstract var topBarTitle: String

    override fun onAttach(context: Context) {
        super.onAttach(context)
        try {
            core = context as? CoreFragmentInterface
        } catch (e: Exception) {
            throw IllegalStateException("Main activity must implement core interface")
        }

        topBar = core?.getTopBar()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val topBarHeight = resources.getDimensionPixelSize(R.dimen.topBarHeight)
        view.setPadding(view.paddingLeft, view.paddingTop + topBarHeight, view.paddingRight, view.paddingBottom)

        topBar?.setTitle(topBarTitle)
        topBar?.isMenuEnabled = sideMenuEnabled
    }

    override fun onDetach() {
        super.onDetach()
        topBar = null
        core = null
    }

}
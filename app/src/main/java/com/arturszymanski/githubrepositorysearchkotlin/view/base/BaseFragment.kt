package com.arturszymanski.githubrepositorysearchkotlin.view.base

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.arturszymanski.presenter.base.BaseView

abstract class BaseFragment : Fragment(), BaseView {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        try {
            view.findNavController()
        } catch (e: IllegalStateException) {
            Navigation.setViewNavController(view, findNavController())
        }
    }

    /**
     * Method that invoke inject dependencies in right moment
     */
    abstract fun injectDependencies()

    //region BaseView
    override fun showProgress() {
        (activity as? BaseView)?.showProgress()
    }

    override fun hideProgress() {
        (activity as? BaseView)?.hideProgress()
    }
    //endregion
}

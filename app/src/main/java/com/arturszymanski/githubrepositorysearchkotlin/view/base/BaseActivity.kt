package com.arturszymanski.githubrepositorysearchkotlin.view.base

import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import com.arturszymanski.presenter.base.BaseView

abstract class BaseActivity : AppCompatActivity(), BaseView {

    override fun onBackPressed() {
        if (supportFragmentManager.fragments.isEmpty()) {
            return
        }
        if (supportFragmentManager.fragments[0] is NavHostFragment) {
            supportFragmentManager.fragments[0]
                .childFragmentManager
                .fragments
                .forEach {
                    if (it.isAdded && it is BackAware) {
                        it.onBackPressed()
                    }
                }
        }
    }

    /**
     * Method that invoke inject dependencies in right moment
     */
    abstract fun injectDependencies()
}

package com.arturszymanski.githubrepositorysearchkotlin.view

import android.os.Bundle
import android.view.View
import androidx.navigation.findNavController
import com.arturszymanski.githubrepositorysearchkotlin.R
import com.arturszymanski.githubrepositorysearchkotlin.view.base.BaseActivity
import com.google.android.material.snackbar.Snackbar
import dagger.android.AndroidInjection
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onSupportNavigateUp(): Boolean {
        return findNavController(R.id.mainNavHostFragment).navigateUp()
    }

    //region BaseView
    override fun showProgress() {
        progressIndicator.visibility = View.VISIBLE
    }

    override fun hideProgress() {
        progressIndicator.visibility = View.GONE
    }

    override fun displayGeneralError() =
        Snackbar
            .make(
                mainContainer,
                R.string.error_general,
                Snackbar.LENGTH_SHORT)
            .show()
    //endregion

    //region Base
    override fun injectDependencies() {
        AndroidInjection.inject(this)
    }
    //endregion
}

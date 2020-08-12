package com.arturszymanski.githubrepositorysearchkotlin.view

import android.os.Bundle
import androidx.navigation.findNavController
import com.arturszymanski.githubrepositorysearchkotlin.R
import com.arturszymanski.githubrepositorysearchkotlin.view.base.BaseActivity
import dagger.android.AndroidInjection

class MainActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onSupportNavigateUp(): Boolean {
        return findNavController(R.id.mainNavHostFragment).navigateUp()
    }

    //region Base
    override fun injectDependencies() {
        AndroidInjection.inject(this)
    }
    //endregion
}

package com.arturszymanski.githubrepositorysearchkotlin.view.splash

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.arturszymanski.githubrepositorysearchkotlin.R
import com.arturszymanski.githubrepositorysearchkotlin.view.base.BaseFragment
import dagger.android.support.AndroidSupportInjection

class SplashFragment : BaseFragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(
        R.layout.fragment_splash,
        container,
        false
    )

    //region Base
    override fun injectDependencies() {
        AndroidSupportInjection.inject(this)
    }
    //endregion
}
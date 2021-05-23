package com.star.wars.landing.presentation

import android.os.Bundle
import androidx.activity.viewModels
import androidx.navigation.findNavController
import com.star.wars.R
import com.star.wars.landing.presentation.SplashEvent.*
import com.star.wars.common.addTo
import com.star.wars.common.base.FullScreenActivity
import com.star.wars.common.viewBinding
import com.star.wars.databinding.ActivitySplashBinding
import com.star.wars.landing.domain.SplashViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SplashActivity : FullScreenActivity<SplashState>() {

    private val binding by viewBinding(ActivitySplashBinding::inflate)
    private val screen by lazy { SplashScreenImpl() }
    private val vm: SplashViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.rootLayout)
        setUpHandlers()
        vm.load()
    }

    private fun setUpHandlers() {
        vm.state.observe(
            this,
            {
                it?.let {
                    _state.onNext(it)
                }
            }
        )
        screen.bind(binding, state.share())
            .addTo(compositeBag)
        screen.event.observe(
            this,
            { event ->
                when (event) {
                    is StartTimerEvent -> {
                        vm.startTimer()
                    }
                    is NavigateToNextScreenEvent -> {
                        findNavController(R.id.navHost).navigate(R.id.nav_spalash_search)
                        finish()
                    }
                }
            }
        )
    }
}

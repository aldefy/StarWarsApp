package com.star.wars.details.presentation

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.activity.viewModels
import com.star.wars.andromeda.AndromedaTheme
import com.star.wars.common.addTo
import com.star.wars.common.base.BaseActivity
import com.star.wars.common.viewBinding
import com.star.wars.details.R
import com.star.wars.details.databinding.ActivityDetailsBinding
import com.star.wars.details.domain.DetailsState
import com.star.wars.details.domain.DetailsViewModel
import com.star.wars.details.model.CharacterDetailsMeta
import dagger.hilt.android.AndroidEntryPoint

const val EXTRA_META = "details_meta"

@AndroidEntryPoint
class DetailsActivity : BaseActivity<DetailsState>() {
    private var meta: CharacterDetailsMeta? = null
    private val binding by viewBinding(ActivityDetailsBinding::inflate)
    private val vm: DetailsViewModel by viewModels()
    private val screen by lazy { DetailsScreenImpl() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        handleExtras()
    }

    private fun handleExtras() {
        meta = intent.getParcelableExtra(EXTRA_META)
        requireNotNull(meta) { "no $EXTRA_META provided in Intent extras" }
        setUpHandlers()
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

        screen.initNavBar(binding, CharacterDetailsMeta("", emptyList()), R.menu.menu_details)

        screen.event.observe(
            this,
            { event ->
                when (event) {
                    is DetailsEvent.CloseScreen -> finish()
                    is DetailsEvent.DetailsErrorEvent -> {
                        //analytics events trigger
                    }
                    is DetailsEvent.DetailsResultsFetched -> {
                        //analytics events trigger
                    }
                    DetailsEvent.ShowThemeChooserEvent -> {
                        toggleTheme()
                    }
                }
            }
        )
    }

    private fun toggleTheme() {
        if (com.star.wars.andromeda.theme.getTheme(this).isDarkTheme) {
            com.star.wars.andromeda.theme.setTheme(this, AndromedaTheme.LIGHT)
        } else {
            com.star.wars.andromeda.theme.setTheme(this, AndromedaTheme.DARK)
        }
        val intent = intent
        Handler(Looper.myLooper()!!).postDelayed({
            finish()
            startActivity(intent)
        }, 100)
    }

    companion object {
        fun intent(context: Context, meta: CharacterDetailsMeta): Intent {
            return Intent(context, DetailsActivity::class.java)
                .apply {
                    flags = Intent.FLAG_ACTIVITY_SINGLE_TOP
                    putExtra(EXTRA_META, meta)
                }
        }
    }
}

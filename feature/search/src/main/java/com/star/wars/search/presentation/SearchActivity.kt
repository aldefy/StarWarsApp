package com.star.wars.search.presentation

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.Parcelable
import androidx.activity.viewModels
import com.star.wars.andromeda.AndromedaTheme
import com.star.wars.andromeda.theme.getTheme
import com.star.wars.andromeda.theme.setTheme
import com.star.wars.common.addTo
import com.star.wars.common.base.BaseActivity
import com.star.wars.common.viewBinding
import com.star.wars.search.R
import com.star.wars.search.databinding.ActivitySearchBinding
import com.star.wars.search.domain.SearchState
import com.star.wars.search.domain.SearchViewModel
import com.star.wars.search.presentation.SearchEvent.SearchErrorEvent
import com.star.wars.search.presentation.SearchEvent.SearchResultsFetched
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class SearchActivity : BaseActivity<SearchState>() {

    private val binding by viewBinding(ActivitySearchBinding::inflate)
    private val vm: SearchViewModel by viewModels()
    private val screen by lazy { SearchScreenImpl() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setUpHandlers()
    }

    private fun setUpHandlers() {
        vm.state.observe(this,
            {
                it?.let {
                    _state.onNext(it)
                }
            }
        )
        screen.bind(binding, state.share())
            .addTo(compositeBag)

        screen.addSearchHandler(binding)
        screen.setupComponentHandlers(binding)
        screen.initNavBar(binding, R.menu.menu_search)

        screen.event.observe(
            this,
            { event ->
                when (event) {
                    is SearchResultsFetched -> {
                        //analytics events trigger
                    }
                    is SearchErrorEvent -> {
                        //analytics events trigger
                    }
                    is SearchEvent.SearchCleared -> {
                        //TODO - add a clear logic on list
                    }
                    is SearchEvent.SearchTriggeredEvent -> vm.searchCharacter(event.searchText)
                    is SearchEvent.ClickFiredEvent -> {
                        navigateToDetails(event.extraPayload)
                    }
                    is SearchEvent.CloseScreen -> {
                        finish()
                    }
                    is SearchEvent.ShowThemeChooserEvent -> {
                        toggleTheme()
                    }
                }
            }
        )
    }

    private fun navigateToDetails(extraPayload: Parcelable) {
        val intent = Intent()
        intent.action = "starwars://details"
        intent.putExtra(
            "details_meta",
            extraPayload
        )
        if (intent.resolveActivity(packageManager) != null) {
            startActivity(intent)
        }
    }

    private fun toggleTheme() {
        if (getTheme(this).isDarkTheme) {
            setTheme(this, AndromedaTheme.LIGHT)
        } else {
            setTheme(this, AndromedaTheme.DARK)
        }
        val intent = intent
        Handler(Looper.myLooper()!!).postDelayed({
            finish()
            startActivity(intent)
        }, 100)
    }
}

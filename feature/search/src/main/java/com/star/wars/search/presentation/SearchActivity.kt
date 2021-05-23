package com.star.wars.search.presentation

import android.os.Bundle
import androidx.activity.viewModels
import com.star.wars.andromeda.tokens.icon_dynamic_default
import com.star.wars.andromeda.views.assets.icon.Icon
import com.star.wars.andromeda.views.navbar.AndromedaNavBar
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
        initNavBar()
        setUpHandlers()
    }

    private fun initNavBar() {
        with(binding.navBarLayout.navBar) {
            setTitle(resources.getString(R.string.navbar_search_title))
            setSubtitle(resources.getString(R.string.navbar_search_subtitle))
            showNavigationIcon(Icon.NAVIGATION_BACK, listener = {
                finish()
            })
            setNavigationLogo(Icon.UNIVERSE, icon_dynamic_default)
            divider = AndromedaNavBar.Divider.LINE
        }
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
        screen.addSearchHandler(binding)
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
                }
            }
        )
    }
}

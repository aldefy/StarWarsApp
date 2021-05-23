package com.star.wars.search.presentation

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import com.star.wars.common.addTo
import com.star.wars.common.base.BaseActivity
import com.star.wars.common.viewBinding
import com.star.wars.search.databinding.ActivitySearchBinding
import com.star.wars.search.domain.SearchState
import com.star.wars.search.domain.SearchViewModel
import com.star.wars.search.presentation.SearchEvent.*

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
                    is SearchResultsFetched -> {
                    }
                    is SearchErrorEvent -> {
                    }
                }
            }
        )
    }
}

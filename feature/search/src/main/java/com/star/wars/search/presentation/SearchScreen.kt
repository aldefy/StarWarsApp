package com.star.wars.search.presentation

import androidx.core.widget.doOnTextChanged
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.star.wars.andromeda.extensions.makeGone
import com.star.wars.andromeda.extensions.makeVisible
import com.star.wars.common.addTo
import com.star.wars.search.databinding.ActivitySearchBinding
import com.star.wars.search.domain.SearchState
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

interface SearchScreen : LifecycleObserver {
    val event: LiveData<SearchEvent>

    fun bind(
        binding: ActivitySearchBinding,
        observable: Observable<SearchState>
    ): Disposable
}

class SearchScreenImpl : SearchScreen {
    private val _event = MutableLiveData<SearchEvent>()
    override val event: LiveData<SearchEvent> = _event

    override fun bind(
        binding: ActivitySearchBinding,
        observable: Observable<SearchState>
    ): Disposable {
        return CompositeDisposable().apply {
            handleContent(binding, observable, this)
            handleLoading(binding, observable, this)
            handleError(binding, observable, this)
        }
    }

    fun addSearchHandler(
        binding: ActivitySearchBinding
    ) {
        binding.layoutSearch.inputSearch.doOnTextChanged { text, _, _, _ ->
            if (!text.isNullOrEmpty()) {
                if (text.isEmpty()) {
                    _event.value = SearchEvent.SearchCleared
                }
                if (text.length >= 2) {
                    _event.value = SearchEvent.SearchTriggeredEvent(text.toString())
                }
            }
        }
    }

    private fun handleContent(
        binding: ActivitySearchBinding,
        observable: Observable<SearchState>,
        bag: CompositeDisposable
    ) {
        observable
            .ofType(SearchState.LoadData::class.java)
            .subscribe {
                binding.contentRV.setUpComponents(it.data)
            }
            .addTo(bag)
    }

    private fun handleLoading(
        binding: ActivitySearchBinding,
        observable: Observable<SearchState>,
        bag: CompositeDisposable
    ) {
        observable
            .ofType(SearchState.ShowLoading::class.java)
            .subscribe {
                binding.loadingView.makeVisible()
                binding.contentRV.makeGone()
            }
            .addTo(bag)

        observable
            .ofType(SearchState.HideLoading::class.java)
            .subscribe {
                binding.contentRV.makeVisible()
                binding.loadingView.makeGone()
            }
            .addTo(bag)
    }

    private fun handleError(
        binding: ActivitySearchBinding,
        observable: Observable<SearchState>,
        bag: CompositeDisposable
    ) {
        observable
            .ofType(SearchState.Error::class.java)
            .subscribe {
            }
            .addTo(bag)
    }
}

package com.star.wars.search.presentation

import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
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


    private fun handleContent(
        binding: ActivitySearchBinding,
        observable: Observable<SearchState>,
        bag: CompositeDisposable
    ) {
        observable
            .ofType(SearchState.LoadData::class.java)
            .subscribe {
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
               // binding.loadingView.show()
            }
            .addTo(bag)

        observable
            .ofType(SearchState.HideLoading::class.java)
            .subscribe {
                //binding.loadingView.hide()
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

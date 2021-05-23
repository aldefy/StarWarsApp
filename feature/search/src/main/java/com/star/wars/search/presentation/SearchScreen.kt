package com.star.wars.search.presentation

import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
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
        return CompositeDisposable()
    }
}

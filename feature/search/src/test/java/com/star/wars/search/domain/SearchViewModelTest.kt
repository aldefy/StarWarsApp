package com.star.wars.search.domain

import androidx.lifecycle.Observer
import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.verifyNoMoreInteractions
import com.star.wars.andromeda.views.list.ComponentData
import com.star.wars.andromeda.views.list.internal.component.text.data.TextComponentData
import com.star.wars.common.test.BaseJUnitTest
import com.star.wars.common.test.willReturn
import io.reactivex.Single
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner


@RunWith(MockitoJUnitRunner::class)
class SearchViewModelTest : BaseJUnitTest() {
    private lateinit var vm: SearchViewModel

    private val useCase: SearchUseCase = mock()
    private val observer: Observer<SearchState> = mock()
    private val query: String = "test"
    private val successComponentList =
        mutableListOf<ComponentData>(TextComponentData(id = "testId"))
    private val emptyComponentList = mutableListOf<ComponentData>()
    private val dummyError = Throwable("mock")

    override fun start() {
        vm = SearchViewModel(useCase)
    }

    override fun stop() {
        verifyNoMoreInteractions(useCase, observer)
    }

    @Test
    fun `when user searches for a keyword verify state when search returns results`() {
        useCase.searchCharacter(query) willReturn Single.fromCallable { successComponentList }
        vm.state.observeForever(observer)

        vm.searchCharacter(query)

        verify(observer).onChanged(SearchState.ShowLoading)
        verify(useCase).searchCharacter(any())
        verify(observer).onChanged(SearchState.HideLoading)
        verify(observer).onChanged(SearchState.LoadData(successComponentList))
    }

    @Test
    fun `when user searches for a keyword verify state when search returns no results`() {
        useCase.searchCharacter(query) willReturn Single.fromCallable { emptyComponentList }
        vm.state.observeForever(observer)

        vm.searchCharacter(query)

        verify(observer).onChanged(SearchState.ShowLoading)
        verify(useCase).searchCharacter(any())
        verify(observer).onChanged(SearchState.HideLoading)
        verify(observer).onChanged(SearchState.LoadData(emptyComponentList))
    }

    @Test
    fun `when user searches for a keyword verify state when search returns a failure`() {
        useCase.searchCharacter(query) willReturn Single.error<Throwable>(dummyError)
        vm.state.observeForever(observer)

        vm.searchCharacter(query)

        verify(observer).onChanged(SearchState.ShowLoading)
        verify(useCase).searchCharacter(any())
        verify(observer).onChanged(SearchState.HideLoading)
        verify(observer).onChanged(SearchState.Error("mock"))
    }
}

package com.star.wars.details.presentation

import androidx.annotation.MenuRes
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.star.wars.andromeda.extensions.makeGone
import com.star.wars.andromeda.extensions.makeVisible
import com.star.wars.andromeda.views.assets.icon.Icon
import com.star.wars.andromeda.views.navbar.AndromedaNavBar
import com.star.wars.common.addTo
import com.star.wars.details.R
import com.star.wars.details.databinding.ActivityDetailsBinding
import com.star.wars.details.domain.DetailsState
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

interface DetailsScreen : LifecycleObserver {
    val event: LiveData<DetailsEvent>

    fun bind(
        binding: ActivityDetailsBinding,
        observable: Observable<DetailsState>
    ): Disposable
}

class DetailsScreenImpl : DetailsScreen {
    private val _event = MutableLiveData<DetailsEvent>()
    override val event: LiveData<DetailsEvent> = _event

    override fun bind(
        binding: ActivityDetailsBinding,
        observable: Observable<DetailsState>
    ): Disposable {
        return CompositeDisposable().apply {
            handleContent(binding, observable, this)
            handleLoading(binding, observable, this)
            handleError(binding, observable, this)
        }
    }

    fun initNavBar(
        binding: ActivityDetailsBinding,
        title:String,
        @MenuRes menu: Int
    ) {
        with(binding.navBarLayout.navBar) {
            setTitle(title)
            setSubtitle(resources.getString(R.string.navbar_details_subtitle))
            showNavigationIcon(
                Icon.NAVIGATION_BACK,
                listener = {
                    _event.value = DetailsEvent.CloseScreen
                })
            inflateMenu(menu) {
                if (it == R.id.menu_theme) {
                    _event.value = DetailsEvent.ShowThemeChooserEvent
                }
            }
            divider = AndromedaNavBar.Divider.LINE
        }
    }

    private fun handleContent(
        binding: ActivityDetailsBinding,
        observable: Observable<DetailsState>,
        bag: CompositeDisposable
    ) {

    }

    private fun handleLoading(
        binding: ActivityDetailsBinding,
        observable: Observable<DetailsState>,
        bag: CompositeDisposable
    ) {
        observable
            .ofType(DetailsState.ShowLoading::class.java)
            .subscribe {
                binding.loadingView.makeVisible()
                binding.contentRV.makeGone()
            }
            .addTo(bag)

        observable
            .ofType(DetailsState.HideLoading::class.java)
            .subscribe {
                binding.contentRV.makeVisible()
                binding.loadingView.makeGone()
            }
            .addTo(bag)
    }

    private fun handleError(
        binding: ActivityDetailsBinding,
        observable: Observable<DetailsState>,
        bag: CompositeDisposable
    ) {
        observable
            .ofType(DetailsState.Error::class.java)
            .subscribe {
            }
            .addTo(bag)
    }
}

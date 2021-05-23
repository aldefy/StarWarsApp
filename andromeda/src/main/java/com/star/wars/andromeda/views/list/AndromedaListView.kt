package com.star.wars.andromeda.views.list

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.star.wars.andromeda.R
import com.star.wars.andromeda.databinding.AndromedaLayoutListBinding
import com.star.wars.andromeda.extensions.ViewComponentNotDrawnHandler
import com.star.wars.andromeda.extensions.readAttributes

class AndromedaListView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : FrameLayout(context, attrs) {
    private var deepLinkHandler: (String) -> Unit = {}
    private var viewComponentNotDrawnHandler: ViewComponentNotDrawnHandler = {}

    enum class Type {
        LINEAR,
        GRID
    }

    private val controller: ComponentController by lazy {
        ComponentController(
            deepLinkHandler = {
                deepLinkHandler(it)
            },
            uncaughtViewData = { data ->
                viewComponentNotDrawnHandler(data)
            }
        )
    }

    private var type: Type = Type.LINEAR
    private var layoutManager: LinearLayoutManager = LinearLayoutManager(context)
    private var binding: AndromedaLayoutListBinding =
        AndromedaLayoutListBinding.inflate(LayoutInflater.from(context), this, true)

    init {
        readAttributes(attrs, R.styleable.AndromedaListView) { typedArray ->
            val typeInt = typedArray.getInt(R.styleable.AndromedaListView_list_type, 0)
            val spanCount = typedArray.getInt(R.styleable.AndromedaListView_grid_span, 3)
            setType(Type.values()[typeInt], spanCount)
        }
        with(binding.eRV) {
            layoutManager = this@AndromedaListView.layoutManager
            setController(controller)
        }
    }

    fun setUpComponents(components: List<ComponentData>) {
        controller.setData(components)
    }

    fun setDeepLinkHandler(deepLinkHandler: (String) -> Unit) {
        this.deepLinkHandler = deepLinkHandler
    }

    fun setViewComponentNotDrawnHandler(viewComponentNotDrawnHandler: ViewComponentNotDrawnHandler) {
        this.viewComponentNotDrawnHandler = viewComponentNotDrawnHandler
    }

    fun getComponentController(): ComponentController {
        return this.controller
    }

    fun setSpanCount(spanCount: Int) {
        if (layoutManager is GridLayoutManager) {
            (layoutManager as GridLayoutManager).spanCount = spanCount
        }
    }

    fun setType(type: Type, spanCount: Int = 3) {
        this.type = type
        layoutManager = if (type == Type.GRID)
            GridLayoutManager(context, spanCount)
                .also {
                    it.spanSizeLookup = controller.spanSizeLookup
                }
        else
            LinearLayoutManager(context)
        if (type == Type.GRID)
            controller.spanCount = spanCount
        binding.eRV.layoutManager = layoutManager
        invalidate()
    }
}

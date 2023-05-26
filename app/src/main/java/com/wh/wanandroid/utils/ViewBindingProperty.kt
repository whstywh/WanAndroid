package com.wh.wanandroid.utils

import android.app.Activity
import android.os.Handler
import android.os.Looper
import android.view.View
import android.view.ViewGroup
import androidx.activity.ComponentActivity
import androidx.annotation.MainThread
import androidx.annotation.RestrictTo
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty

/**
 * 2023/5/26
 * wh
 * desc：ViewBinding 委托
 */
inline fun <V : ViewBinding> ComponentActivity.viewBinding(
    crossinline viewBinder: (View) -> V,
    crossinline viewProvider: (ComponentActivity) -> View = ::findRootView
): ViewBindingProperty<ComponentActivity, V> = ActivityViewBindingProperty {
    viewBinder(viewProvider(it))
}

inline fun < V : ViewBinding> Fragment.viewBinding(
    crossinline viewBinder: (View) -> V,
    crossinline viewProvider: (Fragment) -> View = Fragment::requireView
): ViewBindingProperty<Fragment, V> = FragmentViewBindingProperty {
    viewBinder(viewProvider(it))
}

inline fun <V : ViewBinding> DialogFragment.viewBinding(
    crossinline viewBinder: (View) -> V,
    crossinline viewProvider: (DialogFragment) -> View = DialogFragment::requireView
) : ViewBindingProperty<DialogFragment, V> = DialogFragmentViewBindingProperty {
    viewBinder(viewProvider(it))
}

inline fun <V : ViewBinding> RecyclerView.viewBinding(
    crossinline viewBinder: (View) -> V,
    crossinline viewProvider: (RecyclerView.ViewHolder) -> View = RecyclerView.ViewHolder::itemView
): ViewBindingProperty<RecyclerView.ViewHolder, V> = LazyViewBindingProperty {
    viewBinder(viewProvider(it))
}

inline fun <V : ViewBinding> ViewGroup.viewBinding(
    crossinline viewBinder: (View) -> V,
    crossinline viewProvider: (ViewGroup) -> View = { this }
): ViewBindingProperty<ViewGroup, V> = LazyViewBindingProperty {
    viewBinder(viewProvider(it))
}

// -------------------------

fun findRootView(activity: Activity): View {
    val contentView = activity.findViewById<ViewGroup>(android.R.id.content)
    checkNotNull(contentView) { "Activity has no content view" }
    return when (contentView.childCount) {
        1 -> contentView.getChildAt(0)
        0 -> error("Content view has no children. Provide root view explicitly")
        else -> error("More than one child view found in Activity content view")
    }
}

// -------------------------

@RestrictTo(RestrictTo.Scope.LIBRARY)
class ActivityViewBindingProperty<in T : ComponentActivity, out V : ViewBinding>(
    viewBinder: (T) -> V
) : LifecycleViewBindingProperty<T, V>(viewBinder) {

    override fun getLifecycleOwner(thisRef: T): LifecycleOwner {
        return thisRef
    }
}

class FragmentViewBindingProperty<in T : Fragment, out V : ViewBinding>(
    viewBinder: (T) -> V
) : LifecycleViewBindingProperty<T, V>(viewBinder) {

    override fun getLifecycleOwner(thisRef: T): LifecycleOwner {
        try {
            return thisRef.viewLifecycleOwner
        } catch (ignored: IllegalStateException) {
            error("Fragment doesn't have view associated with it or the view has been destroyed")
        }
    }
}

class DialogFragmentViewBindingProperty<in T : DialogFragment, out V : ViewBinding>(
    viewBinder: (T) -> V
) : LifecycleViewBindingProperty<T, V>(viewBinder) {

    override fun getLifecycleOwner(thisRef: T): LifecycleOwner {
        return if (thisRef.showsDialog) {
            thisRef
        } else {
            try {
                thisRef.viewLifecycleOwner
            } catch (ignored: IllegalStateException) {
                error("Fragment doesn't have view associated with it or the view has been destroyed")
            }
        }
    }
}

@RestrictTo(RestrictTo.Scope.LIBRARY)
class LazyViewBindingProperty<in T : Any, out V : ViewBinding>(
    private val vbBinder: (T) -> V

) : ViewBindingProperty<T, V> {

    private var viewBinding: V? = null

    @MainThread
    override fun getValue(thisRef: T, property: KProperty<*>): V {
        viewBinding?.let { return it }

        return vbBinder(thisRef).also {
            viewBinding = it
        }
    }

    @MainThread
    override fun clear() {
        viewBinding = null
    }
}

// -------------------------

interface ViewBindingProperty<in T : Any, out V : ViewBinding> : ReadOnlyProperty<T, V> {
    @MainThread
    fun clear()
}

abstract class LifecycleViewBindingProperty<in T : Any, out V : ViewBinding>(
    private val vbBinder: (T) -> V
) : ViewBindingProperty<T, V> {

    private var viewBinding: V? = null

    protected abstract fun getLifecycleOwner(thisRef: T): LifecycleOwner

    @MainThread
    override fun getValue(thisRef: T, property: KProperty<*>): V {

        viewBinding?.let { return it }

        val lifecycle = getLifecycleOwner(thisRef).lifecycle
        val vb = vbBinder(thisRef)
        if (lifecycle.currentState != Lifecycle.State.DESTROYED) {
            lifecycle.addObserver(ClearOnDestroyLifecycleObserver(this))
            viewBinding = vb
        }
        return vb
    }

    @MainThread
    override fun clear() {
        viewBinding = null
    }
}

class ClearOnDestroyLifecycleObserver(private val property: LifecycleViewBindingProperty<*, *>) :
    LifecycleEventObserver {

    private companion object {
        private val mainHandler = Handler(Looper.getMainLooper())
    }

    override fun onStateChanged(source: LifecycleOwner, event: Lifecycle.Event) {
        if (event == Lifecycle.Event.ON_DESTROY) {
            mainHandler.post { property.clear() }
        }
    }
}


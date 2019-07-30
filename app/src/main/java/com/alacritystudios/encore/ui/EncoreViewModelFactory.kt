package com.alacritystudios.encore.ui

import javax.inject.Inject
import javax.inject.Provider

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.alacritystudios.encore.dependency.scope.EncoreScope
import javax.inject.Singleton


@EncoreScope
class EncoreViewModelFactory @Inject constructor(
    private val creators: Map<Class<out ViewModel>, @JvmSuppressWildcards Provider<ViewModel>>
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        val creator = creators[modelClass] ?: creators.entries.firstOrNull {
            modelClass.isAssignableFrom(it.key)
        }?.value ?: throw IllegalArgumentException("unknown model class $modelClass")
        try {
            @Suppress("UNCHECKED_CAST")
            return creator.get() as T
        } catch (e: Exception) {
            throw RuntimeException(e)
        }

    }
}
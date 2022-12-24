package id.filab.storyapp.utils

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

inline fun <VM : ViewModel> viewModelFactory(crossinline f: () -> VM) =
    object : ViewModelProvider.NewInstanceFactory() {
        override fun <T : ViewModel> create(aClass: Class<T>):T = f() as T
    }
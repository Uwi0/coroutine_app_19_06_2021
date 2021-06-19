@file:Suppress("UNCHECKED_CAST")

package com.kakapo.coroutineapp.util

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

fun <T: ViewModel, A> singleArgViewModelFactory(constructor: (A) -> T)
: (A) -> ViewModelProvider.NewInstanceFactory{
    return { arg: A ->
        object : ViewModelProvider.NewInstanceFactory(){
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                return constructor(arg) as T
            }
        }
    }
}
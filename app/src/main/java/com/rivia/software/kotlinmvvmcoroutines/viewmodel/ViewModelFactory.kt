package com.rivia.software.kotlinmvvmcoroutines.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.rivia.software.kotlinmvvmcoroutines.repository.ListRepository

class ViewModelFactory(private val listRepository: ListRepository
) : ViewModelProvider.Factory {


    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(ListViewModel::class.java)) {
            ListViewModel(listRepository) as T
        } else {
            error("ViewModel Not Found")
        }
    }

}
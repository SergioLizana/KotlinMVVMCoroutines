package com.rivia.software.kotlinmvvmcoroutines.viewmodel


import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.rivia.software.kotlinmvvmcoroutines.repository.ListRepository
import com.rivia.software.modelapimyjson.Data
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext


class ListViewModel(private val repository: ListRepository) : ViewModel(){

    private val parentJob = Job()

    private val coroutineContext: CoroutineContext
        get() = parentJob + Dispatchers.Default

    private val scope = CoroutineScope(coroutineContext)


    val listData = MutableLiveData<MutableList<Data>>()
    val firstData = MutableLiveData<Data>()

    fun getList(){
        scope.launch {
            //TODO: La logica de convertir la lista al formato correspondiente debería de ser una extension o hacerla en el repository no en el viewmodel
            val sortedList = repository.getData()?.sortedByDescending { it.date }?.filter {
                it.date!=null
            }?.distinctBy {
                it.id
            }?.toMutableList()
            firstData.postValue(sortedList?.get(0))
            sortedList?.removeAt(0)
            listData.postValue(sortedList)

        }
    }

    fun cancelAllRequests() = coroutineContext.cancel()

}
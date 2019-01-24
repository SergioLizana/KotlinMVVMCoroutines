package com.rivia.software.kotlinmvvmcoroutines.repository

import android.util.Log
import com.rivia.software.modelapimyjson.ApiMyJson
import com.rivia.software.modelapimyjson.Data

//TODO: Se podría hacer un repository generico para tener el codigo mas limpio y simplificado
class ListRepository(private val api : ApiMyJson) {
  
    suspend fun getData() : MutableList<Data>?{
    
        var data : MutableList<Data>? = null

        val dataRequest = api.getList()

        try {        
             val response = dataRequest.await()


             if(response.isSuccessful){            
                val dataResponse = response.body()
                data = dataResponse
             }else{
                 //TODO: Tendría que devolver el error correspondiente an un wrapper del objeto Data
                Log.d("error", response.errorBody().toString())
             }   
         }catch (e: Exception){
            Log.d("error", e.message)
         }
    
        return data
    }

}
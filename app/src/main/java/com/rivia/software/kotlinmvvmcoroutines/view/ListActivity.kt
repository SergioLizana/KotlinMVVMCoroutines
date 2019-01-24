package com.rivia.software.kotlinmvvmcoroutines.view

import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.rivia.software.modelapimyjson.ApiMyJson
import com.rivia.software.modelapimyjson.Data
import com.rivia.software.restmodule.client.ApiClient
import kotlinx.android.synthetic.main.activity_list.*
import androidx.recyclerview.widget.DividerItemDecoration
import com.rivia.software.kotlinmvvmcoroutines.*
import com.rivia.software.kotlinmvvmcoroutines.repository.ListRepository
import com.rivia.software.kotlinmvvmcoroutines.utils.*
import com.rivia.software.kotlinmvvmcoroutines.view.adapter.GenericAdapter
import com.rivia.software.kotlinmvvmcoroutines.view.adapter.ViewHolder
import com.rivia.software.kotlinmvvmcoroutines.viewmodel.ListViewModel
import com.rivia.software.kotlinmvvmcoroutines.viewmodel.ViewModelFactory
import kotlinx.android.synthetic.main.recycler_row.*


class ListActivity : AppCompatActivity() {
    //TODO: Injectarlo con Dagger2 / Koin  o similar.
    private val repository : ListRepository =
        ListRepository(ApiClient().createService(ApiMyJson::class.java))

    private lateinit var viewModel: ListViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list)

        viewModel = ViewModelProviders.of(this,
            ViewModelFactory(repository)
        )[ListViewModel::class.java]
        loader.visibility = View.VISIBLE
        group.visibility = View.GONE
        viewModel.getList()

        //TODO: Debería devolver un Wrapper del objeto DATA con solo los valores que se van a utilizar para desacoplar la vista del API
        //TODO: Se podría crear un OBSERVER customizado que en vez de que devolviera un metodo devolviera 2 con el onError y el onReceive
        viewModel.listData.observe(this,Observer<List<Data>> {
            loader.visibility = View.GONE
            group.visibility = View.VISIBLE
            initAdapter(it)
        })
        viewModel.firstData.observe(this,Observer<Data>{
            printFirstData(it)
        })
    }

    //TODO: Este código está duplicado. La fila de la lista y el primer elemento de la lista deberian de ser un CustomView con esta lógica
    private fun printFirstData(data: Data){
        date.text = data.date?.let { it.formatToServerDateDefaults() }
        val amountDouble = data.amount?.let { it ->
            data.fee?.let { fee -> it.parseAmountWithFeeAsDouble(fee) }
        }.apply {
            this?.let { setAmountColor(it) }
        }
        amount.text = amountDouble?.let {
            it.withDecimals()?.let { doubleParsed ->
                doubleParsed.addCurrency()
            }
        }
        description.text = data.description?.let { it }
    }


    private fun setAmountColor(dataAmount: Double) {
        if (dataAmount < 0) {
            amount.setTextColor(Color.parseColor("#FF0000"))
        } else if (dataAmount > 0) {
            amount.setTextColor(Color.parseColor("#00FF00"))
        }

    }

    private fun initAdapter(list : List<Data>){
        val myAdapter = object : GenericAdapter<Data>(list) {
            override fun getLayoutId(position: Int, obj: Data): Int {
                return R.layout.recycler_row
            }

            override fun getViewHolder(view: View, viewType: Int): RecyclerView.ViewHolder {
                return ViewHolder(view)
            }
        }
        val linearLayoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager= LinearLayoutManager(this)
        recyclerView.setHasFixedSize(true)
        recyclerView.adapter=myAdapter
        val dividerItemDecoration = DividerItemDecoration(
            recyclerView.context,
            linearLayoutManager.orientation
        )
        recyclerView.addItemDecoration(dividerItemDecoration)
    }

    override fun onPause() {
        super.onPause()
        viewModel.cancelAllRequests()
    }
}

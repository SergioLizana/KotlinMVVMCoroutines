package com.rivia.software.kotlinmvvmcoroutines.view.adapter

import android.graphics.Color
import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.rivia.software.kotlinmvvmcoroutines.R
import com.rivia.software.kotlinmvvmcoroutines.utils.*
import com.rivia.software.modelapimyjson.Data

class ViewHolder : RecyclerView.ViewHolder, GenericAdapter.Binder<Data> {

    var date: TextView
    var amount: TextView
    var description: TextView

    constructor(itemView: View) : super(itemView) {
        date = itemView.findViewById(R.id.date)
        amount = itemView.findViewById(R.id.amount)
        description = itemView.findViewById(R.id.description)
    }

    override fun bind(data: Data) {
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
}
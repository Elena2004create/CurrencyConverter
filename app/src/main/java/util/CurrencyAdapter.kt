package util

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.currencyconverter.R
import models.Valute

class CurrencyAdapter(private val dataSet: MutableList<Valute>) :
    RecyclerView.Adapter<CurrencyAdapter.CurrencyViewHolder>() {

    class CurrencyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val name: TextView
        val value: TextView

        init {
            name = view.findViewById(R.id.name)
            value = view.findViewById(R.id.value)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CurrencyViewHolder {
        return CurrencyViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.currency_item, parent, false)
        )
    }

    override fun onBindViewHolder(holder: CurrencyViewHolder, position: Int) {
        val valute = dataSet[position]
        Log.d("CurrencyAdapter", "Valute Name: ${valute.Name}, Value: ${valute.Value}")
        holder.name.text = dataSet[position].Name
        holder.value.text =
            holder.itemView.context.getString(R.string.currency_value, dataSet[position].Value)
    }

    override fun getItemCount(): Int {
        return dataSet.size
    }

    fun updateData(newData: Map<String, Valute>?) {
        if (newData != null) {
            dataSet.clear()
            dataSet.addAll(newData.values)
            notifyDataSetChanged()
        }
    }
}
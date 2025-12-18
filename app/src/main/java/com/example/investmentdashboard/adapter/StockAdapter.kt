package com.example.investmentdashboard.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.investmentdashboard.R
import com.example.investmentdashboard.model.StockChartData
import com.example.investmentdashboard.model.StockItem
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet

class StockAdapter(private val stockCharts: MutableList<StockChartData>) : RecyclerView.Adapter<StockAdapter.StockViewHolder>(){

    class StockViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val chart: LineChart = itemView.findViewById(R.id.lineChart)
        val tickerText: TextView = itemView.findViewById(R.id.tickerText)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StockViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_stock, parent, false)
        return StockViewHolder(view)
    }

    override fun onBindViewHolder(holder: StockViewHolder, position: Int) {
        val stock = stockCharts[position]
        holder.tickerText.text = stock.ticker

        // Prepare chart entries
        val entries = stock.candles.mapIndexed { index, candle ->
            Entry(index.toFloat(), candle.close.toFloat())
        }

        val dataSet = LineDataSet(entries, "Price")
        dataSet.color = Color.BLUE
        dataSet.valueTextColor = Color.BLACK
        dataSet.setDrawCircles(false)
        dataSet.lineWidth = 2f

        val lineData = LineData(dataSet)
        holder.chart.data = lineData
        holder.chart.invalidate() // refresh chart
    }

    override fun getItemCount(): Int = stockCharts.size

    fun updateData(newData: List<StockChartData>) {
        stockCharts.clear()
        stockCharts.addAll(newData)
        notifyDataSetChanged()
    }
}


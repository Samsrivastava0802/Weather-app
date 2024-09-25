package com.rsstudio.weather.ui.main.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.os.Build
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.rsstudio.weather.R
import com.rsstudio.weather.app.App
import com.rsstudio.weather.data.network.model.Hourly
import com.rsstudio.weather.ui.main.weather.WeatherType
import com.rsstudio.weather.util.AppHelper
import java.util.*

class HourlyForecastAdapter(
    private var context: Context,
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var list: MutableList<Hourly> = mutableListOf()
    private var currentHour: String = ""

    var logTag = "@HourlyForecastAdapter"

    inner class ItemViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        var tvDate: TextView = view.findViewById(R.id.tvDate)
        var tvHourlyTemperature: TextView = view.findViewById(R.id.tvHourlyTemperature)
        var ivWeatherType: ImageView = view.findViewById(R.id.ivWeatherType)

        var container: RelativeLayout = view.findViewById(R.id.rlRoot)

        @RequiresApi(Build.VERSION_CODES.O)
        @SuppressLint("SetTextI18n", "ResourceAsColor")
        fun onBind(item: Hourly, position: Int) {

            // Icon setup
            var pp = WeatherType.fromWMO(item.weathercode[position])
            ivWeatherType.setImageResource(pp.iconRes)

            // temperature
            tvHourlyTemperature.text = item.temperature_2m[position].toString() + "\u2103"

            var temp = Calendar.getInstance().get(Calendar.HOUR_OF_DAY)

            var currentHour = ""
            currentHour = if(temp < 10){
                "0$temp"
            }else{
                temp.toString()
            }

            Log.d(logTag, "currentHour: $currentHour")

            // current hour
            if (AppHelper.compareForMyHourOFTheDay(item.time[position],currentHour)){
                tvDate.text = "Now"
                tvDate.setTextColor(ContextCompat.getColor(context, R.color.yellow))

            }else{
                tvDate.text = AppHelper.getPatternOfHour(item.time[position])
                tvDate.setTextColor(ContextCompat.getColor(context, R.color.white2))
            }

        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.view_hourly_forecast, parent, false)
        return ItemViewHolder(itemView)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        val item = list
        if (holder is HourlyForecastAdapter.ItemViewHolder) {
            holder.container.animation = AnimationUtils.loadAnimation(context,R.anim.anim_fade_scale)
            holder.onBind(item[0], position)
        }
    }

    fun submitList(newList: Hourly) {
        list.clear()
        list.add(newList)
        notifyDataSetChanged()
    }


    override fun getItemCount(): Int {
        if (list.size != 0) {
            return 24
        }
        return 0
    }

}
/*
this screen flow
1 sabse phle hmlog yaha RecyclerView.Adapter adapter class me extend kar rhe hai
  uske bad yaha hmlog  inner class bana rhe hai  ItemViewHolder()
  jisme hmlog id assign kar rhe hai sabka phir usi me inner class me onBind funct bana rhe hai
  jisme bata rhe hai agar position = 0 hi to if condition me today show karo nhi to else me jao aur hour ka naam show karo
2. ab fun onCreateViewHolder ye chlega jisme hmlog uska layout bata rhe hai kasa dikhega
3 ab onBindViewHolder function bana rhe hai yaha hmlog data bind kar rhe hai specific position ke liye
  kittne no of items ke liye chalega
4. phir getItemCount wala fun banaye hai  jo size return karta hai
 */
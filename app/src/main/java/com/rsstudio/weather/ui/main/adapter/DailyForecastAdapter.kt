package com.rsstudio.weather.ui.main.adapter


import android.annotation.SuppressLint
import android.content.Context
import android.os.Build
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
import com.rsstudio.weather.data.network.model.Daily
import com.rsstudio.weather.data.network.model.Hourly
import com.rsstudio.weather.ui.main.weather.WeatherType
import com.rsstudio.weather.util.AppHelper


class DailyForecastAdapter(
    private var context: Context,
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var list: MutableList<Daily> = mutableListOf()

    var logTag = "@DailyForecastAdapter"

    inner class ItemViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        var tvWeek: TextView = view.findViewById(R.id.tvWeek)
        var tvDate: TextView = view.findViewById(R.id.tvDate)
        var tvDailyTemperature: TextView = view.findViewById(R.id.tvDailyTemperature)
        var ivWeatherType: ImageView = view.findViewById(R.id.ivWeatherType)

        var container: RelativeLayout = view.findViewById(R.id.rlRoot)

        @RequiresApi(Build.VERSION_CODES.O)
        @SuppressLint("SetTextI18n", "ResourceAsColor")
        fun onBind(item: Daily, position: Int) {

            //set icon
            var pp = WeatherType.fromWMO(item.weathercode[position])
            ivWeatherType.setImageResource(pp.iconRes)

            // temperature
            tvDailyTemperature.text = item.temperature_2m_max[position].toString() + "\u2103"

            if (position == 0) {
                tvWeek.text = "Today"
                tvDate.text = AppHelper.getPatternOfDate(item.time[position])

                tvWeek.setTextColor(ContextCompat.getColor(context, R.color.yellow))
                tvDate.setTextColor(ContextCompat.getColor(context, R.color.yellow))
            } else {
                tvWeek.text = AppHelper.getWeekDayName(item.time[position])
                tvDate.text = AppHelper.getPatternOfDate(item.time[position])

                tvWeek.setTextColor(ContextCompat.getColor(context, R.color.white2))
                tvDate.setTextColor(ContextCompat.getColor(context, R.color.white2))
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.view_forecast_item, parent, false)
        return ItemViewHolder(itemView)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        val item = list
        if (holder is DailyForecastAdapter.ItemViewHolder) {
            holder.container.animation = AnimationUtils.loadAnimation(context,R.anim.anim_fade_scale)
            holder.onBind(item[0], position)
        }
    }

    fun submitList(newList: Daily) {
        list.clear()
        list.add(newList)
        notifyDataSetChanged()
    }


    override fun getItemCount(): Int {
        if (list.size != 0) {
            return list[0].time.size
        }
        return 0
    }

}

/*
this screen flow
1 sabse phle hmlog yaha RecyclerView.Adapter adapter class me extend kar rhe hai
  uske bad yaha hmlog  inner class bana rhe hai  ItemViewHolder()
  jisme hmlog id assign kar rhe hai sabka phir usi me inner class me onBind funct bana rhe hai
  jisme bata rhe hai agar position = 0 hi to if condition me today show karo nhi to else me jao aur week ka naam show karo
2. ab fun onCreateViewHolder ye chlega jisme hmlog uska layout bata rhe hai kasa dikhega
3 ab onBindViewHolder function bana rhe hai yaha hmlog data bind kar rhe hai specific position ke liye
  kittne no of items ke liye chalega
4. phir getItemCount wala fun banaye hai  jo size return karta hai
 */
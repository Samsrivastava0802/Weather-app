package com.rsstudio.weather.ui.main


import android.annotation.SuppressLint
import android.location.Geocoder
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.rsstudio.weather.R
import com.rsstudio.weather.data.local.preference.PreferenceProvider
import com.rsstudio.weather.data.network.model.Weather
import com.rsstudio.weather.data.network.repository.MainRepository
import com.rsstudio.weather.databinding.ActivityMainBinding
import com.rsstudio.weather.ui.base.BaseActivity
import com.rsstudio.weather.ui.main.adapter.DailyForecastAdapter
import com.rsstudio.weather.ui.main.adapter.HourlyForecastAdapter
import com.rsstudio.weather.ui.main.viewModel.MainViewModel
import com.rsstudio.weather.ui.main.weather.WeatherType
import com.rsstudio.weather.util.GetTimeAgo
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivity(), View.OnClickListener {

    lateinit var binding: ActivityMainBinding

    private lateinit var dailyForecastAdapter: DailyForecastAdapter
    private lateinit var hourlyForecastAdapter: HourlyForecastAdapter

    private val viewModel: MainViewModel by viewModels()


    var logTag = "@MainActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        //val a = ViewModelProvider(this).get(MainViewModel::class.java )

        //
        initAction()
        initRecyclerViews()
        initObservers()
    }

    private fun initAction() {
        binding.ivSearch.setOnClickListener(this)

        binding.srlSwipeRefresh.setOnRefreshListener {

           binding.srlSwipeRefresh.isRefreshing = true
            viewModel.weatherData()
            viewModel.getWeatherData(
                pref.getLatitude().toDouble(),
                pref.getLongitude().toDouble()
            )
            pref.saveLastRefreshedTime(System.currentTimeMillis())
        }
    }

    @SuppressLint("SetTextI18n")
    private fun initObservers() {

        viewModel.locationPointsData.observe(this) {

            if (it != null) {
                viewModel.getWeatherData(it[0], it[1])
            }
        }

        viewModel.weatherData.observe(this) {

            if (it.isSuccessful) {
                val list: MutableList<Weather> = mutableListOf()
                list.add(it.body()!!)
                dailyForecastAdapter.submitList(list[0].daily)
                hourlyForecastAdapter.submitList(list[0].hourly)

                //
                var pp = WeatherType.fromWMO(list[0].daily.weathercode[0])
                binding.ivWeatherType.setImageResource(pp.iconRes)
                binding.tvWeatherCode.text = pp.weatherDesc

                //
                binding.tvTemperature.text =
                    list[0].daily.temperature_2m_max[0].toString() + "\u2103"
                binding.tvWeatherText.text =
                    "Feels" + " " + "like" + " " + (list[0].daily.temperature_2m_max[0] - 0.5).toString() + "\u2103"

                //
                binding.tvWindDirectionValue.text =
                    list[0].hourly.windspeed_10m[0].toString() + "WNW"
                binding.tvWindSpeedValue.text =
                    list[0].hourly.relativehumidity_2m[0].toString() + "km/h"
                binding.tvPressureValue.text = list[0].hourly.pressure_msl[0].toString() + "hPa"

                //
                binding.rlRoot.setBackgroundResource(R.drawable.background)
                binding.topView.visibility = View.VISIBLE
                //
                binding.tvLocation.text = pref.getAddress()

                // time
                //
                Log.d(logTag, "initObservers: " + pref.getLastRefreshedTime())
                if (pref.getLastRefreshedTime().toInt() == 0) {
                    binding.tvTime.text =
                        GetTimeAgo.getTimeAgo(System.currentTimeMillis(), this)
                    pref.saveLastRefreshedTime(System.currentTimeMillis())
                } else {
                    binding.tvTime.text =
                        GetTimeAgo.getTimeAgo(pref.getLastRefreshedTime(), this)
                }

               binding.srlSwipeRefresh.isRefreshing = false
                binding.iLoader.visibility = View.GONE


            } else {
                Log.d(logTag, "error: ${it.errorBody()} ")
                binding.iLoader.visibility = View.GONE
                binding.iError.tvError.text = it.errorBody().toString()
                binding.iError.rlRoot.visibility = View.VISIBLE
            }


        }


    }

    // for searching location
    private fun search() {

        var searchText = binding.searchInput.text


        if (searchText.isNotEmpty() || searchText != null) {

            try {
                // get latitude and longitude through geocoder
                val gc = Geocoder(this)
                var addresses = gc.getFromLocationName(searchText.toString(), 2)
                var address = addresses[0]

                Log.d(logTag, "searchText: $searchText")
                Log.d(logTag, "search: " + address.latitude)
                Log.d(logTag, "search: " + address.longitude)
                Log.d(logTag, "search: " + address.locality)
            } catch (e: Exception) {
                Log.d(logTag, "search: " + e.message)
            }


        }
    }

    private fun initRecyclerViews() {

        // daily
        val llm = LinearLayoutManager(
            this,
            LinearLayoutManager.HORIZONTAL,
            false
        )
        binding.rvDailyForecast.setHasFixedSize(true)
        binding.rvDailyForecast.layoutManager = llm
        dailyForecastAdapter = DailyForecastAdapter(this)
        binding.rvDailyForecast.adapter = dailyForecastAdapter

        // hourly
        val llm2 = LinearLayoutManager(
            this,
            LinearLayoutManager.HORIZONTAL,
            false
        )
        binding.rvHourlyForecast.setHasFixedSize(true)
        binding.rvHourlyForecast.layoutManager = llm2
        hourlyForecastAdapter = HourlyForecastAdapter(this)
        binding.rvHourlyForecast.adapter = hourlyForecastAdapter


    }

    override fun onClick(p0: View?) {

        when (p0?.id) {

            R.id.ivSearch -> {
                search()
            }

        }
    }



}

/*
1. sabse phele onCreate function chalega
    usme 3 function call ho rha h initAction(), initRecyclerViews(), initObservers()
2. uske bad initAction() function ke andar do chezz ho rha h
     ivSearch.setOnClickListener aur srlSwipeRefresh.setOnRefreshListener chal rha h
3. ab initRecyclerViews() ye wala function chalega  jisme hmlog
   2 variable banaye hai ek hour ke liye and ek daily ke liye aur hm chahte hai ki ye horizontal me chaiye
4. ab initObservers() function chalega isme aisa ho rha hai hm log data ko observe kar rhe hai
 */

//class MainActivity : AppCompatActivity(){
//    // step 1 creating a MainActivity
//    lateinit var testViewModel: TestViewModel
//
//    // step 4 - after making an xml in that we have 2 things one is textView and button
//    //          here we are making a variable by adding a getter function and same for button
//
//    private val factsTextView : TextView
//        get() = findViewById(R.id.factsTextView)
//
//    private val btnUpdate : Button
//        get() = findViewById(R.id.btnUpdate)
//
//
//    override fun onCreate(savedInstanceState: Bundle?){
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_main)
//        testViewModel = ViewModelProvider(this).get(TestViewModel::class.java)
//
//        // step 3 - after that from the mainViewModel class we are calling this factsLiveData variable
//        //          in that we are observing the data which is of type String
//
//        testViewModel.factsLiveData.observe(this, Observer{
//          factsTextView.text = it
//        } )
//
//        // step 5 - here we are adding clickListener on button
//        btnUpdate.setOnClickListener {
//          testViewModel.updateLiveData()
//        }
//    }
//
//}
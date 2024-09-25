package com.rsstudio.weather.ui.main.viewModel

import android.annotation.SuppressLint
import android.location.Address
import android.location.Geocoder
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.rsstudio.weather.app.App
import com.rsstudio.weather.data.local.preference.PreferenceProvider
import com.rsstudio.weather.data.network.model.Weather
import com.rsstudio.weather.data.network.repository.MainRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject


@HiltViewModel
class MainViewModel
@Inject
constructor(
    private val repository: MainRepository,
    private val app: App,
    private val pref: PreferenceProvider
) : ViewModel() {

    var logTag = "@MainViewModel"

    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient
   // var a : Int? = null

    // the pattern that i usually follow
    private val _weatherData: MutableLiveData<Response<Weather>> = MutableLiveData()
    val weatherData: LiveData<Response<Weather>> get() = _weatherData


    private var _locationPointsData: MutableLiveData<MutableList<Double>> = MutableLiveData()
    val locationPointsData: LiveData<MutableList<Double>> get() = _locationPointsData

//    init {
//        getLocationPoints()
//    }


    @SuppressLint("MissingPermission")
//    private fun getLocationPoints() {
//
//        fusedLocationProviderClient =
//            LocationServices.getFusedLocationProviderClient(app.applicationContext)
//
//        var temp: MutableList<Double> = mutableListOf()
////        var temp2 = listOf("sam","hi")
////        var temp3 : List<String> = listOf()
////        temp3.add()
//
//        fusedLocationProviderClient.lastLocation.addOnSuccessListener { location ->
//
//            if (location!= null){
//                var latitude = location.latitude
//                var longitude = location.longitude
//
//                temp.add(latitude)
//                temp.add(longitude)
//
//                // save them
//                pref.saveLatitude(latitude.toFloat())
//                pref.saveLongitude(longitude.toFloat())
//                //
//                Log.d(logTag, "getLocationPoints: " + "line no 68")
//                //
//                Log.d(logTag, "getLocationPoints: " + location.latitude)
//                Log.d(logTag, "getLocationPoints: " + location.longitude)
//                getAddress(latitude,longitude)
//
//                _locationPointsData.value = temp
//                Log.d(logTag, "getLocationPoints1: " + location.latitude)
//                Log.d(logTag, "getLocationPoints1: " + location.longitude)
//
//            }
//            else{
//                Log.d(logTag, "getLocationPoints: " + "line no 75")
//                temp.add(pref.getLatitude().toDouble())
//                temp.add(pref.getLongitude().toDouble())
//                _locationPointsData.value = temp
//            }
//
//        }.addOnFailureListener {
//            temp.add(pref.getLatitude().toDouble())
//            temp.add(pref.getLongitude().toDouble())
//            _locationPointsData.value = temp
//        }
//    }
    private fun getAddress(latitude: Double, longitude: Double) {

        Log.d(logTag, "getAddress: " + "line no 100")

        val addresses: List<Address>
        Log.d(logTag, "getAddress: " + "line no 103")
        val geocoder = Geocoder(app.applicationContext)

        addresses = geocoder.getFromLocation(
            latitude,
            longitude,
            1
        )
        Log.d(logTag, "getAddress: " + "line no 105")
        val city: String? = addresses[0].locality
        val state: String? = addresses[0].adminArea

        Log.d(logTag, "city: line no 115 + $city")

        if (city != null){
            pref.saveAddress(city!!)
        }else{
            pref.saveAddress(state!!)
        }
    }

    fun getWeatherData(latitude: Double, longitude: Double) {

        viewModelScope.launch {
            val result = repository.getWeatherData(latitude, longitude)
            _weatherData.value = result
        }
    }

    fun updateWeatherData(){

    }

    fun weatherData(){
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(app.applicationContext)
        fusedLocationProviderClient.lastLocation.addOnSuccessListener{
            if (it != null){
                val latitude = it.latitude
                val longitude = it.longitude

                if(latitude != pref.getLatitude().toDouble()){
                    pref.saveLatitude(latitude.toFloat())
                    pref.saveLongitude(longitude.toFloat())

                    getAddress(it.latitude, it.longitude)
                    getWeatherData(latitude, longitude)
                }
                else {
                    handleFailureCondition()
                }
            }
        }.addOnFailureListener {
            handleFailureCondition()
        }
    }
    private fun handleFailureCondition(){
        getWeatherData(pref.getLatitude().toDouble(),pref.getLongitude().toDouble())

    }

}
//
///*
//this screen flow
//1. sabse phele getLocationPoints() ye function banaye
//   isme hmlog location ko check kar rhe hai kya wo null hai
//   agar nhi hai to temp variable me latitude and longitude
//   add kar rhe aur save kar rhe hai preferenceProvider me
//   aur float me convert kar rhe hai
//2. ab getAddress() wala function bana rhe h isme hmlog address nikal rhe h
//3. yaha ab getWeatherData() function bana rhe h weather ka data nikal rhe h
// */
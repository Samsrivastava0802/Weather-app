package com.rsstudio.weather.test

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

// step 2- after that creating a MainViewModel class in which we are extending ViewModel
//           in that creating a variable of liveData which is of mutable type

class TestViewModel : ViewModel() {
    val factsLiveData = MutableLiveData<String>("This is a fact")
    var joker = "This is a fact"

    // step 6 - here we are updating the data

    fun updateLiveData(){
       factsLiveData.value = "Another fact"
        joker = "yo"
    }

}



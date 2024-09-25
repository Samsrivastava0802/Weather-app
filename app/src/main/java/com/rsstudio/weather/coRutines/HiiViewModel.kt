package com.rsstudio.weather.coRutines

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class HiiViewModel : ViewModel() {

    /* Parts of coroutine
     1. Scope
     2. context
     3. builder
     */

    /*
       Types of scope:-
       1. GlobalScope
       2. CoroutineScope
       3. viewModelScope
       4. MainScope
     */

    /*
      Types of context (types of Dispatcher):-
      1. Main(Dispatchers.Main)
      2. IO(Dispatchers.Main)
      3. Default(Dispatchers.Default)
      4. Unconfined(Dispatchers.Unconfined)
    */

    /*
       Types of builder (types of Dispatcher):-
       1. launch
       2. async
     */

    init {
        viewModelScope.launch() {

        }

        viewModelScope.async(Dispatchers.Unconfined) {

        }

        CoroutineScope(Dispatchers.IO).launch {
        }

        GlobalScope.launch(Dispatchers.IO) {

        }

        MainScope().launch(Dispatchers.IO) {

        }
    }


    fun api1() {
        print("fetching users")
    }

    fun api2() {
        print("fetching user post")
    }

}
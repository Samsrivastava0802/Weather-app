package com.rsstudio.weather.test

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.rsstudio.weather.R

class TestActivity : AppCompatActivity() {
    // step 1 creating a TestActivity
    lateinit var testViewModel: TestViewModel

    // step 4 - after making an xml in that we have 2 things one is textView and button
    //          here we are making a variable by adding a getter function and same for button

    private val factsTextView : TextView
        get() = findViewById(R.id.factsTextView)

    private val btnUpdate : Button
        get() = findViewById(R.id.btnUpdate)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test)
      testViewModel = ViewModelProvider(this).get(TestViewModel::class.java)
        factsTextView.text = testViewModel.joker


        // step 3 - after that from the mainViewModel class we are calling this factsLiveData variable
        //          in that we are observing the data which is of type String

//        testViewModel.factsLiveData.observe(this, Observer{
//            factsTextView.text = it
//        } )
        // step 5 - here we are adding clickListener on button
        btnUpdate.setOnClickListener {
            testViewModel.updateLiveData()
        }

    }
}
package com.rsstudio.weather.ui.splash

import android.Manifest
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Toast
import androidx.core.content.ContextCompat.startActivity
import androidx.databinding.DataBindingUtil
import com.rsstudio.weather.R
import com.rsstudio.weather.databinding.ActivitySplashBinding
import com.rsstudio.weather.test.TestActivity
import com.rsstudio.weather.ui.base.BaseActivity
import com.rsstudio.weather.ui.main.MainActivity
import com.vmadalin.easypermissions.EasyPermissions
import com.vmadalin.easypermissions.dialogs.SettingsDialog

class SplashActivity : BaseActivity() , EasyPermissions.PermissionCallbacks {

    private lateinit var binding: ActivitySplashBinding

    val PERMISSION_LOCATION_REQUEST_CODE = 1
    fun getName(): String {
        return "sam"
    }

    //    /*
//    this screen flow :-
//    1. first in the if condition has locationPermission called
//       in this if we have given the permission then this will go to main Act
//       or else in will go in the else condition
//    2. now after moving to requestLocationPermission() this function tells that
//       ye function req karta hai hmare location ke liye aur isko 4 parametrs chaiye hota hai
//       1 current act 2 rational ye ek dialog show karta hai jisme hm kuch bhi daal sakte hai hmlog text
//       3 req code and 4 hmlog batate hai kis type ka permission chaiye FINE_LOCATION ya COARSE_LOCATION
//    3. ab agar hmlog permission denied kar diye to wo dialog khulta hai rational wala
//    4 lekin hmlog wapass deny kiye to onPermissionsDenied ye wala function chalta hai
//      ye func check karta ki kya permission denied permanent ho gya hai agr nhi hau hai to ek baar aur req karo
//      aur permanent denied ho gya hai to setting screen kohl do
//     */
//
//
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_splash)
        if (hasLocationPermission()) {
            gotoMainActivity()
        } else {
            requestLocationPermission()
        }
//       val intent = Intent(this, TestActivity::class.java)
//        startActivity(intent)
    }

    private fun gotoMainActivity() {
        Handler(Looper.getMainLooper()).postDelayed(
            Runnable {
                val intent = Intent(this@SplashActivity, MainActivity::class.java)
                startActivity(intent)
                finish()
            }, 500
        )
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this)
    }

    private fun hasLocationPermission() =
        EasyPermissions.hasPermissions(
            this,
            Manifest.permission.ACCESS_FINE_LOCATION
        )

    private fun requestLocationPermission() {
        EasyPermissions.requestPermissions(
            this,
            "This application cannot work without Location Permission.",
            PERMISSION_LOCATION_REQUEST_CODE,
            Manifest.permission.ACCESS_FINE_LOCATION
        )
    }


    override fun onPermissionsDenied(requestCode: Int, perms: List<String>) {
        if (EasyPermissions.somePermissionPermanentlyDenied(this, perms)) {
            SettingsDialog.Builder(this).build().show()
        } else {
            requestLocationPermission()
        }
    }

    override fun onPermissionsGranted(requestCode: Int, perms: List<String>) {
        //
        gotoMainActivity()
    }
}

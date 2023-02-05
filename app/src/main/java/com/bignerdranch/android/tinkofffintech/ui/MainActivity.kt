package com.bignerdranch.android.tinkofffintech.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bignerdranch.android.tinkofffintech.databinding.ActivityMainBinding
import com.bignerdranch.android.tinkofffintech.network.CheckConnection

class MainActivity : AppCompatActivity() {

    private val checkConnection by lazy { CheckConnection(application) }

    private val binding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setupUi()
    }

    private fun setupUi() = with(binding) {
        checkConnection.observe(this@MainActivity) {
            if (it) {
                val movieListFragment = MovieListFragment.newInstance()
                supportFragmentManager.beginTransaction()
                    .replace(binding.mainScreen.id, movieListFragment)
                    .commit()
            } else {
                val noInternetConnectionFragment = NoInternetConnectionFragment.newInstance()
                supportFragmentManager.beginTransaction()
                    .replace(binding.mainScreen.id, noInternetConnectionFragment)
                    .commit()
            }
        }
    }
}
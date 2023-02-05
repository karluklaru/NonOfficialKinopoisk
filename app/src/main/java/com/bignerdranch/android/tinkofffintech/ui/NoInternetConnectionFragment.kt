package com.bignerdranch.android.tinkofffintech.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bignerdranch.android.tinkofffintech.databinding.FragmentNoInternetConnectionBinding

class NoInternetConnectionFragment : Fragment() {

    private lateinit var binding : FragmentNoInternetConnectionBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentNoInternetConnectionBinding.inflate(inflater, container, false)
        return binding.root
    }

    companion object {
        fun newInstance(): NoInternetConnectionFragment {
            return NoInternetConnectionFragment()
        }
    }
}
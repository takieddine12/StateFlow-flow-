package com.example.stateflow

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.ActionBar
import androidx.lifecycle.lifecycleScope
import com.example.stateflow.databinding.ActivityMainBinding
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import java.lang.Error

@ExperimentalCoroutinesApi
class MainActivity : AppCompatActivity() {

    private lateinit var _binding : ActivityMainBinding
    private val binding get() = _binding
    private val mainViewModel: MainViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        _binding = ActivityMainBinding.inflate(layoutInflater)

         // TODO : STATE FLOW ( Equivalent of LiveData)

        mainViewModel.loginCredentials(
            binding.userName.text.toString(),
            binding.userPassword.text.toString()
        )

        lifecycleScope.launchWhenStarted {
            // launch this coroutine when activity is in start cycle
            mainViewModel.stateFlow.collect {
                when(it) {
                    is MainViewModel.UiStates.Loading -> {
                        Snackbar.make(binding.layout,"At Loading State",Snackbar.LENGTH_SHORT).show()
                        // Show Progress or sth
                    }
                    is MainViewModel.UiStates.Success -> {
                        Snackbar.make(binding.layout,"At Success State",Snackbar.LENGTH_SHORT).show()
                    }
                    is MainViewModel.UiStates.Error -> {
                        Snackbar.make(binding.layout,"At Error State",Snackbar.LENGTH_SHORT).show()
                    }
                    else -> Unit
                }
            }
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        //_binding = null
    }
}
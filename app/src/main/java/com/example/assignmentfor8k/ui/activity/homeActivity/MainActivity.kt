package com.example.assignmentfor8k.ui.activity.homeActivity

import android.content.Context
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.example.assignmentfor8k.R
import com.example.assignmentfor8k.applicationClass.AppApplicationClass
import com.example.assignmentfor8k.database.AppDataBase
import com.example.assignmentfor8k.databinding.ActivityMainBinding
import com.example.assignmentfor8k.repository.ChipRepository
import com.example.assignmentfor8k.repository.NewsRepository
import com.example.assignmentfor8k.retrofit.newsApi.newRetrofit.NewsRetrofitInstance
import com.example.assignmentfor8k.ui.activity.homeActivity.viewModel.MainViewModel
import com.example.assignmentfor8k.ui.activity.homeActivity.viewModel.MainViewModelProviderActivity
import com.example.assignmentfor8k.util.Constants.getAllTheChips
import com.example.assignmentfor8k.util.SharedPrefFunc.getChipDataBase
import com.example.assignmentfor8k.util.SharedPrefFunc.updateChipDataBase
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    lateinit var viewModel: MainViewModel
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
      //  enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        setUpViewModel()
        setUpChipItems()
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.newsNavHostFragment) as NavHostFragment
        val navController = navHostFragment.navController
        NavigationUI.setupWithNavController(binding.bottomNavigationView, navController)

    }

    private fun setUpChipItems() {
        // add chips to the database

        if (getChipDataBase(baseContext)) {
            updateChipDataBase(baseContext, false)
            getAllTheChips().forEach {
                viewModel.saveChip(it)
            }
        }
    }

    private fun setUpViewModel() {
        viewModel = ViewModelProvider(
            this,
            MainViewModelProviderActivity(
                AppApplicationClass(),
                NewsRepository(
                    AppDataBase.invoke(this)!!.newsDao()
                ),
                ChipRepository(AppDataBase.invoke(this)!!.chipsDao())
            )
        )[MainViewModel::class.java]
    }
}
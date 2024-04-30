package com.example.assignmentfor8k.ui.activity.homeActivity

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.example.assignmentfor8k.R
import com.example.assignmentfor8k.applicationClass.NewsApplication
import com.example.assignmentfor8k.database.AppDataBase
import com.example.assignmentfor8k.databinding.ActivityMainBinding
import com.example.assignmentfor8k.repository.ChipRepository
import com.example.assignmentfor8k.repository.NewsRepository
import com.example.assignmentfor8k.ui.activity.homeActivity.viewModel.MainViewModel
import com.example.assignmentfor8k.ui.activity.homeActivity.viewModel.MainViewModelProviderActivity
import com.example.assignmentfor8k.util.Constants.getAllTheChips
import com.example.assignmentfor8k.util.SharedPrefFunc.getChipDataBase
import com.example.assignmentfor8k.util.SharedPrefFunc.updateChipDataBase
import com.example.assignmentfor8k.util.observeEvent

/**
 * Single activity application
 */
class MainActivity : AppCompatActivity() {

    lateinit var viewModel: MainViewModel
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
      //  enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(
            this,
            MainViewModelProviderActivity(
                this.application,
                NewsRepository(
                    AppDataBase.invoke(this)!!.newsDao()
                ),
                ChipRepository(AppDataBase.invoke(this)!!.chipsDao())
            )
        )[MainViewModel::class.java]

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        setUpChipItems()
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.newsNavHostFragment) as NavHostFragment
        val navController = navHostFragment.navController
        NavigationUI.setupWithNavController(binding.bottomNavigationView, navController)

        viewModel.handleShareText.observeEvent(this) {
            shareUrlValue(it)
        }

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


    private fun shareUrlValue(url: String) {
        val i = Intent(Intent.ACTION_SEND)
        i.type = "text/plain"
        i.putExtra(Intent.EXTRA_SUBJECT, "Sharing URl")
        i.putExtra(Intent.EXTRA_TEXT, url)
        startActivity(Intent.createChooser(i, "Share URL"))
    }

}
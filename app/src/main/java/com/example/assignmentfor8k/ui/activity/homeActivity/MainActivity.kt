package com.example.assignmentfor8k.ui.activity.homeActivity

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.example.assignmentfor8k.R
import com.example.assignmentfor8k.database.chipsDataBase.ChipDataClass
import com.example.assignmentfor8k.databinding.ActivityMainBinding
import com.example.assignmentfor8k.ui.activity.homeActivity.viewModel.MainViewModel
import com.example.assignmentfor8k.util.observeEvent
import com.example.assignmentfor8k.util.sharePref.ChipData.getChipDataBase
import com.example.assignmentfor8k.util.sharePref.ChipData.updateChipDataBase
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

/**
 * Single activity application
 */
@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    val viewModel: MainViewModel by viewModels()

    @Inject
    lateinit var chipDateItem: List<ChipDataClass>
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

        setUpChipItems()
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.newsNavHostFragment) as NavHostFragment
        val navController = navHostFragment.navController
        NavigationUI.setupWithNavController(binding.bottomNavigationView, navController)

        viewModel.handleShareText.observeEvent(this) {
            shareUrlValue(it)
        }
        startIpSearch()


    }

    private fun startIpSearch() {
        viewModel.startIpGetter()
    }

    private fun setUpChipItems() {
        // add chips to the database

        if (getChipDataBase(baseContext)) {
            updateChipDataBase(baseContext, false)
            chipDateItem.forEach {
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
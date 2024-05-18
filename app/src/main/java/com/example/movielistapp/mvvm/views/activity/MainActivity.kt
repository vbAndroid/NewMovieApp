package com.example.movielistapp.mvvm.views.activity

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI.setupActionBarWithNavController
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.movielistapp.R
import com.example.movielistapp.databinding.ActivityMainBinding
import com.example.movielistapp.mvvm.base.BaseViewModel
import com.example.movielistapp.mvvm.views.fragment.latestMovieFragment.LatestMoviesFragment
import com.example.movielistapp.mvvm.views.fragment.popularMovieFragment.PopularMoviesVM
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        setupMovieTab()
    }




fun setupMovieTab(){

    val navHostFragment =
        supportFragmentManager.findFragmentById(R.id.fragment_container) as NavHostFragment

    val navController = navHostFragment.navController
    setupActionBarWithNavController(navController)
    val tab1 = binding.tabLayout.newTab().apply { text = "Popular" }
    val tab2 = binding.tabLayout.newTab().apply { text = "Latest" }
    binding.tabLayout.addTab(tab1)
    binding.tabLayout.addTab(tab2)
    binding.tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
        override fun onTabSelected(tab: TabLayout.Tab) {
            tab.view.setBackgroundResource(R.drawable.tab_background_selector)
            when (tab.position) {
                0 -> navController.navigate(R.id.popularMovieFragment)
                1 -> navController.navigate(R.id.latestMovieFragment)
            }
        }

        override fun onTabUnselected(tab: TabLayout.Tab) {
            tab.view.setBackgroundResource(R.drawable.tab_background_selector)
        }

        override fun onTabReselected(tab: TabLayout.Tab) {
            // No action needed
        }
    })

    // Optionally set default tab
    binding.tabLayout.selectTab( binding.tabLayout.getTabAt(0))
}


}


package ru.sign.conditional.timetotrip.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import dagger.hilt.android.AndroidEntryPoint
import ru.sign.conditional.timetotrip.R

@AndroidEntryPoint
class AppActivity : AppCompatActivity(R.layout.activity_app) {
    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var appNavController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setSupportActionBar(findViewById(R.id.custom_toolbar))
        appNavController = findNavController(R.id.nav_host_fragment)
        appBarConfiguration = AppBarConfiguration(appNavController.graph)
        setupActionBarWithNavController(
            navController = appNavController,
            configuration = appBarConfiguration
        )
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressedDispatcher.onBackPressed()
        return appNavController.navigateUp(appBarConfiguration) ||
                super.onSupportNavigateUp()
    }
}
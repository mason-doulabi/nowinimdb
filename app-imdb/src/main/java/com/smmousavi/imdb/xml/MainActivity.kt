package com.smmousavi.imdb.xml

import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.material3.SnackbarDuration
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.smmousavi.i_core.presentation.snackbar.SnackbarEvent
import com.smmousavi.i_core.presentation.collectOnLifecycleStarted
import com.smmousavi.imdb.MainActivityViewModel
import com.smmousavi.imdb.R
import com.smmousavi.imdb.databinding.ActivityMainBinding

import dagger.hilt.android.AndroidEntryPoint
import kotlin.getValue

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration

    private var _binding: ActivityMainBinding? = null
    val binding get() = _binding!!

    private val navController: NavController by lazy {
        val navHostFragment = supportFragmentManager.findFragmentById(
            R.id.navHostFragmentContainer,
        ) as NavHostFragment
        navHostFragment.navController
    }

    private val viewModel: MainActivityViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupScreen()

        collectOnLifecycleStarted {
            viewModel.snackbarEvents.collect {
                renderSnackbarEvent(it)
            }
        }
    }

    private fun setupScreen() {
        applyWindowInsets()

        setSupportActionBar(binding.toolbar)

        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.moviesFragmentNav,
                R.id.searchFragmentNav,
                R.id.profileFragmentNav,
            ),
        )

        setupActionBarWithNavController(navController, appBarConfiguration)

        binding.bottomNavigation.setupWithNavController(navController)
    }

    private fun applyWindowInsets() {
        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { view, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            view.setPadding(
                systemBars.left,
                systemBars.top,
                systemBars.right,
                systemBars.bottom,
            )
            insets
        }
    }

    private fun renderSnackbarEvent(event: SnackbarEvent) {
        when (event) {
            is SnackbarEvent.FavoriteAdded -> {
                Snackbar.make(
                    binding.root,
                    "${event.movieTitle} added to favorites",
                    Snackbar.LENGTH_LONG,
                )
                    .show()
            }

            is SnackbarEvent.FavoriteRemoved -> {
                Snackbar.make(
                    binding.root,
                    "${event.movieTitle} removed from favorites",
                    Snackbar.LENGTH_LONG,
                )
                    .show()
            }

            SnackbarEvent.LoggedOut -> {
                Snackbar.make(
                    binding.root,
                    "Successfully logged out",
                    Snackbar.LENGTH_LONG,
                )
                    .show()
            }

            is SnackbarEvent.LoginSuccess -> {
                Snackbar.make(
                    binding.root,
                    "Welcome ${event.userName}",
                    Snackbar.LENGTH_LONG,
                )
                    .show()
            }

            is SnackbarEvent.Error -> {
                Snackbar.make(
                    binding.root,
                    "Error: ${event.imdbError}",
                    Snackbar.LENGTH_INDEFINITE,
                )
                    .setAction("Dismiss") {
                    }
                    .show()
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp(appBarConfiguration)
            || super.onSupportNavigateUp()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}
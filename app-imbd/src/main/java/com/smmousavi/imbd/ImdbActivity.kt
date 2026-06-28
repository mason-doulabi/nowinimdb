package com.smmousavi.imbd

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.google.samples.apps.nowinandroid.core.designsystem.theme.NiaTheme
import com.smmousavi.i_core.navigation.desitnation.ImdbDestination
import com.smmousavi.i_core.navigation.ui.BottomNavigationItem
import com.smmousavi.i_core.navigation.ui.ImdbBottomNavigationBar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ImdbActivity : ComponentActivity() {

    private val viewModel: ImdbActivityViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        // request to get the general info every time the activity is started.
        lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
            }
        }

        setContent {
            NiaTheme {
                ImdbActivityContent()
            }
        }
    }
}

@Composable
fun ImdbActivityContent(
    modifier: Modifier = Modifier,
) {
    val navController = rememberNavController()
    val currentBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = ImdbDestination.fromRoute(
        currentBackStackEntry?.destination?.route,
    )

    Scaffold(
        modifier = modifier,
        bottomBar = {
            ImdbBottomNavigationBar(
                navItems = BottomNavigationItem.ITEMS,
                currentDestination = currentDestination,
                onDestinationClick = { destination ->
                    navController.navigate(destination.route) {
                        launchSingleTop = true
                        restoreState = true
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                    }
                },
            )
        },
    ) { innerPadding ->
        ImdbNavHost(
            modifier = modifier.padding(innerPadding),
            navController = navController,
        )
    }
}

@Composable
@Preview(showBackground = true)
fun ImdbActivityContentPreview() {
    ImdbActivityContent()
}
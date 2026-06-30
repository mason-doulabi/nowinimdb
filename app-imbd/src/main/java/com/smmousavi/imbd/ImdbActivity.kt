package com.smmousavi.imbd

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.google.samples.apps.nowinandroid.core.designsystem.theme.NiaTheme
import com.smmousavi.i_core.navigation.desitnation.ImdbDestination
import com.smmousavi.i_core.navigation.ui.BottomNavigationItem
import com.smmousavi.i_core.navigation.ui.ImdbBottomNavigationBar
import com.smmousavi.i_core.presentation.snackbar.SnackbarEvent
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.distinctUntilChangedBy
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
                ImdbActivityContent(snackbarEvent = viewModel.snackbarEvents)
            }
        }
    }
}

@Composable
fun ImdbActivityContent(
    modifier: Modifier = Modifier,
    snackbarEvent: SharedFlow<SnackbarEvent>,
) {
    val snackbarHostState = remember { SnackbarHostState() }
    val navController = rememberNavController()
    val currentBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = ImdbDestination.fromRoute(
        currentBackStackEntry?.destination?.route,
    )

    LaunchedEffect(Unit) {
        snackbarEvent.distinctUntilChanged()
            .collect { event ->
                collectSnackbarEvent(event, snackbarHostState)
            }
    }

    Scaffold(
        modifier = modifier,
        snackbarHost = {
            SnackbarHost(
                hostState = snackbarHostState,
            )
        },
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

private suspend fun collectSnackbarEvent(
    event: SnackbarEvent,
    snackbarHostState: SnackbarHostState,
) {
    when (event) {
        is SnackbarEvent.FavoriteAdded -> {
            snackbarHostState.showSnackbar(
                "${event.movieTitle} added to favorites",
            )
        }

        is SnackbarEvent.FavoriteRemoved -> {
            snackbarHostState.showSnackbar(
                "${event.movieTitle} removed from favorites",
            )
        }

        SnackbarEvent.LoggedOut -> {
            snackbarHostState.showSnackbar(
                "Logged out successfully",
            )
        }

        is SnackbarEvent.LoginSuccess -> {
            snackbarHostState.showSnackbar(
                "Welcome ${event.userName}",
            )
        }

        is SnackbarEvent.Error -> {
            snackbarHostState.showSnackbar(
                "Error: ${event.imdbError.msg}",
                duration = SnackbarDuration.Indefinite,
                withDismissAction = true,
            )
        }
    }
}

@Composable
@Preview(showBackground = true)
fun ImdbActivityContentPreview() {
    ImdbActivityContent(snackbarEvent = MutableSharedFlow())
}
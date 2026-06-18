package com.smmousavi.imbd

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.smmousavi.i_core.presentation.UiState
import com.smmousavi.i_feature.movies.impl.MoviesScreen
import com.smmousavi.imbd.ui.theme.NowinandroidTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ImdbActivity : ComponentActivity() {

    private val viewModel: ImdbActivityViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.getGeneralInfo()
            }
        }
        setContent {
            val infoState by viewModel.generalInfoUiState.collectAsStateWithLifecycle()
            when (val state = infoState) {
                is UiState.Loading -> {}
                is UiState.Success -> {
                    MoviesScreen(data = state.data)
                }

                is UiState.Error -> {}
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingsPreview() {
    NowinandroidTheme {
    }
}
package com.smmousavi.imbd

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.google.samples.apps.nowinandroid.core.designsystem.component.NiaLoadingWheel
import com.google.samples.apps.nowinandroid.core.designsystem.theme.NiaTheme
import com.smmousavi.i_core.model.movies.generalinfo.MoviesGeneralInfoModel
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

        // request to get the general info every time the activity is started.
        lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.getTop250Movies()
                viewModel.getGeneralInfo()
            }
        }

        setContent {
            Scaffold { innerPadding ->
                val generalInfoState by viewModel.generalInfoUiState.collectAsStateWithLifecycle()
                val top250Movies by viewModel.top250MoviesState.collectAsStateWithLifecycle()

                when (val state = top250Movies) {
                    is UiState.Loading -> {
                        Box(
                            modifier = Modifier
                                .padding(innerPadding)
                                .fillMaxWidth(),
                        ) {
                            NiaLoadingWheel(
                                modifier = Modifier
                                    .padding(16.dp)
                                    .align(Alignment.TopCenter),
                                contentDesc = "Loading General Info",
                            )
                        }
                    }

                    is UiState.Success -> {
                        when (val generalState = generalInfoState) {
                            is UiState.Error -> {}
                            UiState.Loading -> {}
                            is UiState.Success -> {
                                NiaTheme {
                                    MoviesScreen(
                                        modifier = Modifier
                                            .padding(innerPadding)
                                            .padding(top = 16.dp),
                                        top250Movies = state.data,
                                        generalInfo = generalState.data,
                                    )
                                }
                            }
                        }
                    }

                    is UiState.Error -> {
                        Text("Error Loading content.")
                    }
                }
            }
        }
    }
}
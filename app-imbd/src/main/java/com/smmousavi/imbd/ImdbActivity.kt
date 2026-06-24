package com.smmousavi.imbd

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.google.samples.apps.nowinandroid.core.designsystem.theme.NiaTheme
import com.smmousavi.i_feature.search.impl.SearchScreen
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
            Scaffold { innerPadding ->
                NiaTheme {
//                    MoviesScreen(modifier = Modifier.padding(innerPadding))
                    SearchScreen(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}
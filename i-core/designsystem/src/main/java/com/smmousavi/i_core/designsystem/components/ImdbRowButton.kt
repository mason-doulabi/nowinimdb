/*
 * Copyright 2026 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.smmousavi.i_core.designsystem.components

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

sealed interface ImdbRowButtonType {
    object Primary : ImdbRowButtonType
    object Secondary : ImdbRowButtonType
}

@Composable
fun ImdbRowButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    contentPadding: PaddingValues = PaddingValues(12.dp),
    type: ImdbRowButtonType,
    content: @Composable RowScope.() -> Unit,
) {
    Button(
        modifier = modifier.fillMaxWidth(),
        onClick = onClick,
        colors = when (type) {
            ImdbRowButtonType.Primary ->
                ButtonDefaults.textButtonColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    contentColor = MaterialTheme.colorScheme.onPrimary,
                )

            ImdbRowButtonType.Secondary ->
                ButtonDefaults.textButtonColors(
                    containerColor = MaterialTheme.colorScheme.secondary,
                    contentColor = MaterialTheme.colorScheme.onSecondary,
                )
        },
        shape = RoundedCornerShape(4.dp),
        contentPadding = contentPadding,
    ) {
        content()
    }
}

@Composable
@Preview
fun ImdbRowButtonPreview() {
    ImdbRowButton(onClick = {}, type = ImdbRowButtonType.Secondary) {
        Text(text = "Sign In / Sign Up")
    }
}

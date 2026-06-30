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

package com.smmousavi.i_core.common.error

sealed class ImdbError(val msg: String) : Throwable(msg) {

    data object Network : ImdbError("Request Time out")

    data object Timeout : ImdbError("Request Time out")

    data object Unauthorized : ImdbError("Unauthorized attempt")

    data object TooManyRequests : ImdbError("Too many request")

    data object NotFound : ImdbError("Not found")

    data object Unknow : ImdbError("Unknown error")

    data class Validation(val report: String) : ImdbError(report)
}
package com.example.lastfm.common.utils

import androidx.compose.foundation.background
import androidx.compose.material3.Snackbar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.example.lastfm.common.utils.logger.BaseLogger
import com.example.lastfm.common.utils.logger.FactoryLogger

private val logger: BaseLogger = FactoryLogger.getLoggerCompose("ErrorScreen()")

@Composable
fun ErrorScreen(message: String) {
    Snackbar(
        modifier = Modifier.background(color = Color.Red),
        action = { Text(message) },
    ) { Text(text = message) }
}

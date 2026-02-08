package com.example.sayac

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                CounterScreen()
            }
        }
    }
}

@Composable
fun CounterScreen() {
    var count by remember { mutableIntStateOf(1) }
    var running by remember { mutableStateOf(false) }
    var finished by remember { mutableStateOf(false) }

    LaunchedEffect(running, finished) {
        while (running && !finished) {
            delay(1000)
            if (count >= 100) {
                finished = true
                running = false
            } else {
                count += 1
                if (count >= 100) {
                    finished = true
                    running = false
                }
            }
        }
    }

    val statusText = when {
        finished -> "Bitti"
        running -> "Çalışıyor"
        else -> "Durdu"
    }

    val buttonText = when {
        finished -> "Yeniden Başlat"
        running -> "Dur"
        else -> "Başlat"
    }

    Surface(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(text = "Sayı: $count", fontSize = 42.sp)
            Spacer(modifier = Modifier.height(12.dp))
            Text(text = statusText, fontSize = 22.sp)

            Spacer(modifier = Modifier.height(28.dp))

            Button(
                onClick = {
                    if (finished) {
                        count = 1
                        finished = false
                        running = true
                    } else {
                        running = !running
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)
            ) {
                Text(text = buttonText, fontSize = 20.sp)
            }
        }
    }
}

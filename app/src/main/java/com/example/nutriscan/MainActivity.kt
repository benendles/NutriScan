package com.example.nutriscan

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.nutriscan.ui.theme.NutriScanTheme

class MainActivity : ComponentActivity() {
    private val viewModel: NutriCanViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NutriScanTheme {
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
                    NutriCanScreen(viewModel)
                }
            }
        }
    }
}

@Composable
fun NutriCanScreen(viewModel: NutriCanViewModel) {
    val entries by viewModel.entries.collectAsState()
    val total by viewModel.totalCalories.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "NutriCan",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold
        )

        Text(
            text = "Simple, professional calorie tracking",
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.padding(top = 8.dp, bottom = 20.dp)
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 14.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Button(
                onClick = { viewModel.addSimulatedScan() },
                modifier = Modifier.weight(1f)
            ) {
                Text("Scan Food")
            }
            Button(
                onClick = { viewModel.addSimulatedVoice() },
                modifier = Modifier.weight(1f)
            ) {
                Text("Voice Food")
            }
        }

        Divider(modifier = Modifier.padding(vertical = 10.dp))

        Text(
            text = "Logged Items",
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        LazyColumn(modifier = Modifier.weight(1f)) {
            items(entries) { entry ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp),
                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(12.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(text = entry.name, style = MaterialTheme.typography.bodyLarge)
                        Text(text = "${entry.calories} kcal", style = MaterialTheme.typography.bodyLarge)
                    }
                }
            }
        }

        Divider(modifier = Modifier.padding(vertical = 10.dp))

        Text(
            text = "Total Calories: ${"%.1f".format(total)} kcal",
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold
        )

        Button(
            onClick = { viewModel.clearAll() },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 14.dp)
        ) {
            Text("Reset")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun NutriCanScreenPreview() {
    NutriScanTheme {
        // For preview only; static content
        Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text("NutriCan", style = MaterialTheme.typography.headlineMedium, fontWeight = FontWeight.Bold)
                Text("Simple, professional calorie tracking", style = MaterialTheme.typography.bodyMedium)
            }
        }
    }
}

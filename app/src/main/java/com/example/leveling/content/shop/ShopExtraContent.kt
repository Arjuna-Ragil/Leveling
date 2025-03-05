package com.example.leveling.content.shop

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.example.leveling.ui.theme.LevelingTheme

@Composable
fun CustomExhangeDialog( userId: String, context: Context ,showDialog:Boolean, onDismissRequest: () -> Unit) {
    var exchangeGold by remember { mutableStateOf("") }
    var exchangeVoucher by remember { mutableStateOf("")}

    if (showDialog) {
        Dialog(
            onDismissRequest = { onDismissRequest()},
        ) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .size(300.dp)
                    .background(MaterialTheme.colorScheme.tertiary)
                    .border(3.dp, MaterialTheme.colorScheme.outline)
            ) {
                Column(
                    verticalArrangement = Arrangement.SpaceEvenly,
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .fillMaxSize()
                ) {
                    Text("Custom Exhange")

                    Text("Rate: 1 Gold = 10 Rupiah")

                    TextField(
                        value = exchangeGold,
                        onValueChange = { gold ->
                            if (gold.all { it.isDigit() }) {
                                exchangeGold = gold
                                exchangeVoucher = (gold.toLongOrNull()?.times(10) ?: 0).toString()
                            }
                        },
                        placeholder = { Text("Input Gold Amount") },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                    )

                    TextField(
                        value = exchangeVoucher,
                        onValueChange = { voucher ->
                            exchangeVoucher = voucher
                            exchangeGold = (voucher.toLongOrNull()?.div(10) ?: 0).toString()
                        },
                        placeholder = { Text("Input Voucher Amount") },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                    )

                    Button(
                        onClick = {
                            customExchange(userId, context, exchangeGold.toLong(), exchangeVoucher.toLong())
                            exchangeGold = ""
                            exchangeVoucher = ""
                            onDismissRequest()
                        },
                        modifier = Modifier
                            .border(3.dp, MaterialTheme.colorScheme.outline, CircleShape)
                    ) {
                        Text("Exchange")
                    }
                }
            }
        }
    }
}
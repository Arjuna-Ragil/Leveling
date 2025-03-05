package com.example.leveling.content.inventory

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.example.leveling.content.quest.QuestCardContent
import com.example.leveling.ui.theme.LevelingTheme

data class InventoryCardData(
    var id: String = "",
    val voucher: String = ""
)

@Composable
fun InventoryCard(data: InventoryCardData, onDeleteClick: (InventoryCardData) -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .background(MaterialTheme.colorScheme.primary)
            .border(2.dp, MaterialTheme.colorScheme.outline)
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(text = data.voucher)

            Button(
                onClick = { onDeleteClick(data)},
                modifier = Modifier
                    .border(2.dp, MaterialTheme.colorScheme.outline, CircleShape)
            ) {
                Text("Use")
            }
        }
    }
}

@Composable
fun UseVoucherDialog(data: InventoryCardData, onDismiss:() -> Unit, onDelete: (InventoryCardData) -> Unit) {
    Dialog(
        onDismissRequest = { onDismiss() }
    ) {
        Box(
            modifier = Modifier
                .width(250.dp)
                .height(200.dp)
                .background(MaterialTheme.colorScheme.tertiary)
                .border(3.dp, MaterialTheme.colorScheme.outline)
                .padding(16.dp)
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier
                    .fillMaxSize()
            ) {
                Text(
                    "Are you sure you want to use this voucher",
                    textAlign = TextAlign.Center
                )

                Button(
                    onClick = {
                        onDelete(data)
                        onDismiss()
                    }
                ) {
                    Text("Use")
                }

                Button(
                    onClick = { onDismiss() }
                ) {
                    Text("Cancel")
                }
            }
        }
    }
}
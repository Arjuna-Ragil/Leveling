package com.example.leveling.content.inventory

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.example.leveling.main.InventoryTop
import com.example.leveling.ui.theme.background
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.firestore.firestore

@Composable
fun Inventory() {
    var vouchersList by remember { mutableStateOf<List<InventoryCardData>>(emptyList()) }
    var selectedVoucher by remember { mutableStateOf<InventoryCardData?>(null) }

    val db = Firebase.firestore
    val user = Firebase.auth.currentUser

    user?.let {
        val userId = it.uid

        LaunchedEffect(userId) {
            db.collection("Users").document(userId)
                .collection("Inventory")
                .addSnapshotListener { snapshot, error ->
                    if (error != null) {
                        Log.e("FireStore", "Error getting documents: $error")
                        return@addSnapshotListener
                    }

                    if (snapshot != null) {
                        vouchersList = snapshot.documents.mapNotNull { doc ->
                            doc.toObject(InventoryCardData::class.java)
                        }
                    }
                }
        }
    }

    Surface(
        color = Color.Transparent,
        modifier = Modifier
            .fillMaxSize()
            .then(background()),
    ) {
        Column(
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
        ) {
            InventoryTop()

            LazyColumn {
                items(vouchersList) { voucher ->
                    InventoryCard(voucher, onDeleteClick = { selectedVoucher = it})
                }
            }
        }

        if (selectedVoucher != null) {
            UseVoucherDialog(
                selectedVoucher!!,
                onDismiss = { selectedVoucher = null },
                onDelete = { voucher ->
                    useVoucher(voucher)
                    vouchersList -= voucher
                    selectedVoucher = null
                })
        }
    }
}
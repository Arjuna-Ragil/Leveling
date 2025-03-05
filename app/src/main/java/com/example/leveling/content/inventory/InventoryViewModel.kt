package com.example.leveling.content.inventory

import android.util.Log
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.firestore.firestore

fun useVoucher(data: InventoryCardData) {
    val db = Firebase.firestore
    val user = Firebase.auth.currentUser

    user?.let {
        val userId = it.uid

        db.collection("Users").document(userId)
            .collection("Inventory")
            .document(data.id)
            .delete()
            .addOnSuccessListener {
                Log.d("FireStore", "Voucher used successfully")
            }
            .addOnFailureListener { e ->
                Log.e("FireStore", "Error using voucher", e)
            }
    }
}
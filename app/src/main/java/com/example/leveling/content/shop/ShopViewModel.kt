package com.example.leveling.content.shop

import android.content.Context
import android.util.Log
import android.widget.Toast
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore

fun exchange(userId: String, context: Context, amount: Long, type: String) {
    val db = Firebase.firestore

    db.collection("Users").document(userId)
        .get()
        .addOnSuccessListener { document ->
            if (document != null) {
                val currentMoney = document.data?.get("money") as Long
                val newDocRef = db.collection("Users").document(userId).collection("Inventory").document()
                if (currentMoney >= amount) {

                db.collection("Users").document(userId)
                    .update("money", currentMoney - amount)
                    .addOnSuccessListener {

                            newDocRef.set(
                                mapOf(
                                    "id" to newDocRef.id,
                                    "voucher" to type
                                ))
                            .addOnSuccessListener {
                                Log.d("FireStore", "Voucher added successfully")
                            }
                            .addOnFailureListener { e ->
                                Log.e("FireStore", "Error adding voucher", e)
                            }
                    }
                    .addOnFailureListener { e ->
                        Log.w("FireStore", "Error updating money", e)
                    }
                } else {
                    Toast.makeText(context, "You don't have enough money", Toast.LENGTH_SHORT).show()
                }
            } else {
                Log.e("FireStore", "No such document")
            }
        }
        .addOnFailureListener { e ->
            Log.e("FireStore", "Error getting document", e)
        }
}

fun customExchange(userId: String, context: Context, gold: Long, voucher: Long) {

    fun voucherAmount(): Long {
        val voucherAmount: Long = if (voucher in 1000..999999) {
            voucher / 1000
        } else if (voucher >= 1000000) {
            voucher / 1000000
        } else {
            voucher
        }

        return voucherAmount
    }

    fun voucherSymbol(): String {
        val voucherSymbol: String = if (voucher in 1000..999999) {
            "K"
        } else if (voucher >= 1000000) {
            "M"
        } else {
            ""
        }

        return voucherSymbol
    }

    val finalVoucherAmount = voucherAmount().toString()
    val ExchangeSymbol = voucherSymbol()

    val goldExchange = finalVoucherAmount + ExchangeSymbol

    exchange(userId, context, gold, goldExchange)


}
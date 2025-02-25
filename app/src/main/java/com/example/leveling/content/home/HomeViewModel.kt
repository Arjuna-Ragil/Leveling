package com.example.leveling.content.home

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.example.leveling.login.Login
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.firestore.firestore
import com.google.firebase.firestore.snapshots

@Composable
fun levelProgress(): Float {
    val db = Firebase.firestore
    var exp by remember { mutableStateOf(0) }
    var level by remember { mutableStateOf(0) }
    val user = Firebase.auth.currentUser

    fun expCount(): Float {
        val max = 1000f
        var expGain = (exp.toFloat() / max).coerceIn(0f, 1f)

        if (expGain == 1f) {
            user?.let {
                val userId = it.uid

                db.collection("Users").document(userId)
                    .update(
                        mapOf(
                            "exp" to exp - 1000,
                            "level" to level + 1
                        )
                    )
                    .addOnSuccessListener {
                        expGain = exp - 1000f
                        Log.d("FireStore", "Exp reset")
                    }
                    .addOnFailureListener { e ->
                        Log.e("FireStore", "Failed to reset exp")
                    }
            }
        }

        return expGain
    }

    user?.let {
        val userId = it.uid

        db.collection("Users").document(userId)
            .addSnapshotListener { snapshot, e ->
                if (e != null) {
                    Log.e("FireStore", "Failed to get gold")
                    return@addSnapshotListener
                }
                if (snapshot != null && snapshot.exists()) {
                    exp = snapshot.getLong("exp")?.toInt() ?: 0
                    level = snapshot.getLong("level")?.toInt() ?: 0
                    Log.d("FireStore", "Exp: $exp")
                }
            }
    }

    return expCount()
}


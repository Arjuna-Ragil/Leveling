package com.example.leveling.content.quest

import android.content.Context
import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.text.intl.Locale
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.firestore.firestore
import kotlinx.coroutines.delay
import java.text.SimpleDateFormat
import java.time.LocalTime
import java.util.Date

fun getCurrentDate(): String {
    val sdf = SimpleDateFormat("EEE, dd/MM/yyyy", java.util.Locale.getDefault())
    return sdf.format(Date())
}

fun getCurrentTime(): String {
    val sdf = SimpleDateFormat("HH:mm:ss", java.util.Locale.getDefault())
    val currentTime = sdf.format(Date())
    return currentTime
}

fun addDailyToFirestore(userId: String, title: String, description: String, reward: String, done: Boolean = false, onSuccess: (QuestCardContent) -> Unit) {
    val db = Firebase.firestore

    val newDocRef = db.collection("Users").document(userId)
        .collection("Quest")
        .document()

    val addDaily = QuestCardContent(id = newDocRef.id, title, description, reward, done)

    newDocRef.set(addDaily)
    .addOnSuccessListener {
        onSuccess(addDaily)
    }
    .addOnFailureListener { e ->
        Log.e("FireStore", "Failed to add quest")
    }

}

fun dailyReset(userId: String) {
    val db = Firebase.firestore

    db.collection("Users").document(userId)
        .collection("Quest")
        .get()
        .addOnSuccessListener { documents ->
            for (document in documents) {
                document.reference.update("done", false)
                    .addOnSuccessListener {
                        Log.d("FireStore", "Quest reset")
                    }
                    .addOnFailureListener {
                        Log.e("FireStore", "Failed to reset quest")
                    }
            }
        }
        .addOnFailureListener {
            Log.e("FireStore", "Failed to retrieve daily quest")
        }

}

fun isNewDay(context: Context): Boolean {
    val sharedPreferenced = context.getSharedPreferences("app_preferences", Context.MODE_PRIVATE)
    val lastResetDate = sharedPreferenced.getString("last_reset_date", "")

    val currentDate = getCurrentDate()

    return if (lastResetDate != currentDate) {
        sharedPreferenced.edit().putString("last_reset_date", currentDate).apply()
        true
    } else {
        false
    }
}
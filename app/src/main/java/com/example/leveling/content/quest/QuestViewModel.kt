package com.example.leveling.content.quest

import android.content.Context
import android.util.Log
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.firestore.firestore
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date

data class QuestCardContent(
    var id: String = "",
    var title: String = "",
    var description: String = "",
    var reward: String = "",
    var done: Boolean = false,
)

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
        .collection("Daily")
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

fun editDailyToFireStore(questCardContent: QuestCardContent) {
    val db = Firebase.firestore
    val user = Firebase.auth.currentUser

    user?.let {
        val userId = it.uid

        db.collection("Users").document(userId)
            .collection("Daily")
            .document(questCardContent.id)
            .update(
                mapOf(
                    "title" to questCardContent.title,
                    "description" to questCardContent.description,
                    "reward" to questCardContent.reward,
                )
            )
            .addOnSuccessListener {
                Log.d("FireStore", "Quest updated")
            }
            .addOnFailureListener { e ->
                Log.e("FireStore", "Failed to update quest")
            }
    }
}

fun deleteDailyFromFirestore(questCardContent: QuestCardContent) {
    val db = Firebase.firestore
    val user = Firebase.auth.currentUser

    user?.let {
        val userId = it.uid

        db.collection("Users").document(userId)
            .collection("Daily")
            .document(questCardContent.id)
            .delete()
            .addOnSuccessListener {
                Log.d("FireStore", "Quest deleted")
            }
            .addOnFailureListener { e ->
                Log.e("FireStore", "Failed to delete quest")
            }
    }
}

fun addWeeklyToFirestore(userId: String, title: String, description: String, reward: String, done: Boolean = false, onSuccess: (QuestCardContent) -> Unit) {
    val db = Firebase.firestore

    val newDocRef = db.collection("Users").document(userId)
        .collection("Weekly")
        .document()

    val addWeekly = QuestCardContent(id = newDocRef.id, title, description, reward, done)

    newDocRef.set(addWeekly)
        .addOnSuccessListener {
            onSuccess(addWeekly)
        }
        .addOnFailureListener { e ->
            Log.e("FireStore", "Failed to add quest")
        }

}

fun editWeeklyToFireStore(questCardContent: QuestCardContent) {
    val db = Firebase.firestore
    val user = Firebase.auth.currentUser

    user?.let {
        val userId = it.uid

        db.collection("Users").document(userId)
            .collection("Weekly")
            .document(questCardContent.id)
            .update(
                mapOf(
                    "title" to questCardContent.title,
                    "description" to questCardContent.description,
                    "reward" to questCardContent.reward,
                )
            )
            .addOnSuccessListener {
                Log.d("FireStore", "Quest updated")
            }
            .addOnFailureListener { e ->
                Log.e("FireStore", "Failed to update quest")
            }
    }
}

fun deleteWeeklyFromFirestore(questCardContent: QuestCardContent) {
    val db = Firebase.firestore
    val user = Firebase.auth.currentUser

    user?.let {
        val userId = it.uid

        db.collection("Users").document(userId)
            .collection("Weekly")
            .document(questCardContent.id)
            .delete()
            .addOnSuccessListener {
                Log.d("FireStore", "Quest deleted")
            }
            .addOnFailureListener { e ->
                Log.e("FireStore", "Failed to delete quest")
            }
    }
}

fun addMonthlyearlyToFirestore(userId: String, title: String, description: String, reward: String, done: Boolean = false, onSuccess: (QuestCardContent) -> Unit) {
    val db = Firebase.firestore

    val newDocRef = db.collection("Users").document(userId)
        .collection("Monthlyearly")
        .document()

    val addWeekly = QuestCardContent(id = newDocRef.id, title, description, reward, done)

    newDocRef.set(addWeekly)
        .addOnSuccessListener {
            onSuccess(addWeekly)
        }
        .addOnFailureListener { e ->
            Log.e("FireStore", "Failed to add quest")
        }

}

fun editMonthlyearlyToFireStore(questCardContent: QuestCardContent) {
    val db = Firebase.firestore
    val user = Firebase.auth.currentUser

    user?.let {
        val userId = it.uid

        db.collection("Users").document(userId)
            .collection("Monthlyearly")
            .document(questCardContent.id)
            .update(
                mapOf(
                    "title" to questCardContent.title,
                    "description" to questCardContent.description,
                    "reward" to questCardContent.reward,
                )
            )
            .addOnSuccessListener {
                Log.d("FireStore", "Quest updated")
            }
            .addOnFailureListener { e ->
                Log.e("FireStore", "Failed to update quest")
            }
    }
}

fun deleteMonthlyearlyFromFirestore(questCardContent: QuestCardContent) {
    val db = Firebase.firestore
    val user = Firebase.auth.currentUser

    user?.let {
        val userId = it.uid

        db.collection("Users").document(userId)
            .collection("Monthlyearly")
            .document(questCardContent.id)
            .delete()
            .addOnSuccessListener {
                Log.d("FireStore", "Quest deleted")
            }
            .addOnFailureListener { e ->
                Log.e("FireStore", "Failed to delete quest")
            }
    }
}

fun dailyReset(userId: String) {
    val db = Firebase.firestore

    db.collection("Users").document(userId)
        .collection("Daily")
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

fun WeeklyReset(userId: String) {
    val db = Firebase.firestore

    db.collection("Users").document(userId)
        .collection("Weekly")
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
            Log.e("FireStore", "Failed to retrieve weekly quest")
        }

}

fun isNewWeek(context: Context): Boolean {
    val sharedPreferenced = context.getSharedPreferences("app_preferences", Context.MODE_PRIVATE)
    val lastResetWeek = sharedPreferenced.getInt("last_reset_week", -1)
    val lastResetYear = sharedPreferenced.getInt("last_reset_year", -1)

    val calendar = Calendar.getInstance()
    val currentWeek = calendar.get(Calendar.WEEK_OF_YEAR)
    val currentYear = calendar.get(Calendar.YEAR)

    return if (lastResetWeek != currentWeek || lastResetYear != currentYear) {
        sharedPreferenced.edit()
            .putInt("last_reset_week", currentWeek)
            .putInt("last_reset_year", currentYear)
            .apply()
        true
    } else {
        false
    }
}

fun monthlyearlyReset(userId: String) {
    val db = Firebase.firestore

    db.collection("Users").document(userId)
        .collection("Monthlyearly")
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
            Log.e("FireStore", "Failed to retrieve monthlyearly quest")
        }

}

fun isNewMonthYear(context: Context): Boolean {
    val sharedPreferenced = context.getSharedPreferences("app_preferences", Context.MODE_PRIVATE)
    val lastResetMonth = sharedPreferenced.getInt("last_reset_month", -1)
    val lastResetYear = sharedPreferenced.getInt("last_reset_year", -1)

    val calendar = Calendar.getInstance()
    val currentMonth = calendar.get(Calendar.MONTH)
    val currentYear = calendar.get(Calendar.YEAR)

    return if (lastResetMonth != currentMonth || lastResetYear != currentYear) {
        sharedPreferenced.edit()
            .putInt("last_reset_month", currentMonth)
            .putInt("last_reset_year", currentYear)
            .apply()
        true
    } else {
        false
    }
}
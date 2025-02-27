package com.example.leveling.login

import android.content.Context
import android.util.Log
import androidx.compose.ui.graphics.evaluateY
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.leveling.content.quest.isNewDay
import com.google.android.gms.tasks.Task
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.firestore
import kotlin.math.log

class LoginViewModel: ViewModel() {

    private val auth = FirebaseAuth.getInstance()

    private val _authState = MutableLiveData<AuthState>()
    val authState: LiveData<AuthState> = _authState

    init {
        checkAuthStatus()
    }

    fun checkAuthStatus() {
        if (auth.currentUser != null) {
            _authState.value = AuthState.Authenticated
        } else {
            _authState.value = AuthState.Unauthenticated
        }
    }

    fun signin(email: String, password: String) {
        val db = Firebase.firestore

        if (email.isEmpty() || password.isEmpty()) {
            _authState.value = AuthState.Error("Email and password cannot be empty")
            return
        }

        _authState.value = AuthState.Loading
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener{ task ->
                if (task.isSuccessful) {
                    _authState.value = AuthState.Authenticated
                    val user = auth.currentUser
                    user?.let {
                        val userId = it.uid

                        db.collection("Users").document(userId)
                            .get()
                            .addOnSuccessListener { document ->
                                if (!document.exists()) {
                                    val userData = hashMapOf(
                                        "name" to it.displayName,
                                        "email" to it.email,
                                        "profilePic" to it.photoUrl.toString(),
                                        "level" to 1,
                                        "xp" to 0,
                                        "money" to 0,
                                        "createdAt" to FieldValue.serverTimestamp()
                                    )

                                    db.collection("Users").document(userId)
                                        .set(userData)
                                        .addOnSuccessListener { Log.e("FireStore", "Users data have been added") }
                                        .addOnFailureListener { Log.e("FireStore", "Users data have not been added") }
                                } else {
                                    Log.d("FireStore", "Users data already exist")
                                }
                            }
                            .addOnFailureListener { e -> Log.e("FireStore", "Error checking user")}
                    }
                } else {
                    _authState.value = AuthState.Error(task.exception?.message ?: "Something went wrong")
                }
            }
    }

    fun signup(email: String, password: String, confirmPassword: String) {

        val db = Firebase.firestore

        if (email.isEmpty() || password.isEmpty()) {
            _authState.value = AuthState.Error("Email and password cannot be empty")
            return
        }

        if (password != confirmPassword) {
            _authState.value = AuthState.Error("Passwords do not match")
            return
        }

        _authState.value = AuthState.Loading
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener{ task ->
                if (task.isSuccessful) {
                    _authState.value = AuthState.Authenticated
                    val user = auth.currentUser
                    user?.let {
                        val userId = it.uid

                        db.collection("Users").document(userId)
                            .get()
                            .addOnSuccessListener { document ->
                                if (!document.exists()) {
                                    val userData = hashMapOf(
                                        "name" to it.displayName,
                                        "email" to it.email,
                                        "profilePic" to it.photoUrl.toString(),
                                        "level" to 1,
                                        "exp" to 0,
                                        "money" to 0,
                                        "createdAt" to FieldValue.serverTimestamp()
                                    )

                                    db.collection("Users").document(userId)
                                        .set(userData)
                                        .addOnSuccessListener {
                                            Log.e(
                                                "FireStore",
                                                "Users data have been added"
                                            )
                                        }
                                        .addOnFailureListener {
                                            Log.e(
                                                "FireStore",
                                                "Users data have not been added"
                                            )
                                        }
                                } else {
                                    Log.d("FireStore", "Users data already exist")
                                }
                            }
                            .addOnFailureListener { e -> Log.e("FireStore", "Error checking user") }
                    }
                } else {
                    _authState.value = AuthState.Error(task.exception?.message ?: "Something went wrong")
                }
            }
    }

    fun signout() {
        auth.signOut()
        _authState.value = AuthState.Unauthenticated
    }
}

sealed class AuthState {
    object Authenticated: AuthState()
    object Unauthenticated: AuthState()
    object Loading: AuthState()
    data class Error(val message: String): AuthState()
}
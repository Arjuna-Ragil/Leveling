package com.example.leveling.login

import android.content.Context
import android.util.Log
import androidx.credentials.ClearCredentialStateRequest
import androidx.credentials.CredentialManager
import androidx.credentials.CustomCredential
import androidx.credentials.GetCredentialRequest
import androidx.credentials.GetCredentialResponse
import com.google.android.libraries.identity.googleid.GetGoogleIdOption
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import com.google.android.libraries.identity.googleid.GoogleIdTokenParsingException
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.firestore
import kotlinx.coroutines.tasks.await
import kotlin.coroutines.cancellation.CancellationException

class LoginGoogleClient(
    private val context: Context
) {
    private val tag = "LoginGoogleClient: "

    private val credentialManager = CredentialManager.create(context)
    private val auth = FirebaseAuth.getInstance()

    suspend fun signIn(): Boolean {

        try {

            val result = buildCredentialRequest()
            return handleSignIn(result)

        } catch (e: Exception) {
            e.printStackTrace()
            if (e is CancellationException) {
                throw e
            }
            println(tag + "Error: " + e.message)
            return false
        }
    }

    suspend fun signOut(): Boolean {
        credentialManager.clearCredentialState(
            ClearCredentialStateRequest()
        )
        auth.signOut()
        return true
    }

    private suspend fun handleSignIn(result: GetCredentialResponse): Boolean {
        val db = Firebase.firestore

        val credential = result.credential

        if (
            credential is CustomCredential && credential.type == GoogleIdTokenCredential.TYPE_GOOGLE_ID_TOKEN_CREDENTIAL
        ) {
            try {
                val tokenCredential = GoogleIdTokenCredential.createFrom(credential.data)

                println(tag + "Name: ${tokenCredential.displayName}")
                println(tag + "Email: ${tokenCredential.id}")
                println(tag + "Photo: ${tokenCredential.profilePictureUri}")

                val authCredential = GoogleAuthProvider.getCredential(tokenCredential.idToken, null)

                val authResult = auth.signInWithCredential(authCredential).await()

                val user = FirebaseAuth.getInstance().currentUser
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

                return authResult.user != null

            } catch (e: GoogleIdTokenParsingException) {
                println(tag + "Error: " + e.message)
                return false
            }
        } else {
            println(tag + "credential is not GoogleIdTokenCredential")
            return false
        }
    }

    private suspend fun buildCredentialRequest(): GetCredentialResponse {
        val request = GetCredentialRequest.Builder()
            .addCredentialOption(
                GetGoogleIdOption.Builder()
                    .setFilterByAuthorizedAccounts(false)
                    .setServerClientId(
                        "589798806704-qs989umhd5gkojb8sjpdtc0a2qhhjv8n.apps.googleusercontent.com"
                    )
                    .setAutoSelectEnabled(false)
                    .build()
            )
            .build()

        return credentialManager.getCredential(
            request = request, context = context
        )
    }
}
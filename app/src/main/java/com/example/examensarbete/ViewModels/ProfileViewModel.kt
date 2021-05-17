package com.example.examensarbete.ViewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.examensarbete.DataClasses.UserData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase

class ProfileViewModel: ViewModel () {

    lateinit var db: FirebaseFirestore
    private lateinit var auth: FirebaseAuth
    var uid = ""

    private val _userName = MutableLiveData<String>()

    fun setUserName(): LiveData<String> {
        return _userName
    }

    fun getUserInfo(uid: String) {
        println("!!! get info körs")
        db = FirebaseFirestore.getInstance()
        val userRef = db.collection("users").document(uid)
        userRef.get()
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        val document = task.result
                        println("!!! ${document}")
                        if (document!!.exists()) {
                            println("!!! Firebase User: ${document.data}")
                            val userData = document.toObject(UserData::class.java)!!
                            _userName.value = userData.user_name
                        }
                    }
                    else {
                        println("!!! Get profile went wrong")
                    }
        }

    }



    fun getUserUid () {
        val currentUser: FirebaseUser? = auth.currentUser
        if (currentUser != null) {
            uid = currentUser.uid
        }
    }
}
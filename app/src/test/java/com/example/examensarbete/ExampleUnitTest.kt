package com.example.examensarbete

import android.content.Context
import android.view.View
import android.widget.PopupMenu
import com.example.examensarbete.Encryption.DataEncryption
import com.example.examensarbete.Firebase.CreateUser
import com.example.examensarbete.NetWork.NetworkHandler
import com.example.examensarbete.Screens.SignUp
import com.google.firebase.firestore.FirebaseFirestore

import org.junit.Assert.*
import org.junit.Test
import kotlin.math.sign

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {


    val ref = CreateUser()
    val networkRef = NetworkHandler
    val signRef = SignUp()


    //private lateinit var context: Context


    @Test
    fun myEncryptionTest() {
        encryptionCheck()
    }

    @Test
    fun myTest() {
        assert(signUpEmailTest())
    }


    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }


    fun encryptionCheck() {
        val stringToEncrypt = "A string message"

        val encryptedString = DataEncryption().encrypt(stringToEncrypt.toByteArray(), stringToEncrypt.toCharArray())

        assertNotEquals(stringToEncrypt, encryptedString)

        val decryptedString = DataEncryption().decrypt(encryptedString, stringToEncrypt.toCharArray())

        assertEquals(stringToEncrypt, decryptedString)
    }


    /*fun createUserTest(): Boolean {
        lateinit var db: FirebaseFirestore
        db = FirebaseFirestore.getInstance()
        if (ref.userCreate("","", "") == false) {
            return true
        } else {
            return false
        }
    } */

    //A test that checks if email is empty when sign up
    fun signUpEmailTest (): Boolean {
        val email = "test@live.se"
        if (signRef.checkEmailValidation(email) == false) {
            return false
        } else {
            return true
        }
    }



    /*fun networkTest(): Boolean {
        if (networkRef.isOnline(context)){
            return true
        } else {
            return false
        }
    } */

}

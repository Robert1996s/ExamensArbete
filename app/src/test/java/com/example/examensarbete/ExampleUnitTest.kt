package com.example.examensarbete

import android.view.View
import android.widget.PopupMenu
import com.example.examensarbete.Encryption.DataEncryption
import com.example.examensarbete.Firebase.CreateUser
import com.example.examensarbete.NetWork.NetworkHandler

import org.junit.Assert.*
import org.junit.Test

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {


    val ref = CreateUser()
    val networkRef = NetworkHandler



    @Test
    fun myEncryptionTest() {
        encryptionCheck()
    }

    @Test
    fun myTest() {
        //assert(createUserTest())
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
        if (ref.userCreate("", "", "") == false) {
            return true
        } else {
            return false
        }
    } */


    /*fun networkTest() {
        if (networkRef.isOnline(this))
    }*/

}

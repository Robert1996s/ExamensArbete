package com.example.examensarbete.ViewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.examensarbete.DataClasses.CurrentGame
import com.example.examensarbete.DataClasses.Question
import com.example.examensarbete.GlobalVariables.CurrentQuestions
import com.google.firebase.firestore.FirebaseFirestore

class GameViewModel: ViewModel() {

    lateinit var db: FirebaseFirestore

    var player_score = 0

    var questionIndex = 0

    var objList = ArrayList<Question>()

    var questionTextList = mutableListOf<String?>()
    var correctAnswers = mutableListOf<Int>()

    private var questionObjList: MutableLiveData<MutableList<Question>> = MutableLiveData()

    fun addScore(){
        player_score++
    }

    fun setQuestionList(list: MutableList<Question>) {
        var index = 0
        for (i in list){
            objList.add(list[index])
            index++
        }
        questionObjList.value = objList
        println("!!!OBJ LIST: $objList")
        println("!!!Q OBJ LIST: $questionObjList")
        //currentGames.value = objList
    }

    fun getObjQuestionList(): MutableLiveData<MutableList<Question>>{
        return questionObjList
    }


    fun getQuestions(categoryId: String) {

        // en tempLista
        var tempList = mutableListOf<Question>()

        db = FirebaseFirestore.getInstance()
        val docRef = db.collection("Categories").document(categoryId).collection("questions")
        docRef.get()
                .addOnSuccessListener { documents ->
                    for (document in documents) {
                        val data = document.toObject(Question::class.java)


                        tempList.add(data)
                        questionTextList.add(data.text)
                        correctAnswers.add(data.correct_answer)


                    }

                    setQuestionList(tempList)
                    println("!!!data: ${CurrentQuestions.currQuestions}")

                }
    }
}



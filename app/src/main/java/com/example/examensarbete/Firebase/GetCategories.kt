package com.example.examensarbete.Firebase

import com.example.examensarbete.DataClasses.Category
import com.google.firebase.firestore.FirebaseFirestore

lateinit var db: FirebaseFirestore
var categoriesList = mutableListOf<Category>()


class GetCategories {
     fun getAllCategories(): List<Category> {
        db = FirebaseFirestore.getInstance()
        db.collection("Categories")
                .get()
                .addOnSuccessListener { documents ->
                    for (document in documents) {
                        val cat = document.toObject(Category::class.java)
                        categoriesList.add(cat)
                        println("!!! ${cat.categoryName}")
                    }
                    println("!!!List : $categoriesList")
                }
         return categoriesList
    }
}
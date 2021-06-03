package com.example.examensarbete.ViewModels

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class ViewModelFactory(val context: Context) : ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel?> create(modelClass: Class<T>) : T {
        return ProfileViewModel(context) as T
    }
}
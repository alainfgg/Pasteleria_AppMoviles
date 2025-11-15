package com.example.login001v.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.login001v.data.model.Post
import com.example.login001v.data.repository.PostRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class PostViewModel : ViewModel(){

    private val repository = PostRepository()

    //solose controlan los flujos
    //flujo mutable que contenga lista de post

    private val _postList = MutableStateFlow<List<Post>>(emptyList())
    //flujo público de soilo lectura

    val postList: StateFlow<List<Post>> = _postList

    init {
        fetchPosts()
    }

    private fun fetchPosts(){
        viewModelScope.launch{
            try {
                _postList.value = repository.getPosts()
            } catch (e: Exception){
                println("Error al obtener datos: ${e.localizedMessage}")
            }
        }
    }


}
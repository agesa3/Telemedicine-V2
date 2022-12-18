package com.agesadev.telmedv2.utils

sealed class Resource<out T>{
    data class Success<out T>(val data: T) : Resource<T>()
    data class Error(val exception: Exception) : Resource<Nothing>()
    object Loading : Resource<Nothing>()
}

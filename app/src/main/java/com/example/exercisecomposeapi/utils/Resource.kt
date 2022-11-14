package com.example.exercisecomposeapi.utils

public sealed class Resource<out T>(
    val data: T? = null,
    val message: String? = null
) {
    public class Success<T>(data: T) : Resource<T>(data = data)
    public class Failure<T>(message: String) : Resource<T>(message = message)
    public class Loading<T> : Resource<T>()
    public class Idle<T> : Resource<T>()
}
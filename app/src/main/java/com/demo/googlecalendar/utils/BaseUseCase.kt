package com.demo.googlecalendar.utils

interface InputUseCase<I, O> {
    suspend operator fun invoke(input: I): O
}

interface NoInputUseCase<T> {
    suspend operator fun invoke(): T
}
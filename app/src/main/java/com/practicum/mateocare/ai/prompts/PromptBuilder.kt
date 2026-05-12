package com.practicum.mateocare.ai.prompts

interface PromptBuilder<T> {
    fun build(input: T): String
}
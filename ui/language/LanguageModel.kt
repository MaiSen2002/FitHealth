package com.bloodpressure.bloodtracker.bptracker.ui.language

data class LanguageModel(
    var name: String,
    var code: String,
    var drawable: Int,
    var active: Boolean = false
)
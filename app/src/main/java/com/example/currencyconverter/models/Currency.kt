package com.example.currencyconverter.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Currency(
    @PrimaryKey val code: String,
    val name: String,
)

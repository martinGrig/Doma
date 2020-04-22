package com.example.doma

import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
data class User(
    var username: String? = "",
    var user_id: String? = "",
    var email: String? = "",
    var password: String? = "",
    var color: Int? = 0
)

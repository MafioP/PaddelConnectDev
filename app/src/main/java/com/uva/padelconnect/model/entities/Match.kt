package com.uva.padelconnect.model.entities

import java.util.Date

data class Match(
    val public:Boolean=false,
    val name:String="",
    val date:Date,
    val place:String="",
    val profileImageUrls: List<String>,
    val doubles:Boolean=true
) {
}
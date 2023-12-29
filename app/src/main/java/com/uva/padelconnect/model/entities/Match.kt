package com.uva.padelconnect.model.entities

import java.util.Date

data class Match(
    val public:Boolean=false,
    val name:String="",
    val date:Date,
    val place:String="",
    val user1:String="",
    val user2:String="",
    val user3:String="",
    val user4:String="",
) {
}
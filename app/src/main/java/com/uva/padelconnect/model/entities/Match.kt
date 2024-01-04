package com.uva.padelconnect.model.entities

import java.util.Date

data class Match(
    var idMatch:String="",
    val public:Boolean=false,
    val name:String="",
    val date:Date,
    val place:String="",
    val idUser1:String="",
    val idUser2:String="",
    val idUser3:String="",
    val idUser4:String="",
    val doubles:Boolean=true,
    val resultado:String=""
) {
}
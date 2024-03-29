package com.uva.padelconnect.modelView.viewmodel

import android.net.Uri
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.uva.padelconnect.model.entities.Match
import com.uva.padelconnect.modelView.repositories.MatchRepository
import com.uva.padelconnect.modelView.repositories.UserRepository
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class MatchesViewModel: ViewModel() {
    private var matchRepository: MatchRepository = MatchRepository()
    private var userRepository: UserRepository = UserRepository()
    private val createResultLiveData: MutableLiveData<Boolean> = MutableLiveData()
    private val _matchesLiveData = MutableLiveData<List<Match>>()
    val matchesLiveData: LiveData<List<Match>> get() = _matchesLiveData
    private val _likedMatchesLiveData = MutableLiveData<List<Match>>()
    val likedMatchesLiveData: LiveData<List<Match>> get() = _likedMatchesLiveData
    private val _playedMatchesLiveData = MutableLiveData<List<Match>>()
    val playedMatchesLiveData: LiveData<List<Match>> get() = _playedMatchesLiveData


    private val _fotoPerfilUri1LiveData = MutableLiveData<Uri>()
    private val _fotoPerfilUri2LiveData = MutableLiveData<Uri>()
    private val _fotoPerfilUri3LiveData = MutableLiveData<Uri>()
    private val _fotoPerfilUri4LiveData = MutableLiveData<Uri>()
    val fotoPerfilUri1LiveData: LiveData<Uri> get() = _fotoPerfilUri1LiveData
    val fotoPerfilUri2LiveData: LiveData<Uri> get() = _fotoPerfilUri2LiveData
    val fotoPerfilUri3LiveData: LiveData<Uri> get() = _fotoPerfilUri3LiveData
    val fotoPerfilUri4LiveData: LiveData<Uri> get() = _fotoPerfilUri4LiveData

    fun getCreateResult(): LiveData<Boolean> = createResultLiveData
    fun getMatchesByCity(city: String) {
        matchRepository.getMatchesByCity(city) { matches ->
            _matchesLiveData.postValue(matches)
        }
    }

    fun getMatches(): List<Match> {
        return matchRepository.getMatches2()
    }

    fun registerMatch(selectedMatchPrivacy: String, name: String, fechaString: String, place: String, selectedMatchType: String,idUser1:String,code:String) {
        val public = selectedMatchPrivacy == "Publico"
        val doubles = selectedMatchType == "Dobles"
        var date:Date?=null
        var city =""
        val formatoFecha = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        Log.d("LOG", "Match Created with name: $name")
        try {
            date = formatoFecha.parse(fechaString)
        } catch (e: ParseException) {
        }
        val addressParts = place.split(",")
        if (addressParts.size >= 3) {
            city = addressParts[2].trim() // Obtén la ciudad (puede necesitar más validación o manipulación según el formato esperado)
        } else {
        }
        val match=Match("",public,name,date,place,idUser1,"","","",doubles,"",code)
        matchRepository.createMatch(match){ success ->
            createResultLiveData.value = success
        }
    }
    fun getMatchById(matchId: String): Match? {
        return _matchesLiveData.value?.find { match -> match.idMatch == matchId }
    }

    fun getMatchById2(matchId:String): Match? {
        return getMatches().find { match -> match.idMatch == matchId }
    }

    fun updateMatchUserId(matchId:String,userId:String, pos: Int){
        matchRepository.asignUserToMatch(matchId,userId,pos)
    }

    fun getPerfilPhoto(userId:String,pos:Int){
        userRepository.obtenerUsuarioById(userId){ user ->
            user?.imageView?.let { uri ->
                when (pos) {
                    1 -> _fotoPerfilUri1LiveData.value = uri
                    2 -> _fotoPerfilUri2LiveData.value = uri
                    3 -> _fotoPerfilUri3LiveData.value = uri
                    4 -> _fotoPerfilUri4LiveData.value = uri
                }
            }
        }
    }

    fun updateResult(matchId:String,result:String){
        matchRepository.updateResult(matchId,result)
    }

    fun cargarLikedMatchesList(likedMatchesIdsList: List<String>) {
        val likedMatchesList = mutableListOf<Match>()
        for (matchId in likedMatchesIdsList) {
            matchRepository.getMatchById(matchId) { match ->
                match?.let { likedMatchesList.add(it) }
                // Verificar si se han obtenido todos los matches y actualizar LiveData si es necesario
                if (likedMatchesList.size == likedMatchesIdsList.size) {
                    _likedMatchesLiveData.value = likedMatchesList
                }
            }
        }
    }

    fun cargarPlayedMatches(userId: String){
        matchRepository.getMatchesPlayed(userId){matchesPlayed->
            _playedMatchesLiveData.value=matchesPlayed
        }
    }

     fun generateUniqueCode(length: Int = 6): String {
        val allowedChars = ('A'..'Z') + ('a'..'z') + ('0'..'9') // Caracteres permitidos
        return (1..length)
            .map { allowedChars.random() } // Genera un código aleatorio con la longitud especificada
            .joinToString("") // Convierte la lista de caracteres en un String
    }

    fun setPuntos(userId: String, nuevosPuntos: Int, callback: (Boolean) -> Unit) {
        userRepository.setPuntos(userId, nuevosPuntos) { success ->
            callback(success)
        }
    }
}
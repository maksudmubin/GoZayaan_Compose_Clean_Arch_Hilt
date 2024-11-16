package com.mubin.gozayaan.ui.home

import android.annotation.SuppressLint
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.mubin.gozayaan.data.model.DestinationResponse

@SuppressLint("MutableCollectionMutableState")
class HomeUiState {

    var query by mutableStateOf("")
    var isLoading by mutableStateOf(false)
    var showRootLayout by mutableStateOf(false)
    var hideBottomNav by mutableStateOf(false)

    var response: DestinationResponse? by mutableStateOf(null)



}
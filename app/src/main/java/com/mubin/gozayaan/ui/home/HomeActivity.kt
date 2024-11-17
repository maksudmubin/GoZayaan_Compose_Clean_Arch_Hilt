package com.mubin.gozayaan.ui.home

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.tooling.preview.Preview
import com.mubin.gozayaan.base.theme.GoZayaanTheme
import com.mubin.gozayaan.base.utils.executeBodyOrReturnNullSuspended
import com.mubin.gozayaan.ui.composable.CustomAlertDialog
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeActivity : ComponentActivity() {

    private val vm by viewModels<HomeViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            GoZayaanTheme {

                val shouldShowDialog = remember { mutableStateOf(false) }
                val scope = rememberCoroutineScope()

                CustomAlertDialog(
                    shouldShowDialog = shouldShowDialog,
                    title = "Error",
                    text = "Ops! Something bad happened.",
                    positiveButtonTitle = "Okay"
                )

                if (vm.uiState.response == null) {
                    LaunchedEffect(
                        key1 = "HomeActivity",
                        block = {
                            scope.launch {
                                executeBodyOrReturnNullSuspended {
                                    vm.uiState.setIsLoading(true)

                                    val response = vm.getDestinations()
                                    if (response == null) {
                                        shouldShowDialog.value = true
                                    } else {
                                        vm.uiState.setRemoteResponse(response)
                                    }

                                    vm.uiState.setIsLoading(false)

                                }
                            }
                        }
                    )
                }

                MainScreen(vm.uiState)
            }
        }
    }

}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    GoZayaanTheme {
        MainScreen(HomeUiState())
    }
}
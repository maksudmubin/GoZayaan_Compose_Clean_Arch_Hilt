package com.mubin.gozayaan.ui.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.PlatformTextStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.mubin.gozayaan.R
import com.mubin.gozayaan.base.theme.Background
import com.mubin.gozayaan.base.theme.GoZayaanTheme
import com.mubin.gozayaan.base.utils.RubikFontBold
import com.mubin.gozayaan.base.utils.RubikFontMedium
import com.mubin.gozayaan.base.utils.RubikFontRegular
import com.mubin.gozayaan.base.utils.executionlocker.withExecutionLocker
import com.mubin.gozayaan.data.model.DestinationResponse
import com.mubin.gozayaan.ui.composable.AutoImageSlider
import com.mubin.gozayaan.ui.composable.BottomNavGraph
import com.mubin.gozayaan.ui.composable.CategoryGrid
import com.mubin.gozayaan.ui.composable.CircularProgressBar
import com.mubin.gozayaan.ui.composable.FloatingNavigationBar
import com.mubin.gozayaan.ui.composable.GlobalTopBar
import com.mubin.gozayaan.ui.composable.HomeTopBar
import com.mubin.gozayaan.ui.composable.RecommendationCardVertical
import com.mubin.gozayaan.ui.composable.RecommendedRow
import com.mubin.gozayaan.ui.composable.SearchBar

@Composable
fun MainScreen(uiState: HomeUiState) {
    val navController = rememberNavController()

    Box(modifier = Modifier.fillMaxSize()) {
        // Main content
        BottomNavGraph(navController = navController, uiState)

        if (!uiState.hideBottomNav) {
            // Floating Bottom Navigation Bar
            Box(
                modifier = Modifier
                    .align(Alignment.BottomCenter) // Align it at the bottom of the screen
                    .padding(bottom = 36.dp)
            ) {
                FloatingNavigationBar(navController = navController)
            }
        }

    }
}

@Composable
fun HomeScreen(uiState: HomeUiState, navController: NavHostController) {

    val focusManager = LocalFocusManager.current
    val keyboardController = LocalSoftwareKeyboardController.current

    Scaffold(
        modifier = Modifier
            .background(Background)
            .padding(WindowInsets.statusBars.asPaddingValues())
            .fillMaxSize(),
        topBar = {

            HomeTopBar(
                onOptionClick = {

                },
                onLocationClick = {

                },
                onProfileClick = {

                }
            )

        }
    ) { it ->

        if (uiState.isLoading) {
            Box(
                modifier = Modifier
                    .background(Background)
                    .fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressBar()
            }
        } else {
            Column(
                modifier = Modifier
                    .padding(it)
                    .fillMaxSize()
                    .background(Background)
            ) {
                SearchBar(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 15.dp)
                        .padding(horizontal = 30.dp),
                    query = uiState.query,
                    onQueryChange = { uiState.query = it },
                    onSearch = {
                        focusManager.clearFocus()
                        keyboardController?.hide()
                        navController.navigate("recommended/${true}")
                    },
                    placeholder = "Search"
                )

                CategoryGrid { clcikedItem ->

                    when (clcikedItem) {

                        "Flights" -> {
                            navController.navigate("recommended/${false}")
                        }

                        "Hotels" -> {
                            navController.navigate("recommended/${false}")
                        }

                        "Visa" -> {
                            navController.navigate("recommended/${false}")
                        }

                        "Buses" -> {
                            navController.navigate("recommended/${false}")
                        }

                    }

                }

                uiState.response?.let { recommendations ->
                    RecommendedRow(
                        recommendations,
                        onItemClick = { index ->
                            navController.navigate("details/$index")
                        },
                        onSeeAllClick = {
                            navController.navigate("recommended/${false}")
                        }
                    )
                }

            }
        }
    }
}

@Composable
fun BookmarkScreen(uiState: HomeUiState) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Background),
        contentAlignment = Alignment.Center
    ) {
        Text(
            color = Color.White,
            text = "Bookmark Screen"
        )
    }
}

@Composable
fun NotificationsScreen(uiState: HomeUiState) {
    Box(modifier = Modifier.fillMaxSize().background(Background), contentAlignment = Alignment.Center) {
        Text(color = Color.White, text = "Notifications Screen")
    }
}

@Composable
fun ProfileScreen(uiState: HomeUiState) {
    Box(modifier = Modifier.fillMaxSize().background(Background), contentAlignment = Alignment.Center) {
        Text(color = Color.White, text = "Profile Screen")
    }
}

@Composable
fun RecommendedScreen(isSearch: Boolean, uiState: HomeUiState, navController: NavController) {

    Scaffold(
        modifier = Modifier
            .background(Background)
            .padding(WindowInsets.statusBars.asPaddingValues())
            .fillMaxSize(),
        topBar = {
            GlobalTopBar(
                modifier = Modifier
                    .background(Background),
                title = if (isSearch) "Searched: '${uiState.query}'" else "Recommended",
                onBackPressed = {
                    withExecutionLocker(delay = 1000) {
                        navController.popBackStack()
                    }
                }
            )
        }
    ) {

        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            modifier = Modifier
                .fillMaxSize()
                .background(Background)
                .padding(it)
                .padding(horizontal = 30.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            if (isSearch) {
                uiState.response?.let { items ->
                    val newList = mutableListOf<DestinationResponse.DestinationResponseItem>()
                    items.forEach { item ->
                        if (item.propertyName?.contains(uiState.query, ignoreCase = true) == true
                            || item.location?.contains(uiState.query, ignoreCase = true) == true)
                        {
                            newList.add(item)
                        }
                    }

                    if (newList.isNotEmpty()) {
                        items(newList.size) { index ->
                            uiState.response?.get(index)?.let { item ->
                                RecommendationCardVertical(
                                    recommendation = item,
                                    onClick = {
                                        navController.navigate("details/$index")
                                    }
                                )
                            }
                        }
                    }

                }
            } else {
                uiState.response?.size?.let { size ->
                    items(size) { index ->
                        uiState.response?.get(index)?.let { item ->
                            RecommendationCardVertical(
                                recommendation = item,
                                onClick = {
                                    navController.navigate("details/$index")
                                }
                            )
                        }
                    }
                }
            }

        }

    }

}

@Composable
fun DetailsScreen(item: DestinationResponse.DestinationResponseItem?, navController: NavController) {

    Column(
      modifier = Modifier
          .background(Background)
          .fillMaxSize()
          .padding(WindowInsets.statusBars.asPaddingValues())
    ) {

        AutoImageSlider(
            imageUrls = item?.detailImages,
            onBackPressed = {
                withExecutionLocker(1000) {
                    navController.popBackStack()
                }
            }
        )

        Row (
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 40.dp)
                .padding(horizontal = 30.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {

            item?.propertyName?.let { propertyName ->
                val screenWidth = LocalConfiguration.current.screenWidthDp.dp
                Text(
                    modifier = Modifier
                        .width(screenWidth.times(0.7f)),
                    color = Color.White,
                    fontFamily = RubikFontBold,
                    fontSize = 24.sp,
                    text = propertyName,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    style = TextStyle(
                        platformStyle = PlatformTextStyle(
                            includeFontPadding = false
                        )
                    )
                )
            }

            item?.rating?.let { rating ->
                Row(
                    modifier = Modifier,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        painter = painterResource(R.drawable.ic_details_rating),
                        contentDescription = "Rating"
                    )
                    Spacer(
                        modifier = Modifier
                            .size(4.dp)
                    )
                    Text(
                        modifier = Modifier
                            .alpha(0.7f),
                        color = Color.White,
                        fontFamily = RubikFontRegular,
                        fontSize = 14.sp,
                        text = rating.toString()
                    )
                }

            }

        }

        Row(
            modifier = Modifier
                .padding(top = 8.dp)
                .padding(horizontal = 30.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(R.drawable.ic_location_filled),
                contentDescription = "Location"
            )

            Spacer(
                modifier = Modifier
                    .size(4.dp)
            )

            item?.location?.let { location ->
                Text(
                    modifier = Modifier
                        .alpha(0.7f),
                    color = Color.White,
                    fontFamily = RubikFontRegular,
                    fontSize = 14.sp,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    text = location
                )
            }
        }

        Row(
            modifier = Modifier
                .padding(top = 30.dp)
                .padding(horizontal = 30.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

                Text(
                    modifier = Modifier,
                    color = Color.White.copy(alpha = 0.9f),
                    fontFamily = RubikFontMedium,
                    fontSize = 18.sp,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    text = "About This Trip"
                )


            Spacer(
                modifier = Modifier
                    .size(6.dp)
            )

            Image(
                painter = painterResource(R.drawable.ic_star_eyes_emoji),
                contentDescription = "Location"
            )
        }

        item?.description?.let { description ->
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 12.dp)
                    .padding(horizontal = 30.dp)
                    .alpha(0.7f),
                color = Color.White,
                fontFamily = RubikFontMedium,
                fontSize = 18.sp,
                text = description
            )
        }

    }

}

@Preview(showBackground = true)
@Composable
fun BottomNavScreensPreview() {
    GoZayaanTheme {
        MainScreen(HomeUiState())
    }
}
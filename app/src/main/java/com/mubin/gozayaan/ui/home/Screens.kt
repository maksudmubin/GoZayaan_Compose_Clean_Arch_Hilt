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
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.mubin.gozayaan.R
import com.mubin.gozayaan.base.theme.Background
import com.mubin.gozayaan.base.theme.GoZayaanTheme
import com.mubin.gozayaan.base.utils.RubikFontBold
import com.mubin.gozayaan.base.utils.RubikFontMedium
import com.mubin.gozayaan.base.utils.RubikFontRegular
import com.mubin.gozayaan.base.utils.executionlocker.withExecutionLocker
import com.mubin.gozayaan.base.utils.logger.GzLogger
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

/**
 * Composable function to display the main screen of the application.
 * The screen includes a bottom navigation bar, main content handled by a [BottomNavGraph],
 * and conditional rendering of a floating navigation bar based on the [HomeUiState].
 *
 * @param uiState [HomeUiState] that determines the visibility of the bottom navigation bar and provides other UI state information.
 */
@Composable
fun MainScreen(uiState: HomeUiState) {
    // Log the current UI state of the MainScreen
    GzLogger.d("MainScreen", "Rendering MainScreen with uiState: $uiState")

    // Remember the NavController for navigation
    val navController = rememberNavController()

    // Main container for the screen
    Box(modifier = Modifier.fillMaxSize()) {
        // Log the rendering of the main content
        GzLogger.d("MainScreen", "Rendering BottomNavGraph")

        // Main content handled by BottomNavGraph
        BottomNavGraph(navController = navController, uiState)

        // Conditionally render the floating bottom navigation bar
        if (!uiState.hideBottomNav) {
            GzLogger.d("MainScreen", "Rendering FloatingNavigationBar, hideBottomNav: ${uiState.hideBottomNav}")

            // Floating Bottom Navigation Bar
            Box(
                modifier = Modifier
                    .align(Alignment.BottomCenter) // Align it at the bottom of the screen
                    .padding(bottom = 36.dp) // Add bottom padding for aesthetics
            ) {
                FloatingNavigationBar(navController = navController)
            }
        } else {
            GzLogger.d("MainScreen", "FloatingNavigationBar is hidden")
        }
    }
}

/**
 * Composable function for the Home Screen UI.
 * Displays a top bar, search functionality, categories, recommendations, and a loading indicator if necessary.
 * Integrates with a navigation controller to handle user interactions.
 *
 * @param uiState [HomeUiState] that provides state information for the screen, including loading status and data.
 * @param navController [NavHostController] used for navigating between different screens in the app.
 */
@Composable
fun HomeScreen(uiState: HomeUiState, navController: NavHostController) {

    // Ensure the status bar is visible
    GzLogger.d("HomeScreen", "Displaying HomeScreen with status bar visible")
    ShowHideStatusBarScreen(isVisible = true)

    // Local focus and keyboard controllers
    val focusManager = LocalFocusManager.current
    val keyboardController = LocalSoftwareKeyboardController.current

    // Scaffold for screen layout structure
    Scaffold(
        modifier = Modifier
            .background(Background) // Background color
            .padding(WindowInsets.statusBars.asPaddingValues()) // Padding to account for the status bar
            .fillMaxSize(),
        topBar = {
            // Top bar of the Home Screen
            HomeTopBar(
                onOptionClick = {
                    GzLogger.d("HomeScreen", "Options menu clicked")
                },
                onLocationClick = {
                    GzLogger.d("HomeScreen", "Location button clicked")
                },
                onProfileClick = {
                    GzLogger.d("HomeScreen", "Profile button clicked")
                }
            )
        }
    ) { paddingValues ->
        // Check if the screen is in a loading state
        if (uiState.isLoading) {
            GzLogger.d("HomeScreen", "UI is in loading state")
            Box(
                modifier = Modifier
                    .background(Background)
                    .fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressBar()
            }
        } else {
            GzLogger.d("HomeScreen", "Rendering content with UI state: $uiState")

            Column(
                modifier = Modifier
                    .padding(paddingValues)
                    .fillMaxSize()
                    .background(Background)
            ) {
                // Search bar
                SearchBar(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 15.dp)
                        .padding(horizontal = 30.dp),
                    query = uiState.query,
                    onQueryChange = {
                        GzLogger.d("HomeScreen", "Search query changed to: $it")
                        uiState.updateQuery(it)
                    },
                    onSearch = {
                        GzLogger.d("HomeScreen", "Search initiated with query: ${uiState.query}")
                        focusManager.clearFocus()
                        keyboardController?.hide()
                        navController.navigate("recommended/${true}")
                    },
                    placeholder = "Search"
                )

                // Category Grid
                CategoryGrid { clickedItem ->
                    GzLogger.d("HomeScreen", "Category clicked: $clickedItem")
                    when (clickedItem) {
                        "Flights", "Hotels", "Visa", "Buses" -> {
                            navController.navigate("recommended/${false}")
                        }
                    }
                }

                // Recommendations Section
                uiState.response?.let { recommendations ->
                    GzLogger.d("HomeScreen", "Displaying recommendations: ${recommendations.size} items")
                    RecommendedRow(
                        recommendations = recommendations,
                        onItemClick = { index ->
                            GzLogger.d("HomeScreen", "Recommendation item clicked at index: $index")
                            navController.navigate("details/$index")
                        },
                        onSeeAllClick = {
                            GzLogger.d("HomeScreen", "See All clicked for recommendations")
                            navController.navigate("recommended/${false}")
                        }
                    )
                }
            }
        }
    }
}

/**
 * Displays the Bookmark Screen of the application.
 * This screen shows a centered text indicating the user is on the Bookmark Screen.
 *
 * @param uiState The current state of the Home UI, passed as a parameter for potential state handling.
 */
@Composable
fun BookmarkScreen(uiState: HomeUiState) {
    // Log the entry to the BookmarkScreen
    GzLogger.d("BookmarkScreen", "Displaying Bookmark Screen")

    // Ensures the status bar is visible when this screen is displayed
    ShowHideStatusBarScreen(isVisible = true)

    // Sets up the screen with a full background and centered text
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

/**
 * Displays the Notifications Screen of the application.
 * This screen shows a centered text indicating the user is on the Notifications Screen.
 *
 * @param uiState The current state of the Home UI, passed as a parameter for potential state handling.
 */
@Composable
fun NotificationsScreen(uiState: HomeUiState) {
    // Log the entry to the NotificationsScreen
    GzLogger.d("NotificationsScreen", "Displaying Notifications Screen")

    // Ensures the status bar is visible when this screen is displayed
    ShowHideStatusBarScreen(isVisible = true)

    // Sets up the screen with a full background and centered text
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Background),
        contentAlignment = Alignment.Center
    ) {
        Text(color = Color.White, text = "Notifications Screen")
    }
}

/**
 * Displays the Profile Screen of the application.
 * This screen shows a centered text indicating the user is on the Profile Screen.
 *
 * @param uiState The current state of the Home UI, passed as a parameter for potential state handling.
 */
@Composable
fun ProfileScreen(uiState: HomeUiState) {
    // Log the entry to the ProfileScreen
    GzLogger.d("ProfileScreen", "Displaying Profile Screen")

    // Ensures the status bar is visible when this screen is displayed
    ShowHideStatusBarScreen(isVisible = true)

    // Sets up the screen with a full background and centered text
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Background),
        contentAlignment = Alignment.Center
    ) {
        Text(color = Color.White, text = "Profile Screen")
    }
}

/**
 * Displays the Recommended Screen, which dynamically adapts based on whether the user is searching
 * or viewing general recommendations. It uses a grid layout to display recommended items or search results.
 *
 * @param isSearch A flag indicating if the screen is in search mode.
 * @param uiState The current state of the Home UI, containing the query, response, and other data.
 * @param navController Navigation controller to manage navigation between screens.
 */
@Composable
fun RecommendedScreen(isSearch: Boolean, uiState: HomeUiState, navController: NavController) {
    // Log the entry to the RecommendedScreen
    GzLogger.d("RecommendedScreen", "Displaying Recommended Screen with isSearch = $isSearch")

    // Ensures the status bar is visible when this screen is displayed
    ShowHideStatusBarScreen(isVisible = true)

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
                    // Log back button press
                    GzLogger.d("RecommendedScreen", "Back button pressed")
                    // Handles back navigation with a delay to prevent spamming
                    withExecutionLocker(delay = 1000) {
                        navController.popBackStack()
                    }
                }
            )
        }
    ) {
        // Log the initialization of the LazyVerticalGrid
        GzLogger.d("RecommendedScreen", "Initializing LazyVerticalGrid")

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
                    // Filter the items based on the search query
                    val filteredList = mutableListOf<DestinationResponse.DestinationResponseItem>()
                    items.forEach { item ->
                        if (item.propertyName?.contains(uiState.query, ignoreCase = true) == true
                            || item.location?.contains(uiState.query, ignoreCase = true) == true
                        ) {
                            filteredList.add(item)
                        }
                    }

                    if (filteredList.isNotEmpty()) {
                        // Log the number of filtered items
                        GzLogger.d("RecommendedScreen", "Filtered items count: ${filteredList.size}")

                        items(filteredList.size) { index ->
                            uiState.response?.get(index)?.let { item ->
                                RecommendationCardVertical(
                                    recommendation = item,
                                    onClick = {
                                        // Log item click with index
                                        GzLogger.d("RecommendedScreen", "Navigating to details for index: $index")
                                        navController.navigate("details/$index")
                                    }
                                )
                            }
                        }
                    }
                }
            } else {
                uiState.response?.size?.let { size ->
                    // Log the total size of recommendations
                    GzLogger.d("RecommendedScreen", "Displaying $size recommended items")

                    items(size) { index ->
                        uiState.response?.get(index)?.let { item ->
                            RecommendationCardVertical(
                                recommendation = item,
                                onClick = {
                                    // Log item click with index
                                    GzLogger.d("RecommendedScreen", "Navigating to details for index: $index")
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

/**
 * Displays the Details Screen for a selected destination.
 * This screen includes an image slider, property details such as name, rating, location,
 * and a detailed description of the destination.
 *
 * @param item The destination item to display details for.
 * @param navController Navigation controller to manage back navigation.
 */
@Composable
fun DetailsScreen(item: DestinationResponse.DestinationResponseItem?, navController: NavController) {
    // Log the entry to the DetailsScreen
    GzLogger.d("DetailsScreen", "Displaying details for item: ${item?.propertyName}")

    // Ensures the status bar is hidden for this screen
    ShowHideStatusBarScreen(isVisible = false)

    Column(
        modifier = Modifier
            .background(Background)
            .fillMaxSize()
    ) {
        // Display the auto image slider with back navigation
        AutoImageSlider(
            imageUrls = item?.detailImages,
            onBackPressed = {
                // Log back button press
                GzLogger.d("DetailsScreen", "Back button pressed")
                withExecutionLocker(1000) {
                    navController.popBackStack()
                }
            }
        )

        // Display the property name and rating
        Row(
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
                    Spacer(modifier = Modifier.size(4.dp))
                    Text(
                        modifier = Modifier.alpha(0.7f),
                        color = Color.White,
                        fontFamily = RubikFontRegular,
                        fontSize = 14.sp,
                        text = rating.toString()
                    )
                }
            }
        }

        // Display the location of the property
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
            Spacer(modifier = Modifier.size(4.dp))
            item?.location?.let { location ->
                Text(
                    modifier = Modifier.alpha(0.7f),
                    color = Color.White,
                    fontFamily = RubikFontRegular,
                    fontSize = 14.sp,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    text = location
                )
            }
        }

        // Display the "About This Trip" section title
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
            Spacer(modifier = Modifier.size(6.dp))
            Image(
                painter = painterResource(R.drawable.ic_star_eyes_emoji),
                contentDescription = "Location"
            )
        }

        // Display the description of the trip
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

/**
 * Controls the visibility of the system status bar and its color.
 *
 * @param isVisible A flag indicating whether the status bar should be visible.
 */
@Composable
fun ShowHideStatusBarScreen(isVisible: Boolean) {
    // Remember the System UI Controller to manage system bar properties
    val systemUiController = rememberSystemUiController()

    // Log the status bar visibility change
    GzLogger.d(
        "ShowHideStatusBarScreen",
        "Setting status bar visibility to ${if (isVisible) "visible" else "hidden"}"
    )

    // Set the color of the system bars and their visibility based on the isVisible parameter
    systemUiController.setSystemBarsColor(
        color = if (isVisible) Background else Color.Transparent, // Set background color based on visibility
        darkIcons = false // Use light icons for better contrast
    )
    systemUiController.isStatusBarVisible = isVisible // Show or hide the status bar
}

@Preview(showBackground = true)
@Composable
fun BottomNavScreensPreview() {
    GoZayaanTheme {
        MainScreen(HomeUiState())
    }
}
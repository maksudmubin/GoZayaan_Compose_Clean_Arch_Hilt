package com.mubin.gozayaan.ui.composable

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.PlatformTextStyle
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.navArgument
import coil.compose.SubcomposeAsyncImage
import com.mubin.gozayaan.R
import com.mubin.gozayaan.base.theme.Background
import com.mubin.gozayaan.base.theme.GoZayaanTheme
import com.mubin.gozayaan.base.theme.Surface
import com.mubin.gozayaan.base.utils.GilroyFontMedium
import com.mubin.gozayaan.base.utils.RubikFontBold
import com.mubin.gozayaan.base.utils.RubikFontMedium
import com.mubin.gozayaan.base.utils.RubikFontRegular
import com.mubin.gozayaan.base.utils.createImageRequest
import com.mubin.gozayaan.base.utils.logger.GzLogger
import com.mubin.gozayaan.data.model.DestinationResponse
import com.mubin.gozayaan.ui.home.BookmarkScreen
import com.mubin.gozayaan.ui.home.DetailsScreen
import com.mubin.gozayaan.ui.home.HomeScreen
import com.mubin.gozayaan.ui.home.HomeUiState
import com.mubin.gozayaan.ui.home.MainScreen
import com.mubin.gozayaan.ui.home.NotificationsScreen
import com.mubin.gozayaan.ui.home.ProfileScreen
import com.mubin.gozayaan.ui.home.RecommendedScreen
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

/**
 * Sealed class representing the items in the bottom navigation menu.
 * Each item has a route, a label, and an icon associated with it.
 * This class helps define the navigation structure in the bottom navigation bar.
 *
 * @property route The unique route associated with the navigation item.
 * @property label The label text displayed for the navigation item.
 * @property icon The resource ID of the icon for the navigation item.
 */
sealed class BottomNavItem(val route: String, val label: String, val icon: Int) {

    /**
     * Represents the 'Home' item in the bottom navigation.
     * This is typically the starting or main screen of the app.
     */
    data object Home : BottomNavItem("home", "Home", R.drawable.ic_bottom_nav_home)

    /**
     * Represents the 'Bookmark' item in the bottom navigation.
     * This item leads to a screen where users can view their saved content.
     */
    data object Bookmark : BottomNavItem("bookmark", "Bookmark", R.drawable.ic_bottom_nav_bookmark)

    /**
     * Represents the 'Notifications' item in the bottom navigation.
     * This item navigates to a screen showing notifications or alerts for the user.
     */
    data object Notifications : BottomNavItem("notifications", "Notifications", R.drawable.ic_bottom_nav_notification)

    /**
     * Represents the 'Profile' item in the bottom navigation.
     * This item allows users to navigate to their profile or account settings.
     */
    data object Profile : BottomNavItem("profile", "Profile", R.drawable.ic_bottom_nav_profile)
}

/**
 * This Composable defines the navigation graph for the bottom navigation bar.
 * It sets up the routes and screens associated with each bottom navigation item.
 *
 * @param navController The [NavHostController] used to manage navigation actions.
 * @param uiState The current UI state for managing visibility and data related to the screens.
 */
@Composable
fun BottomNavGraph(navController: NavHostController, uiState: HomeUiState) {
    NavHost(
        navController = navController,  // Controls navigation for this graph
        startDestination = BottomNavItem.Home.route  // The initial screen to show when the app starts
    ) {
        // Home screen - default bottom navigation screen
        composable(route = BottomNavItem.Home.route) {
            uiState.hideBottomNav = false  // Make sure the bottom nav is visible
            GzLogger.d("BottomNavGraph", "Navigating to Home Screen")
            HomeScreen(uiState, navController)
        }

        // Bookmark screen - view saved items
        composable(route = BottomNavItem.Bookmark.route) {
            uiState.hideBottomNav = false  // Bottom nav remains visible
            GzLogger.d("BottomNavGraph", "Navigating to Bookmark Screen")
            BookmarkScreen(uiState)
        }

        // Notifications screen - shows alerts and messages
        composable(route = BottomNavItem.Notifications.route) {
            uiState.hideBottomNav = false  // Keep bottom nav visible
            GzLogger.d("BottomNavGraph", "Navigating to Notifications Screen")
            NotificationsScreen(uiState)
        }

        // Profile screen - view or edit user profile
        composable(route = BottomNavItem.Profile.route) {
            uiState.hideBottomNav = false  // Bottom nav remains visible
            GzLogger.d("BottomNavGraph", "Navigating to Profile Screen")
            ProfileScreen(uiState)
        }

        // Recommended screen - could be for displaying recommended content
        composable(
            route = "recommended/{isSearch}",
            arguments = listOf(navArgument("isSearch") { type = NavType.BoolType })
        ) { backStackEntry ->
            uiState.hideBottomNav = true  // Hide bottom nav for detailed views
            val isSearch = backStackEntry.arguments?.getBoolean("isSearch", false) ?: false
            GzLogger.d("BottomNavGraph", "Navigating to Recommended Screen, isSearch: $isSearch")
            RecommendedScreen(isSearch, uiState, navController)
        }

        // Details screen - display detailed view for a particular item, identified by index
        composable(
            route = "details/{index}",
            arguments = listOf(navArgument("index") { type = NavType.IntType })
        ) { backStackEntry ->
            uiState.hideBottomNav = true  // Hide bottom nav for detailed views
            val index = backStackEntry.arguments?.getInt("index")
            GzLogger.d("BottomNavGraph", "Navigating to Details Screen, index: $index")
            DetailsScreen(index?.let { uiState.response?.get(it) }, navController)
        }
    }
}

/**
 * A Composable that creates a floating navigation bar with icons for navigating between
 * different screens in the app, such as Home, Bookmark, Notifications, and Profile.
 * The navigation bar adapts its icon color based on whether the item is selected.
 *
 * @param navController The [NavController] used to manage the navigation between screens.
 */
@Composable
fun FloatingNavigationBar(navController: NavController) {
    val items = listOf(
        BottomNavItem.Home,
        BottomNavItem.Bookmark,
        BottomNavItem.Notifications,
        BottomNavItem.Profile
    )

    NavigationBar(
        windowInsets = WindowInsets(0.dp, 0.dp, 0.dp, 0.dp), // Remove any insets to customize the layout
        containerColor = Color.Transparent, // Transparent background for the navigation bar
        modifier = Modifier
            .padding(horizontal = 16.dp, vertical = 8.dp) // Adjust padding to remove extra space
            .fillMaxWidth() // Make the navigation bar fill the width of the screen
            .height(70.dp) // Set the height of the navigation bar
            .clip(RoundedCornerShape(40)) // Apply rounded corners for the navigation bar
            .background(color = Surface) // Set background color for the navigation bar itself
    ) {
        // Get the current route from the NavController
        val currentRoute = navController.currentBackStackEntryAsState().value?.destination?.route
        items.forEach { item ->
            val selected = currentRoute == item.route // Check if the current item is selected
            NavigationBarItem(
                icon = {
                    Icon(
                        painter = painterResource(item.icon), // Load the icon resource
                        contentDescription = item.label, // Set the content description for accessibility
                        modifier = Modifier.size(24.dp), // Adjust the size of the icon
                        //tint = if (selected) Color(0xFFFF6D6D) else Color.White // Color change for selected item
                    )
                },
                label = null, // No label for the icon in this layout
                selected = selected, // Set whether the item is selected
                onClick = {
                    if (!selected) {
                        GzLogger.d("FloatingNavigationBar", "Navigating to ${item.label} screen")
                        navController.navigate(item.route) {
                            popUpTo(navController.graph.startDestinationId) { saveState = true }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = Color(0xFFFF6D6D), // Set color for the selected icon
                    unselectedIconColor = Color.White, // Set color for the unselected icon
                    indicatorColor = Color.Transparent // Remove the indicator color to match the desired style
                ),
                alwaysShowLabel = false // Hide labels for a more minimalistic look
            )
        }
    }
}

/**
 * This Composable represents the top bar for the home screen, which includes:
 * - An options button
 * - A location display section that is clickable
 * - A profile button
 *
 * Each button accepts a lambda function that is invoked when clicked.
 *
 * @param onOptionClick The lambda function to execute when the options button is clicked.
 * @param onLocationClick The lambda function to execute when the location section is clicked.
 * @param onProfileClick The lambda function to execute when the profile button is clicked.
 */
@Composable
fun HomeTopBar(
    onOptionClick: () -> Unit,
    onLocationClick: () -> Unit,
    onProfileClick: () -> Unit,
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(Background), // Background color for the top bar
        contentAlignment = Alignment.Center // Center align the content in the Box
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 30.dp, vertical = 25.dp), // Add padding around the top bar
            horizontalArrangement = Arrangement.Absolute.SpaceBetween, // Space items apart
            verticalAlignment = Alignment.CenterVertically // Vertically center the items
        ) {

            // Options Button
            IconButton(
                modifier = Modifier
                    .size(32.dp), // Icon size for the options button
                onClick = {
                    GzLogger.d("HomeTopBar", "Options button clicked")
                    onOptionClick() // Invoke the passed onOptionClick lambda
                }
            ) {
                Image(
                    modifier = Modifier
                        .size(24.dp), // Icon size for the options button
                    painter = painterResource(R.drawable.ic_home_options), // Load the options icon
                    contentDescription = "Options", // Content description for accessibility
                )
            }

            // Location Section
            Row(
                modifier = Modifier
                    .clickable {
                        GzLogger.d("HomeTopBar", "Location clicked")
                        onLocationClick() // Invoke the passed onLocationClick lambda
                    }
                    .padding(horizontal = 8.dp), // Padding between the icon and text
                verticalAlignment = Alignment.CenterVertically // Align items vertically in the row
            ) {
                Image(
                    painter = painterResource(R.drawable.ic_location_filled), // Location icon
                    contentDescription = "Location" // Content description for accessibility
                )

                Spacer(
                    modifier = Modifier
                        .size(4.dp) // Spacer between the icon and the text
                )

                Text(
                    modifier = Modifier
                        .alpha(0.5F), // Set text opacity to make it less prominent
                    color = Color.White, // White text color
                    fontFamily = RubikFontRegular, // Font style for the text
                    fontSize = 12.sp, // Font size for the location text
                    text = "New York, NY" // The location text
                )
            }

            // Profile Button
            IconButton(
                modifier = Modifier
                    .background(
                        color = Surface, // Set background color for the profile icon
                        shape = RoundedCornerShape(10.dp) // Apply rounded corners to the background
                    )
                    .size(32.dp), // Icon size for the profile button
                onClick = {
                    GzLogger.d("HomeTopBar", "Profile button clicked")
                    onProfileClick() // Invoke the passed onProfileClick lambda
                }
            ) {
                Image(
                    modifier = Modifier
                        .size(18.dp), // Icon size for the profile button
                    painter = painterResource(R.drawable.ic_home_profile), // Load the profile icon
                    contentDescription = "Profile" // Content description for accessibility
                )
            }

        }
    }
}

/**
 * This Composable represents a customizable search bar. It includes an icon,
 * a text field for query input, and triggers the onSearch action when the search is done.
 *
 * @param query The current text query in the search field.
 * @param onQueryChange A lambda function that updates the query when the text changes.
 * @param modifier Modifier to adjust the search bar's appearance (default is Modifier).
 * @param placeholder Text shown when the search field is empty, guiding users.
 * @param onSearch A lambda function that is invoked when the user presses "Done" or triggers search.
 */
@Composable
fun SearchBar(
    query: String,
    onQueryChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    placeholder: String = "Search...",
    onSearch: () -> Unit = {}
) {
    Row(
        modifier = modifier
            .fillMaxWidth() // Fill the available width
            .height(56.dp) // Set fixed height for the search bar
            .clip(RoundedCornerShape(16.dp)) // Apply rounded corners
            .background(Surface) // Set background color
            .padding(horizontal = 16.dp, vertical = 8.dp), // Padding inside the search bar
        verticalAlignment = Alignment.CenterVertically // Vertically center content in the Row
    ) {
        // Search Icon
        Image(
            painter = painterResource(R.drawable.ic_search), // Load the search icon
            contentDescription = "Search", // Accessibility content description
            modifier = Modifier.size(24.dp) // Set icon size
        )
        Spacer(modifier = Modifier.width(10.dp)) // Add some space between the icon and the text field

        // Basic Text Field for query input
        BasicTextField(
            modifier = Modifier.fillMaxWidth(), // Allow text field to take full available width
            value = query, // Bind the current query value
            onValueChange = { newQuery ->
                GzLogger.d("SearchBar", "Query changed: $newQuery") // Log query change
                onQueryChange(newQuery) // Invoke onQueryChange lambda when text changes
            },
            singleLine = true, // Ensure the text field is single line
            cursorBrush = SolidColor(Background), // Set cursor color to match background
            textStyle = MaterialTheme.typography.bodyMedium.copy(
                fontFamily = RubikFontRegular, // Set font family
                fontSize = 14.sp, // Set font size
                color = Color.White.copy(alpha = 0.5f) // Set text color with opacity
            ),
            decorationBox = { innerTextField ->
                // Show placeholder text when the query is empty
                if (query.isEmpty()) {
                    Text(
                        text = placeholder, // Display placeholder text
                        style = MaterialTheme.typography.bodyMedium.copy(
                            fontFamily = RubikFontRegular, // Set font family for placeholder
                            fontSize = 14.sp, // Set font size for placeholder
                            color = Color.White.copy(alpha = 0.3f) // Lighter color for placeholder
                        )
                    )
                }
                innerTextField() // Display the actual text field content
            },
            keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done), // Set IME action to "Done"
            keyboardActions = KeyboardActions(onDone = {
                GzLogger.d("SearchBar", "Search triggered") // Log when search is done
                onSearch() // Invoke onSearch lambda when "Done" action is triggered
            })
        )
    }
}

/**
 * Data class representing a Category item with a title and an icon resource.
 *
 * @param title The title or name of the category.
 * @param iconRes The resource ID for the category icon.
 */
data class CategoryItem(
    val title: String,  // Title of the category
    val iconRes: Int    // Resource ID for the category icon
)

/**
 * Composable function that displays a grid of categories, each represented by a card.
 * When a card is clicked, the provided [onCardClick] function is called with the category title.
 *
 * @param onCardClick A lambda function that gets invoked with the title of the clicked category.
 */
@Composable
fun CategoryGrid(onCardClick: (String) -> Unit) {
    // List of categories to be displayed
    val categories = listOf(
        CategoryItem("Flights", R.drawable.ic_category_flights),
        CategoryItem("Hotels", R.drawable.ic_category_hotels),
        CategoryItem("Visa", R.drawable.ic_category_visa),
        CategoryItem("Buses", R.drawable.ic_category_buses)
    )

    // Column layout for the grid and header text
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 40.dp)
            .padding(horizontal = 30.dp)
    ) {

        // Header text displaying the title "Popular Categories"
        Text(
            modifier = Modifier,
            fontSize = 18.sp,
            fontFamily = RubikFontMedium,
            color = Color.White.copy(alpha = 0.9f),
            text = "Popular Categories"
        )

        // LazyVerticalGrid to display the categories in a grid layout
        LazyVerticalGrid(
            columns = GridCells.Fixed(4),  // 4 columns in the grid
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 25.dp),  // Adds top padding
            horizontalArrangement = Arrangement.spacedBy(16.dp),  // Horizontal spacing between items
            verticalArrangement = Arrangement.spacedBy(12.dp)  // Vertical spacing between items
        ) {
            // Iterate through the categories and create CategoryCards for each item
            items(categories.size) { index ->
                // Log the category title when a category is rendered
                GzLogger.d("CategoryGrid", "Rendering category: ${categories[index].title}")
                CategoryCard(category = categories[index], onCardClick = onCardClick)
            }
        }
    }
}

/**
 * Composable function that represents a category card. The card displays an icon and a title
 * for a given category. When clicked, the [onCardClick] function is invoked with the category title.
 *
 * @param category The category data object containing the title and icon resource.
 * @param onCardClick A lambda function that gets called with the category title when the card is clicked.
 */
@Composable
fun CategoryCard(category: CategoryItem, onCardClick: (String) -> Unit) {
    // Log when a category card is being rendered
    GzLogger.d("CategoryCard", "Rendering card for category: ${category.title}")

    // Column layout for the category card
    Column(
        modifier = Modifier
            .clip(RoundedCornerShape(16.dp)) // Round the corners of the card
            .background(Surface) // Set the background of the card
            .size(width = 70.dp, height = 80.dp) // Set the fixed size for the card
            .clickable {
                // Log the category click event
                GzLogger.d("CategoryCard", "Category clicked: ${category.title}")
                onCardClick(category.title) // Trigger the onCardClick lambda
            },
        horizontalAlignment = Alignment.CenterHorizontally, // Align items horizontally in the center
        verticalArrangement = Arrangement.Center // Align items vertically in the center
    ) {
        // Display the category icon
        Image(
            painter = painterResource(id = category.iconRes),  // Load the category icon
            contentDescription = category.title,  // Description for accessibility
            contentScale = ContentScale.Fit,  // Fit the content within the image container
            modifier = Modifier.size(30.dp)  // Set the image size
        )

        // Spacer for spacing between the icon and the title
        Spacer(modifier = Modifier.height(10.dp))

        // Display the category title
        Text(
            text = category.title,  // Set the category title
            color = Color.White.copy(alpha = 0.8f),  // Text color with slight transparency
            fontSize = 14.sp,  // Font size for the title
            fontFamily = GilroyFontMedium,  // Font style for the title
            style = TextStyle(
                platformStyle = PlatformTextStyle(
                    includeFontPadding = false  // Remove font padding for a cleaner look
                )
            )
        )
    }
}

/**
 * Composable function that displays a row of recommended items. The row consists of a header
 * with a title and a "See All" button. Below the header, a horizontal scrollable row of recommendation cards is shown.
 *
 * @param recommendations List of recommended destinations to be displayed in the horizontal row.
 * @param onItemClick A lambda function that gets invoked when a recommendation item is clicked, passing the index.
 * @param onSeeAllClick A lambda function that gets invoked when the "See All" text is clicked.
 */
@Composable
fun RecommendedRow(
    recommendations: List<DestinationResponse.DestinationResponseItem>,
    onItemClick: (Int) -> Unit,
    onSeeAllClick: () -> Unit
) {
    // Log when the RecommendedRow is being displayed
    GzLogger.d("RecommendedRow", "Rendering row with ${recommendations.size} recommendations")

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 40.dp)
    ) {
        // Header Row displaying the title and See All button
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 30.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Title Text for the Recommended section
            Text(
                modifier = Modifier,
                color = Color.White.copy(alpha = 0.9f),
                fontSize = 18.sp,
                fontFamily = RubikFontMedium,
                text = "Recommended",
            )

            // See All Text with clickable functionality
            Text(
                modifier = Modifier
                    .clickable {
                        // Log the See All click event
                        GzLogger.d("RecommendedRow", "See All clicked")
                        onSeeAllClick() // Trigger the See All action
                    },
                fontSize = 12.sp,
                fontFamily = RubikFontMedium,
                text = buildAnnotatedString {
                    append("See All")
                    addStyle(SpanStyle(color = Color(0xFFF88264)), 0, length) // Styling the text color
                }
            )
        }

        // Spacer to add vertical spacing between the header and the horizontal row
        Spacer(modifier = Modifier.height(20.dp))

        // Horizontal Scrollable Row for displaying the recommendations
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            contentPadding = PaddingValues(horizontal = 30.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            // Log the start of item rendering
            GzLogger.d("RecommendedRow", "Rendering ${recommendations.size} recommendation cards")

            items(recommendations.size) { index ->
                // Log the index of the item being rendered
                GzLogger.d("RecommendedRow", "Rendering recommendation at index: $index")

                // Recommendation Card
                RecommendationCardHorizontal(
                    recommendation = recommendations[index],
                    onClick = {
                        // Log the item click event with the index
                        GzLogger.d("RecommendedRow", "Recommendation clicked at index: $index")
                        onItemClick(index) // Trigger the item click action
                    }
                )
            }
        }
    }
}

/**
 * Composable function that displays a recommendation card with an image and details (title, location).
 * The card is clickable and triggers a callback when clicked.
 *
 * @param recommendation A `DestinationResponse.DestinationResponseItem` containing the details of the recommendation (title, location, image).
 * @param onClick A lambda function that is invoked when the card is clicked.
 */
@Composable
fun RecommendationCardHorizontal(
    recommendation: DestinationResponse.DestinationResponseItem,
    onClick: () -> Unit
) {
    val context = LocalContext.current
    val screenWidth = LocalConfiguration.current.screenWidthDp.dp

    // Log the start of rendering the recommendation card
    GzLogger.d("RecommendationCardHorizontal", "Rendering recommendation card for ${recommendation.propertyName}")

    Box(
        modifier = Modifier
            .clickable {
                // Log the card click event
                GzLogger.d("RecommendationCardHorizontal", "Card clicked for ${recommendation.propertyName}")
                onClick()
            }
            .width(screenWidth.times(0.6f)) // Set width to 60% of screen width
            .height(screenWidth.times(0.62f)) // Set height to 62% of screen width
            .clip(RoundedCornerShape(20.dp)) // Apply rounded corners
            .background(Surface) // Background color for the card
    ) {

        // Create image request for loading the hero image
        val imageRequest = createImageRequest(context, recommendation.heroImage)

        // SubcomposeAsyncImage for loading and displaying the hero image with a circular progress bar
        SubcomposeAsyncImage(
            modifier = Modifier
                .fillMaxSize(),
            model = imageRequest,
            contentDescription = null, // No content description for the image
            contentScale = ContentScale.Crop, // Crop the image to fit the container
            loading = {
                Box(
                    modifier = Modifier
                        .fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressBar() // Show a progress bar while the image loads
                }
            }
        )
        Spacer(modifier = Modifier.height(8.dp))

        // Column containing the text details (title, location)
        Column(
            modifier = Modifier
                .padding(bottom = 20.dp) // Padding from bottom
                .padding(horizontal = 20.dp) // Padding from sides
                .align(Alignment.BottomStart), // Align text to the bottom left
        ) {
            // Property Name (Title)
            recommendation.propertyName?.let { propertyName ->
                Text(
                    fontFamily = RubikFontBold, // Bold font for the title
                    fontSize = 16.sp,
                    color = Color.White, // White color for text
                    text = propertyName, // Set the property name as text
                    maxLines = 1, // Ensure the text is on a single line
                    overflow = TextOverflow.Ellipsis, // Add ellipsis if the text overflows
                )
            }

            // Location
            recommendation.location?.let { location ->

                Row(
                    modifier = Modifier,
                    verticalAlignment = Alignment.CenterVertically // Vertically center the location text and icon
                ) {
                    // Location icon
                    Image(
                        painter = painterResource(R.drawable.ic_location_outlined),
                        contentDescription = "Location"
                    )

                    Spacer(
                        modifier = Modifier
                            .size(4.dp) // Add space between the icon and text
                    )

                    // Location Text (only showing the first part of the location before the comma)
                    Text(
                        modifier = Modifier,
                        color = Color.White,
                        fontFamily = RubikFontRegular, // Regular font for the location text
                        fontSize = 14.sp,
                        maxLines = 1, // Ensure the text is on a single line
                        overflow = TextOverflow.Ellipsis, // Add ellipsis if the text overflows
                        text = location.split(",")[0] // Display only the first part of the location (before the comma)
                    )
                }
            }
        }
    }
}

/**
 * Composable function that displays a vertical recommendation card with an image and details (title, location).
 * The card is clickable and triggers a callback when clicked.
 *
 * @param recommendation A `DestinationResponse.DestinationResponseItem` containing the details of the recommendation (title, location, image).
 * @param onClick A lambda function that is invoked when the card is clicked.
 */
@Composable
fun RecommendationCardVertical(
    recommendation: DestinationResponse.DestinationResponseItem,
    onClick: () -> Unit
) {
    val context = LocalContext.current
    val screenWidth = LocalConfiguration.current.screenWidthDp.dp

    // Log the start of rendering the recommendation card
    GzLogger.d("RecommendationCardVertical", "Rendering vertical recommendation card for ${recommendation.propertyName}")

    Box(
        modifier = Modifier
            .clickable {
                // Log the card click event
                GzLogger.d("RecommendationCardVertical", "Card clicked for ${recommendation.propertyName}")
                onClick()
            }
            .width(screenWidth.times(0.5f)) // Set width to 50% of screen width
            .height(screenWidth.times(0.5f)) // Set height to 50% of screen width
            .clip(RoundedCornerShape(20.dp)) // Apply rounded corners
            .background(Surface) // Background color for the card
    ) {

        // Create image request for loading the hero image
        val imageRequest = createImageRequest(context, recommendation.heroImage)

        // SubcomposeAsyncImage for loading and displaying the hero image with a circular progress bar
        SubcomposeAsyncImage(
            modifier = Modifier
                .fillMaxSize(),
            model = imageRequest,
            contentDescription = null, // No content description for the image
            contentScale = ContentScale.Crop, // Crop the image to fit the container
            loading = {
                Box(
                    modifier = Modifier
                        .fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressBar() // Show a progress bar while the image loads
                }
            }
        )
        Spacer(modifier = Modifier.height(8.dp))

        // Column containing the text details (title, location)
        Column(
            modifier = Modifier
                .padding(bottom = 16.dp) // Padding from bottom
                .padding(horizontal = 16.dp) // Padding from sides
                .align(Alignment.BottomStart), // Align text to the bottom left
        ) {
            // Property Name (Title)
            recommendation.propertyName?.let { propertyName ->
                Text(
                    fontFamily = RubikFontBold, // Bold font for the title
                    fontSize = 14.sp,
                    color = Color.White, // White color for text
                    text = propertyName, // Set the property name as text
                    maxLines = 1, // Ensure the text is on a single line
                    overflow = TextOverflow.Ellipsis, // Add ellipsis if the text overflows
                )
            }

            // Location
            recommendation.location?.let { location ->

                Row(
                    modifier = Modifier,
                    verticalAlignment = Alignment.CenterVertically // Vertically center the location text and icon
                ) {
                    // Location icon
                    Image(
                        painter = painterResource(R.drawable.ic_location_outlined),
                        contentDescription = "Location"
                    )

                    Spacer(
                        modifier = Modifier
                            .size(4.dp) // Add space between the icon and text
                    )

                    // Location Text (only showing the first part of the location before the comma)
                    Text(
                        modifier = Modifier,
                        color = Color.White,
                        fontFamily = RubikFontRegular, // Regular font for the location text
                        fontSize = 14.sp,
                        maxLines = 1, // Ensure the text is on a single line
                        overflow = TextOverflow.Ellipsis, // Add ellipsis if the text overflows
                        text = location.split(",")[0] // Display only the first part of the location (before the comma)
                    )
                }
            }
        }
    }
}


/**
 * Composable function that displays an auto-scrolling image slider with dot indicators for navigation.
 * The slider will automatically scroll every 3 seconds, and the user can manually navigate by clicking the dot indicators.
 *
 * @param imageUrls A list of image URLs to be displayed in the slider. The images are loaded asynchronously.
 * @param onBackPressed A lambda function that is triggered when the back button in the top bar is pressed.
 */
@Composable
fun AutoImageSlider(imageUrls: List<String?>?, onBackPressed: () -> Unit) {
    // Initialize pager state to manage the current page index
    val pagerState = rememberPagerState(
        pageCount = { imageUrls?.size ?: 0 }
    )
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()
    val screenHeight = LocalConfiguration.current.screenHeightDp.dp

    // Auto-scroll effect: every 3 seconds, move to the next image
    LaunchedEffect(pagerState.currentPage) {
        // Log the auto-scroll interval
        GzLogger.d("AutoImageSlider", "Auto-scrolling every 3 seconds")

        while (true) {
            delay(3000) // Delay of 3 seconds
            val nextPage = (pagerState.currentPage + 1) % (imageUrls?.size ?: 0)
            coroutineScope.launch {
                pagerState.animateScrollToPage(nextPage)
            }
        }
    }

    // Outer Box that contains the pager and other UI elements
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                color = Background, // Background color of the slider
                shape = RoundedCornerShape(
                    topStart = 0.dp,
                    topEnd = 0.dp,
                    bottomStart = 40.dp,
                    bottomEnd = 40.dp
                )
            )
    ) {

        // HorizontalPager to display the images
        HorizontalPager(
            state = pagerState,
            modifier = Modifier
                .clip(
                    shape = RoundedCornerShape(
                        topStart = 0.dp,
                        topEnd = 0.dp,
                        bottomStart = 40.dp,
                        bottomEnd = 40.dp
                    )
                )
                .fillMaxWidth()
                .height(screenHeight.times(0.5f)) // Adjust the height to 50% of the screen
                .background(Surface), // Background for the image area
            verticalAlignment = Alignment.Top
        ) { page ->

            // Log the current image being displayed
            GzLogger.d("AutoImageSlider", "Displaying image at position $page")

            // Image loading request using Coil's SubcomposeAsyncImage
            val imageRequest = createImageRequest(context, imageUrls?.get(page))

            SubcomposeAsyncImage(
                modifier = Modifier
                    .fillMaxSize(),
                model = imageRequest,
                contentDescription = null, // No content description for the image
                contentScale = ContentScale.Crop, // Crop the image to fill the area
                loading = {
                    Box(
                        modifier = Modifier
                            .fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressBar() // Show a progress bar while the image is loading
                    }
                }
            )
        }

        // Dot indicators to show which image is currently displayed
        Row(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 16.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            imageUrls?.forEachIndexed { index, _ ->
                DotIndicator(isSelected = index == pagerState.currentPage)
            }
        }

        // Top bar with a back button, which invokes the onBackPressed callback when clicked
        GlobalTopBar(
            modifier = Modifier
                .align(Alignment.TopCenter)
                .padding(top = 10.dp),
            onBackPressed = {
                GzLogger.d("AutoImageSlider", "Back button pressed")
                onBackPressed()
            }
        )
    }
}

/**
 * Composable function that displays a dot indicator, typically used to indicate the current page in a pager.
 * The dot's color and size are animated based on whether the dot is selected or not.
 *
 * @param isSelected Boolean flag indicating whether this dot is the currently selected dot.
 *                   If true, the dot will have a different color and size compared to non-selected dots.
 */
@Composable
fun DotIndicator(isSelected: Boolean) {
    // Determine the color of the dot based on whether it is selected
    val color = if (isSelected) Color(0xFFF88264) else Color.White
    val size = 10.dp // Define the default size of the dot

    // Animate the size of the dot when it changes state (selected or not)
    val animatedSize by animateDpAsState(
        targetValue = size,
        animationSpec = tween(durationMillis = 300), // Animate the size change over 300ms
        label = "DotIndicatorSizeAnimation"
    )

    // Log the color and size change for debugging purposes
    GzLogger.d("DotIndicator", "Dot selected: $isSelected, Color: $color, Size: $animatedSize")

    // Draw the dot using a Canvas to represent the indicator
    Canvas(
        modifier = Modifier
            .size(animatedSize) // Apply the animated size to the dot
            .padding(2.dp) // Add padding around the dot
    ) {
        // Draw the circle representing the dot with the selected color
        drawCircle(color = color)
    }
}

/**
 * Composable function that displays a top bar with an optional title and a back button.
 * The top bar is typically used for navigation purposes, with the back button triggering
 * the `onBackPressed` callback when clicked.
 *
 * @param modifier [Modifier] to apply additional styling or layout adjustments.
 * @param title String to be displayed as the title in the center of the top bar. If not provided, no title is displayed.
 * @param onBackPressed Lambda function that is called when the back button is pressed.
 */
@Composable
fun GlobalTopBar(
    modifier: Modifier = Modifier,
    title: String = "",
    onBackPressed: () -> Unit
) {
    // Log the state of the top bar title and back button action
    GzLogger.d("GlobalTopBar", "Title: $title")

    Box(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 30.dp, vertical = 25.dp) // Adds padding around the top bar
    ) {
        // Back Button
        IconButton(
            modifier = Modifier
                .background(
                    color = Color.White.copy(alpha = 0.5f), // Semi-transparent white background for the button
                    shape = RoundedCornerShape(10.dp) // Rounded corners for the button
                )
                .size(32.dp) // Set the button size
                .align(Alignment.CenterStart), // Position the button at the start (left) of the top bar
            onClick = {
                GzLogger.d("GlobalTopBar", "Back button clicked")
                onBackPressed() // Trigger the back button action when clicked
            }
        ) {
            Icon(
                modifier = Modifier
                    .size(18.dp), // Icon size
                imageVector = Icons.AutoMirrored.Filled.ArrowBack, // Back arrow icon
                contentDescription = "Back", // Accessibility description
                tint = Color.White // Set icon color to white
            )
        }

        // Title
        if (title.isNotEmpty()) {
            Text(
                modifier = Modifier
                    .align(Alignment.Center), // Center the title horizontally
                fontFamily = RubikFontMedium, // Use Rubik font family for the title
                fontSize = 18.sp, // Set font size
                color = Color.White.copy(alpha = 0.9f), // White color with slight transparency
                text = title, // Set the title text
            )
        }
    }
}

/**
 * Composable function to display a circular progress bar.
 * The progress bar is used to indicate loading states in the UI.
 * The appearance of the circular progress indicator can be customized via
 * the `modifier`, `width`, `loadingColor`, and `trackColor` parameters.
 *
 * @param modifier [Modifier] to apply additional styling or layout adjustments (default size is 36.dp).
 * @param width [Dp] to set the stroke width of the progress bar (default is 4.dp).
 * @param loadingColor [Color] to set the color of the loading indicator (default is white).
 * @param trackColor [Color] to set the color of the background track (default is `Surface` color).
 */
@Composable
fun CircularProgressBar(
    modifier: Modifier = Modifier
        .size(size = 36.dp), // Default size of the progress bar
    width: Dp = 4.dp, // Default stroke width of the progress bar
    loadingColor: Color = Color.White, // Default loading color (white)
    trackColor: Color = Surface // Default track color (Surface color)
) {
    // Log the circular progress bar state (size, width, loading color, and track color)
    GzLogger.d("CircularProgressBar", "Size: $modifier, Width: $width, LoadingColor: $loadingColor, TrackColor: $trackColor")

    CircularProgressIndicator(
        modifier = modifier, // Apply the modifier to control size and layout
        color = loadingColor, // Set the color for the loading indicator
        strokeWidth = width, // Set the stroke width for the progress bar
        trackColor = trackColor // Set the track color for the background circle
    )
}

/**
 * Composable function to display a customizable alert dialog.
 * The dialog can show a title, a message, and a positive button.
 * The dialog visibility is controlled via the [shouldShowDialog] MutableState.
 * The dialog will be dismissed when the positive button is clicked or when the user dismisses the dialog.
 *
 * @param shouldShowDialog [MutableState<Boolean>] that controls whether the dialog is shown or not.
 * @param title [String] the title of the alert dialog.
 * @param text [String] the content text displayed in the dialog.
 * @param positiveButtonTitle [String] the title of the positive button, typically used for actions like "OK" or "Confirm".
 */
@Composable
fun CustomAlertDialog(
    shouldShowDialog: MutableState<Boolean>, // Controls the visibility of the dialog
    title: String, // Title displayed in the dialog
    text: String, // Main content of the dialog
    positiveButtonTitle: String // Title for the positive button
) {
    // Log the dialog state (whether it is shown or not)
    GzLogger.d("CustomAlertDialog", "Dialog visibility: ${shouldShowDialog.value}, Title: $title, Text: $text, Button: $positiveButtonTitle")

    // Show the alert dialog only when shouldShowDialog is true
    if (shouldShowDialog.value) {
        AlertDialog(
            containerColor = Surface, // The background color of the dialog container
            onDismissRequest = {
                // Set the dialog visibility to false when it is dismissed (e.g., tapped outside or back pressed)
                shouldShowDialog.value = false
            },
            title = {
                Text(
                    text = title, // Title of the dialog
                    color = MaterialTheme.colorScheme.onPrimary // Title color based on theme
                )
            },
            text = {
                Text(
                    text = text, // Text content of the dialog
                    color = MaterialTheme.colorScheme.onPrimary // Text color based on theme
                )
            },
            confirmButton = {
                Button(
                    onClick = {
                        // Close the dialog when the button is clicked
                        shouldShowDialog.value = false
                    },
                    border = BorderStroke(
                        width = 1.dp, // Border width for the button
                        color = MaterialTheme.colorScheme.outline // Border color based on theme
                    )
                ) {
                    Text(
                        text = positiveButtonTitle, // Button text
                        color = MaterialTheme.colorScheme.onPrimary // Button text color based on theme
                    )
                }
            }
        )
    }
}



@Preview(showBackground = true)
@Composable
fun BottomNavScreensPreview() {
    GoZayaanTheme {
        MainScreen(HomeUiState())
    }
}
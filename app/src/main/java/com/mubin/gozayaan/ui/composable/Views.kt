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
import coil.request.CachePolicy
import coil.request.ImageRequest
import com.mubin.gozayaan.R
import com.mubin.gozayaan.base.theme.Background
import com.mubin.gozayaan.base.theme.GoZayaanTheme
import com.mubin.gozayaan.base.theme.Surface
import com.mubin.gozayaan.base.utils.GilroyFontMedium
import com.mubin.gozayaan.base.utils.RubikFontBold
import com.mubin.gozayaan.base.utils.RubikFontMedium
import com.mubin.gozayaan.base.utils.RubikFontRegular
import com.mubin.gozayaan.data.model.DestinationResponse
import com.mubin.gozayaan.ui.home.BookmarkScreen
import com.mubin.gozayaan.ui.home.DetailsScreen
import com.mubin.gozayaan.ui.home.HomeScreen
import com.mubin.gozayaan.ui.home.HomeUiState
import com.mubin.gozayaan.ui.home.MainScreen
import com.mubin.gozayaan.ui.home.NotificationsScreen
import com.mubin.gozayaan.ui.home.ProfileScreen
import com.mubin.gozayaan.ui.home.RecommendedScreen
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

sealed class BottomNavItem(val route: String, val label: String, val icon: Int) {
    data object Home : BottomNavItem("home", "Home", R.drawable.ic_bottom_nav_home)
    data object Bookmark : BottomNavItem("bookmark", "Bookmark", R.drawable.ic_bottom_nav_bookmark)
    data object Notifications : BottomNavItem("notifications", "Notifications", R.drawable.ic_bottom_nav_notification)
    data object Profile : BottomNavItem("profile", "Profile", R.drawable.ic_bottom_nav_profile)
}

@Composable
fun BottomNavGraph(navController: NavHostController, uiState: HomeUiState) {
    NavHost(
        navController = navController,
        startDestination = BottomNavItem.Home.route
    ) {
        composable(route = BottomNavItem.Home.route) {
            uiState.hideBottomNav = false
            HomeScreen(uiState, navController)
        }
        composable(route = BottomNavItem.Bookmark.route) {
            uiState.hideBottomNav = false
            BookmarkScreen(uiState)
        }
        composable(route = BottomNavItem.Notifications.route) {
            uiState.hideBottomNav = false
            NotificationsScreen(uiState)
        }
        composable(route = BottomNavItem.Profile.route) {
            uiState.hideBottomNav = false
            ProfileScreen(uiState)
        }
        composable(
            route = "recommended/{isSearch}",
            arguments = listOf(navArgument("isSearch") { type = NavType.BoolType })
        ) { backStackEntry ->
            uiState.hideBottomNav = true
            val isSearch = backStackEntry.arguments?.getBoolean("isSearch", false) ?: false
            RecommendedScreen(isSearch, uiState, navController)
        }
        composable(
            route = "details/{index}",
            arguments = listOf(navArgument("index") { type = NavType.IntType })
        ) { backStackEntry ->
            uiState.hideBottomNav = true
            val index = backStackEntry.arguments?.getInt("index")
            DetailsScreen(index?.let { uiState.response?.get(it) }, navController)
        }
    }
}

@Composable
fun FloatingNavigationBar(navController: NavController) {
    val items = listOf(
        BottomNavItem.Home,
        BottomNavItem.Bookmark,
        BottomNavItem.Notifications,
        BottomNavItem.Profile
    )

    NavigationBar(
        windowInsets = WindowInsets(0.dp, 0.dp, 0.dp, 0.dp),
        containerColor = Color.Transparent, // Transparent to avoid any background on the bar
        modifier = Modifier
            .padding(horizontal = 16.dp, vertical = 8.dp) // Adjust padding to remove extra space
            .fillMaxWidth()
            .height(70.dp)// Adjust height to reduce extra padding
            .clip(RoundedCornerShape(40))
            .background(color = Surface) // Background color for the navigation bar itself
    ) {
        val currentRoute = navController.currentBackStackEntryAsState().value?.destination?.route
        items.forEach { item ->
            val selected = currentRoute == item.route
            NavigationBarItem(
                icon = {
                    Icon(
                        painter = painterResource(item.icon),
                        contentDescription = item.label,
                        modifier = Modifier.size(24.dp), // Adjust icon size if needed
                        //tint = if (selected) Color(0xFFFF6D6D) else Color.White // Only color change
                    )
                },
                label = null,
                selected = selected,
                onClick = {
                    if (!selected) {
                        navController.navigate(item.route) {
                            popUpTo(navController.graph.startDestinationId) { saveState = true }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = Color(0xFFFF6D6D),
                    unselectedIconColor = Color.White,
                    indicatorColor = Color.Transparent // Remove background indicator color
                ),
                alwaysShowLabel = false // Hide labels to match the desired style
            )
        }
    }
}

@Composable
fun HomeTopBar(
    onOptionClick: () -> Unit,
    onLocationClick: () -> Unit,
    onProfileClick: () -> Unit,
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(Background),
        contentAlignment = Alignment.Center
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 30.dp, vertical = 25.dp),
            horizontalArrangement = Arrangement.Absolute.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {

            IconButton(
                modifier = Modifier
                    .size(32.dp),
                onClick = {
                    onOptionClick()
                }
            ) {
                Image(
                    modifier = Modifier
                        .size(24.dp),
                    painter = painterResource(R.drawable.ic_home_options),
                    contentDescription = "Options",
                )
            }

            Row(
                modifier = Modifier
                    .clickable {
                        onLocationClick()
                    }
                    .padding(horizontal = 8.dp),
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

                Text(
                    modifier = Modifier
                        .alpha(0.5F),
                    color = Color.White,
                    fontFamily = RubikFontRegular,
                    fontSize = 12.sp,
                    text = "New York, NY"
                )
            }

            IconButton(
                modifier = Modifier
                    .background(
                        color = Surface,
                        shape = RoundedCornerShape(10.dp)
                    )
                    .size(32.dp),
                onClick = {
                    onProfileClick()
                }
            ) {
                Image(
                    modifier = Modifier
                        .size(18.dp),
                    painter = painterResource(R.drawable.ic_home_profile),
                    contentDescription = "Profile"
                )
            }

        }
    }
}

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
            .fillMaxWidth()
            .height(56.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(Surface)
            .padding(horizontal = 16.dp, vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(R.drawable.ic_search),
            contentDescription = "Search",
            modifier = Modifier.size(24.dp)
        )
        Spacer(modifier = Modifier.width(10.dp))
        BasicTextField(
            modifier = Modifier.fillMaxWidth(),
            value = query,
            onValueChange = onQueryChange,
            singleLine = true,
            cursorBrush = SolidColor(Background),
            textStyle = MaterialTheme.typography.bodyMedium.copy(
                fontFamily = RubikFontRegular,
                fontSize = 14.sp,
                color = Color.White.copy(alpha = 0.5f)
            ),
            decorationBox = { innerTextField ->
                if (query.isEmpty()) {
                    Text(
                        text = placeholder,
                        style = MaterialTheme.typography.bodyMedium.copy(
                            fontFamily = RubikFontRegular,
                            fontSize = 14.sp,
                            color = Color.White.copy(alpha = 0.3f)
                        )
                    )
                }
                innerTextField()
            },
            keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done),
            keyboardActions = KeyboardActions(onDone = { onSearch() })
        )
    }
}

data class CategoryItem(val title: String, val iconRes: Int)

@Composable
fun CategoryGrid(onCardClick: (String) -> Unit) {
    val categories = listOf(
        CategoryItem("Flights", R.drawable.ic_category_flights),
        CategoryItem("Hotels", R.drawable.ic_category_hotels),
        CategoryItem("Visa", R.drawable.ic_category_visa),
        CategoryItem("Buses", R.drawable.ic_category_buses)
    )

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 40.dp)
            .padding(horizontal = 30.dp)
    ) {

        Text(
            modifier =Modifier,
            fontSize = 18.sp,
            fontFamily = RubikFontMedium,
            color = Color.White.copy(alpha = 0.9f),
            text = "Popular Categories"
        )

        LazyVerticalGrid(
            columns = GridCells.Fixed(4),
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 25.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(categories.size) { index ->
                CategoryCard(category = categories[index], onCardClick = onCardClick)
            }
        }


    }

}

@Composable
fun CategoryCard(category: CategoryItem, onCardClick: (String) -> Unit) {
    Column(
        modifier = Modifier
            .clip(RoundedCornerShape(16.dp))
            .background(Surface)
            .size(width = 70.dp, height = 80.dp) // Adjust size as needed
            .clickable { onCardClick(category.title) },
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            painter = painterResource(id = category.iconRes),
            contentDescription = category.title,
            contentScale = ContentScale.Fit,
            modifier = Modifier.size(30.dp)
        )
        Spacer(modifier = Modifier.height(10.dp))
        Text(
            text = category.title,
            color = Color.White.copy(alpha = 0.8f),
            fontSize = 14.sp,
            fontFamily = GilroyFontMedium ,
            style = TextStyle(
                platformStyle = PlatformTextStyle(
                    includeFontPadding = false
                )
        ))
    }
}

@Composable
fun RecommendedRow(
    recommendations: List<DestinationResponse.DestinationResponseItem>,
    onItemClick: (Int) -> Unit,
    onSeeAllClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 40.dp)
    ) {
        // Header Row
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 30.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                modifier = Modifier,
                color = Color.White.copy(alpha = 0.9f),
                fontSize = 18.sp,
                fontFamily = RubikFontMedium,
                text = "Recommended",
            )

            Text(
                modifier = Modifier
                    .clickable {
                        onSeeAllClick()
                    },
                fontSize = 12.sp,
                fontFamily = RubikFontMedium,
                text = buildAnnotatedString {
                    append("See All")
                    addStyle(SpanStyle(color = Color(0xFFF88264)), 0, length)
                }
            )
        }

        Spacer(modifier = Modifier.height(20.dp))

        // Horizontal Scrollable Row
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            contentPadding = PaddingValues(horizontal = 30.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            items(recommendations.size) { index ->
                RecommendationCardHorizontal(
                    recommendation = recommendations[index],
                    onClick = {
                        onItemClick(index)
                    }
                )
            }
        }
    }
}

@Composable
fun RecommendationCardHorizontal(
    recommendation: DestinationResponse.DestinationResponseItem,
    onClick: () -> Unit
) {
    val context = LocalContext.current
    val screenWidth = LocalConfiguration.current.screenWidthDp.dp
    Box(
        modifier = Modifier
            .clickable {
                onClick()
            }
            .width(screenWidth.times(0.6f))
            .height(screenWidth.times(0.62f))
            .clip(RoundedCornerShape(20.dp))
            .background(Surface),
    ) {
        // Image
        val listener = object : ImageRequest.Listener {}

        val imageRequest = ImageRequest.Builder(context)
            .data(data = recommendation.heroImage)
            .listener(listener = listener)
            .dispatcher(dispatcher = Dispatchers.IO)
            .memoryCacheKey(key = recommendation.heroImage)
            .diskCacheKey(key = recommendation.heroImage)
            .error(drawableResId = R.drawable.ic_no_image_available)
            .fallback(drawableResId = R.drawable.ic_no_image_available)
            .crossfade(enable = true)
            .diskCachePolicy(policy = CachePolicy.ENABLED)
            .memoryCachePolicy(policy = CachePolicy.ENABLED)
            .build()

        // Sub Compose AsyncImage from Coil with zoom functionality
        SubcomposeAsyncImage(
            modifier = Modifier
                .fillMaxSize(),
            model = imageRequest,
            contentDescription = null,
            contentScale = ContentScale.Crop,
            loading = {
                Box(
                    modifier = Modifier
                        .fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressBar()
                }
            }
        )
        Spacer(modifier = Modifier.height(8.dp))

        Column(
            modifier = Modifier
                .padding(bottom = 20.dp)
                .padding(horizontal = 20.dp)
                .align(Alignment.BottomStart),
        ) {
            // Title
            recommendation.propertyName?.let { propertyName ->
                Text(
                    fontFamily = RubikFontBold,
                    fontSize = 16.sp,
                    color = Color.White,
                    text = propertyName,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                )
            }

            // Location
            recommendation.location?.let { location ->

                Row(
                    modifier = Modifier,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        painter = painterResource(R.drawable.ic_location_outlined),
                        contentDescription = "Location"
                    )

                    Spacer(
                        modifier = Modifier
                            .size(4.dp)
                    )

                    Text(
                        modifier = Modifier,
                        color = Color.White,
                        fontFamily = RubikFontRegular,
                        fontSize = 14.sp,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        text = location.split(",")[0]
                    )
                }
            }
        }


    }
}

@Composable
fun RecommendationCardVertical(
    recommendation: DestinationResponse.DestinationResponseItem,
    onClick: () -> Unit
) {
    val context = LocalContext.current
    val screenWidth = LocalConfiguration.current.screenWidthDp.dp
    Box(
        modifier = Modifier
            .clickable {
                onClick()
            }
            .width(screenWidth.times(0.5f))
            .height(screenWidth.times(0.5f))
            .clip(RoundedCornerShape(20.dp))
            .background(Surface),
    ) {
        // Image
        val listener = object : ImageRequest.Listener {}

        val imageRequest = ImageRequest.Builder(context)
            .data(data = recommendation.heroImage)
            .listener(listener = listener)
            .dispatcher(dispatcher = Dispatchers.IO)
            .memoryCacheKey(key = recommendation.heroImage)
            .diskCacheKey(key = recommendation.heroImage)
            .error(drawableResId = R.drawable.ic_no_image_available)
            .fallback(drawableResId = R.drawable.ic_no_image_available)
            .crossfade(enable = true)
            .diskCachePolicy(policy = CachePolicy.ENABLED)
            .memoryCachePolicy(policy = CachePolicy.ENABLED)
            .build()

        SubcomposeAsyncImage(
            modifier = Modifier
                .fillMaxSize(),
            model = imageRequest,
            contentDescription = null,
            contentScale = ContentScale.Crop,
            loading = {
                Box(
                    modifier = Modifier
                        .fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressBar()
                }
            }
        )
        Spacer(modifier = Modifier.height(8.dp))

        Column(
            modifier = Modifier
                .padding(bottom = 16.dp)
                .padding(horizontal = 16.dp)
                .align(Alignment.BottomStart),
        ) {
            // Title
            recommendation.propertyName?.let { propertyName ->
                Text(
                    fontFamily = RubikFontBold,
                    fontSize = 14.sp,
                    color = Color.White,
                    text = propertyName,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                )
            }

            // Location
            recommendation.location?.let { location ->

                Row(
                    modifier = Modifier,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        painter = painterResource(R.drawable.ic_location_outlined),
                        contentDescription = "Location"
                    )

                    Spacer(
                        modifier = Modifier
                            .size(4.dp)
                    )

                    Text(
                        modifier = Modifier,
                        color = Color.White,
                        fontFamily = RubikFontRegular,
                        fontSize = 14.sp,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        text = location.split(",")[0]
                    )
                }
            }
        }


    }
}

@Composable
fun AutoImageSlider(imageUrls: List<String?>?, onBackPressed: () -> Unit) {
    val pagerState = rememberPagerState(
        pageCount = { imageUrls?.size ?: 0 }
    )
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()
    val screenHeight = LocalConfiguration.current.screenHeightDp.dp

    // Auto-scroll effect
    LaunchedEffect(pagerState.currentPage) {
        while (true) {
            delay(3000)
            val nextPage = (pagerState.currentPage + 1) % (imageUrls?.size ?: 0)
            coroutineScope.launch {
                pagerState.animateScrollToPage(nextPage)
            }
        }
    }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                color = Background,
                shape = RoundedCornerShape(
                    topStart = 0.dp,
                    topEnd = 0.dp,
                    bottomStart = 40.dp,
                    bottomEnd = 40.dp
                )
            )
    ) {

        // HorizontalPager for scrolling images
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
                .height(screenHeight.times(0.5f))
                .background(Surface),
            verticalAlignment = Alignment.Top
        ) { page ->

            // Image
            val listener = object : ImageRequest.Listener {}

            val imageRequest = ImageRequest.Builder(context)
                .data(data = imageUrls?.get(page))
                .listener(listener = listener)
                .dispatcher(dispatcher = Dispatchers.IO)
                .memoryCacheKey(key = imageUrls?.get(page))
                .diskCacheKey(key = imageUrls?.get(page))
                .error(drawableResId = R.drawable.ic_no_image_available_text)
                .fallback(drawableResId = R.drawable.ic_no_image_available_text)
                .crossfade(enable = true)
                .diskCachePolicy(policy = CachePolicy.ENABLED)
                .memoryCachePolicy(policy = CachePolicy.ENABLED)
                .build()

            SubcomposeAsyncImage(
                modifier = Modifier
                    .fillMaxSize(),
                model = imageRequest,
                contentDescription = null,
                contentScale = ContentScale.Crop,
                loading = {
                    Box(
                        modifier = Modifier
                            .fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressBar()
                    }
                }
            )

        }

        // Dot Indicators
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

        GlobalTopBar(
            modifier = Modifier
                .align(Alignment.TopCenter),
            onBackPressed = {
                onBackPressed()
            }
        )
    }
}

@Composable
fun DotIndicator(isSelected: Boolean) {
    val color = if (isSelected) Color(0xFFF88264) else Color.White
    val size = 10.dp
    val animatedSize by animateDpAsState(
        targetValue = size,
        animationSpec = tween(durationMillis = 300),
        label = ""
    )

    Canvas(
        modifier = Modifier
            .size(animatedSize)
            .padding(2.dp)
    ) {
        drawCircle(color = color)
    }
}

@Composable
fun GlobalTopBar(
    modifier: Modifier = Modifier,
    title: String = "",
    onBackPressed: () -> Unit
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 30.dp, vertical = 25.dp)
    ) {
        IconButton(
            modifier = Modifier
                .background(
                    color = Color.White.copy(alpha = 0.5f),
                    shape = RoundedCornerShape(10.dp)
                )
                .size(32.dp)
                .align(Alignment.CenterStart),
            onClick = { onBackPressed() }
        ) {
            Icon(
                modifier = Modifier
                    .size(18.dp),
                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                contentDescription = "Back",
                tint = Color.White
            )
        }

        if (title.isNotEmpty()) {
            Text(
                modifier = Modifier
                    .align(Alignment.Center),
                fontFamily = RubikFontMedium,
                fontSize = 18.sp,
                color = Color.White.copy(alpha = 0.9f),
                text = title,
            )
        }
    }
}

@Composable
fun CircularProgressBar(
    modifier: Modifier = Modifier
        .size(size = 36.dp),
    width: Dp = 4.dp,
    loadingColor: Color = Color.White,
    trackColor: Color = Surface
) {
    CircularProgressIndicator(
        modifier = modifier,
        color = loadingColor,
        strokeWidth = width,
        trackColor = trackColor
    )
}

@Composable
fun CustomAlertDialog(
    shouldShowDialog: MutableState<Boolean>,
    title: String,
    text: String,
    positiveButtonTitle: String
) {
    if (shouldShowDialog.value) {
        AlertDialog(
            containerColor = Surface,
            onDismissRequest = {
                shouldShowDialog.value = false
            },
            title = {
                Text(
                    text = title,
                    color = MaterialTheme.colorScheme.onPrimary
                )
            },
            text = {
                Text(
                    text = text,
                    color = MaterialTheme.colorScheme.onPrimary
                )
            },
            confirmButton = {
                Button(
                    onClick = {
                        shouldShowDialog.value = false
                    },
                    border = BorderStroke(
                        width = 1.dp,
                        color = MaterialTheme.colorScheme.outline
                    )
                ) {
                    Text(
                        text = positiveButtonTitle,
                        color = MaterialTheme.colorScheme.onPrimary
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
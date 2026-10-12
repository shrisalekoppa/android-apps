package com.example.ui

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Assessment
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Lightbulb
import androidx.compose.material.icons.filled.NotificationsActive
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.Storefront
import androidx.compose.material.icons.outlined.Assessment
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Lightbulb
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material.icons.outlined.Storefront
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.localization.AppLanguage
import com.example.data.localization.LocalizationStrings
import com.example.ui.components.LanguageSwitchButton
import com.example.ui.screens.AlertsScreen
import com.example.ui.screens.DashboardScreen
import com.example.ui.screens.GoogleSearchScreen
import com.example.ui.screens.MarketPricesScreen
import com.example.ui.screens.ProfileScreen
import com.example.ui.screens.ReportsExportScreen
import com.example.ui.screens.SellTimingAdvisorScreen
import com.example.ui.screens.TrendsAnalyticsScreen
import com.example.ui.viewmodel.MandiViewModel

enum class AppNavDestination(
    val route: String,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector
) {
    DASHBOARD("dashboard", Icons.Filled.Home, Icons.Outlined.Home),
    MANDIS("mandis", Icons.Filled.Storefront, Icons.Outlined.Storefront),
    SEARCH("search", Icons.Filled.Search, Icons.Outlined.Search),
    TRENDS("trends", Icons.Filled.Assessment, Icons.Outlined.Assessment),
    ADVISOR("advisor", Icons.Filled.Lightbulb, Icons.Outlined.Lightbulb),
    ALERTS("alerts", Icons.Filled.NotificationsActive, Icons.Filled.NotificationsActive),
    REPORTS("reports", Icons.Filled.Share, Icons.Filled.Share),
    PROFILE("profile", Icons.Filled.Person, Icons.Outlined.Person)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainAppScreen(
    viewModel: MandiViewModel,
    modifier: Modifier = Modifier
) {
    var currentDestination by remember { mutableStateOf(AppNavDestination.DASHBOARD) }
    val currentLanguage by viewModel.currentLanguage.collectAsState()
    val alerts by viewModel.allAlerts.collectAsState()
    val activeAlertCount = alerts.count { it.isActive }

    // Android back button handling
    BackHandler(enabled = currentDestination != AppNavDestination.DASHBOARD) {
        currentDestination = AppNavDestination.DASHBOARD
    }

    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = when (currentDestination) {
                            AppNavDestination.DASHBOARD -> "${LocalizationStrings.getAppName(currentLanguage)} 🌾"
                            AppNavDestination.MANDIS -> LocalizationStrings.navPrices(currentLanguage)
                            AppNavDestination.SEARCH -> "${LocalizationStrings.navSearch(currentLanguage)} 🔍"
                            AppNavDestination.TRENDS -> LocalizationStrings.navTrends(currentLanguage)
                            AppNavDestination.ADVISOR -> LocalizationStrings.navAdvisor(currentLanguage)
                            AppNavDestination.ALERTS -> LocalizationStrings.navAlerts(currentLanguage)
                            AppNavDestination.REPORTS -> LocalizationStrings.navReports(currentLanguage)
                            AppNavDestination.PROFILE -> LocalizationStrings.navProfile(currentLanguage)
                        },
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                },
                actions = {
                    // One-tap Language Switcher Button (English / Kannada / Hindi)
                    LanguageSwitchButton(
                        currentLanguage = currentLanguage,
                        onLanguageSelected = { viewModel.setLanguage(it) }
                    )

                    IconButton(
                        onClick = { currentDestination = AppNavDestination.ALERTS },
                        modifier = Modifier.testTag("top_bar_alerts_btn")
                    ) {
                        if (activeAlertCount > 0) {
                            BadgedBox(badge = { Badge { Text("$activeAlertCount") } }) {
                                Icon(
                                    imageVector = Icons.Default.NotificationsActive,
                                    contentDescription = "Alerts",
                                    tint = MaterialTheme.colorScheme.onSurface
                                )
                            }
                        } else {
                            Icon(
                                imageVector = Icons.Default.NotificationsActive,
                                contentDescription = "Alerts",
                                tint = MaterialTheme.colorScheme.onSurface
                            )
                        }
                    }

                    IconButton(
                        onClick = { currentDestination = AppNavDestination.PROFILE },
                        modifier = Modifier.testTag("top_bar_profile_btn")
                    ) {
                        Icon(
                            imageVector = Icons.Default.Person,
                            contentDescription = "Profile",
                            tint = MaterialTheme.colorScheme.onSurface
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surface
                )
            )
        },
        bottomBar = {
            NavigationBar(
                containerColor = MaterialTheme.colorScheme.surface,
                tonalElevation = 6.dp,
                modifier = Modifier.testTag("bottom_nav_bar")
            ) {
                val bottomItems = listOf(
                    AppNavDestination.DASHBOARD,
                    AppNavDestination.MANDIS,
                    AppNavDestination.SEARCH,
                    AppNavDestination.TRENDS,
                    AppNavDestination.ADVISOR
                )

                bottomItems.forEach { destination ->
                    val isSelected = currentDestination == destination
                    val labelText = when (destination) {
                        AppNavDestination.DASHBOARD -> LocalizationStrings.navHome(currentLanguage)
                        AppNavDestination.MANDIS -> LocalizationStrings.navPrices(currentLanguage)
                        AppNavDestination.SEARCH -> LocalizationStrings.navSearch(currentLanguage)
                        AppNavDestination.TRENDS -> LocalizationStrings.navTrends(currentLanguage)
                        AppNavDestination.ADVISOR -> LocalizationStrings.navAdvisor(currentLanguage)
                        else -> ""
                    }

                    NavigationBarItem(
                        selected = isSelected,
                        onClick = { currentDestination = destination },
                        icon = {
                            Icon(
                                imageVector = if (isSelected) destination.selectedIcon else destination.unselectedIcon,
                                contentDescription = labelText,
                                modifier = Modifier.size(22.dp)
                            )
                        },
                        label = { Text(labelText, fontSize = 11.sp, fontWeight = FontWeight.SemiBold) },
                        colors = NavigationBarItemDefaults.colors(
                            selectedIconColor = MaterialTheme.colorScheme.primary,
                            selectedTextColor = MaterialTheme.colorScheme.primary,
                            indicatorColor = MaterialTheme.colorScheme.primaryContainer
                        ),
                        modifier = Modifier.testTag("nav_item_${destination.route}")
                    )
                }
            }
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            when (currentDestination) {
                AppNavDestination.DASHBOARD -> {
                    DashboardScreen(
                        viewModel = viewModel,
                        onNavigateToMarkets = { currentDestination = AppNavDestination.MANDIS },
                        onNavigateToTrends = { currentDestination = AppNavDestination.TRENDS },
                        onNavigateToAdvisor = { currentDestination = AppNavDestination.ADVISOR },
                        onNavigateToAlerts = { currentDestination = AppNavDestination.ALERTS },
                        onNavigateToReports = { currentDestination = AppNavDestination.REPORTS },
                        onNavigateToSearch = { currentDestination = AppNavDestination.SEARCH },
                        onRecordSelected = {
                            viewModel.selectRecordForDetail(it)
                            currentDestination = AppNavDestination.MANDIS
                        }
                    )
                }
                AppNavDestination.MANDIS -> {
                    MarketPricesScreen(
                        viewModel = viewModel,
                        onNavigateToAlerts = { currentDestination = AppNavDestination.ALERTS },
                        onNavigateToReports = { currentDestination = AppNavDestination.REPORTS }
                    )
                }
                AppNavDestination.SEARCH -> {
                    GoogleSearchScreen(
                        viewModel = viewModel
                    )
                }
                AppNavDestination.TRENDS -> {
                    TrendsAnalyticsScreen(
                        viewModel = viewModel
                    )
                }
                AppNavDestination.ADVISOR -> {
                    SellTimingAdvisorScreen(
                        viewModel = viewModel
                    )
                }
                AppNavDestination.ALERTS -> {
                    AlertsScreen(
                        viewModel = viewModel
                    )
                }
                AppNavDestination.REPORTS -> {
                    ReportsExportScreen(
                        viewModel = viewModel
                    )
                }
                AppNavDestination.PROFILE -> {
                    ProfileScreen(
                        viewModel = viewModel
                    )
                }
            }
        }
    }
}


package com.example.weather_app

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteScaffold
import androidx.compose.material3.rememberSearchBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.clearAndSetSemantics
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewScreenSizes
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.weather_app.ui.theme.Weather_appTheme
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Weather_appTheme {
                Weather_appApp()
            }
        }
    }
}

@PreviewScreenSizes
@Composable
fun Weather_appApp() {
    var currentDestination by rememberSaveable { mutableStateOf(AppDestinations.CURRENT) }
    val textFieldState = rememberTextFieldState()

    NavigationSuiteScaffold(
        navigationSuiteItems = {
            AppDestinations.entries.forEach {
                item(
                    icon = {
                        Icon(
                            modifier = Modifier.size(24.dp),
                            painter = painterResource(it.icon),
                            contentDescription = it.label
                        )
                    },
                    label = { Text(it.label) },
                    selected = it == currentDestination,
                    onClick = { currentDestination = it }
                )
            }
        }
    ) {
        Scaffold(modifier = Modifier.fillMaxSize(),
            topBar = { _SearchBar(modifier = Modifier, textFieldState = textFieldState) }
        )
        { innerPadding ->
            val cityName = textFieldState.text.toString()

            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
                ) {
                when (currentDestination) {
                    AppDestinations.CURRENT -> CurrentScreen(modifier = Modifier.padding(innerPadding),
                                                            cityName = cityName)
                    AppDestinations.TODAY -> TodayScreen(modifier = Modifier.padding(innerPadding),
                                                            cityName = cityName)
                    AppDestinations.WEEKLY -> WeeklyScreen(modifier = Modifier.padding(innerPadding),
                                                            cityName = cityName)
                }
            }
            Log.d("SEARCH LOCATION: ", textFieldState.text.toString())
        }
    }
}

enum class AppDestinations(
    val label: String,
    val icon: Int,
) {
    CURRENT("Current", R.drawable.location_icon),
    TODAY("Today", R.drawable.today),
    WEEKLY("Weekly", R.drawable.week),
}




@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun _SearchBar(textFieldState: TextFieldState, modifier: Modifier)
{
    val searchBarState = rememberSearchBarState()

    val scope = rememberCoroutineScope()
    val inputField =
        @Composable {
            SearchBarDefaults.InputField(
                modifier = Modifier,
                textFieldState = textFieldState,
                searchBarState = searchBarState,
                onSearch = { scope.launch { searchBarState.animateToCollapsed() } },
                placeholder = {
                    Text(modifier = Modifier.clearAndSetSemantics {}, text = "Search Location")
                },
                trailingIcon = {
                    Icon(
                        modifier = Modifier.size(24.dp),
                        painter = painterResource(R.drawable.location_icon),
                        contentDescription = "Location"
                    )
                }
            )
        }
    SearchBar(
        modifier = Modifier.statusBarsPadding()
                .fillMaxWidth()
                .padding(horizontal = 10.dp),
        state = searchBarState,
        inputField = inputField,

    )
}

@Composable
fun CurrentScreen(modifier: Modifier, cityName: String)
{
    Text("Current Screen \n $cityName", modifier = modifier, textAlign = TextAlign.Center, fontSize = 20.sp)
}

@Composable
fun TodayScreen(modifier: Modifier, cityName: String)
{
    Text("Today screen \n $cityName", modifier = modifier, textAlign = TextAlign.Center, fontSize = 20.sp)
}

@Composable
fun WeeklyScreen(modifier: Modifier, cityName: String)
{
    Text("Weekly Screen \n $cityName", modifier = modifier, textAlign = TextAlign.Center, fontSize = 20.sp)
}



@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    Weather_appTheme {

    }
}
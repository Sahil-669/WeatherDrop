package com.example.weatherdrop.ui.screens


import androidx.activity.compose.BackHandler
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.weatherdrop.WeatherVm
import com.example.weatherdrop.ui.theme.ManRope


@Composable
fun SearchScreen(viewModel: WeatherVm, navController: NavController) {
    var city by remember { mutableStateOf("") }
    val suggestion by viewModel.citySuggestions

    val focusRequester = remember { FocusRequester() }
    val focusManager = LocalFocusManager.current
    val keyboardController = LocalSoftwareKeyboardController.current
    val isFocused = remember { mutableStateOf(false) }

    val interactionSource = remember { MutableInteractionSource() }

    val offsetY by animateDpAsState(
        targetValue = if (isFocused.value) 200.dp else 0.dp,
        animationSpec = tween(durationMillis = 800)
    )

    BackHandler(enabled = isFocused.value) {
        focusManager.clearFocus()
    }
    LaunchedEffect(Unit) {
        city = ""
        viewModel.clearSuggestions()
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.linearGradient(
                    colors = listOf(
                        Color(0xFF40E0D0),
                        Color(0xFFE3E3E3)
                    )
                )
            )
            .clickable(
                interactionSource = interactionSource,
                indication = null
            ) {
                focusManager.clearFocus()
            }
            .padding(16.dp)


    ) {
        Row(
            modifier = Modifier
                .align(Alignment.TopCenter)
                .padding(top = 45.dp)
        ) {
            Text(
                text = "WeatherDrop",
                fontFamily = ManRope,
                fontWeight = FontWeight.Bold,
                fontSize = 35.sp
            )
        }

        Box(
            modifier = Modifier
                .padding(top = 100.dp)
                .fillMaxSize()
        ) {

        Column(
            modifier = Modifier
                .align(Alignment.Center)
        ) {

            OutlinedTextField(
                value = city,
                onValueChange = {
                    city = it
                    viewModel.getCitySuggestions(it)
                },
                label = { Text("Search a Location") },
                textStyle = TextStyle(
                    fontFamily = ManRope,
                    fontSize = 16.sp
                ),
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                keyboardActions = KeyboardActions(onDone = {
                    keyboardController?.hide()
                    focusManager.clearFocus()
                }),
                modifier = Modifier
                    .focusRequester(focusRequester)
                    .onFocusChanged {
                        isFocused.value = it.isFocused
                    }
                    .align(Alignment.CenterHorizontally)
                    .offset(y = -offsetY)
            )

            Box(
                modifier = Modifier
                    .height(200.dp)
                    .offset(y = -offsetY)
                    .align(Alignment.CenterHorizontally)
            ) {
                if (suggestion.isNotEmpty()) {
                    LazyColumn(
                        modifier = Modifier
                            .widthIn(max = 280.dp)
                            .heightIn(max = 200.dp)
                            .clip(
                                shape = RoundedCornerShape(
                                    bottomStart = 30.dp,
                                    bottomEnd = 30.dp
                                )
                            )
                            .background(color = Color.White)

                    )
                    {
                        items(suggestion.size) { index ->

                            val item = suggestion[index]
                            Text(
                                text = listOf(item.name.trim(), item.region.trim())
                                    .filter { it.isNotBlank() }
                                    .joinToString(", "),
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clickable {
                                        city = item.name
                                        viewModel.getData(city)
                                        focusManager.clearFocus()
                                        navController.navigate("weather")
                                    }
                                    .padding(12.dp),
                                style = TextStyle(
                                    fontFamily = ManRope,
                                    fontSize = 16.sp,
                                    color = Color.Black
                                )
                            )
                            if (index != suggestion.lastIndex)
                                HorizontalDivider(
                                    color = Color.Black,
                                    thickness = 1.dp
                                )
                        }
                    }
                }
            }

        }
    }

        Box(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 32.dp)
                .size(72.dp)
                .clip(CircleShape)
                .background(Color(0xFFBDBDF6))
                .clickable {
                    if (city.isNotBlank()) {
                        viewModel.getData(city)
                        navController.navigate("weather")
                    }
                },
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = "Search",
                modifier = Modifier.size(30.dp)
            )

        }
    }
}
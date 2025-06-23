package com.example.weatherdrop.ui.screens

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil3.compose.AsyncImage
import com.example.weatherdrop.R
import com.example.weatherdrop.WeatherVm
import com.example.weatherdrop.api.NetworkResponse
import com.example.weatherdrop.api.WeatherModel
import com.example.weatherdrop.ui.theme.ManRope


@Composable
fun WeatherDisplay(viewModel: WeatherVm, navController: NavController){

    val weatherResult = viewModel.weatherData.observeAsState()
    val context = LocalContext.current
    when(val result = weatherResult.value){
        is NetworkResponse.Error -> {
                Toast.makeText(context, result.message, Toast.LENGTH_LONG).show()
                navController.navigate("search")
        }
        is NetworkResponse.Loading -> {
            Box(
                modifier = Modifier
                    .fillMaxSize(),
                    contentAlignment = Alignment.Center
            ){
            CircularProgressIndicator()
            }
        }
        is NetworkResponse.Success -> {
            WeatherDetails(result.data)
        }
        null -> {}
    }


}
@Composable
fun WeatherDetails(data : WeatherModel){
    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        BackgroundImage(data)

        Column (modifier = Modifier.wrapContentHeight()) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .padding(top = 32.dp, start = 16.dp)
            ) {
                Column(
                    modifier = Modifier
                        .padding(top = 35.dp)
                ) {
                    Row {
                        Icon(
                            imageVector = Icons.Default.LocationOn,
                            contentDescription = "Location",
                            modifier = Modifier
                                .align(Alignment.CenterVertically)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = data.location.name,
                            fontFamily = ManRope,
                            fontWeight = FontWeight.ExtraBold,
                            fontSize = 30.sp
                        )
                    }
                    Text(
                        text = data.current.temp_c + "°C",
                        fontFamily = ManRope,
                        fontWeight = FontWeight.Bold,
                        fontSize = 60.sp
                    )
                    Text(
                        text = "Feels like ${ data.current.feelslike_c } °",
                        fontFamily = ManRope,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 20.sp
                    )
                    Spacer(modifier = Modifier.height(5.dp))
                    Row {
                        Text(
                            text = "High ${data.forecast.forecastday[0].day.maxtemp_c} °",
                            fontFamily = ManRope,
                            fontWeight = FontWeight.Normal,
                            fontSize = 15.sp
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "Low ${data.forecast.forecastday[0].day.mintemp_c} °",
                            fontFamily = ManRope,
                            fontWeight = FontWeight.Normal,
                            fontSize = 15.sp
                        )

                    }
                }
            }
            Spacer(modifier = Modifier.height(10.dp))
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
            ) {
                Card (
                    shape = RoundedCornerShape(35.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = Color.DarkGray.copy(alpha = 0.5f)
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight()
                ) {
                    Column (
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                    ) {
                        Text(
                            text = "Hourly Forecast",
                            fontFamily = ManRope,
                            fontWeight = FontWeight.Bold,
                            color = Color.White,
                            fontSize = 20.sp,
                            modifier = Modifier.padding(8.dp)
                        )

                        LazyRow (
                            contentPadding = PaddingValues(horizontal = 12.dp),
                            horizontalArrangement = Arrangement.spacedBy(16.dp)
                        ) {
                            items(data.forecast.forecastday[0].hour.take(12)) { hour ->
                                Column (horizontalAlignment = Alignment.CenterHorizontally) {
                                    Text(
                                        text = hour.time.takeLast(5),
                                        color = Color.White,
                                        fontFamily = ManRope,
                                        fontSize = 14.sp
                                    )
                                    Spacer(modifier = Modifier.height(8.dp))
                                    AsyncImage(
                                        model = "https:${hour.condition.icon}",
                                        contentDescription = null,
                                        modifier = Modifier.size(40.dp)
                                    )
                                    Spacer(modifier = Modifier.height(4.dp))
                                    Text(
                                        text = "${hour.temp_c}°",
                                        fontSize = 16.sp,
                                        color = Color.White
                                    )
                                }

                            }
                        }
                    }
                }
            }
        }
            Box(
                modifier = Modifier
                    .size(400.dp, 300.dp)
                    .align(Alignment.BottomCenter)

            ) {
                Card(
                    shape = RoundedCornerShape(topStart = 35.dp, topEnd = 35.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = Color.DarkGray.copy(alpha = 0.5f)
                    ),
                    modifier = Modifier

                        .size(400.dp)

                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                    ) {
                        WeatherMetric(
                            leftLabel = "Humidity",
                            leftValue = "${data.current.humidity} %",
                            rightLabel = "Wind Speed",
                            rightValue = "${data.current.wind_mph} mph",
                            leftWeight = 1f,
                            rightWeight = 1.1f,
                            horizontalGap = 100.dp
                        )
                        HorizontalDivider(
                            color = Color.White,
                            thickness = 1.dp,
                            modifier = Modifier.padding(vertical = 10.dp)
                        )
                        WeatherMetric(
                            leftLabel = "Precipitation",
                            leftValue = "${data.current.precip_in} in",
                            rightLabel = "UV Index",
                            rightValue = data.current.uv,
                            leftWeight = 1.2f,
                            rightWeight = 1f,
                            horizontalGap = 70.dp
                        )
                        HorizontalDivider(
                            color = Color.White,
                            thickness = 1.dp,
                            modifier = Modifier.padding(vertical = 10.dp)
                        )
                        WeatherMetric(
                            leftLabel = "Pressure",
                            leftValue = "${data.current.pressure_in} in",
                            rightLabel = "Visibility",
                            rightValue = "${data.current.vis_miles} mi",
                            leftWeight = 1f,
                            rightWeight = 1f,
                            horizontalGap = 100.dp
                        )
                    }
                }
            }
    }
}

@Composable
fun WeatherMetric(leftLabel: String,
                  leftValue: String,
                  rightLabel: String,
                  rightValue: String,
                  leftWeight: Float,
                  rightWeight: Float,
                  horizontalGap : Dp
)
    {
    Row (
        modifier = Modifier
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(
            modifier = Modifier
                .weight(leftWeight),
            horizontalAlignment = Alignment.Start
        ) {
            Text(
                text = leftLabel,
                color = Color.White,
                fontFamily = ManRope,
                fontSize = 24.sp
            )
            Spacer(modifier = Modifier.height(1.dp))
            Text(
                text = leftValue,
                color = Color.White,
                fontFamily = ManRope,
                fontSize = 20.sp
            )
        }
        Spacer(modifier = Modifier.width(horizontalGap))
        Column(
            modifier = Modifier
                .weight(rightWeight),
            horizontalAlignment = Alignment.Start
        ) {
            Text(
                text = rightLabel,
                color = Color.White,
                fontFamily = ManRope,
                fontSize = 24.sp
            )
            Spacer(modifier = Modifier.height(1.dp))
            Text(
                text = rightValue,
                color = Color.White,
                fontFamily = ManRope,
                fontSize = 20.sp
            )
        }
    }
}
@Composable
fun BackgroundImage(data: WeatherModel){

    val condition = data.current.condition.text.lowercase()
    when {

        condition.contains("sunny", ignoreCase = true) -> {
                Image(
                    painter = painterResource(R.drawable.sunny_bg),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxSize()
                        .alpha(0.5f)
                )
        }

            condition.contains("cloud", ignoreCase = true) -> {
                Image(
                    painter = painterResource(R.drawable.cloudy_bg),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxSize()
                        .alpha(0.5f)
                )
            }

            condition.contains("clear", ignoreCase = true) -> {
                Image(
                    painter = painterResource(R.drawable.clear_bg),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxSize()
                        .alpha(0.5f)
                )
            }

            condition.contains("mist", ignoreCase = true) -> {
                Image(
                    painter = painterResource(R.drawable.misty_bg),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxSize()
                        .alpha(0.5f)
                )
            }

            condition.contains("rain", ignoreCase = true) -> {
                Image(
                    painter = painterResource(R.drawable.rainy_bg),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxSize()
                        .alpha(0.5f)
                )
            }

            condition.contains("snow", ignoreCase = true) -> {
                Image(
                    painter = painterResource(R.drawable.snowy_bg),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxSize()
                        .alpha(0.5f)
                )
            }

            condition.contains("drizzle", ignoreCase = true) -> {
                Image(
                    painter = painterResource(R.drawable.rainy_bg),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxSize()
                        .alpha(0.5f)
                )
            }

            condition.contains("fog", ignoreCase = true) -> {
                Image(
                    painter = painterResource(R.drawable.misty_bg),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxSize()
                        .alpha(0.5f)
                )
            }
            condition.contains("storm", ignoreCase = true) -> {
                Image(
                    painter = painterResource(R.drawable.stormy_bg),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxSize()
                        .alpha(0.5f)
                )
            }
        condition.contains("thunder", ignoreCase = true) -> {
            Image(
                painter = painterResource(R.drawable.stormy_bg),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxSize()
                    .alpha(0.5f)
            )
        }
    }
}

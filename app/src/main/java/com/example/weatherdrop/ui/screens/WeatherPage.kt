package com.example.weatherdrop.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.weatherdrop.R
import com.example.weatherdrop.WeatherVm
import com.example.weatherdrop.api.NetworkResponse
import com.example.weatherdrop.api.WeatherModel
import com.example.weatherdrop.ui.theme.ManRope


@Composable
fun WeatherDisplay(viewModel: WeatherVm){

    val weatherResult = viewModel.weatherData.observeAsState()

    when(val result = weatherResult.value){
        is NetworkResponse.Error -> {
            Text(text = result.message)
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
    ){
        Image(
            painter = painterResource(R.drawable.sunny_bg),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxSize()
                .alpha(0.5f)
        )
        Box(
            modifier = Modifier
                .size(300.dp)
                .padding(top = 32.dp, start = 10.dp)


        ){
            Column (
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
            }
        }
        Box (
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .size(400.dp, 300.dp)
        ) {
            Card (
                shape = RoundedCornerShape(topStart = 35.dp, topEnd = 35.dp),
                colors = CardDefaults.cardColors(
                    containerColor = Color.DarkGray.copy(alpha = 0.5f)
                ),
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .size(400.dp)

            ) {
                Column (
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(20.dp)
                )
                {
                    Row (
                        modifier = Modifier
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    )
                    {
                        WeatherMetric("Humidity", "${data.current.humidity} %")
                        WeatherMetric("Wind", "${data.current.wind_mph} mph")
                    }
                    Row (
                        modifier = Modifier
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    )
                    {
                       WeatherMetric("Precipitation", "${data.current.precip_in} in")
                       WeatherMetric("UV Index", data.current.uv)
                    }
                    Row (
                        modifier = Modifier
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    )
                    {
                        WeatherMetric("Pressure", data.current.pressure_in + " in")
                        WeatherMetric("Visibility", "${data.current.vis_miles} mi")
                    }
                }
            }
        }
    }
}

@Composable
fun WeatherMetric(label: String, value: String){
    Column {
        Text(text = label,
            color = Color.White,
            fontFamily = ManRope ,
            fontSize = 24.sp
        )
        Text(text = value,
            color = Color.White,
            fontFamily = ManRope,
            fontSize = 20.sp
        )
    }
}

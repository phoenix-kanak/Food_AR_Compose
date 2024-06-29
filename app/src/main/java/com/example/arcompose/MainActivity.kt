package com.example.arcompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.*
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.arcompose.models.Food
import com.example.arcompose.ui.theme.ARComposeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ARComposeTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Greeting(
                        name = "Android",
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    ARComposeTheme {
        Greeting("Android")
    }
}

@Composable
fun Menu(modifier: Modifier){
    var index by remember {
        mutableStateOf(0)
    }
    val itemList= listOf(
        Food("burger",R.drawable.burger),
        Food("instant",R.drawable.instant),
        Food("momos",R.drawable.momos),
        Food("pizza",R.drawable.pizza),
        Food("ramen",R.drawable.ramen)
    )
    Row(modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceAround){
        IconButton(onClick = { /*TODO*/ }) {
            Icon(painter = painterResource(id = R.drawable.baseline_arrow_back_ios_24), contentDescription ="Back Button" )
            CircularImage(modifier = modifier, image = itemList[index].image)
            Icon(painter = painterResource(id = R.drawable.baseline_arrow_forward_ios_24), contentDescription ="Forward Button" )

        }
    }
}

@Composable
fun CircularImage(
    modifier: Modifier,
    image:Int
){
    Box(modifier = modifier
        .size(120.dp)
        .clip(CircleShape)
        .border(width = 4.dp, color = Color.Transparent)){
        Image(painter = painterResource(id = image), contentDescription ="Food Image", contentScale = ContentScale.FillBounds)
    }
}
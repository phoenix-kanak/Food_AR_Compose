package com.example.arcompose

import android.graphics.Bitmap
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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import io.github.sceneview.ar.ARScene
import io.github.sceneview.ar.node.ArModelNode
import io.github.sceneview.ar.node.ArNode
import io.github.sceneview.ar.node.PlacementMode
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
import com.google.android.filament.Scene
import com.google.ar.core.Config
import io.github.sceneview.Scene

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ARComposeTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Box(modifier = Modifier.fillMaxSize()) {
                        Menu(modifier = Modifier.align(Alignment.BottomCenter))
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    ARComposeTheme {

    }
}

@Composable
fun ARScreen() {
    val nodes = remember {
        mutableListOf<ArNode>()
    }
    val modelNodes= remember {
        mutableStateOf<ArModelNode?>(null)
    }
    var placeModelButton= remember {
        mutableStateOf(false)
    }
    ARScene(
        modifier = Modifier.fillMaxSize(),
        nodes = nodes,
        planeRenderer = true,
        onCreate = {
            it.planeRenderer.isShadowReceiver = false
            it.lightEstimationMode= Config.LightEstimationMode.DISABLED
            modelNodes.value=ArModelNode(it.engine,PlacementMode.INSTANT).apply {
                loadModelGlbAsync(
                    glbFileLocation = "",
//                    scaleToUnits = 0.01f,
//                    centerOrigin = true,
//                    autoAnimate = true
                ){

                }
                onAnchorChanged={
                    placeModelButton.value=!isAnchored
                }
                onHitResult={node , hitResult ->
                    placeModelButton.value=node.isTracking
                }
                nodes.add(modelNodes.value!!)
            }
        }
    )
    if(placeModelButton.value) {
        Button(onClick = {
            modelNodes.value!!.anchor()
        }) {
            Text(text = "Place Here")
        }
    }

}

@Composable
fun Menu(modifier: Modifier) {
    var index by remember {
        mutableStateOf(0)
    }
    val itemList = listOf(
        Food("burger", R.drawable.burger),
        Food("instant", R.drawable.instant),
        Food("momos", R.drawable.momos),
        Food("pizza", R.drawable.pizza),
        Food("ramen", R.drawable.ramen)
    )

    fun updateIndex(offset: Int) {
        index = (index + itemList.size + offset) % itemList.size
    }
    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceAround
    ) {
        IconButton(onClick = {
            updateIndex(-1)
        }) {
            Icon(
                painter = painterResource(id = R.drawable.baseline_arrow_back_ios_24),
                contentDescription = "Back Button"
            )
        }
        CircularImage(modifier = modifier, image = itemList[index].image)
        IconButton(onClick = {
            updateIndex(1)
        }) {
            Icon(
                painter = painterResource(id = R.drawable.baseline_arrow_forward_ios_24),
                contentDescription = "Forward Button"
            )
        }
    }
}

@Composable
fun CircularImage(
    modifier: Modifier,
    image: Int
) {
    Box(
        modifier = modifier
            .size(120.dp)
            .clip(CircleShape)
            .border(width = 4.dp, color = Color.Transparent)
    ) {
        Image(
            painter = painterResource(id = image),
            contentDescription = "Food Image",
            contentScale = ContentScale.FillBounds
        )
    }
}
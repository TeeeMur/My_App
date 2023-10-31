package com.example.myapp

import android.content.res.Configuration
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

const val STR_NAME_SAVE_COUNT = "objCount"
const val INT_COUNT_OF_COLUMNS_LANDSCAPE = 4
const val INT_COUNT_OF_COLUMNS_PORTRAIT = 3
const val FLOAT_SIZE_OF_COLUMNS_LANDSCAPE = 0.8f
const val FLOAT_SIZE_OF_COLUMNS_PORTRAIT = 0.92f
class MainActivity : ComponentActivity() {

	private var rectCount = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
	        val context = LocalContext.current
	        val startMyActivity = fun (numOfCard: Int, numOfColor: Int) {
		        startActivity(MainActivity2.newInstance(context, numOfCard, numOfColor))
	        }
	        if (savedInstanceState != null) {
		        rectCount = savedInstanceState.getInt(STR_NAME_SAVE_COUNT)
	        }
	        val countOfRectangles = remember{mutableStateOf(rectCount)}
	        Column(
		        modifier = Modifier
			        .fillMaxSize()
			        .background(Color.Black)
			        .padding(10.dp),
		        horizontalAlignment = Alignment.CenterHorizontally,
		        verticalArrangement = Arrangement.SpaceBetween
	        ) {
		        MyLazyVerticalGrid(countOfRectangles, startMyActivity)
		        Button(
			        onClick = {
				        rectCount++
				        countOfRectangles.value = rectCount
			        },
			        colors = ButtonDefaults.buttonColors(containerColor = Color.White),
			        modifier = Modifier
				        .fillMaxWidth()
		        ) {
			        Text(
				        text = stringResource(R.string.button_name),
				        fontSize = 24.sp,
				        color = Color.Black,
			        )
		        }

	        }
        }
    }

	override fun onSaveInstanceState(outState: Bundle) {
		outState.putInt(STR_NAME_SAVE_COUNT, rectCount)
		super.onSaveInstanceState(outState)
	}
}


@Composable
fun Rectangle(num: Int, func: (Int, Int) -> Unit) {
	val intentColor = num % 2
	Card(modifier = Modifier
		.requiredSize(100.dp)
		.clickable(onClick = {
			func(num, intentColor)
		}),
		colors = CardDefaults.cardColors(
			containerColor = (if (intentColor == 0) Color.Red else Color.Blue)
		),
	){
		Box(
			modifier = Modifier.fillMaxSize(),
			contentAlignment = Alignment.Center
		) {
			Text(
				text = num.toString(),
				fontSize = 32.sp,
				color = Color.White,
				fontWeight = FontWeight.Bold
			)
		}
	}
}

@Composable
fun MyLazyVerticalGrid(countOfBoxes: MutableState<Int>, func: (Int, Int) -> Unit) {
	val screenConfig = LocalConfiguration.current
	val countOfColumns =
		(if (screenConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) INT_COUNT_OF_COLUMNS_LANDSCAPE else INT_COUNT_OF_COLUMNS_PORTRAIT)
	val gridMaxHeight = (if (screenConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) FLOAT_SIZE_OF_COLUMNS_LANDSCAPE else FLOAT_SIZE_OF_COLUMNS_PORTRAIT)
	LazyVerticalGrid(
		columns = GridCells.Fixed(countOfColumns),
		verticalArrangement = Arrangement.spacedBy(10.dp),
		horizontalArrangement = Arrangement.spacedBy(10.dp),
		modifier = Modifier
			.fillMaxHeight(gridMaxHeight)
	){
		items(countOfBoxes.value) {numOfBox ->
			Rectangle(num = numOfBox, func)
		}
	}
}

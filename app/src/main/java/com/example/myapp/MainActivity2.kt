package com.example.myapp

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

const val COLOR = "IntentColor"
const val NUM = "IntentNum"
class MainActivity2 : ComponentActivity() {
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContent {
			Box(
				modifier = Modifier
					.fillMaxSize()
					.background((if (intent.getIntExtra(COLOR, 0) == 0) Color.Red else Color.Blue)),
				contentAlignment = Alignment.Center
			) {
				Text(
					text = intent.getIntExtra(NUM, 0).toString(),
					fontSize = 24.sp,
					color = Color.White,
					fontWeight = FontWeight.Bold
				)
			}
		}
	}

	companion object {
		fun newInstance(context: Context, num: Int, color: Int): Intent {
			return Intent(context, MainActivity2::class.java).apply {
				putExtra(NUM, num)
				putExtra(COLOR, color)
			}
		}
	}
}

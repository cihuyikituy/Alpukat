package com.example.alpukat

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.alpukat.ui.theme.AlpukatTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AlpukatTheme {
                AlpukatApp()
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AlpukatApp() {
    var currentStep by remember { mutableIntStateOf(1) }
    var blendCount by remember { mutableIntStateOf(0) }
    var blendTaps by remember { mutableIntStateOf(0) }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = stringResource(R.string.app_name),
                        fontWeight = FontWeight.Bold,
                        fontSize = 22.sp,
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFFAED581), // Fresh avocado green
                ),
            )
        }
    ) { innerPadding ->
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            color = MaterialTheme.colorScheme.background,
        ) {
            when (currentStep) {
                1 -> {
                    AlpukatImageAndText(
                        textResourceId = R.string.alpukat_select,
                        drawableResourceId = R.drawable.ic_alpukat_utuh,
                        contentDescriptionResourceId = R.string.alpukat_select_content_description,
                        onImageClick = {
                            currentStep = 2
                        },
                    )
                }

                2 -> {
                    AlpukatImageAndText(
                        textResourceId = R.string.alpukat_split,
                        drawableResourceId = R.drawable.ic_alpukat_terbelah,
                        contentDescriptionResourceId = R.string.alpukat_split_content_description,
                        onImageClick = {
                            currentStep = 3
                            blendCount = (2..5).random()
                            blendTaps = 0
                        },
                    )
                }

                3 -> {
                    val blendText = when (blendTaps) {
                        0 -> stringResource(R.string.alpukat_blend)
                        1 -> stringResource(R.string.alpukat_blend_1)
                        2 -> stringResource(R.string.alpukat_blend_2)
                        3 -> stringResource(R.string.alpukat_blend_3)
                        else -> stringResource(R.string.alpukat_blend_4)
                    }

                    AlpukatImageAndText(
                        textString = blendText,
                        drawableResourceId = R.drawable.ic_alpukat_blender,
                        contentDescriptionResourceId = R.string.alpukat_blend_content_description,
                        onImageClick = {
                            blendTaps++
                            if (blendTaps >= blendCount) {
                                currentStep = 4
                            }
                        },
                    )
                }

                4 -> {
                    AlpukatImageAndText(
                        textResourceId = R.string.alpukat_drink,
                        drawableResourceId = R.drawable.ic_jus_alpukat,
                        contentDescriptionResourceId = R.string.alpukat_drink_content_description,
                        onImageClick = {
                            currentStep = 5
                        },
                    )
                }

                5 -> {
                    AlpukatImageAndText(
                        textResourceId = R.string.alpukat_empty,
                        drawableResourceId = R.drawable.ic_gelas_kosong,
                        contentDescriptionResourceId = R.string.alpukat_empty_content_description,
                        onImageClick = {
                            currentStep = 1
                        },
                    )
                }
            }
        }
    }
}

@Composable
fun AlpukatImageAndText(
    drawableResourceId: Int,
    contentDescriptionResourceId: Int,
    onImageClick: () -> Unit,
    modifier: Modifier = Modifier,
    textResourceId: Int? = null,
    textString: String? = null,
) {
    val textToDisplay = when {
        textString != null -> textString
        textResourceId != null -> stringResource(textResourceId)
        else -> ""
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = modifier.fillMaxSize(),
    ) {
        Surface(
            onClick = onImageClick,
            shape = RoundedCornerShape(28.dp),
            color = Color(0xFFC8E6C9),
            border = BorderStroke(2.dp, Color(0xFF9CCC65)),
        ) {
            Image(
                painter = painterResource(drawableResourceId),
                contentDescription = stringResource(contentDescriptionResourceId),
                modifier = Modifier
                    .size(240.dp)
                    .padding(24.dp),
            )
        }
        Spacer(modifier = Modifier.height(32.dp))
        Text(
            text = textToDisplay,
            style = MaterialTheme.typography.bodyLarge,
            fontSize = 18.sp,
            fontWeight = FontWeight.Medium,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(horizontal = 24.dp),
        )
    }
}

@Preview(showBackground = true)
@Composable
fun AlpukatAppPreview() {
    AlpukatTheme {
        AlpukatApp()
    }
}

package com.dissertation.emopal.ui.screens.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.rememberNavController
import com.dissertation.emopal.R
import com.dissertation.emopal.ui.theme.EmoPalTheme

@Composable
fun HomeScreen(
    onDiaryButtonClicked: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            Spacer(modifier = Modifier.height(24.dp))

            Text(
                // TODO: Add to string Resource
                text = "Welcome to EmoPal",
                style = MaterialTheme.typography.headlineLarge,
            )

            Spacer(modifier = Modifier.height(8.dp))

            // HOME PAGE IMAGE //
            Image(
                modifier = Modifier
                    .width(300.dp)
                    .weight(1F, fill = true),
                painter = painterResource(id = R.drawable.smiley_bee),
                // TODO: Add to string Resource
                contentDescription = "Smiley Cat",
            )

            // PLAY BUTTON //
            Button(
                onClick = { /*TODO*/ },
                modifier = Modifier.sizeIn(minWidth = 144.dp, minHeight = 62.dp),
                shape = MaterialTheme.shapes.small,
            )
            {
                Text(
                    text = "Play",
                    fontSize = 24.sp
                )
            }
//            Spacer(modifier = Modifier.height(8.dp))

            // Bottom Button Column
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
//                Spacer(modifier = Modifier.height(48.dp))
                Row(
                    modifier = Modifier
                        .padding(24.dp, 24.dp)
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly,

                    ) {
                    // DIARY BUTTON //
                    Button(
                        onClick = { onDiaryButtonClicked() },
                        modifier = Modifier.sizeIn(minWidth = 124.dp, minHeight = 62.dp),
                        shape = MaterialTheme.shapes.small,
                    ) {

                        Text(
                            text = "Diary",
                            fontSize = 24.sp
                        )
                    }

                    // HELP BUTTON //
                    Button(
                        onClick = { /*TODO*/ },
                        modifier = Modifier.sizeIn(minWidth = 124.dp, minHeight = 62.dp),
                        shape = MaterialTheme.shapes.small,
                    ) {

                        Text(
                            text = "Help",
                            fontSize = 24.sp
                        )
                    }

                }
            }
        }

    }
}

@Preview
@Composable
fun HomeScreenPreview() {
    EmoPalTheme {
        HomeScreen(
            onDiaryButtonClicked = {},
            modifier = Modifier
                .fillMaxSize()
                .padding(1.dp)
        )
    }

}
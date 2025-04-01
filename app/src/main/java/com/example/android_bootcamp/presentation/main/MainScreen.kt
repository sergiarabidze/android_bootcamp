package com.example.android_bootcamp.presentation.main

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredSizeIn
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.compositionLocalWithComputedDefaultOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.android_bootcamp.R

@Composable
fun MainScreen(
    email: String,
    onLogout: () -> Unit,
    onUsersClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val purpleColor = Color(0xFF6A35F2)
    val lightPurpleColor = Color(0xFFD1C4E9)

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        Image(
            painter = painterResource(R.drawable.elipse),
            contentDescription = null,
            modifier = Modifier.size(150.dp, 150.dp)
                .align(Alignment.TopStart)
        )

        Image(
            painter = painterResource(R.drawable.elipsetwo),
            contentDescription = null,
            modifier = Modifier
                .align(Alignment.BottomStart)
                .size(120.dp, 120.dp)
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 30.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(95.dp))

            Text(
                text = stringResource(R.string.welcome),
                fontSize = 48.sp,
                color = Color.Black
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = email,
                fontSize = 20.sp,
                color = Color.Black,
                modifier = Modifier.padding(top = 8.dp)
            )

            Spacer(modifier = Modifier.height(20.dp))

            Image(
                painter = painterResource(R.drawable.welcome),
                contentDescription = "Welcome icon",
                modifier = Modifier
                    .height(450.dp).width(200.dp)
            )

            Spacer(modifier = Modifier.height(32.dp))

            Button(
                onClick = onLogout,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                shape = RoundedCornerShape(30.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = purpleColor,
                    contentColor = lightPurpleColor
                ),
            ) {
                Text(
                    text = stringResource(R.string.log_out),
                    fontSize = 23.sp,
                    color = Color.White
                )
            }

            Spacer(modifier = Modifier.height(10.dp))

            Button(
                onClick = onUsersClick,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(60.dp),
                shape = RoundedCornerShape(30.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = purpleColor
                )
            ) {
                Text(
                    text = stringResource(R.string.others_profiles),
                    fontSize = 23.sp,
                    color = Color.White
                )
            }
        }
    }
}
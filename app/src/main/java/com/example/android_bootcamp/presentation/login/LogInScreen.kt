package com.example.android_bootcamp.presentation.login

import android.util.Log.d
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.android_bootcamp.R
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.requiredSizeIn
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.sp

@Composable
fun LoginScreen(
    viewModel: LoginViewModel,
    onRegisterClick: () -> Unit,
    onLoginSuccess: (email: String) -> Unit
) {
    val uiState by viewModel.state.collectAsState()
    val purpleColor = Color(0xFF6A35F2)

    LaunchedEffect(key1 = Unit) {
        viewModel.eventFlow.collect { event ->
            when (event) {
                is LoginUiEvent.NavigateToHome -> onLoginSuccess(event.email)
            }
        }
    }

    Box(
        modifier = Modifier
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
            Spacer(modifier = Modifier.height(65.dp))

            Text(
                text = stringResource(R.string.login),
                fontSize = 48.sp,
                color = Color.Black
            )

            Spacer(modifier = Modifier.height(35.dp))

            Image(
                painter = painterResource(R.drawable.register),
                contentDescription = "Register icon",
                modifier = Modifier.size(200.dp)
            )

            Spacer(modifier = Modifier.height(24.dp))

            PurpleTextField(
                value = uiState.email,
                onValueChange = { viewModel.onEvent(LoginEvent.EmailChanged(it)) },
                label = stringResource(R.string.email),
                iconRes = R.drawable.login_icon
            )



            Spacer(modifier = Modifier.height(24.dp))


            PurpleTextField(
                value = uiState.password,
                onValueChange = { viewModel.onEvent(LoginEvent.PasswordChanged(it)) },
                label = stringResource(R.string.password),
                iconRes = R.drawable.password,
                isPassword = true
            )

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Checkbox(
                    checked = uiState.rememberMe,
                    onCheckedChange = { viewModel.onEvent(LoginEvent.UpdateRememberMe(it)) },
                    modifier = Modifier.padding(start = 8.dp)
                )
                Text(
                    text = stringResource(R.string.remember_me),
                    color = Color.Black,
                    modifier = Modifier.padding(start = 8.dp)
                )
            }

            Spacer(modifier = Modifier.height(10.dp))

            uiState.error?.let { error ->
                Text(
                    text = error,
                    color = Color.Red,
                    modifier = Modifier.padding(top = 8.dp).fillMaxWidth().height(23.dp)
                )
            }

            Spacer(modifier = Modifier.height(10.dp))

            Button(
                onClick = {
                    viewModel.onEvent(LoginEvent.Submit(
                        uiState.email,
                        uiState.password,
                        uiState.rememberMe
                    ))
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(60.dp),
                enabled = uiState.isButtonEnabled,
                shape = RoundedCornerShape(30.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = purpleColor
                )
            ) {
                Text(
                    text = stringResource(R.string.login),
                    fontSize = 23.sp
                )
            }

            Spacer(modifier = Modifier.height(25.dp))

            Button(
                onClick = onRegisterClick,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                shape = RoundedCornerShape(30.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = purpleColor
                )
            ) {
                Text(
                    text = stringResource(R.string.register),
                    fontSize = 18.sp
                )
            }

            if (uiState.isLoading) {
                Spacer(modifier = Modifier.height(16.dp))
                CircularProgressIndicator(
                    color = purpleColor
                )
            }


        }
    }
}

@Composable
fun PurpleTextField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    iconRes: Int,
    isPassword: Boolean = false
) {
    val shape = RoundedCornerShape(30.dp)
    val lightPurpleColor = Color(0xFF6A35F2)
    val purpleColor = Color(0x4D700BEF)


    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(purpleColor, shape)
            .padding(horizontal = 16.dp, vertical = 12.dp)
    ) {
        BasicTextField(
            value = value,
            onValueChange = onValueChange,
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 36.dp),
            textStyle = TextStyle(
                color = Color.White,
                fontSize = 18.sp
            ),
            visualTransformation = if (isPassword) PasswordVisualTransformation() else VisualTransformation.None,
            singleLine = true
        )

        Icon(
            painter = painterResource(iconRes),
            contentDescription = null,
            tint = lightPurpleColor,
            modifier = Modifier
                .size(20.dp)
                .align(Alignment.CenterStart)
        )

        if (value.isEmpty()) {
            Text(
                text = label,
                color = Color.Gray,
                fontSize = 18.sp,
                modifier = Modifier.padding(start = 36.dp)
            )
        }
    }
}

package com.example.android_bootcamp.presentation.users

import android.content.Context
import android.graphics.Bitmap
import android.net.ConnectivityManager
import android.net.Network
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import com.bumptech.glide.Glide
import com.example.android_bootcamp.R
import com.example.android_bootcamp.presentation.helper.NetworkUtils
import com.example.android_bootcamp.presentation.users.model.user.UserPresentation


@Composable
fun UsersScreen(
    viewModel: UsersViewModel,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val userPagingData = viewModel.userPagingDataFlow.collectAsLazyPagingItems()
    val isOnline by rememberNetworkStatus()

    LaunchedEffect(isOnline) {
        val message = if (isOnline) "You are online" else "You are offline"
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(horizontal = 12.dp)
    ) {
        Box(modifier = Modifier.fillMaxWidth()) {
            IconButton(
                onClick = onBackClick,
                modifier = Modifier
                    .align(Alignment.TopStart)
                    .padding(25.dp)
            ) {
                Icon(
                    painter = painterResource(R.drawable.backarrow),
                    contentDescription = "Back",
                    tint = Color.Black
                )
            }

            Text(
                text = stringResource(R.string.users),
                fontSize = 32.sp,
                color = Color.Black,
                modifier = Modifier
                    .align(Alignment.TopCenter)
                    .padding(top = 25.dp)
            )
        }

        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .padding(top = 20.dp, bottom = 120.dp)
        ) {
            items(
                count = userPagingData.itemCount,
                key = { index -> userPagingData[index]?.id ?: index }
            ) { index ->
                val user = userPagingData[index]
                user?.let {
                    UserItem(user = it)
                }
            }

            userPagingData.apply {
                when {
                    loadState.refresh is LoadState.Loading -> {
                        item { LoadingIndicator(modifier = Modifier.fillParentMaxSize()) }
                    }
                    loadState.append is LoadState.Loading -> {
                        item { LoadingIndicator() }
                    }
                    loadState.refresh is LoadState.Error -> {
                        val error = loadState.refresh as LoadState.Error
                        item { ErrorItem(error.error.message ?: "Unknown error") }
                    }
                    loadState.append is LoadState.Error -> {
                        val error = loadState.append as LoadState.Error
                        item { ErrorItem(error.error.message ?: "Unknown error") }
                    }
                }
            }
        }
    }
}

@Composable
fun UserItem(user: UserPresentation, modifier: Modifier = Modifier) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp),
        shape = RoundedCornerShape(8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(180.dp)
                .background(Color.White)
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(120.dp)
                    .clip(CircleShape)
            ) {
                GlideImage(
                    model = user.avatar,
                    contentDescription = "User avatar",
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )
            }

            Spacer(modifier = Modifier.width(16.dp))

            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = user.email,
                    fontSize = 22.sp,
                    color = Color.Black,
                    modifier = Modifier.padding(bottom = 8.dp)
                )

                Text(
                    text = "${user.firstName} ${user.lastName}",
                    fontSize = 18.sp,
                    color = Color.Black
                )
            }
        }
    }
}

@Composable
fun GlideImage(
    model: Any?,
    contentDescription: String?,
    modifier: Modifier = Modifier,
    contentScale: ContentScale = ContentScale.Crop
) {
    val context = LocalContext.current
    val imageLoader = remember { Glide.with(context) }
    val bitmap = remember { mutableStateOf<Bitmap?>(null) }

    LaunchedEffect(model) {
        if (model != null) {
            try {
                val futureTarget = imageLoader
                    .asBitmap()
                    .load(model)
                    .submit()

                bitmap.value = futureTarget.get()
            } catch (e: Exception) {
                bitmap.value = null
            }
        }
    }

    bitmap.value?.let {
        Image(
            bitmap = it.asImageBitmap(),
            contentDescription = contentDescription,
            modifier = modifier,
            contentScale = contentScale
        )
    } ?: run {
        Box(
            modifier = modifier
                .background(Color.LightGray)
        )
    }
}

@Composable
fun LoadingIndicator(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator()
    }
}

@Composable
fun ErrorItem(message: String) {
    Text(
        text = message,
        color = Color.Red,
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    )
}

@Composable
fun rememberNetworkStatus(): State<Boolean> {
    val context = LocalContext.current
    val isOnline = remember { mutableStateOf(true) }

    LaunchedEffect(Unit) {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkCallback = object : ConnectivityManager.NetworkCallback() {
            override fun onAvailable(network: Network) {
                isOnline.value = true
            }

            override fun onLost(network: Network) {
                isOnline.value = false
            }
        }

        connectivityManager.registerDefaultNetworkCallback(networkCallback)
        isOnline.value = NetworkUtils.isInternetAvailable(context)

    }

    return isOnline
}

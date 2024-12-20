package com.dicoding.clubfootball.ui.screen

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Divider
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.dicoding.clubfootball.R
import com.dicoding.clubfootball.di.Injection
import com.dicoding.clubfootball.ui.common.UiState
import com.dicoding.clubfootball.ui.viewmodel.DetailViewModel
import com.dicoding.clubfootball.ui.viewmodel.ViewModelFactory

@Composable
fun DetailScreen(
    newsId: Int,
    navigateBack: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: DetailViewModel = viewModel(
        factory = ViewModelFactory(Injection.provideRepository())
    )
) {
    viewModel.uiState.collectAsState(initial = UiState.Loading).value.let { uiState ->
        when(uiState) {
            is UiState.Loading -> {
                viewModel.getNewsById(newsId)
            }
            is UiState.Success -> {
                val data = uiState.data
                DetailInformation(
                    id = data.id,
                    name = data.name,
                    description = data.description,
                    image = data.image,
                    isBookmark = data.isBookmark,
                    navigateBack = navigateBack,
                    onBookmarkButtonClicked = {id, state ->
                        viewModel.updateNews(id, state)
                    }
                )
            }
            is UiState.Error -> {}
        }
    }
}

@Composable
fun DetailInformation(
    id: Int,
    name: String,
    description: String,
    @DrawableRes image: Int,
    isBookmark: Boolean,
    navigateBack: () -> Unit,
    onBookmarkButtonClicked: (id: Int, state: Boolean) -> Unit,
    modifier: Modifier = Modifier
){
    Box(
        modifier = Modifier
            .fillMaxSize()

    ) {
        Column(
            modifier = Modifier
                .verticalScroll(rememberScrollState())
                .padding(bottom = 16.dp)
        ) {
            Image(
                painter = painterResource(image),
                contentDescription = name,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("scrollToBottom")
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text =name,
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            )

            Divider(modifier = Modifier.padding(horizontal = 16.dp, vertical = 16.dp))
            Text(
                text = description,
                fontSize = 14.sp,
                lineHeight = 28.sp,
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(8.dp))
        }
        IconButton(
            onClick = navigateBack,
            modifier = Modifier
                .padding(start = 16.dp, top = 8.dp)
                .align(Alignment.TopStart)
                .clip(CircleShape)
                .size(40.dp)
                .testTag("back_home")
                .background(Color.White)
        ) {
            Icon(
                imageVector = Icons.Default.ArrowBack,
                contentDescription = stringResource(R.string.back),
            )
        }
        IconButton(onClick = {
            onBookmarkButtonClicked(id, isBookmark)
        }, modifier = Modifier
            .padding(end = 16.dp, top = 8.dp)
            .align(Alignment.TopEnd)
            .clip(CircleShape)
            .size(40.dp)
            .background(Color.White)
            .testTag("bookmark_detail_button")
        ) {
            Icon(
                painter = if (!isBookmark) painterResource(R.drawable.baseline_favorite_24) else
                    painterResource(R.drawable.favorite_border_24),
                contentDescription = if (!isBookmark) stringResource(R.string.add_bookmark) else stringResource(
                    R.string.delete_bookmark),
                tint = if(!isBookmark) Color.Black else Color.Red
            )
        }
    }
}
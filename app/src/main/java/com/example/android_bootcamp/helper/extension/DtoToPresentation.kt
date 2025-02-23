package com.example.android_bootcamp.helper.extension

import com.example.android_bootcamp.data.remote.dto.OwnerDto
import com.example.android_bootcamp.data.remote.dto.PostDto
import com.example.android_bootcamp.data.remote.dto.StoryDto
import com.example.android_bootcamp.helper.data_classes.Owner
import com.example.android_bootcamp.helper.data_classes.Post
import com.example.android_bootcamp.helper.data_classes.Story
import java.text.SimpleDateFormat
import java.util.*

fun StoryDto.toStory(): Story {
    return Story(id = id, title = title, imageUrl = cover)
}
fun OwnerDto.toOwner() : Owner {
    return Owner(firstName = firstName, lastName = lastName, postDate = formatEpochToDate(postDate), profile = profile ?: "")
}

fun PostDto.toPost(): Post {
    return Post(
        comments = comments,
        id = id,
        images = images?: emptyList(),
        likes = likes,
        owner = owner.toOwner(),
        shareContent = shareContent,
        title = title
    )
}


fun formatEpochToDate(epoch: Long): String {
    val sdf = SimpleDateFormat("MM-yy-dd", Locale.getDefault())
    return sdf.format(Date(epoch))
}
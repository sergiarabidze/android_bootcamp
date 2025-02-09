package com.example.android_bootcamp.remote.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.android_bootcamp.remote.api.ServiceApi
import com.example.android_bootcamp.remote.api.serializable_classes.User
import com.example.android_bootcamp.remote.httpRequest.ApiHelper
import com.example.android_bootcamp.remote.httpRequest.Resource

class UserPagingSource(
    private val serviceApi: ServiceApi
) : PagingSource<Int, User>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, User> {
        val page = params.key ?: 1

        val result = ApiHelper.handleHttpRequest {
            serviceApi.getUsers(page)
        }

        return when (result) {
            is Resource.Success -> {
                val users = result.data.data
                val endOfPaginationReached = users.isEmpty()
                LoadResult.Page(
                    data = users,
                    prevKey = if (page == 1) null else page - 1,
                    nextKey = if (endOfPaginationReached) null else page + 1
                )
            }
            is Resource.Error -> LoadResult.Error(Exception(result.message))
            is Resource.Loading, is Resource.Idle -> LoadResult.Error(Exception("Unexpected state"))
        }
    }

    override fun getRefreshKey(state: PagingState<Int, User>): Int? {
        return state.anchorPosition?.let { position ->
            state.closestPageToPosition(position)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(position)?.nextKey?.minus(1)
        }
    }
}

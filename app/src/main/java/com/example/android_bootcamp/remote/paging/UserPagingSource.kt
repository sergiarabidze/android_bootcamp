package com.example.android_bootcamp.remote.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.android_bootcamp.remote.api.ServiceApi
import com.example.android_bootcamp.remote.api.serializable_classes.User

class UserPagingSource(
    private val serviceApi: ServiceApi
) : PagingSource<Int, User>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, User> {
        val page = params.key ?: 1
        return try {
            val response = serviceApi.getUsers(page)
            val users = response.body()?.data ?: emptyList()

            if(response.isSuccessful) {
                val endOfPaginationReached = response.body()!!.data.isEmpty()
                LoadResult.Page(
                    data = users,
                    prevKey = if (page == 1) null else page - 1,
                    nextKey = if (endOfPaginationReached) null else page + 1
                )
            }
            else{
                LoadResult.Error(Exception("No Response"))
            }
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
        catch (httpException: Exception) {
            LoadResult.Error(httpException)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, User>): Int? {
        return state.anchorPosition?.let { position ->
            state.closestPageToPosition(position)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(position)?.nextKey?.minus(1)
        }
    }
}

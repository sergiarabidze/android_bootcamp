package com.example.android_bootcamp.local.room
import androidx.paging.*
import androidx.room.withTransaction
import com.example.android_bootcamp.helper.toEntity
import com.example.android_bootcamp.remote.api.ServiceApi
import retrofit2.HttpException
import java.io.IOException

@OptIn(ExperimentalPagingApi::class)
class UserRemoteMediator(
    private val apiService: ServiceApi,
    private val database: DataBase
) : RemoteMediator<Int, UserEntity>() {

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, UserEntity>
    ): MediatorResult {
        try {
            val page = when (loadType) {
                LoadType.REFRESH -> 1
                LoadType.PREPEND -> return MediatorResult.Success(endOfPaginationReached = true)
                LoadType.APPEND -> {
                    val lastItem = state.lastItemOrNull()
                    if (lastItem == null) {
                        1
                    } else {
                        (lastItem.id / state.config.pageSize) + 1
                    }
                }
            }

            val response = apiService.getUsers(page)
            val users = response.body()?.data


            database.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    database.userDao().deleteAllUsers()
                }

                if (users != null) {
                    database.userDao().insertUsers(users.map { it.toEntity() })
                }
            }

            return MediatorResult.Success(endOfPaginationReached = page >= (response.body()?.totalPages
                ?: 0)
            )

        } catch (e: IOException) {
            return MediatorResult.Error(e)
        } catch (e: HttpException) {
            return MediatorResult.Error(e)
        }
    }
}

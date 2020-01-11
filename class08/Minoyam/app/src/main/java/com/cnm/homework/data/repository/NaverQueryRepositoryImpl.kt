package com.cnm.homework.data.repository

import com.cnm.homework.data.model.NaverResponse
import com.cnm.homework.data.source.remote.NaverQueryRemoteDataSource
import io.reactivex.Single

class NaverQueryRepositoryImpl(private val remoteDataSource: NaverQueryRemoteDataSource) :
    NaverQueryRepository {
    override fun getNaverMovie(query: String): Single<NaverResponse> {
        return remoteDataSource.getNaverMovie(query)
    }

    companion object {
        private var INSTANCE: NaverQueryRepositoryImpl? = null

        fun getInstance(remoteDataSource: NaverQueryRemoteDataSource): NaverQueryRepository =
            INSTANCE ?: NaverQueryRepositoryImpl(remoteDataSource).apply {
                INSTANCE = this
            }
    }
}
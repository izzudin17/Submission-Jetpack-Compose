package com.dicoding.clubfootball.di

import com.dicoding.clubfootball.repository.FootballRepository

object Injection {
    fun provideRepository(): FootballRepository {
        return FootballRepository.getInstance()
    }
}
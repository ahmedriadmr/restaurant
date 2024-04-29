package com.doubleclick.rovleapp.feature.profile.domain

import com.doubleclick.rovleapp.core.interactor.UseCase
import com.doubleclick.rovleapp.feature.profile.ProfileRepository
import com.doubleclick.rovleapp.feature.profile.data.visits.VisitsData
import javax.inject.Inject

class ShowVisitsUseCase @Inject constructor(private val showVisitsRepository: ProfileRepository) :
    UseCase<List<VisitsData>, UseCase.None>() {

    override suspend fun run(params: None) = showVisitsRepository.showVisits()
}
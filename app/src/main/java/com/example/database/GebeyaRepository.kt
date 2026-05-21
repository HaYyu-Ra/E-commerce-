package com.example.database

import kotlinx.coroutines.flow.Flow

class GebeyaRepository(private val gebeyaDao: GebeyaDao) {
    val userProfile: Flow<UserProfile?> = gebeyaDao.getUserProfile()
    val allGigs: Flow<List<Gig>> = gebeyaDao.getAllGigs()
    val allCompletedTasks: Flow<List<CompletedTask>> = gebeyaDao.getAllCompletedTasks()
    val allCashouts: Flow<List<Cashout>> = gebeyaDao.getAllCashouts()
    val allReferrals: Flow<List<Referral>> = gebeyaDao.getAllReferrals()

    suspend fun getUserProfileOneShot(): UserProfile? {
        return gebeyaDao.getUserProfileOneShot()
    }

    suspend fun insertUserProfile(userProfile: UserProfile) {
        gebeyaDao.insertUserProfile(userProfile)
    }

    suspend fun insertGigs(gigs: List<Gig>) {
        gebeyaDao.insertGigs(gigs)
    }

    suspend fun insertGig(gig: Gig) {
        gebeyaDao.insertGig(gig)
    }

    suspend fun insertCompletedTask(completedTask: CompletedTask) {
        gebeyaDao.insertCompletedTask(completedTask)
    }

    suspend fun insertCashout(cashout: Cashout) {
        gebeyaDao.insertCashout(cashout)
    }

    suspend fun insertReferral(referral: Referral) {
        gebeyaDao.insertReferral(referral)
    }
}

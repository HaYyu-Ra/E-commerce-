package com.example.database

import androidx.room.Dao
import androidx.room.Database
import androidx.room.Entity
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.PrimaryKey
import androidx.room.Query
import androidx.room.RoomDatabase
import kotlinx.coroutines.flow.Flow

@Entity(tableName = "gigs")
data class Gig(
    @PrimaryKey val id: String,
    val titleEn: String,
    val titleAm: String,
    val categoryEn: String,
    val categoryAm: String,
    val payoutEtb: Int,
    val descriptionEn: String,
    val descriptionAm: String,
    val requirementsEn: String,
    val requirementsAm: String,
    val estMinutes: Int,
    val offlineCompatible: Boolean,
    val state: String = "AVAILABLE" // AVAILABLE, CLAIMED, COMPLETED_OFFLINE, SUBMITTED
)

@Entity(tableName = "completed_tasks")
data class CompletedTask(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val gigId: String,
    val titleEn: String,
    val titleAm: String,
    val payoutEtb: Int,
    val timestamp: Long = System.currentTimeMillis(),
    val isSynced: Boolean = false,
    val submissionNote: String = ""
)

@Entity(tableName = "cashouts")
data class Cashout(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val method: String, // Telebirr, Chapa, CBE Birr
    val accountNumber: String,
    val amountEtb: Int,
    val timestamp: Long = System.currentTimeMillis(),
    val status: String, // PENDING, PROCESSING, SUCCESS, FAILED
    val transactionRef: String
)

@Entity(tableName = "referrals")
data class Referral(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val invitedPhone: String,
    val invitedName: String,
    val dateRegistered: Long = System.currentTimeMillis(),
    val status: String, // ACTIVE, SIGNED_UP
    val rewardEtb: Int = 20
)

@Entity(tableName = "user_profile")
data class UserProfile(
    @PrimaryKey val id: Int = 1,
    val name: String = "Hayyu Ragea",
    val phone: String = "0913529010",
    val language: String = "ENGLISH", // ENGLISH, AMHARIC
    val balanceEtb: Int = 150, // default signup welcome bonus!
    val referralCode: String = "GIG-HAYYU-2026",
    val premiumUser: Boolean = false,
    val currentSkillRank: String = "Bronze Tasker"
)

@Dao
interface GebeyaDao {
    @Query("SELECT * FROM user_profile WHERE id = 1 LIMIT 1")
    fun getUserProfile(): Flow<UserProfile?>

    @Query("SELECT * FROM user_profile WHERE id = 1 LIMIT 1")
    suspend fun getUserProfileOneShot(): UserProfile?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUserProfile(userProfile: UserProfile)

    @Query("SELECT * FROM gigs")
    fun getAllGigs(): Flow<List<Gig>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertGig(gig: Gig)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertGigs(gigs: List<Gig>)

    @Query("SELECT * FROM completed_tasks ORDER BY timestamp DESC")
    fun getAllCompletedTasks(): Flow<List<CompletedTask>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCompletedTask(task: CompletedTask)

    @Query("SELECT * FROM cashouts ORDER BY timestamp DESC")
    fun getAllCashouts(): Flow<List<Cashout>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCashout(cashout: Cashout)

    @Query("SELECT * FROM referrals ORDER BY dateRegistered DESC")
    fun getAllReferrals(): Flow<List<Referral>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertReferral(referral: Referral)
}

@Database(entities = [Gig::class, CompletedTask::class, Cashout::class, Referral::class, UserProfile::class], version = 1, exportSchema = false)
abstract class GebeyaDatabase : RoomDatabase() {
    abstract fun gebeyaDao(): GebeyaDao
}

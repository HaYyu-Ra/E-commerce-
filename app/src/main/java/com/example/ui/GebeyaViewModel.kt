package com.example.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.database.*
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.util.UUID

class GebeyaViewModel(private val repository: GebeyaRepository) : ViewModel() {

    // UI state flows from local Room Database
    val userProfile: StateFlow<UserProfile> = repository.userProfile
        .map { it ?: UserProfile() }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), UserProfile())

    val gigs: StateFlow<List<Gig>> = repository.allGigs
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val completedTasks: StateFlow<List<CompletedTask>> = repository.allCompletedTasks
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val cashouts: StateFlow<List<Cashout>> = repository.allCashouts
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val referrals: StateFlow<List<Referral>> = repository.allReferrals
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    // Cashout simulation states
    private val _cashoutStatus = MutableStateFlow<String?>(null)
    val cashoutStatus = _cashoutStatus.asStateFlow()

    private val _cashoutLogs = MutableStateFlow<List<String>>(emptyList())
    val cashoutLogs = _cashoutLogs.asStateFlow()

    init {
        seedGigs()
    }

    private fun seedGigs() {
        viewModelScope.launch {
            // Check & ensure UserProfile is updated with current requested information
            val currentProfile = repository.getUserProfileOneShot()
            if (currentProfile == null) {
                val defaultUserProfile = UserProfile(name = "Hayyu Ragea", phone = "0913529010")
                repository.insertUserProfile(defaultUserProfile)
            } else if (currentProfile.name != "Hayyu Ragea" || currentProfile.phone != "0913529010") {
                repository.insertUserProfile(currentProfile.copy(name = "Hayyu Ragea", phone = "0913529010"))
            }

            // First check if profile or gigs are populated
            val currentGigs = repository.allGigs.first()
            if (currentGigs.isEmpty()) {
                val defaultUserProfile = UserProfile()
                repository.insertUserProfile(defaultUserProfile)

                val list = listOf(
                    Gig(
                        id = "gig_1",
                        titleEn = "Verify Bole Coffee Shop QR Support",
                        titleAm = "በቦሌ የሚገኝ ካፌ የQR ክፍያ ማረጋገጥ",
                        categoryEn = "Fintech Directory Audit",
                        categoryAm = "የክፍያ ቴክኖሎጂ ፍተሻ",
                        payoutEtb = 50,
                        descriptionEn = "Visit Tomoca Coffee or any café near Bole Medhanialem. Ask the cashier if they support Chapa or Telebirr QR pay. Note down their payment terminal brand.",
                        descriptionAm = "ቦሌ መድኃኔዓለም አቅራቢያ የሚገኝ ቶሞካ ካፌ ወይም ማንኛውንም ካፌ ይጎብኙ። ቻፓ (Chapa) ወይም ቴሌብር ኪውአር (Telebirr QR) ክፍያ እንደሚቀበሉ ገንዘብ ያዥውን ይጠይቁ::",
                        requirementsEn = "1. Confirm Telebirr or Chapa acceptance\n2. Note machine brand\n3. Take a quick photo of the checkout desk counter (offline-safe).",
                        requirementsAm = "1. የቴሌብር ወይም ቻፓ ተቀባይነትን ያረጋግጡ\n2. የክፍያ ማሽኑን ስም ይጻፉ\n3. የክፍያ ጠረጴዛውን ፈጣን ፎቶ ያንሱ (ከኢንተርኔት ውጭ ይሰራል)።",
                        estMinutes = 10,
                        offlineCompatible = true
                    ),
                    Gig(
                        id = "gig_2",
                        titleEn = "Record Amharic Speech Phrases",
                        titleAm = "የአማርኛ ንግግር ድምፅ መቅረጽ",
                        categoryEn = "AI Voice Training",
                        categoryAm = "የሰው ሰራሽ አስተዋይ ድምፅ ስልጠና",
                        payoutEtb = 75,
                        descriptionEn = "Record 5 short conversational Amharic phrases in a quiet room to train local speech-to-text translators.",
                        descriptionAm = "በኢትዮጵያ ውስጥ የሚሰሩ የድምፅ መተርጎሚያ ቴክኖሎጂዎችን ለማሰልጠን በተረጋጋ ቦታ ሆነው 5 አጫጭር የአማርኛ ዓረፍተ ነገሮችን በድምፅ ይቅረጹ::",
                        requirementsEn = "1. Clear voice without background echo\n2. Speak standard Amharic naturally\n3. File size is under 500KB (highly compressed).",
                        requirementsAm = "1. ከጀርባ ድምፅ የሌለበት ግልጽ ድምፅ\n2. መደበኛ የአማርኛ ቋንቋን በተፈጥሮአዊ መንገድ መናገር\n3. የድምፅ ፋይሉ ከ500KB በታች መሆን አለበት።",
                        estMinutes = 5,
                        offlineCompatible = true
                    ),
                    Gig(
                        id = "gig_3",
                        titleEn = "Merkato Teff Market Price Audit",
                        titleAm = "የመርካቶ ጤፍ ወቅታዊ ዋጋ መረጃ",
                        categoryEn = "Agri-Tech Price Tracker",
                        categoryAm = "የግብርና ምርቶች ዋጋ መከታተያ",
                        payoutEtb = 60,
                        descriptionEn = "Visit the grain sector in Merkato (Addis Ketema). Ask or note down the price for 1 quintal of Magna Teff, Mixed Teff, and White Teff.",
                        descriptionAm = "መርካቶ (አዲስ ከተማ) የሚገኘውን የእህል ገበያ ይጎብኙ። የአንድ ኩንታል የማኛ ጤፍ፣ የቀይ/የተቀላቀለ ጤፍ እና የነጭ ጤፍ ወቅታዊ ዋጋ ጠይቀው ይመዝግቡ።",
                        requirementsEn = "1. Real price validation\n2. Enter three grain types price in ETB\n3. Accurate location registration (GPS cache).",
                        requirementsAm = "1. እውነተኛ ዋጋዎችን ብቻ ማስገባት\n2. የሶስት የጤፍ ዓይነቶችን ዋጋ በብር ማስገባት\n3. ትክክለኛ የቦታ ምዝገባ (በጂፒኤስ የተቀመጠ)።",
                        estMinutes = 15,
                        offlineCompatible = true
                    ),
                    Gig(
                        id = "gig_4",
                        titleEn = "CBE Branch Geolocation Verification",
                        titleAm = "የኢትዮጵያ ንግድ ባንክ ቅርንጫፍ ቦታ ማረጋገጥ",
                        categoryEn = "Digital Mapping",
                        categoryAm = "ዲጂታል ካርታ ስራ",
                        payoutEtb = 90,
                        descriptionEn = "Locate any newly established Commercial Bank of Ethiopia (CBE) branch or CBE Birr agent kiosk. Capture the coordinate, active status, and operating hours.",
                        descriptionAm = "አዲስ የተከፈተ የኢትዮጵያ ንግድ ባንክ (CBE) ቅርንጫፍ ወይም የሲቢኢ ብር ወኪል መገኛ ቦታን ይወቁ። የቦታውን መጋጠሚያ፣ የስራ ሁኔታ እና የስራ ሰዓታትን ያረጋግጡ።",
                        requirementsEn = "1. Take coordinates inside the app\n2. Enter business working hours\n3. Confirm whether CBE Birr cashout is active.",
                        requirementsAm = "1. የቦታ መጋጠሚያዎችን በካርታው መሠረት ማስገባት\n2. የስራ ሰዓታትን ማስገባት\n3. የሲቢኢ ብር ገንዘብ ማውጣት መስራቱን ማረጋገጥ።",
                        estMinutes = 12,
                        offlineCompatible = true
                    ),
                    Gig(
                        id = "gig_5",
                        titleEn = "Translate Small FinTech Onboarding Texts",
                        titleAm = "አጫጭር የፋይናንስ መተግበሪያ ፅሁፎችን መተርጎም",
                        categoryEn = "Localization Gig",
                        categoryAm = "የትርጉም ስራ",
                        payoutEtb = 110,
                        descriptionEn = "Translate 10 fintech onboarding texts (e.g. 'Verify OTP', 'Insufficient funds', 'Daily Transaction Limit exceeded') from English to Amharic or Afaan Oromoo.",
                        descriptionAm = "10 የፋይናንስ መተግበሪያ ፅሁፎችን (ለምሳሌ 'የይለፍ ቃል አረጋግጥ'፣ 'በቂ የገንዘብ መጠን የለም'፣ 'የዕለታዊ ዝውውር ገደብ አልፏል') ከእንግሊዘኛ ወደ አማርኛ ወይም አፋን ኦሮሞ ይተርጉሙ።",
                        requirementsEn = "1. Professional translations suitable for dynamic mobile app layouts\n2. Keep character length compact\n3. Zero spelling mistakes.",
                        requirementsAm = "1. ለሞባይል መተግበሪያዎች የሚመጥኑ ሙያዊ ትርጉሞች\n2. የፅሁፍ መጠኑን አጭር ማድረግ\n3. ምንም አይነት የፊደል ስህተት አለመኖር::",
                        estMinutes = 8,
                        offlineCompatible = true
                    )
                )
                repository.insertGigs(list)
            }
        }
    }

    fun toggleLanguage() {
        viewModelScope.launch {
            val current = userProfile.value
            val nextLang = if (current.language == "ENGLISH") "AMHARIC" else "ENGLISH"
            repository.insertUserProfile(current.copy(language = nextLang))
        }
    }

    fun claimGig(gigId: String) {
        viewModelScope.launch {
            val gigList = gigs.value
            val gig = gigList.find { it.id == gigId }
            if (gig != null && gig.state == "AVAILABLE") {
                repository.insertGig(gig.copy(state = "CLAIMED"))
            }
        }
    }

    fun completeGigOffline(gigId: String, note: String) {
        viewModelScope.launch {
            val gigList = gigs.value
            val gig = gigList.find { it.id == gigId }
            if (gig != null) {
                // Update State to COMPLETED_OFFLINE (shows immediate local feedback, works 100% offline!)
                repository.insertGig(gig.copy(state = "COMPLETED_OFFLINE"))

                // Save Completed Task to Room
                val completed = CompletedTask(
                    gigId = gig.id,
                    titleEn = gig.titleEn,
                    titleAm = gig.titleAm,
                    payoutEtb = gig.payoutEtb,
                    submissionNote = note,
                    isSynced = false
                )
                repository.insertCompletedTask(completed)
            }
        }
    }

    fun submitGig(gigId: String) {
        viewModelScope.launch {
            val gigList = gigs.value
            val gig = gigList.find { it.id == gigId }
            if (gig != null) {
                // Submit updates state and awards cash payout directly to profile balance!
                repository.insertGig(gig.copy(state = "SUBMITTED"))

                // Update balance in user_profile table
                val profile = userProfile.value
                val newBalance = profile.balanceEtb + gig.payoutEtb
                repository.insertUserProfile(profile.copy(balanceEtb = newBalance))
            }
        }
    }

    fun inviteFriend(name: String, phone: String) {
        viewModelScope.launch {
            if (name.isBlank() || phone.isBlank()) return@launch
            // Insert referral locally in Room
            val ref = Referral(
                invitedName = name,
                invitedPhone = phone,
                status = "ACTIVE"
            )
            repository.insertReferral(ref)

            // Reward user (+20 ETB referral fee)
            val profile = userProfile.value
            val newBalance = profile.balanceEtb + 20
            repository.insertUserProfile(profile.copy(balanceEtb = newBalance))
        }
    }

    fun togglePremium() {
        viewModelScope.launch {
            val profile = userProfile.value
            val currentStatus = profile.premiumUser
            repository.insertUserProfile(profile.copy(premiumUser = !currentStatus))
        }
    }

    fun simulateCashout(method: String, accountNumber: String, amount: Int) {
        viewModelScope.launch {
            val profile = userProfile.value
            if (amount <= 0 || amount > profile.balanceEtb) {
                _cashoutStatus.value = "ERROR_INSUFFICIENT"
                _cashoutLogs.value = listOf("Error: Amount exceeds available balance of ${profile.balanceEtb} ETB!")
                return@launch
            }

            _cashoutStatus.value = "PROCESSING"
            val logs = mutableListOf<String>()
            _cashoutLogs.value = logs

            // Step 1: Initializing request local simulation
            logs.add("🕒 [T+0s] Initiating payout of $amount ETB. Method: $method")
            _cashoutLogs.value = logs.toList()
            kotlinx.coroutines.delay(1000)

            // Step 2: Hitting mock API endpoints (representing CBE, Chapa, CBE Birr or Telebirr payout endpoints)
            logs.add("🔗 [T+1s] Connecting to payments.partner_api.$method.et secure port...")
            _cashoutLogs.value = logs.toList()
            kotlinx.coroutines.delay(1200)

            // Step 3: Auth payload handover
            logs.add("🔑 [T+2s] Auth Handshake completed using secured merchant credentials.")
            logs.add("💸 [T+2.5s] Injecting balance transfer node: amount=$amount, destination=$accountNumber")
            _cashoutLogs.value = logs.toList()
            kotlinx.coroutines.delay(1400)

            // Step 4: Webhook callback simulation and reference key verification
            val txRef = when (method) {
                "Telebirr" -> "C-T-BIRR-${System.currentTimeMillis() % 1000000}"
                "Chapa" -> "CHAPA-TX-${UUID.randomUUID().toString().take(6).uppercase()}"
                else -> "CBE-MOBILE-${System.currentTimeMillis() % 1000000}"
            }
            logs.add("📥 [T+4s] Webhook signal received. Callback status code: 200 SUCCESS.")
            logs.add("✅ [T+4.5s] Reference generated: $txRef")
            _cashoutLogs.value = logs.toList()

            // Insert cashout record to SQLite
            val cashoutRecord = Cashout(
                method = method,
                accountNumber = accountNumber,
                amountEtb = amount,
                status = "SUCCESS",
                transactionRef = txRef
            )
            repository.insertCashout(cashoutRecord)

            // Deduct user balance
            val newBalance = profile.balanceEtb - amount
            repository.insertUserProfile(profile.copy(balanceEtb = newBalance))

            _cashoutStatus.value = "SUCCESS"
        }
    }

    fun clearCashoutStatus() {
        _cashoutStatus.value = null
        _cashoutLogs.value = emptyList()
    }
}

class GebeyaViewModelFactory(private val repository: GebeyaRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(GebeyaViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return GebeyaViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

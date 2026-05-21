package com.example.ui

import android.widget.Toast
import androidx.compose.animation.*
import androidx.compose.animation.core.spring
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.database.Gig
import com.example.ui.theme.*

// Localized string mapper for premium dynamic translations
object LocalizedText {
    fun get(key: String, language: String): String {
        val amMap = mapOf(
            "app_title" to "ገበያጊግስ",
            "app_subtitle" to "የሃበሻ ስማርት ስራዎች",
            "motto" to "የወጣቶች የገቢ ምንጭ እና የዲጂታል ወኪል መረብ",
            "welcome_back" to "እንኳን ደህና መጡ",
            "balance" to "የተከማቸ ሒሳብ",
            "skill_rank" to "የክህሎት ደረጃ",
            "claim_task" to "ስራውን ውሰድ",
            "claimed" to "ተወስዷል",
            "payout" to "የክፍያ መጠን / Payout",
            "offline_status" to "ከኢንተርኔት ውጭ የሚሰራ",
            "complete_offline" to "ያልተገናኘ ስራ ማጠናቀቂያ",
            "submit_verify" to "ስራውን አስረክብ ( payout + )",
            "submitted" to "የጸደቀ (ተከፍሏል)",
            "submit_note" to "የስራ ማስረጃዎን እዚህ ያስገቡ (ለምሳሌ የሱቃ ስም ወይም መከታተያ ዋጋ)...",
            "save_offline" to "ለጊዜው በስልክ አስቀምጥ (ከመስመር ውጪ)",
            "sync_payout" to "አስረክብና ክፍያ ተቀበል (ሲንክ ቁልፍ)",
            "available_gigs" to "ንቁ የስራ እድሎች",
            "wallet" to "የኪስ ቦርሳ",
            "cashout" to "ገንዘብ ማውጣት",
            "refer_earn" to "ጓደኛ ጋብዘው ያትርፉ (ሪፈራል)",
            "refer_inst" to "ለእያንዳንዱ ንቁ ተጋባዥ 20 የኢትዮጵያ ‘ብር’ ያግኙ!",
            "referral_code" to "የመጋበዣ ኮድ",
            "invite_friend" to "አዲስ ወዳጅ መጋበዣ",
            "friend_name" to "የጓደኛ ስም",
            "friend_phone" to "የጓደኛ ስልክ (09...) ",
            "register_referral" to "ጓደኛ ጋብዝ (+ 20 ETB)",
            "copied" to "ኮፒ ተደርጓል!",
            "cashout_history" to "ያለፉ የክፍያ ታሪኮች",
            "blueprint" to "ስልታዊ ዕቅድ",
            "db_spec" to "የመረጃ ቋት እና ኤፒአይ",
            "telebirr" to "ቴሌብር",
            "chapa" to "ቻፓ",
            "cbe_birr" to "ሲቢኢ ብር",
            "amount" to "መጠን (ETB)",
            "account" to "የስልክ መለያ ወይም የሒሳብ ቁጥር",
            "trans_logs" to "የክፍያ API ሂደት",
            "cashout_processing" to "ገንዘቡ እየተላለፈ ነው...",
            "cashout_success" to "የክፍያ ማስተላለፍ ተሳክቷል!",
            "cashout_error" to "ሒሳብዎ በቂ አይደለም!",
            "search_task" to "ስራዎችን ፈልግ...",
            "category" to "ዘርፍ",
            "premium_upgrade" to "ወደ ኘሪሚየም ያሳድጉ (2x Tasks)!",
            "premium_member" to "ኘሪሚየም አባል (VIP)",
            "all_gigs" to "ሁሉም ስራዎች",
            "claimed_gigs" to "የተያዙ",
            "completed_gigs" to "ያለቁ",
            "no_gigs" to "ምንም ንቁ ስራዎች የሉም።",
            "instructions" to "የስራ መመሪያዎች",
            "offline_tip" to "ይህ ስራ ከኢንተርኔት ውጭ ተጠናቆ በኋላ ላይ ሲንክ መደረግ ይችላል!",
            "submit_success_toast" to "ስራው በተሳካ ሁኔታ ተጠናቋል! ክፍያዎ ተፈጽሟል።"
        )
        val enMap = mapOf(
            "app_title" to "GebeyaGigs",
            "app_subtitle" to "Ethiopia Smart Micro-Gigs",
            "motto" to "Empowering Youth Earnings & Agent Networks",
            "welcome_back" to "Welcome back",
            "balance" to "Total Wallet Balance",
            "skill_rank" to "Skill Tier",
            "claim_task" to "Claim Task",
            "claimed" to "Claimed",
            "payout" to "Payout Amount",
            "offline_status" to "Offline Support Active",
            "complete_offline" to "Complete Task Form",
            "submit_verify" to "Submit for Verification",
            "submitted" to "Verified & Paid",
            "submit_note" to "Enter proofs, e.g. barcode name, coordinates, store labels...",
            "save_offline" to "Save Offline (Queue Local)",
            "sync_payout" to "Sync & Claim Payout",
            "available_gigs" to "Active Marketplace Gigs",
            "wallet" to "Wallet Ledger",
            "cashout" to "Cashout Earnings",
            "refer_earn" to "Referral Program",
            "refer_inst" to "Earn 20 ETB for every active Ethiopian user referred!",
            "referral_code" to "Your Referral Code",
            "invite_friend" to "Invite a Friend",
            "friend_name" to "Friend's Full Name",
            "friend_phone" to "Friend's Phone (09...)",
            "register_referral" to "Register Referral (+ 20 ETB)",
            "copied" to "Referral link copied!",
            "cashout_history" to "Payout History Logs",
            "blueprint" to "Strategic Blueprint",
            "db_spec" to "Database Schema & API",
            "telebirr" to "Telebirr",
            "chapa" to "Chapa",
            "cbe_birr" to "CBE Birr",
            "amount" to "Amount (ETB)",
            "account" to "Phone Number / Account ID",
            "trans_logs" to "Payment API Transaction Logs",
            "cashout_processing" to "Processing disbursement transfer...",
            "cashout_success" to "Disbursement successful!",
            "cashout_error" to "Insufficient balance!",
            "search_task" to "Search gigs...",
            "category" to "Category",
            "premium_upgrade" to "Upgrade to Premium (Double Gigs)!",
            "premium_member" to "Premium Member (VIP)",
            "all_gigs" to "All Gigs",
            "claimed_gigs" to "Claimed",
            "completed_gigs" to "Completed",
            "no_gigs" to "No gigs found.",
            "instructions" to "Instructions",
            "offline_tip" to "This gig requires zero active internet to complete. Syncs up whenever network is found!",
            "submit_success_toast" to "Submission approved successfully! Remittance paid."
        )
        return (if (language == "AMHARIC") amMap[key] else enMap[key]) ?: enMap[key] ?: key
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun GebeyaAppContent(viewModel: GebeyaViewModel) {
    val userProfile by viewModel.userProfile.collectAsStateWithLifecycle()
    val gigs by viewModel.gigs.collectAsStateWithLifecycle()
    val completedTasks by viewModel.completedTasks.collectAsStateWithLifecycle()
    val cashouts by viewModel.cashouts.collectAsStateWithLifecycle()
    val referrals by viewModel.referrals.collectAsStateWithLifecycle()
    val cashoutStatus by viewModel.cashoutStatus.collectAsStateWithLifecycle()
    val cashoutLogs by viewModel.cashoutLogs.collectAsStateWithLifecycle()

    val currentLang = userProfile.language // ENGLISH or AMHARIC
    var activeTab by remember { mutableStateOf("GIGS") } // GIGS, WALLET, REFS, BLUEPRINT, DBSPEC

    val context = LocalContext.current

    Scaffold(
        topBar = {
            Surface(
                color = MaterialTheme.colorScheme.background,
                tonalElevation = 4.dp,
                modifier = Modifier.drawBehind {
                    val strokeWidth = 1.dp.toPx()
                    drawLine(
                        color = Color(0x1F7D8491),
                        start = Offset(0f, size.height - strokeWidth),
                        end = Offset(size.width, size.height - strokeWidth),
                        strokeWidth = strokeWidth
                    )
                }
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 12.dp)
                        .statusBarsPadding(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        // Custom circular Indigo/SiraHub Avatar
                        Box(
                            modifier = Modifier
                                .size(40.dp)
                                .clip(RoundedCornerShape(20.dp))
                                .background(Indigo600),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "S",
                                style = MaterialTheme.typography.titleMedium,
                                color = Color.White,
                                fontWeight = FontWeight.Bold
                            )
                        }
                        Column {
                            Text(
                                text = "SiraHub",
                                style = MaterialTheme.typography.titleLarge,
                                fontWeight = FontWeight.Bold,
                                color = Slate900,
                                letterSpacing = (-0.5).sp
                            )
                            Text(
                                text = "Ethiopia • ኢትዮጵያ",
                                fontSize = 10.sp,
                                fontWeight = FontWeight.Bold,
                                color = Slate500,
                                letterSpacing = 1.sp
                            )
                        }
                    }

                    Row(verticalAlignment = Alignment.CenterVertically) {
                        // Premium VIP Toggle
                        IconButton(
                            onClick = {
                                viewModel.togglePremium()
                                val msg = if (userProfile.premiumUser) "Premium Unlocked" else "Returned to Normal"
                                Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
                            }
                        ) {
                            Icon(
                                imageVector = Icons.Default.Star,
                                contentDescription = "Premium Mode",
                                tint = if (userProfile.premiumUser) GoldAccent else Color.Gray.copy(alpha = 0.6f)
                            )
                        }

                        // Style Toggle button
                        Button(
                            onClick = { viewModel.toggleLanguage() },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Slate100,
                                contentColor = Slate900
                            ),
                            border = BorderStroke(1.dp, Slate200),
                            shape = RoundedCornerShape(20.dp),
                            contentPadding = PaddingValues(horizontal = 12.dp, vertical = 6.dp)
                        ) {
                            Text(
                                text = if (currentLang == "ENGLISH") "አማርኛ" else "ENG",
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }
            }
        },
        bottomBar = {
            Surface(
                color = MaterialTheme.colorScheme.surface,
                tonalElevation = 8.dp,
                modifier = Modifier.navigationBarsPadding()
            ) {
                // Customized dynamic horizontal scroll or row of bottom navigation pills matching fintech style
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 10.dp, horizontal = 8.dp)
                        .horizontalScroll(rememberScrollState()),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    val tabs = listOf(
                        Triple("GIGS", Icons.Default.Work, "Gigs"),
                        Triple("WALLET", Icons.Default.AccountBalanceWallet, "Wallet"),
                        Triple("BLUEPRINT", Icons.Default.MenuBook, "Strategy"),
                        Triple("DBSPEC", Icons.Default.Code, "Schema")
                    )

                    tabs.forEach { (tabKey, icon, label) ->
                        val isSelected = activeTab == tabKey
                        Surface(
                            modifier = Modifier
                                .padding(horizontal = 4.dp)
                                .clip(RoundedCornerShape(12.dp))
                                .clickable { activeTab = tabKey },
                            color = if (isSelected) GreenPrimary.copy(alpha = 0.15f) else Color.Transparent
                        ) {
                            Row(
                                modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(
                                    imageVector = icon,
                                    contentDescription = label,
                                    tint = if (isSelected) GreenPrimary else Color.Gray,
                                    modifier = Modifier.size(20.dp)
                                )
                                Spacer(modifier = Modifier.width(6.dp))
                                Text(
                                    text = if (currentLang == "AMHARIC") {
                                        when (tabKey) {
                                            "GIGS" -> "ገበያ"
                                            "WALLET" -> "ቦርሳ"
                                            "BLUEPRINT" -> "ዕቅድ"
                                            else -> "ኤፒአይ"
                                        }
                                    } else label,
                                    color = if (isSelected) GreenPrimary else Color.Gray,
                                    fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                                    fontSize = 12.sp
                                )
                            }
                        }
                    }
                }
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(MaterialTheme.colorScheme.background)
        ) {
            // Display User overview summary header (fintech styling - Professional Polish Credit/Wallet visual)
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                shape = RoundedCornerShape(24.dp),
                colors = CardDefaults.cardColors(containerColor = Indigo700)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(20.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.Top
                    ) {
                        Column {
                            Text(
                                text = "Current Earnings",
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Medium,
                                color = Color.White.copy(alpha = 0.8f)
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Row(verticalAlignment = Alignment.Bottom) {
                                Text(
                                    text = "${userProfile.balanceEtb}.00",
                                    style = MaterialTheme.typography.headlineLarge,
                                    fontWeight = FontWeight.Black,
                                    color = Color.White
                                )
                                Spacer(modifier = Modifier.width(4.dp))
                                Text(
                                    text = "ETB",
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.Medium,
                                    color = Color.White.copy(alpha = 0.8f)
                                )
                            }
                        }

                        // Glassmorphic chip for Resilience
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(12.dp))
                                .background(Color.White.copy(alpha = 0.2f))
                                .padding(horizontal = 8.dp, vertical = 6.dp)
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(
                                    imageVector = Icons.Default.WifiOff,
                                    contentDescription = "Low Network",
                                    tint = Color.White,
                                    modifier = Modifier.size(14.dp)
                                )
                                Spacer(modifier = Modifier.width(4.dp))
                                Text(
                                    text = "Offline Core Active",
                                    fontSize = 9.sp,
                                    color = Color.White,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(20.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.Default.Star,
                                contentDescription = "Rank",
                                tint = GoldAccent,
                                modifier = Modifier.size(16.dp)
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                text = "${LocalizedText.get("skill_rank", currentLang)}: ${
                                    if (userProfile.premiumUser) "Premium VIP Gold" else userProfile.currentSkillRank
                                }",
                                style = MaterialTheme.typography.bodySmall,
                                color = GoldAccent,
                                fontWeight = FontWeight.Bold
                            )
                        }

                        Text(
                            text = "Referrals counter: ${referrals.size}",
                            fontSize = 11.sp,
                            color = Color.White.copy(alpha = 0.9f),
                            fontWeight = FontWeight.SemiBold
                        )
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    // Dynamic CTA inside the Wallet Card itself matching SiraHub HTML!
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        Button(
                            onClick = { activeTab = "WALLET" },
                            modifier = Modifier.weight(1f),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color.White,
                                contentColor = Indigo700
                            ),
                            shape = RoundedCornerShape(16.dp),
                            contentPadding = PaddingValues(vertical = 12.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.AccountBalanceWallet,
                                contentDescription = null,
                                modifier = Modifier.size(16.dp)
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = "Transfer / Withdraw",
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }

                        Button(
                            onClick = { activeTab = "WALLET" },
                            modifier = Modifier.width(48.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Indigo600,
                                contentColor = Color.White
                            ),
                            shape = RoundedCornerShape(16.dp),
                            contentPadding = PaddingValues(0.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Add,
                                contentDescription = "Quick Action",
                                modifier = Modifier.size(18.dp)
                            )
                        }
                    }
                }
            }

            Divider(color = Color.Gray.copy(alpha = 0.2f), thickness = 1.dp)

            // Primary Tab Content routing
            Box(modifier = Modifier.fillMaxSize()) {
                when (activeTab) {
                    "GIGS" -> GigsTabContent(
                        gigs = gigs,
                        currentLang = currentLang,
                        onClaim = { id -> viewModel.claimGig(id) },
                        onCompleteOffline = { id, note -> viewModel.completeGigOffline(id, note) },
                        onSubmit = { id ->
                            viewModel.submitGig(id)
                            Toast.makeText(context, LocalizedText.get("submit_success_toast", currentLang), Toast.LENGTH_SHORT).show()
                        }
                    )

                    "WALLET" -> WalletTabContent(
                        currentLang = currentLang,
                        balance = userProfile.balanceEtb,
                        referralCode = userProfile.referralCode,
                        referrals = referrals,
                        cashouts = cashouts,
                        cashoutStatus = cashoutStatus,
                        cashoutLogs = cashoutLogs,
                        onCashout = { method, account, amt -> viewModel.simulateCashout(method, account, amt) },
                        onResetCashout = { viewModel.clearCashoutStatus() },
                        onInviteFriend = { name, phone ->
                            viewModel.inviteFriend(name, phone)
                            Toast.makeText(context, "Friend registered! Reward credited.", Toast.LENGTH_SHORT).show()
                        }
                    )

                    "BLUEPRINT" -> BlueprintTabContent(currentLang = currentLang)

                    "DBSPEC" -> DbSpecTabContent(currentLang = currentLang)
                }
            }
        }
    }
}

@Composable
fun GigsTabContent(
    gigs: List<Gig>,
    currentLang: String,
    onClaim: (String) -> Unit,
    onCompleteOffline: (String, String) -> Unit,
    onSubmit: (String) -> Unit
) {
    var searchQuery by remember { mutableStateOf("") }
    val filteredGigs = gigs.filter {
        if (currentLang == "AMHARIC") {
            it.titleAm.contains(searchQuery, ignoreCase = true) || it.categoryAm.contains(searchQuery, ignoreCase = true)
        } else {
            it.titleEn.contains(searchQuery, ignoreCase = true) || it.categoryEn.contains(searchQuery, ignoreCase = true)
        }
    }

    Column(modifier = Modifier.fillMaxSize()) {
        // Search & Filter header
        OutlinedTextField(
            value = searchQuery,
            onValueChange = { searchQuery = it },
            placeholder = { Text(text = LocalizedText.get("search_task", currentLang), color = Color.Gray) },
            leadingIcon = { Icon(imageVector = Icons.Default.Search, contentDescription = "Search", tint = GreenPrimary) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 12.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = GreenPrimary,
                unfocusedBorderColor = Color.Gray.copy(alpha = 0.5f),
                focusedContainerColor = MaterialTheme.colorScheme.surface,
                unfocusedContainerColor = MaterialTheme.colorScheme.surface
            ),
            singleLine = true,
            shape = RoundedCornerShape(12.dp)
        )

        Text(
            text = LocalizedText.get("available_gigs", currentLang),
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.ExtraBold,
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 4.dp),
            color = Slate900
        )

        if (filteredGigs.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(24.dp),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Icon(
                        imageVector = Icons.Default.WifiOff,
                        contentDescription = "No tasks",
                        tint = Color.Gray,
                        modifier = Modifier.size(64.dp)
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    Text(
                        text = LocalizedText.get("no_gigs", currentLang),
                        color = Color.Gray,
                        textAlign = TextAlign.Center
                    )
                }
            }
        } else {
            LazyColumn(
                contentPadding = PaddingValues(bottom = 32.dp)
            ) {
                items(filteredGigs, key = { it.id }) { gig ->
                    GigCard(
                        gig = gig,
                        currentLang = currentLang,
                        onClaim = { onClaim(gig.id) },
                        onCompleteOffline = { note -> onCompleteOffline(gig.id, note) },
                        onSubmit = { onSubmit(gig.id) }
                    )
                }
            }
        }
    }
}

@Composable
fun GigCard(
    gig: Gig,
    currentLang: String,
    onClaim: () -> Unit,
    onCompleteOffline: (String) -> Unit,
    onSubmit: () -> Unit
) {
    var expanded by remember { mutableStateOf(false) }
    var submissionNoteInput by remember { mutableStateOf("") }

    val category = if (currentLang == "AMHARIC") gig.categoryAm else gig.categoryEn
    val title = if (currentLang == "AMHARIC") gig.titleAm else gig.titleEn
    val description = if (currentLang == "AMHARIC") gig.descriptionAm else gig.descriptionEn
    val requirements = if (currentLang == "AMHARIC") gig.requirementsAm else gig.requirementsEn

    val stateColor = when (gig.state) {
        "CLAIMED" -> BlueAccent
        "COMPLETED_OFFLINE" -> Color(0xFFFF9800)
        "SUBMITTED" -> GreenPrimary
        else -> GreenPrimary
    }

    val isAudio = title.lowercase().contains("voice") || title.lowercase().contains("audio") || title.lowercase().contains("record") || category.lowercase().contains("ትርጉም")
    val isDelivery = title.lowercase().contains("pick") || title.lowercase().contains("delivery") || category.lowercase().contains("ትዕዛዝ")
    
    val badgeBg = if (isAudio) Amber50 else if (isDelivery) Sky50 else Indigo50
    val badgeText = if (isAudio) Amber600 else if (isDelivery) Sky600 else Indigo600

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .clickable { expanded = !expanded },
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        border = BorderStroke(1.dp, Slate200),
        shape = RoundedCornerShape(16.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            // Category + Reward Row
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(8.dp))
                        .background(badgeBg)
                        .padding(horizontal = 10.dp, vertical = 5.dp)
                ) {
                    Text(
                        text = category,
                        color = badgeText,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.ExtraBold
                    )
                }

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.MonetizationOn,
                        contentDescription = "Payout",
                        tint = Emerald600,
                        modifier = Modifier.size(18.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = "+${gig.payoutEtb}.00 ETB",
                        fontWeight = FontWeight.ExtraBold,
                        color = Emerald700,
                        fontSize = 14.sp
                    )
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            // Content side by side icon alignment like HTML
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                // Vector Circle Avatar based on Category
                Box(
                    modifier = Modifier
                        .size(44.dp)
                        .clip(RoundedCornerShape(12.dp))
                        .background(badgeBg),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = if (isAudio) Icons.Default.Mic else if (isDelivery) Icons.Default.LocalShipping else Icons.Default.Assignment,
                        contentDescription = null,
                        tint = badgeText,
                        modifier = Modifier.size(22.dp)
                    )
                }

                Column(modifier = Modifier.weight(1f)) {
                    // Title
                    Text(
                        text = title,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = Slate900
                    )
                    if (description.isNotBlank()) {
                        Text(
                            text = description,
                            style = MaterialTheme.typography.bodySmall,
                            color = Slate500,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                }

                Icon(
                    imageVector = Icons.Default.ChevronRight,
                    contentDescription = "Expand",
                    tint = Slate500,
                    modifier = Modifier.size(20.dp)
                )
            }

            Spacer(modifier = Modifier.height(4.dp))

            // Meta tags (minutes, offline support indicator)
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.Settings,
                        contentDescription = "Est Time",
                        tint = Color.Gray,
                        modifier = Modifier.size(12.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(text = "${gig.estMinutes} mins", fontSize = 11.sp, color = Color.Gray)
                }

                if (gig.offlineCompatible) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Default.WifiOff,
                            contentDescription = "Offline capable",
                            tint = GreenPrimary,
                            modifier = Modifier.size(12.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = LocalizedText.get("offline_status", currentLang),
                            fontSize = 11.sp,
                            color = GreenPrimary,
                            fontWeight = FontWeight.Medium
                        )
                    }
                }
            }

            // Expanded details block
            AnimatedVisibility(
                visible = expanded,
                enter = expandVertically() + fadeIn(),
                exit = shrinkVertically() + fadeOut()
            ) {
                Column(
                    modifier = Modifier
                        .padding(top = 16.dp)
                        .fillMaxWidth()
                ) {
                    Divider(color = Color.Gray.copy(alpha = 0.2f), modifier = Modifier.padding(vertical = 8.dp))

                    Text(
                        text = "Description",
                        fontWeight = FontWeight.Bold,
                        color = Indigo600,
                        fontSize = 12.sp
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = description,
                        style = MaterialTheme.typography.bodyMedium,
                        color = Slate600
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    Text(
                        text = LocalizedText.get("instructions", currentLang),
                        fontWeight = FontWeight.Bold,
                        color = Indigo600,
                        fontSize = 12.sp
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = requirements,
                        style = MaterialTheme.typography.bodySmall,
                        color = Slate600
                    )

                    if (gig.offlineCompatible && gig.state == "CLAIMED") {
                        Spacer(modifier = Modifier.height(8.dp))
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(8.dp))
                                .background(GreenPrimary.copy(alpha = 0.1f))
                                .padding(8.dp)
                        ) {
                            Text(
                                text = LocalizedText.get("offline_tip", currentLang),
                                fontSize = 10.sp,
                                color = GreenPrimary,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    // Buttons states dispatcher
                    when (gig.state) {
                        "AVAILABLE" -> {
                            Button(
                                onClick = { onClaim() },
                                modifier = Modifier.fillMaxWidth(),
                                colors = ButtonDefaults.buttonColors(containerColor = GreenPrimary)
                            ) {
                                Icon(imageVector = Icons.Default.CheckCircle, contentDescription = "Claim")
                                Spacer(modifier = Modifier.width(6.dp))
                                Text(
                                    text = LocalizedText.get("claim_task", currentLang),
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        }

                        "CLAIMED" -> {
                            Column(modifier = Modifier.fillMaxWidth()) {
                                OutlinedTextField(
                                    value = submissionNoteInput,
                                    onValueChange = { submissionNoteInput = it },
                                    placeholder = {
                                        Text(
                                            text = LocalizedText.get("submit_note", currentLang),
                                            color = Slate500,
                                            fontSize = 12.sp
                                        )
                                    },
                                    modifier = Modifier.fillMaxWidth(),
                                    colors = OutlinedTextFieldDefaults.colors(
                                        focusedBorderColor = Indigo600,
                                        unfocusedBorderColor = Slate200,
                                        focusedContainerColor = Slate50,
                                        unfocusedContainerColor = Slate50
                                    ),
                                    textStyle = TextStyle(color = Slate900)
                                )
                                Spacer(modifier = Modifier.height(8.dp))
                                Row(modifier = Modifier.fillMaxWidth()) {
                                    Button(
                                        onClick = {
                                            if (submissionNoteInput.isNotBlank()) {
                                                onCompleteOffline(submissionNoteInput)
                                            }
                                        },
                                        modifier = Modifier.weight(1f),
                                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFF9800)),
                                        enabled = submissionNoteInput.isNotBlank()
                                    ) {
                                        Icon(imageVector = Icons.Default.Save, contentDescription = "Save Offline", modifier = Modifier.size(16.dp))
                                        Spacer(modifier = Modifier.width(4.dp))
                                        Text(
                                            text = LocalizedText.get("save_offline", currentLang),
                                            fontSize = 11.sp,
                                            fontWeight = FontWeight.Bold
                                        )
                                    }
                                    Spacer(modifier = Modifier.width(8.dp))
                                    Button(
                                        onClick = { onSubmit() },
                                        modifier = Modifier.weight(1f),
                                        colors = ButtonDefaults.buttonColors(containerColor = GreenPrimary)
                                    ) {
                                        Icon(imageVector = Icons.Default.CloudUpload, contentDescription = "Submit", modifier = Modifier.size(16.dp))
                                        Spacer(modifier = Modifier.width(4.dp))
                                        Text(
                                            text = LocalizedText.get("sync_payout", currentLang),
                                            fontSize = 11.sp,
                                            fontWeight = FontWeight.Bold
                                        )
                                    }
                                }
                            }
                        }

                        "COMPLETED_OFFLINE" -> {
                            Button(
                                onClick = { onSubmit() },
                                modifier = Modifier.fillMaxWidth(),
                                colors = ButtonDefaults.buttonColors(containerColor = GreenPrimary)
                            ) {
                                Icon(imageVector = Icons.Default.CloudUpload, contentDescription = "Sync Payout")
                                Spacer(modifier = Modifier.width(6.dp))
                                Text(
                                    text = LocalizedText.get("sync_payout", currentLang),
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        }

                        "SUBMITTED" -> {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clip(RoundedCornerShape(8.dp))
                                    .background(GreenPrimary.copy(alpha = 0.2f))
                                    .padding(12.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Icon(
                                        imageVector = Icons.Default.Check,
                                        contentDescription = "Paid Indicator",
                                        tint = GreenPrimary
                                    )
                                    Spacer(modifier = Modifier.width(8.dp))
                                    Text(
                                        text = LocalizedText.get("submitted", currentLang),
                                        color = GreenPrimary,
                                        fontWeight = FontWeight.Bold
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun WalletTabContent(
    currentLang: String,
    balance: Int,
    referralCode: String,
    referrals: List<com.example.database.Referral>,
    cashouts: List<com.example.database.Cashout>,
    cashoutStatus: String?,
    cashoutLogs: List<String>,
    onCashout: (String, String, Int) -> Unit,
    onResetCashout: () -> Unit,
    onInviteFriend: (String, String) -> Unit
) {
    var cashoutMethod by remember { mutableStateOf("Telebirr") }
    var payoutAccountInput by remember { mutableStateOf("") }
    var payoutAmountInput by remember { mutableStateOf("") }

    var inviteNameInput by remember { mutableStateOf("") }
    var invitePhoneInput by remember { mutableStateOf("") }

    val clipboardManager = LocalClipboardManager.current
    val context = LocalContext.current

    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(scrollState)
    ) {
        // Debit card style visual
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(180.dp)
                .clip(RoundedCornerShape(16.dp))
                .background(
                    Brush.linearGradient(
                        colors = listOf(Color(0xFF0D47A1), Color(0xFF00C853)),
                        start = Offset(0f, 0f),
                        end = Offset(1000f, 1000f)
                    )
                )
                .padding(20.dp)
        ) {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "GebeyaGigs Pay Card",
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        fontSize = 14.sp
                    )
                    Text(
                        text = "ETBR-Wallet",
                        color = GoldAccent,
                        fontWeight = FontWeight.Black,
                        fontSize = 12.sp
                    )
                }

                Text(
                    text = "$balance.00 ETB",
                    fontSize = 32.sp,
                    color = Color.White,
                    fontWeight = FontWeight.Black,
                    letterSpacing = 1.sp
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text(text = "HAYYU RAGEA", color = Color.White.copy(alpha = 0.8f), fontSize = 11.sp)
                        Text(text = "ACTIVE USER", color = Color.White.copy(alpha = 0.6f), fontSize = 9.sp)
                    }
                    Box(
                        modifier = Modifier
                            .size(32.dp)
                            .clip(RoundedCornerShape(6.dp))
                            .background(Color.White.copy(alpha = 0.2f))
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        // Payout form
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            border = BorderStroke(1.dp, Slate200),
            shape = RoundedCornerShape(16.dp)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = LocalizedText.get("cashout", currentLang),
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = Slate900
                )

                Spacer(modifier = Modifier.height(12.dp))

                // Method Pills switcher
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    val methods = listOf("Telebirr", "Chapa", "CBE Birr")
                    methods.forEach { m ->
                        val selected = cashoutMethod == m
                        val col = when (m) {
                            "Telebirr" -> Indigo600
                            "Chapa" -> Sky600
                            else -> Color(0xFF00AAFF)
                        }
                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .clip(RoundedCornerShape(8.dp))
                                .background(if (selected) col.copy(alpha = 0.15f) else Slate50)
                                .border(1.dp, if (selected) col else Slate200, RoundedCornerShape(8.dp))
                                .clickable { cashoutMethod = m }
                                .padding(vertical = 10.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = LocalizedText.get(
                                    when (m) {
                                        "Telebirr" -> "telebirr"
                                        "Chapa" -> "chapa"
                                        else -> "cbe_birr"
                                    }, currentLang
                                ),
                                fontSize = 12.sp,
                                color = if (selected) col else Slate600,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Input fields accounts
                OutlinedTextField(
                    value = payoutAccountInput,
                    onValueChange = { payoutAccountInput = it },
                    label = { Text(text = LocalizedText.get("account", currentLang)) },
                    modifier = Modifier.fillMaxWidth(),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = Indigo600,
                        unfocusedBorderColor = Slate200,
                        focusedContainerColor = Slate50,
                        unfocusedContainerColor = Slate50,
                        focusedLabelColor = Indigo600,
                        unfocusedLabelColor = Slate600
                    ),
                    textStyle = TextStyle(color = Slate900),
                    singleLine = true
                )

                Spacer(modifier = Modifier.height(12.dp))

                OutlinedTextField(
                    value = payoutAmountInput,
                    onValueChange = { payoutAmountInput = it },
                    label = { Text(text = LocalizedText.get("amount", currentLang)) },
                    modifier = Modifier.fillMaxWidth(),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = Indigo600,
                        unfocusedBorderColor = Slate200,
                        focusedContainerColor = Slate50,
                        unfocusedContainerColor = Slate50,
                        focusedLabelColor = Indigo600,
                        unfocusedLabelColor = Slate600
                    ),
                    textStyle = TextStyle(color = Slate900),
                    singleLine = true
                )

                Spacer(modifier = Modifier.height(16.dp))

                if (cashoutStatus == null) {
                    Button(
                        onClick = {
                            val amt = payoutAmountInput.toIntOrNull() ?: 0
                            if (amt > 0 && payoutAccountInput.isNotBlank()) {
                                onCashout(cashoutMethod, payoutAccountInput, amt)
                            }
                        },
                        modifier = Modifier.fillMaxWidth(),
                        colors = ButtonDefaults.buttonColors(containerColor = GreenPrimary),
                        enabled = payoutAccountInput.isNotBlank() && payoutAmountInput.isNotBlank()
                    ) {
                        Text(
                            text = "${LocalizedText.get("cashout", currentLang)} (${cashoutMethod})",
                            fontWeight = FontWeight.Bold
                        )
                    }
                } else {
                    // Simulated transaction console window
                    TerminalConsoleOverlay(
                        status = cashoutStatus!!,
                        logs = cashoutLogs,
                        onClose = { onResetCashout() }
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        // Program invitation referrals card - Styled in gorgeous Emerald Green from requested "Professional Polish" spec!
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = Emerald50),
            border = BorderStroke(1.dp, Emerald100),
            shape = RoundedCornerShape(16.dp)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = LocalizedText.get("refer_earn", currentLang),
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = Emerald700
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = LocalizedText.get("refer_inst", currentLang),
                    style = MaterialTheme.typography.bodySmall,
                    color = Emerald700.copy(alpha = 0.8f)
                )

                Spacer(modifier = Modifier.height(12.dp))

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(12.dp))
                        .background(Color.White)
                        .border(1.dp, Emerald100, RoundedCornerShape(12.dp))
                        .padding(12.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text(text = LocalizedText.get("referral_code", currentLang), fontSize = 10.sp, color = Slate500)
                            Text(text = referralCode, fontWeight = FontWeight.Black, color = Emerald600, fontSize = 18.sp)
                        }

                        IconButton(
                            onClick = {
                                clipboardManager.setText(AnnotatedString(referralCode))
                                Toast.makeText(context, LocalizedText.get("copied", currentLang), Toast.LENGTH_SHORT).show()
                            }
                        ) {
                            Icon(imageVector = Icons.Default.ContentCopy, contentDescription = "Copy", tint = Emerald600)
                        }
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Sub form friends registers
                Text(
                    text = LocalizedText.get("invite_friend", currentLang),
                    style = MaterialTheme.typography.bodySmall,
                    fontWeight = FontWeight.Bold,
                    color = Emerald700
                )
                Spacer(modifier = Modifier.height(8.dp))

                OutlinedTextField(
                    value = inviteNameInput,
                    onValueChange = { inviteNameInput = it },
                    placeholder = { Text(text = LocalizedText.get("friend_name", currentLang), color = Slate500, fontSize = 12.sp) },
                    modifier = Modifier.fillMaxWidth(),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = Emerald600,
                        unfocusedBorderColor = Emerald100,
                        focusedContainerColor = Color.White,
                        unfocusedContainerColor = Color.White
                    ),
                    textStyle = TextStyle(color = Slate900),
                    singleLine = true
                )

                Spacer(modifier = Modifier.height(8.dp))

                OutlinedTextField(
                    value = invitePhoneInput,
                    onValueChange = { invitePhoneInput = it },
                    placeholder = { Text(text = LocalizedText.get("friend_phone", currentLang), color = Slate500, fontSize = 12.sp) },
                    modifier = Modifier.fillMaxWidth(),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = Emerald600,
                        unfocusedBorderColor = Emerald100,
                        focusedContainerColor = Color.White,
                        unfocusedContainerColor = Color.White
                    ),
                    textStyle = TextStyle(color = Slate900),
                    singleLine = true
                )

                Spacer(modifier = Modifier.height(12.dp))

                Button(
                    onClick = {
                        if (inviteNameInput.isNotBlank() && invitePhoneInput.isNotBlank()) {
                            onInviteFriend(inviteNameInput, invitePhoneInput)
                            inviteNameInput = ""
                            invitePhoneInput = ""
                        }
                    },
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(containerColor = Emerald600, contentColor = Color.White),
                    enabled = inviteNameInput.isNotBlank() && invitePhoneInput.isNotBlank()
                ) {
                    Text(text = LocalizedText.get("register_referral", currentLang), fontWeight = FontWeight.Bold, fontSize = 12.sp)
                }
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        // Historical lists cashout
        Text(
            text = LocalizedText.get("cashout_history", currentLang),
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(vertical = 8.dp),
            color = Slate900
        )

        if (cashouts.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(text = "No cashout records found in local Room Database.", color = Slate500, fontSize = 12.sp)
            }
        } else {
            cashouts.forEach { c ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    border = BorderStroke(1.dp, Slate200)
                ) {
                    Row(
                        modifier = Modifier.padding(12.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text(text = "${c.method} cashout", fontWeight = FontWeight.Bold, color = Slate900)
                            Text(text = c.transactionRef, fontSize = 10.sp, color = Slate500, fontFamily = FontFamily.Monospace)
                        }

                        Column(horizontalAlignment = Alignment.End) {
                            Text(text = "-${c.amountEtb}.00 ETB", fontWeight = FontWeight.Black, color = Color(0xFFD32F2F))
                            Text(text = c.status, fontSize = 10.sp, color = Emerald600, fontWeight = FontWeight.Bold)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun TerminalConsoleOverlay(
    status: String,
    logs: List<String>,
    onClose: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(8.dp))
            .background(Color.Black)
            .border(1.dp, GreenPrimary.copy(alpha = 0.3f), RoundedCornerShape(8.dp))
            .padding(12.dp)
    ) {
        Column {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = "API Sandbox Terminal", color = GreenPrimary, fontSize = 11.sp, fontWeight = FontWeight.Bold, fontFamily = FontFamily.Monospace)
                if (status == "SUCCESS" || status == "ERROR_INSUFFICIENT") {
                    IconButton(onClick = onClose, modifier = Modifier.size(16.dp)) {
                        Icon(imageVector = Icons.Default.Close, contentDescription = "Close", tint = Color.Red, modifier = Modifier.size(12.dp))
                    }
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Loading indicator loop simulations
            if (status == "PROCESSING") {
                LinearProgressIndicator(color = GreenPrimary, modifier = Modifier.fillMaxWidth())
                Spacer(modifier = Modifier.height(8.dp))
            }

            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(140.dp)
            ) {
                items(logs) { log ->
                    Text(
                        text = log,
                        color = if (log.startsWith("Error")) Color.Red else Color.Green,
                        fontFamily = FontFamily.Monospace,
                        fontSize = 10.sp,
                        modifier = Modifier.padding(vertical = 2.dp)
                    )
                }
            }
        }
    }
}

@Composable
fun BlueprintTabContent(currentLang: String) {
    val blueprints = listOf(
        Triple(
            "1. Market Solution & Opportunity (ኢትዮጵያ ገበያ ፍላጎት)",
            Icons.Default.Groups,
            "• **Problem Statement:** youth unemployment in Ethiopia is exceptionally high (~24% under age 25). Modern smartphones are ubiquitous, but gig infrastructure with localized instant payouts is absent.\n\n• **Strategic Solution:** GebeyaGigs creates a lightweight, high-utility gateway matching small B2B digital needs (like listing audits, map verifications, speech training, or user translations) with local students and job hunters.\n\n• **Target Users:** public university students, young retail operators, map curators, and local translation agents in cities like Addis Ababa, Hawassa, Adama, & Bahir Dar."
        ),
        Triple(
            "2. Monetization Matrix (የገቢ ማስገኛ ስልቶች)",
            Icons.Default.MonetizationOn,
            "• **15% Enterprise Commission:** Charge B2B companies (e.g. Fintech founders, Mapping agencies, Telecom suppliers) a 15% commission fee over and above the ETB task value for sourcing quality crowdsourced data.\n\n• **B2B Analytics API access:** Subscribed local logistics, real-estate, and financial firms pay recurrent monthly access to verified geo-points listings dashboards.\n\n• **Premium VIP Onboarding:** Normal task limit capped. Regular taskers pay a 50 ETB monthly VIP status to unlock top-tier reward audits and immediate CBE-disbursements integrations without queues.\n\n• **Regional Native Banner Adverts:** Local brands run surveys and micro-research within the feeds."
        ),
        Triple(
            "3. Low-Speed & Offline Network Resilience (ኢንተርኔት ግንኙነት መፍትሄ)",
            Icons.Default.WifiOff,
            "• **SQLite Caching Framework:** Whole list tasks, profiles, data are saved locally using Room SQLite database. The worker loads pages instantaneously even. with 0 bytes internet.\n\n• **Compressed Payload Buffers:** Compressed submission assets (e.g., photo proofs reduced to 50KB directly, low bitrate speech recorded directly in AAC format) to avoid network chokes on 2G/3G networks.\n\n• **Smart SMS Gateway Callback:** If cellular GPRS internet drops flat, the application serializes task proofs into encrypted base64 short texts and sends via standard cellular SMS to an automated premium gate (e.g., SMS Gateway 8044), allowing workers to confirm offline submissions instantly!"
        ),
        Triple(
            "4. Development Roadmap Phases (የስራ እቅድ እድገት ደረጃዎች)",
            Icons.Default.TrendingUp,
            "• **Phase 1 (Months 1-2) Sandbox Alpha Core:** Build Jetpack Compose UI with Room storage. Emulate CBE Birr, Telebirr, and Chapa payouts. Verify basic user retention loops.\n\n• **Phase 2 (Months 3-4) Firebase Dynamic Integration:** Hook up Firestore, Firebase Auth for verified phone sign-ups, and Cloud Storage for compressed uploads. Develop a Telegram mini-app wrapper to acquire low-data gamers.\n\n• **Phase 3 (Month 5) Native African Gateway Certification:** Interface production Chapa API backend payloads (`POST /v1/transfers`) and get certified for live CBE and Telebirr corporate disbursement wallets.\n\n• **Phase 4 (Month 6+) National Rollout and Expansion:** Deploy campus-ambassador reward loops and form strategic gig syndicates in university groups."
        ),
        Triple(
            "5. Make first $1,000 & Scale to 100K Users (የመጀመሪያ $1,000 እና 100ሺህ ተጠቃሚዎች)",
            Icons.Default.EmojiEvents,
            "• **Direct Path to first $1,000 Revenue:** Partner with 4 local brands in Addis needing verified listings (e.g., restaurants carrying QR signs, branch hours audits, or price indexes in Merkato). Charge businesses 100 ETB (~$0.80) per check. Deploy task rewards to youth at 55 ETB. Run 2,500 tasks, earning $2,000 billing, payout $1,100, leaving a net $900 profit + introductory premium subscriptions.\n\n• **Scaling to 100,000 Workers:** Leverage Telegram viral loops where referrals translate to instant cash balances (+20 ETB). Partner with local tech groups, university student bodies, and gamified leaderboards offering premium CBE rewards."
        )
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState())
    ) {
        Text(
            text = LocalizedText.get("blueprint", currentLang),
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold,
            color = Slate900
        )
        Text(
            text = "Strategic Blueprint Masterclass",
            style = MaterialTheme.typography.bodySmall,
            color = Indigo600
        )

        Spacer(modifier = Modifier.height(16.dp))

        blueprints.forEach { (title, icon, body) ->
            var expandedCollapse by remember { mutableStateOf(false) }
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 6.dp)
                    .clickable { expandedCollapse = !expandedCollapse },
                border = BorderStroke(1.dp, if (expandedCollapse) Indigo600.copy(alpha = 0.5f) else Slate200),
                colors = CardDefaults.cardColors(containerColor = Color.White)
            ) {
                Column(modifier = Modifier.padding(14.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(modifier = Modifier.weight(1f), verticalAlignment = Alignment.CenterVertically) {
                            Icon(imageVector = icon, contentDescription = null, tint = Indigo600, modifier = Modifier.size(20.dp))
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(text = title, fontWeight = FontWeight.Bold, fontSize = 13.sp, color = Slate900)
                        }
                        Icon(
                            imageVector = if (expandedCollapse) Icons.Default.KeyboardArrowUp else Icons.Default.KeyboardArrowDown,
                            contentDescription = null,
                            tint = Slate500,
                            modifier = Modifier.size(18.dp)
                        )
                    }

                    AnimatedVisibility(visible = expandedCollapse) {
                        Column {
                            Spacer(modifier = Modifier.height(10.dp))
                            Text(
                                  text = body,
                                  style = MaterialTheme.typography.bodyMedium,
                                  color = Slate600,
                                  lineHeight = 18.sp
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun DbSpecTabContent(currentLang: String) {
    val schemaDocs = listOf(
        Triple(
            "Firebase Firestore Collections Schema",
            "Cloud database structure with JSON mappings",
            "1. **users/{userId}** (Security Rule: Authenticated read/write)\n```json\n{\n  \"userId\": \"UIDstring\",\n  \"name\": \"Full Name\",\n  \"phone\": \"0913529010\",\n  \"balanceEtb\": 450,\n  \"tier\": \"Bronze\",\n  \"premiumActive\": false,\n  \"invitedBy\": \"INV-CODE-12\"\n}\n```\n\n2. **gigs/{gigId}** (Security Rule: Public read, Admin write)\n```json\n{\n  \"gigId\": \"GIG-003\",\n  \"payout\": 75,\n  \"titleEn\": \"Bole Area QR Audit\",\n  \"status\": \"AVAILABLE\",\n  \"latitude\": 9.022,\n  \"longitude\": 38.746\n}\n```\n\n3. **completed_tasks/{taskId}**\n```json\n{\n  \"taskId\": \"AUTO-GEN-ID\",\n  \"gigId\": \"GIG-003\",\n  \"workerId\": \"UIDstring\",\n  \"submissionNote\": \"Tomoca Coffee has Chapa\",\n  \"payoutEarned\": 75,\n  \"timestamp\": 1779283920\n}\n```"
        ),
        Triple(
            "Chapa Payout REST API Endpoints",
            "Integration payload definitions for payout cashouts",
            "**INITIATING PAYOUT DISBURSEMENT**\n• Endpoint: `POST https://api.chapa.co/v1/transfers` \n• Headers:\n  - `Authorization: Bearer CHAPA-SECRET-KEY` \n  - `Content-Type: application/json` \n• Request Payload:\n```json\n{\n  \"account_name\": \"Hayyu Ragea\",\n  \"account_number\": \"0913529010\",\n  \"amount\": 350,\n  \"currency\": \"ETB\",\n  \"reference\": \"TRANS-HAYYU-2026-X\",\n  \"bank_code\": \"telebirr\"\n}\n```\n\n• Webhook Response Validation (HMAC SHA-256 Verification Signature):\n`Chapa-Signature: hash_code_matching_secret_secret`"
        ),
        Triple(
            "Local SQLite Cache (Room Database Model)",
            "Offline-first mobile storage optimization schemas",
            "• **Entity UserProfile:** Saved directly inside Room. Allows reading balances instantly without downloading any bytes on launching.\n\n• **Entity OfflineTaskQueue:** If submission fails due to network drop, task stays in SQLite table with marker `isSynced = false`. A background worker (WorkManager) periodically sweeps table and re-attempts Firestore sync whenever signal is restored.\n\n• **Zero Dead-End UI:** Caches historical payout reference strings directly in sqlite so workers can show valid transaction proofs to CBE bank operators if queries occur."
        )
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState())
    ) {
        Text(
            text = LocalizedText.get("db_spec", currentLang),
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold,
            color = Slate900
        )
        Text(
            text = "Firebase Collections & Payment Gateway Schemas",
            style = MaterialTheme.typography.bodySmall,
            color = Indigo600
        )

        Spacer(modifier = Modifier.height(16.dp))

        schemaDocs.forEach { (title, subtitle, documentation) ->
            var expandedDocs by remember { mutableStateOf(false) }
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 6.dp)
                    .clickable { expandedDocs = !expandedDocs },
                border = BorderStroke(1.dp, if (expandedDocs) Indigo600.copy(alpha = 0.5f) else Slate200),
                colors = CardDefaults.cardColors(containerColor = Color.White)
            ) {
                Column(modifier = Modifier.padding(14.dp)) {
                    Text(text = title, fontWeight = FontWeight.Bold, fontSize = 13.sp, color = Indigo600)
                    Text(text = subtitle, fontSize = 10.sp, color = Slate500)

                    AnimatedVisibility(visible = expandedDocs) {
                        Column {
                            Spacer(modifier = Modifier.height(12.dp))
                            Text(
                                text = documentation,
                                style = MaterialTheme.typography.bodySmall,
                                fontFamily = FontFamily.Monospace,
                                color = Slate100,
                                modifier = Modifier
                                    .clip(RoundedCornerShape(8.dp))
                                    .background(Slate900)
                                    .padding(10.dp)
                            )
                        }
                    }
                }
            }
        }
    }
}

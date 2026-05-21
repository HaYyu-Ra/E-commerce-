package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.room.Room
import com.example.database.GebeyaDatabase
import com.example.database.GebeyaRepository
import com.example.ui.GebeyaAppContent
import com.example.ui.GebeyaViewModel
import com.example.ui.GebeyaViewModelFactory
import com.example.ui.theme.MyApplicationTheme

class MainActivity : ComponentActivity() {

    private val db by lazy {
        Room.databaseBuilder(
            applicationContext,
            GebeyaDatabase::class.java,
            "gebeya_gigs_db"
        ).fallbackToDestructiveMigration().build()
    }

    private val repository by lazy {
        GebeyaRepository(db.gebeyaDao())
    }

    private val viewModel: GebeyaViewModel by viewModels {
        GebeyaViewModelFactory(repository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyApplicationTheme {
                GebeyaAppContent(viewModel = viewModel)
            }
        }
    }
}

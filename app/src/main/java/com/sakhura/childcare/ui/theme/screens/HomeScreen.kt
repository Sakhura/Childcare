package com.sakhura.childcare.ui.theme.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.sakhura.childcare.presentation.viewmodel.HomeViewModel
import com.sakhura.childcare.ui.components.ActiveSessionCard
import com.sakhura.childcare.ui.components.ChildCard

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    navController: NavController,
    viewModel: HomeViewModel = hiltViewModel()
) {
    val children by viewModel.children.collectAsState(initial = emptyList())
    val activeSession by viewModel.activeSession.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Childcare App") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary
                )
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { navController.navigate("add_child") }
            ) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "Add Child")
            }
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            activeSession?.let { session ->
                item {
                    ActiveSessionCard(
                        session = session,
                        onEndSession = { viewModel.endCareSession() }
                    )
                }
            }

            items(children) { child ->
                ChildCard(
                    child = child,
                    onClick = { navController.navigate("child_detail/${child.id}") },
                    onStartCare = { viewModel.startCareSession(child.id) }
                )
            }
        }
    }
}
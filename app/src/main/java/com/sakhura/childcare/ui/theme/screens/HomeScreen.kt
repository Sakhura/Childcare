package com.sakhura.childcare.ui.theme.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    navController: NavController,
    viewModel: HomeViewModel = hiltViewModel()
){
    val children by viewModel.children.collectAsState(initial = emptyList())
        val activeSession by viewModel.activeSession.collectAsState(initial = null)

    Scaffold (
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
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            activeSession?.let {
                item {
                    ActiveSessionCard(session = it)
                }
            }
            items(children) { child ->
                ChildCard(
                    child = child
                    onClick = { navController.navigate("child_detail/${child.id}") }
                    onStarCare = { viewModel.startCareSession(child.id) }
                )
            }
        }

    }
}

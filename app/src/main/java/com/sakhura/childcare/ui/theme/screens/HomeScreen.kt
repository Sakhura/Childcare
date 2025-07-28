package com.sakhura.childcare.ui.theme.screens

import androidx.compose.material3.Scaffold

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    navController: NavController,
    viewModel: ChildcareViewModel()
){
    val children by viewModel.children.collectAsState(initial = emptyList()
        val activeSession by viewModel.activeSession.collectAsState(initial = null)

    Scaffold (
        topBar = {
            TopAppBar(
                title = { Text(text = "Childcare App") },
                colors = TopAppBarDefaults.smallTopAppBarColors(
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
                .fillMaxSize()
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
        )
}
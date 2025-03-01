package es.infolojo.newkeepitdroid.ui.screens.home

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import es.infolojo.newkeepitdroid.R
import es.infolojo.newkeepitdroid.ui.activities.main.events.MainEvents
import es.infolojo.newkeepitdroid.utils.getSize

private const val PREVIEW_NOTES_SIZE = 8

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    viewModel: HomeScreenViewModel? = hiltViewModel(),
    isPreview: Boolean = false,
    mainEvents: ((MainEvents) -> Unit) = {},
    navHostController: NavHostController? = null
) {
    // init viewModel
    viewModel?.init(mainEvents = mainEvents, navController = navHostController)

    // observe notes states
    val notes = viewModel?.notes?.collectAsState(initial = emptyList())
    Scaffold(
        topBar = {
            // appBar preparada para incluir títulos he iconos
            TopAppBar(
                // shadow similar a elevaion en xml
                modifier = Modifier
                    .fillMaxWidth()
                    .shadow(2.dp),
                title = {
                    Text(text = stringResource(R.string.my_notes), fontWeight = FontWeight.SemiBold)
                },

                // aquí se incluye la parte de los iconos
                actions = {
                    IconButton(onClick = {
                        viewModel?.openSearchScreen()
                    }) {
                        Icon(
                            imageVector = Icons.Default.Search,
                            contentDescription = stringResource(R.string.search_your_note)
                        )
                    }
                    Spacer(modifier = Modifier.width(8.dp))

                    // DropDown component
                    IconButton(onClick = { /*TODO*/ }) {
                        Icon(
                            painterResource(R.drawable.baseline_filter_list_24),
                            contentDescription = stringResource(R.string.filter_by)
                        )
                    }
                    DropdownMenu(
                        expanded = false,
                        onDismissRequest = { /*TODO*/ }
                    ) {
                        DropdownMenuItem(
                            text = {
                                Text(text = stringResource(R.string.creation_date_filter))
                            },
                            onClick = { /*TODO*/ }
                        )
                        DropdownMenuItem(
                            text = {
                                Text(text = stringResource(R.string.ascend_filter))
                            },
                            onClick = { /*TODO*/ }
                        )
                        DropdownMenuItem(
                            text = {
                                Text(text = stringResource(R.string.descend_filter))
                            },
                            onClick = { /*TODO*/ }
                        )
                    }
                }
            )
        },
        // floating button to add a new note
        floatingActionButton = {
            FloatingActionButton(
                shape = CircleShape,
                onClick = {
                    viewModel?.openAddScreen()
                }
            ) {
                Icon(
                    painter = painterResource(R.drawable.baseline_add_circle_outline_24),
                    contentDescription = stringResource(R.string.add_a_new_note)
                )
            }
        },
        bottomBar = { /*here will be the add*/ }
    ) { innerPadding ->
        // Contenido de la pantalla con contenedor columna y el contenido ajustado al centro
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(innerPadding),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // RecyclerView
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        start = 16.dp,
                        end = 16.dp,
                        bottom = 8.dp,
                        top = 8.dp
                    ),
                // contenido centrado
                horizontalAlignment = Alignment.CenterHorizontally,
                // esoacio entre items de 8dps
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(
                    if (isPreview) {
                        PREVIEW_NOTES_SIZE
                    } else {
                        notes?.value.getSize()
                    }
                ) {
                    if (isPreview) ItemNote() else {
                        notes?.value?.getOrNull(it)?.let { noteVO ->
                            ItemNote(
                                noteVO = noteVO,
                                events = viewModel::manageHomeScreenGridEvents
                            )
                        }
                    }
                }
                // para hacer un clipToPadding false
                item {
                    Box(modifier = Modifier.size(80.dp))
                }
            }

            // Dialog alert to remove a note
            AnimatedVisibility(visible = viewModel?.showAlertToRemove?.value == true) {
                val noteTitle = viewModel?.noteToRemove?.title.orEmpty()
                AlertDialog(
                    text = {
                        val text = "$noteTitle ${stringResource(R.string.will_be_removed)}"
                        Text(text = text)
                    },
                    title = {
                        Text(
                            text = "${stringResource(R.string.shure_to_remove)} $noteTitle ?",
                            fontWeight = FontWeight.Bold
                        )
                    },
                    onDismissRequest = {
                        viewModel?.restartRemoveStates()
                    },
                    confirmButton = {
                        Text(
                            text = stringResource(R.string.yes),
                            fontWeight = FontWeight.Normal,
                            modifier = Modifier.clickable(enabled = true) {
                                viewModel?.removeSelectedNote()
                            }
                        )
                    },
                    dismissButton = {
                        Text(
                            text = stringResource(R.string.no),
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.clickable {
                                viewModel?.restartRemoveStates()
                            }
                        )
                    }
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    HomeScreen(
        viewModel = null,
        isPreview = true
    )
}
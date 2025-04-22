package es.infolojo.newkeepitdroid.ui.screens.home

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.rememberLazyStaggeredGridState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Search
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
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import es.infolojo.newkeepitdroid.R
import es.infolojo.newkeepitdroid.ui.activities.main.events.MainEvents
import es.infolojo.newkeepitdroid.ui.screens.commons.RegularAlertDialogComponent
import es.infolojo.newkeepitdroid.utils.getSize

private const val PREVIEW_NOTES_SIZE = 8

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
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
    // needed to manage number of columns. In the other way with simple lazy list is ok
    val lazyListState = rememberLazyStaggeredGridState()

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

                // Here goes icons
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

                    // Text that works as a button to show current view.
                    Text(
                        text = buildAnnotatedString {
                            withStyle(
                                style = SpanStyle(
                                    fontWeight = FontWeight.Normal
                                )
                            ) {
                                append(stringResource(R.string.view))
                            }
                            append(" ")
                            append("${viewModel?.numberOfColumns?.intValue ?: 1}")
                        },
                        fontWeight = FontWeight.Bold,
                        maxLines = 1,
                        textAlign = TextAlign.Start,
                        modifier = Modifier
                            .align(Alignment.CenterVertically)
                            .combinedClickable(
                                role = Role.Button
                            ) {
                                viewModel?.changeNumberOfColumns()
                            }
                    )
                    Spacer(modifier = Modifier.width(8.dp))

                    // DropDown component (when click update dropMenuExpanded state)
                    IconButton(onClick = { viewModel?.clickInDropMenuIcon() }) {
                        Icon(
                            painterResource(R.drawable.baseline_filter_list_24),
                            contentDescription = stringResource(R.string.filter_by)
                        )
                    }
                    DropdownMenu(
                        expanded = viewModel?.dropMenuExpanded?.value == true,
                        onDismissRequest = { viewModel?.restartDropMenuExpanded() }
                    ) {
                        DropdownMenuItem(
                            text = {
                                Text(text = stringResource(R.string.creation_date_filter))
                            },
                            onClick = {
                                viewModel?.filterByDate()
                            }
                        )
                        DropdownMenuItem(
                            text = {
                                Text(text = stringResource(R.string.ascend_filter))
                            },
                            onClick = {
                                viewModel?.filterByTitleAscend()
                            }
                        )
                        DropdownMenuItem(
                            text = {
                                Text(text = stringResource(R.string.descend_filter))
                            },
                            onClick = {
                                viewModel?.filterByTitleDescend()
                            }
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
                    imageVector = Icons.Default.Add,
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
            LazyVerticalStaggeredGrid(
                columns = StaggeredGridCells.Fixed(viewModel?.numberOfColumns?.intValue ?: 1),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        start = 16.dp,
                        end = 16.dp,
                        bottom = 8.dp,
                        top = 8.dp
                    ),
                state = lazyListState,
                horizontalArrangement = if ((viewModel?.numberOfColumns?.intValue ?: 1) == 1) {
                    Arrangement.Center
                } else {
                    Arrangement.spacedBy(8.dp)
                },
                verticalItemSpacing = 8.dp,
                content = {

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
                    // to do a clipToPadding false
                    item {
                        Box(modifier = Modifier.size(80.dp))
                    }
                }
            )

            /**
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
                // content centered
                horizontalAlignment = Alignment.CenterHorizontally,
                // space between items of 8.dp
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
            */

            // Dialog alert to remove a note
            RegularAlertDialogComponent(
                title = "${viewModel?.noteToRemove?.title.orEmpty()} ${stringResource(R.string.will_be_removed).lowercase()}",
                text = "${stringResource(R.string.shure_to_remove)} ${viewModel?.noteToRemove?.title.orEmpty()} ?",
                onConfirm = {
                    viewModel?.removeSelectedNote()
                },
                onDismiss = {
                    viewModel?.restartRemoveStates()
                },
                enabled = viewModel?.showAlertToRemove?.value == true
            )
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

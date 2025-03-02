package es.infolojo.newkeepitdroid.ui.screens.update

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import es.infolojo.newkeepitdroid.R
import es.infolojo.newkeepitdroid.ui.activities.main.events.MainEvents

@Composable
fun UpdateScreen(
    modifier: Modifier = Modifier,
    isPreview: Boolean = false,
    viewModel: UpdateScreenViewModel? = hiltViewModel(),
    mainEvents: (MainEvents) -> Unit = {},
    noteId: Long
) {
    // launch the viewModel
    viewModel?.init(noteId, mainEvents)

    // Scaffold -> common structure for normal screen
    Scaffold(
        modifier = modifier.fillMaxWidth(),
        // Inside it we can add a regular topBar that we will use to include back and save buttons
        topBar = {
            // IconButton can be used as a clickable element with the icon as a composable param
            // that will include the real icon inside. In this case we use it to save.
            Row {
                IconButton(
                    onClick = { }
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = stringResource(R.string.back_arrow),
                    )
                }

                // Space between first and second Icons. with weight 1 we will take all the space
                // and have the icons at the ends of the screens.
                Spacer(modifier = Modifier.weight(1f))

                // Same of the previous IconButton. But in this case it is to update
                IconButton(
                    onClick = {
                        viewModel?.updateNote()
                    },
                    enabled = viewModel?.updatedValidated == true
                ) {
                    Icon(
                        painterResource(R.drawable.baseline_update_24),
                        contentDescription = stringResource(R.string.save_icon)
                    )
                }
            }
        }
    ) { paddingValues ->
        // Scaffold (structure) returns always the paddings to adjust to the screen size and not to the edge to edge
        // Inside this real content we will use a column that occupies the full width of the screen
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(paddingValues)
        ) {

            // Inside it this is another column with a margin of 16 dp and space between elements of 8dp
            Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                // region calendar (here we will group all the calendar info (mouth, day of the mouth, year and day of the week))
                Text(
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                    // for the date we will use buildAnnotatedString that gives more intense control for test
                    text = buildAnnotatedString {
                        withStyle(
                            style = SpanStyle(
                                color = MaterialTheme.colorScheme.primary
                            )
                        ) {
                            append(
                                viewModel?.takeIf {
                                    !isPreview
                                }?.dateModel?.month?.monthName ?: stringResource(R.string.month_name_preview)
                            )
                        }
                        append(" ")
                        append(
                            viewModel?.takeIf {
                                !isPreview
                            }?.dateModel?.dayOfMonth ?: stringResource(R.string.day_of_month_preview)
                        )
                    }
                )

                // for year and day with a simple text is enough.
                val yearAndDayTest: String = viewModel?.takeIf { !isPreview }?.let {
                    "${it.dateModel.currentYear} ${it.dateModel.dayOfWeek.dayName}"
                } ?: "${stringResource(R.string.year_preview)} ${stringResource(R.string.day_of_week_preview)}"

                Text(text = yearAndDayTest)
                // endregion calendar
            }

            // Line with horizontal to
            HorizontalDivider(modifier = Modifier.height(2.dp))

            // region nota
            // Notes goes with his own column to group the content with more margins
            Column(modifier = Modifier.fillMaxWidth().padding(16.dp)) {
                // To include a editable text field we will use a TextField
                TextField(
                    modifier = Modifier.fillMaxWidth(),
                    colors = TextFieldDefaults.colors(
                        disabledTextColor = Color.Transparent,
                        focusedContainerColor = Color.Transparent,
                        disabledContainerColor = Color.Transparent,
                        unfocusedContainerColor = Color.Transparent,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent
                    ),
                    keyboardOptions = KeyboardOptions(
                        autoCorrectEnabled = true,
                        imeAction = ImeAction.Next,
                        capitalization = KeyboardCapitalization.Sentences
                    ),
                    textStyle = TextStyle(fontWeight = FontWeight.Bold, fontSize = 18.sp),
                    value = viewModel?.title.orEmpty(),
                    onValueChange = {
                        viewModel?.updateTitleAndContent(newTitle = it)
                    },
                    placeholder = {
                        Text(
                            text = stringResource(R.string.title_placeholder),
                            color = Color.Gray
                        )
                    }
                )
                HorizontalDivider(modifier = Modifier.height(2.dp))
                TextField(
                    modifier = Modifier.fillMaxWidth(),
                    colors = TextFieldDefaults.colors(
                        disabledTextColor = Color.Transparent,
                        focusedContainerColor = Color.Transparent,
                        disabledContainerColor = Color.Transparent,
                        unfocusedContainerColor = Color.Transparent,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent
                    ),
                    keyboardOptions = KeyboardOptions(
                        autoCorrectEnabled = true,
                        capitalization = KeyboardCapitalization.Sentences
                    ),
                    value = viewModel?.content.orEmpty(),
                    onValueChange = { viewModel?.updateTitleAndContent(newContent = it) },
                    placeholder = {
                        Text(
                            text = stringResource(R.string.content_placeholder),
                            color = Color.Gray,
                            fontWeight = FontWeight.Light
                        )
                    }
                )
            }
            // endregion nota
        }
    }
}

@Preview(showBackground = true)
@Composable
fun UpdateScreenPreview() {
    UpdateScreen(isPreview = true, viewModel = null, noteId = 0)
}

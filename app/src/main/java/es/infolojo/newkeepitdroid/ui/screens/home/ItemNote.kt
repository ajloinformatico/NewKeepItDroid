package es.infolojo.newkeepitdroid.ui.screens.home

import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.Card
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import es.infolojo.newkeepitdroid.R
import es.infolojo.newkeepitdroid.ui.screens.vo.NoteVO
import es.infolojo.newkeepitdroid.utils.getFormattedDate
import es.infolojo.newkeepitdroid.utils.lengthOfWords

private const val MAX_LENGTH_CONTENT = 40

/**
 * Row of the home screen
 */
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ItemNote(
    noteVO: NoteVO? = null,
    events: ((HomeScreenGridEvents) -> Unit) = {}
) {

    // At the first load check if note content is longer than MAX_LENGTH_CONTENT
    var expanded by rememberSaveable { mutableStateOf((noteVO?.content?.lengthOfWords() ?: 0) < MAX_LENGTH_CONTENT) }
    var dropMenuExpanded by rememberSaveable { mutableStateOf(false) }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            // needed combinedClickable for remove and update instead of simple click
            .combinedClickable(
                onClick = {
                    noteVO?.id?.let { noteId ->
                        events(HomeScreenGridEvents.Update(noteId))
                    }
                },
                onLongClick = {
                    noteVO?.id?.let { noteId ->
                        events(HomeScreenGridEvents.Delete(id = noteId, noteVO = noteVO))
                    }
                }
            )
    ) {
        // region header
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.secondary),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // NoteTitle
            Text(
                text = noteVO?.title ?: stringResource(R.string.title),
                // More weight "1f" to take up more space
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 8.dp),
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp,
                overflow = TextOverflow.Ellipsis, // idem a elipsis end en xml
                maxLines = 1,
                color = MaterialTheme.colorScheme.background,
                style = TextStyle(lineHeight = 1.5.em)
            )

            // MainOptions
            IconButton(
                onClick = { dropMenuExpanded = !dropMenuExpanded }
            ) {
                Icon(
                    imageVector = Icons.Default.MoreVert,
                    contentDescription = stringResource(R.string.more_options),
                    tint = MaterialTheme.colorScheme.background
                )
            }

            Row(
                horizontalArrangement = Arrangement.End,
                verticalAlignment = Alignment.Bottom
            ) {
                DropdownMenu(
                    expanded = dropMenuExpanded,
                    onDismissRequest = { dropMenuExpanded = false },
                    offset = DpOffset(0.dp, 22.dp)
                ) {
                    DropdownMenuItem(
                        text = { Text(text = stringResource(R.string.delete_note)) },
                        onClick = {
                            noteVO?.id?.let { noteId ->
                                Log.d("TonyTest", "onClick delete")
                                events(HomeScreenGridEvents.Delete(id = noteId, noteVO = noteVO))
                            }
                            dropMenuExpanded = false
                        }
                    )
                    DropdownMenuItem(
                        text = { Text(text = stringResource(R.string.edit_note)) },
                        onClick = {
                            noteVO?.id?.let { noteId ->
                                Log.d("TonyTest", "onClick update")
                                events(HomeScreenGridEvents.Update(noteId))
                            }
                            dropMenuExpanded = false
                        }
                    )
                }
            }
        }
        // endregion header

        // region body
        // note content
        AnimatedVisibility(visible = expanded) {
            Column(
                modifier = Modifier
                    .padding(start = 8.dp, end = 16.dp, bottom = 8.dp, top = 8.dp)
                    .align(Alignment.Start)
                    .fillMaxWidth(),
            ) {
                Text(
                    text = noteVO?.date?.getFormattedDate() ?: stringResource(R.string.note_date),
                    color = Color.Gray,
                    fontSize = 12.sp,
                    style = TextStyle(lineHeight = 0.em),
                    fontWeight = FontWeight.Light,
                    textAlign = TextAlign.End,
                    modifier = Modifier.fillMaxWidth().align(Alignment.End)
                )
                Text(
                    text = noteVO?.content ?: stringResource(R.string.note_content),
                    color = Color.Gray,
                    overflow = TextOverflow.Ellipsis,
                    fontSize = 16.sp,
                    style = TextStyle(lineHeight = 1.5.em)
                )

            }
        }

        // It will show and gone the content
        IconButton(
            modifier = Modifier.align(Alignment.End),
            onClick = { expanded = !expanded }
        ) {
            Icon(
                imageVector = if (expanded) {
                    Icons.Default.KeyboardArrowUp
                } else {
                    Icons.Default.KeyboardArrowDown
                },
                tint = Color.Gray,
                contentDescription = stringResource(R.string.expand_and_collapse)
            )
        }
        // endregion body
    }
}

@Preview(showBackground = true)
@Composable
fun ItemNotePreview() {
    ItemNote()
}

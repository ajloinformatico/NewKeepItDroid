package es.infolojo.newkeepitdroid.ui.screens.commons

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.clickable
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import es.infolojo.newkeepitdroid.R

@Composable
fun RegularAlertDialogComponent(
    title: String,
    text: String,
    confirmText: String = stringResource(R.string.yes),
    dismissText: String = stringResource(R.string.no),
    enabled: Boolean = false,
    onConfirm: () -> Unit = {},
    onDismiss: () -> Unit = {}
) {
    AnimatedVisibility(visible = enabled) {
        AlertDialog(
            text = {
                Text(text = text)
            },
            title = {
                Text(
                    text = title,
                    fontWeight = FontWeight.Bold
                )
            },
            onDismissRequest = {
                onDismiss()
            },
            confirmButton = {
                Text(
                    text = confirmText,
                    fontWeight = FontWeight.Normal,
                    modifier = Modifier.clickable(enabled = true) {
                        onConfirm()
                    }
                )
            },
            dismissButton = {
                Text(
                    text = dismissText,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.clickable {
                        onDismiss()
                    }
                )
            }
        )
    }

}


@Preview(showBackground = true)
@Composable
fun RegularAlertDialogComponentPreview() {
    RegularAlertDialogComponent("title", "text", enabled = true)
}
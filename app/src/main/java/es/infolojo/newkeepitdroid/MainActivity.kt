package es.infolojo.newkeepitdroid

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import dagger.hilt.android.AndroidEntryPoint
import es.infolojo.newkeepitdroid.ui.screens.add.AddScreen
import es.infolojo.newkeepitdroid.ui.screens.home.HomeScreen
import es.infolojo.newkeepitdroid.ui.screens.search.SearchScreen
import es.infolojo.newkeepitdroid.ui.screens.update.UpdateScreen
import es.infolojo.newkeepitdroid.ui.theme.NewKeepItDroidTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            NewKeepItDroidTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    /**Greeting(
                        name = "Android",
                        modifier = Modifier.padding(innerPadding)
                    )*/
                    // HomeScreen(modifier = Modifier.padding(innerPadding))
                    SearchScreen(modifier = Modifier.padding(innerPadding))
                    // AddScreen(modifier = Modifier.padding(innerPadding))
                    // UpdateScreen(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}

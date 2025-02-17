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
import es.infolojo.newkeepitdroid.ui.screens.home.HomeScreen
import es.infolojo.newkeepitdroid.ui.screens.search.SearchScreen
import es.infolojo.newkeepitdroid.ui.theme.NewKeepItDroidTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            NewKeepItDroidTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    innerPadding
                    /**Greeting(
                        name = "Android",
                        modifier = Modifier.padding(innerPadding)
                    )*/
                    // HomeScreen()
                    SearchScreen()
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    NewKeepItDroidTheme {
        Greeting("Android")
    }
}
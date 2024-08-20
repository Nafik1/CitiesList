package org.example.citieslist

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.SearchBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.arkivanov.decompose.defaultComponentContext
import com.arkivanov.decompose.extensions.compose.jetpack.stack.Children
import com.totowka.kmp.ui.theme.AppTheme
import org.example.citieslist.di.kodein
import org.example.citieslist.presentation.root.RootComponent
import org.example.citieslist.presentation.root.RootComponentImpl
import org.example.citieslist.presentation.search.SearchContent
import org.kodein.di.erased.instance

class MainActivity : ComponentActivity() {

    val rootComponentFactory: RootComponentImpl.Factory by kodein.instance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            RootContent(component = rootComponentFactory.create(defaultComponentContext()))
        }
    }
}


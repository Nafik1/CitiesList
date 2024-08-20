//package org.example.citieslist.presentation.root
//
//import androidx.compose.runtime.Composable
//import com.arkivanov.decompose.extensions.compose.jetpack.stack.Children
//import com.totowka.kmp.ui.theme.AppTheme
//import org.example.citieslist.presentation.search.SearchContent
//

//@Composable
//fun RootContent(component: RootComponent) {
//
//    AppTheme {
//        Children(stack = component.stack) {
//            when(val instance = it.instance) {
//                is RootComponent.Child.Search -> {
//                    SearchContent(component = instance.component)
//                }
//            }
//        }
//    }
//}
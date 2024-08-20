package org.example.citieslist.presentation.root

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.value.Value
import com.arkivanov.essenty.parcelable.Parcelable
import com.arkivanov.essenty.parcelable.Parcelize
import kotlinx.serialization.Serializable
import org.example.citieslist.di.kodein
import org.example.citieslist.presentation.search.SearchComponentImpl
import org.kodein.di.erased.instance

class RootComponentImpl(
    componentContext: ComponentContext,
) : RootComponent, ComponentContext by componentContext {

    private val searchComponentImpl: SearchComponentImpl.Factory by kodein.instance()
    private val navigation = StackNavigation<Config>()

    override val stack: Value<ChildStack<*, RootComponent.Child>> = childStack(
        source = navigation,
        serializer = Config.serializer(),
        initialConfiguration = Config.Search,
        handleBackButton = true,
        childFactory = ::child
    )

    private fun child(
        config: Config,
        componentContext: ComponentContext
    ): RootComponent.Child {
        return when (config) {
            Config.Search -> {
                val component = searchComponentImpl.create(
                    componentContext = componentContext
                )
                RootComponent.Child.Search(component)
            }
        }
    }

    @Serializable
    sealed interface Config {

        @Serializable
        data object Search : Config

    }

    class Factory {
        fun create(
            componentContext: ComponentContext
        ): RootComponentImpl {
            return RootComponentImpl(
                componentContext = componentContext
            )
        }
    }

}
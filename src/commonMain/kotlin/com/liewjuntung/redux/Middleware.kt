package com.liewjuntung.redux

import com.liewjuntung.redux.api.*
import com.liewjuntung.redux.api.enhancer.Middleware


fun <S : Any> applyMiddleware(vararg middlewares: Middleware<S>): StoreEnhancer<S> {
    return { next ->
        { reducer, initialState ->
            object : Store<S> {
                override fun subscribe(subscriber: StoreSubscriber): Store.Subscription {
                    return store.subscribe(subscriber)
                }

                private val store = next(reducer, initialState)
                override val state: S = store.state
                val thisStore: Store<S> = this
                private val rootDispatcher = middlewares.foldRight(store as Dispatcher) { middleware, next ->
                    object: Dispatcher {
                        override fun dispatch(action: Any): Any {
                            return middleware(thisStore, next, action)
                        }
                    }
                }

                override fun dispatch(action: Any) = rootDispatcher.dispatch(action)

                override fun replaceReducer(reducer: Reducer<S>) = store.replaceReducer(reducer)

            }
        }
    }

}


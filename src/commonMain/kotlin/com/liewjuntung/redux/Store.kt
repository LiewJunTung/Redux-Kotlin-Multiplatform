package com.liewjuntung.redux

import com.liewjuntung.redux.api.*


/*
 * Copyright (C) 2016 Michael Pardo
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

/**
 * When a store is created, an "INIT" action is dispatched so that every reducer returns their initial state.
 * This effectively populates the initial state tree.
 */
val INSTANCE = Store.INIT

/**
 * Creates a Redux store that holds the complete state tree of your component. There should only be a single
 * store per component.
 *
 * @see <a href="http://redux.js.org/docs/api/createStore.html">http://redux.js.org/docs/api/createStore.html</a>
 *
 * @param[reducer] The [Reducer] which returns the next state tree, given the current state tree and an action to handle
 * @param[initialState] The initial state
 * @param[enhancer] The store [StoreEnhancer]
 * @return An object that holds the complete state of your component.
 */
fun <S : Any> createStore(
    reducer: Reducer<S>,
    initialState: S,
    enhancer: StoreEnhancer<S> = { next -> next}
): Store<S> {
    val screator: StoreCreator<S> = { cReducer, cInitialState ->
        object : Store<S> {
            override fun replaceReducer(reducer: Reducer<S>) {
                this.reducer = reducer
                dispatch(INSTANCE)
            }

            private var reducer = cReducer
            private var subscribers = mutableListOf<StoreSubscriber>()
            private var isDispatching = false

            override var state: S = cInitialState

            override fun subscribe(subscriber: StoreSubscriber): Store.Subscription {
                subscribers.add(subscriber)
                return object: Store.Subscription {
                    override fun unsubscribe() {
                        subscribers.remove(subscriber)
                    }

                }
            }

            override fun dispatch(action: Any): Any {
                if (isDispatching) {
                    throw Exception("Reducers may not dispatch actions.")
                }
                try {
                    isDispatching = true
                    state = this.reducer(state, action)
                } finally {
                    isDispatching = false
                }

                subscribers
                    .toList()
                    .forEach { it.invoke() }
                return action
            }
        }
    }
    val store = enhancer(screator)(reducer, initialState)
    store.dispatch(INSTANCE)
    return store
}

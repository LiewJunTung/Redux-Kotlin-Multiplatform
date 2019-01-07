package com.liewjuntung.redux.api

/**
 * Coordinates actions and [Reducer]. Store has the following responsibilities:
 *
 *  * Holds application state
 *  * Allows access to state via [.getState]
 *  * Allows state to be updated via [Dispatcher.dispatch]
 *  * Registers listeners via [.subscribe]
 *  * Handles unregistering of listeners via the [Subscriber] returned by [.subscribe]
 *
 *
 * @see [http://redux.js.org/docs/basics/Store.html](http://redux.js.org/docs/basics/Store.html)
 */

/**
 * A listener which will be called any time an action is dispatched, and some part of the state tree may potentially
 * have changed. You may then call [.getState] to read the current state tree inside the listener.
 *
 * @see [http://redux.js.org/docs/api/Store.html.subscribe](http://redux.js.org/docs/api/Store.html.subscribe)
 */
typealias StoreSubscriber = ()-> Unit
/**
 * An interface that creates a store.
 *
 * @see [http://redux.js.org/docs/Glossary.html.store-creator](http://redux.js.org/docs/Glossary.html.store-creator)
 * @param reducer Reducer for the store state
 * @param initialState Initial state for the store
 * @return The store
 */
typealias StoreCreator<S> = (reducer: Reducer<S>, initialState: S)-> Store<S>
/**
 * An interface that composes a store creator to return a new, enhanced store creator.
 *
 * @see [http://redux.js.org/docs/Glossary.html.store-enhancer](http://redux.js.org/docs/Glossary.html.store-enhancer)
 * @param next The next store creator to compose
 * @return The composed store creator
 */
typealias StoreEnhancer<S> = (next: StoreCreator<S>) -> StoreCreator<S>

interface Store<S> : Dispatcher {
    /**
     * Returns the current state tree of your application. It is equal to the last value returned by the store’s
     * reducer.
     *
     * @return the current state
     * @see [http://redux.js.org/docs/api/Store.html.getState](http://redux.js.org/docs/api/Store.html.getState)
     */
    val state: S

    /**
     * Adds a change listener. It will be called any time an action is dispatched, and some part of the state tree may
     * potentially have changed. You may then call [.getState] to read the current state tree inside the
     * callback.
     *
     * @param subscriber The subscriber
     * @return A subscription
     * @see [http://redux.js.org/docs/api/Store.html.subscribe](http://redux.js.org/docs/api/Store.html.subscribe)
     */
    fun subscribe(subscriber: StoreSubscriber): Subscription

    /**
     * Replaces the reducer currently used by the store to calculate the state.
     *
     * @param reducer The reducer
     * @see [http://redux.js.org/docs/api/Store.html.replaceReducer](http://redux.js.org/docs/api/Store.html.replaceReducer)
     */
    fun replaceReducer(reducer: Reducer<S>)

    /**
     * A reference to the [StoreSubscriber] to allow for unsubscription.
     */
    interface Subscription {

        /**
         * Unsubscribe the [StoreSubscriber] from the [Store].
         */
        fun unsubscribe()

        companion object {

            val EMPTY: Subscription = object :
                Subscription {
                override fun unsubscribe() {}
            }
        }

    }

    /**
     * Initialization action.
     */
    object INSTANCE

    companion object {

        /**
         * Initialization action reference.
         */
        val INIT = INSTANCE
    }

}

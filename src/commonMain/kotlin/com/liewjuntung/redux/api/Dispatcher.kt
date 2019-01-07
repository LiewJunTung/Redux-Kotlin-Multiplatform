package com.liewjuntung.redux.api

/**
 * A dispatcher is an interface that accepts an action or an async action; it then may or may not dispatch one or more
 * actions to the store.
 *
 * @see [http://redux.js.org/docs/Glossary.html.dispatching-function](http://redux.js.org/docs/Glossary.html.dispatching-function)
 */

//interface Dispatcher {
//
//    /**
//     * Dispatches an action. This is the only way to trigger a state change.
//     *
//     * @param action A plain object describing the change that makes sense for your application
//     * @return The dispatched action
//     * @see [http://redux.js.org/docs/api/Store.html.dispatch](http://redux.js.org/docs/api/Store.html.dispatch)
//     */
//    fun dispatch(action: Any): Any
//
//}
interface Dispatcher {

    /**
     * Dispatches an action. This is the only way to trigger a state change.
     *
     * @param action A plain object describing the change that makes sense for your application
     * @return The dispatched action
     * @see [http://redux.js.org/docs/api/Store.html.dispatch](http://redux.js.org/docs/api/Store.html.dispatch)
     */
    fun dispatch(action: Any): Any

}

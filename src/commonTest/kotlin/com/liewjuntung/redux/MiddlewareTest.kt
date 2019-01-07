package com.liewjuntung.redux

import com.liewjuntung.redux.api.Dispatcher
import com.liewjuntung.redux.api.Store
import com.liewjuntung.redux.api.enhancer.Middleware
import com.liewjuntung.redux.helpers.Reducers
import com.liewjuntung.redux.helpers.Todos
import kotlin.test.Test
import kotlin.test.assertEquals

class MiddlewareTest {

    @Test
    fun testMiddleware(){
        var dispatches = 0
        val middleware: Middleware<Todos.State> = { store: Store<Todos.State>, next: Dispatcher, action: Any ->
            val result = next.dispatch(action)
            dispatches++
            action
        }

        val initialState = Todos.State(listOf(Todos.Todo(1, "Hello")))
        val store = createStore(
            Reducers.TODOS,
            initialState,
            applyMiddleware(middleware)
        )

        assertEquals(1, dispatches)

        store.dispatch(Todos.Action.AddTodo("World"))

        assertEquals(2, dispatches)
    }
}

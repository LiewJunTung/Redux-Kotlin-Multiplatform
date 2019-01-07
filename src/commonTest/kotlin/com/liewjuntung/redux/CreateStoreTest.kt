package com.liewjuntung.redux

import com.liewjuntung.redux.api.StoreSubscriber
import com.liewjuntung.redux.helpers.ActionCreators
import com.liewjuntung.redux.helpers.ActionCreators.addTodo
import com.liewjuntung.redux.helpers.ActionCreators.unknownAction
import com.liewjuntung.redux.helpers.Reducers
import com.liewjuntung.redux.helpers.Todos
import com.liewjuntung.redux.helpers.Todos.State
import com.liewjuntung.redux.helpers.Todos.Todo
import kotlin.test.Test
import kotlin.test.assertFalse
import kotlin.test.assertTrue
import kotlin.test.expect

class CreateStoreTest {
    /**
     * "passes the initial action and the initial state"
     */
    @Test
    fun testReduxInitialState() {
        val store = createStore(
            Reducers.TODOS, State(
                listOf(
                    Todo(1, "Hello")
                )
            )
        )
        expect(store.state) {
            Todos.State(
                listOf(
                    Todo(1, "Hello")
                )
            )
        }
    }

    /**
     * "applies the reducer to the previous state"
     */
    @Test
    fun testReduxAddState() {
        val store = createStore(
            Reducers.TODOS,
            State()
        )
        expect(store.state) { Todos.State() }

        store.dispatch(ActionCreators.unknownAction())
        expect(store.state) { Todos.State() }

        store.dispatch(ActionCreators.addTodo("Hello"))
        expect(store.state) {
            Todos.State(
                listOf(
                    Todo(1, "Hello")
                )
            )
        }

        store.dispatch(ActionCreators.addTodo("World"))
        expect(store.state) {
            Todos.State(
                listOf(
                    Todo(1, "Hello"),
                    Todo(2, "World")
                )
            )
        }
    }

    /**
     * "preserves the state when replacing a reducer"
     */
    @Test
    fun testReplaceReducer() {
        val store = createStore(Reducers.TODOS, State())
        store.dispatch(addTodo("Hello"))
        store.dispatch(addTodo("World"))
        expect(store.state) {
            Todos.State(
                listOf(
                    Todo(1, "Hello"),
                    Todo(2, "World")
                )
            )
        }

        store.replaceReducer(Reducers.TODOS_REVERSE)
        expect(store.state) {
            Todos.State(
                listOf(
                    Todo(1, "Hello"),
                    Todo(2, "World")
                )
            )
        }

        store.dispatch(addTodo("Perhaps"))
        expect(store.state) {
            Todos.State(
                listOf(
                    Todo(3, "Perhaps"),
                    Todo(1, "Hello"),
                    Todo(2, "World")
                )
            )
        }

        store.replaceReducer(Reducers.TODOS)
        expect(store.state) {
            Todos.State(
                listOf(
                    Todo(3, "Perhaps"),
                    Todo(1, "Hello"),
                    Todo(2, "World")
                )
            )
        }

        store.dispatch(addTodo("Surely"))
        expect(store.state) {
            Todos.State(
                listOf(
                    Todo(3, "Perhaps"),
                    Todo(1, "Hello"),
                    Todo(2, "World"),
                    Todo(4, "Surely")
                )
            )
        }
    }

    @Test
    fun higherOrderFunctionSubscriptions() {
        val store = createStore(Reducers.TODOS, State())
        var onStateChangedCalled = false

        store.dispatch(unknownAction())
        assertTrue(!onStateChangedCalled)

        val subscription: StoreSubscriber = {
            onStateChangedCalled = true
        }
        store.subscribe(subscription)
        store.dispatch(unknownAction())
        assertTrue(onStateChangedCalled)
    }

    @Test
    fun testUnsubscribe() {
        val store = createStore(Reducers.TODOS, State())
        var onStateChangedCalled = false

        store.dispatch(unknownAction())
        assertTrue(!onStateChangedCalled)

        val subscription: StoreSubscriber = {
            onStateChangedCalled = true
        }
        val subscriber = store.subscribe(subscription)
        subscriber.unsubscribe()
        store.dispatch(unknownAction())
        assertFalse(onStateChangedCalled)

    }
}

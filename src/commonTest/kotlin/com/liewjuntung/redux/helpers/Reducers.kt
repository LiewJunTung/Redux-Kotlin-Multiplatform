package com.liewjuntung.redux.helpers

import com.liewjuntung.redux.api.Reducer

object Reducers {

    fun id(state: Todos.State): Int {
        return state.todos.fold(0) { result, item ->
            if (item.id > result) item.id else result
        } + 1
    }

    val TODOS:Reducer<Todos.State> = { state: Todos.State, action: Any ->
        when (action) {
            is Todos.Action.AddTodo -> state.copy(todos = state.todos + Todos.Todo(
                id(state),
                action.todo
            )
            )
            else -> state
        }
    }


    val TODOS_REVERSE: Reducer<Todos.State> = { state: Todos.State, action: Any ->
        when (action) {
            is Todos.Action.AddTodo -> state.copy(todos = listOf(
                Todos.Todo(
                    id(state),
                    action.todo
                )
            ) + state.todos)
            else -> state
        }
    }
}

package com.liewjuntung.redux.helpers

object ActionCreators {

    fun unknownAction() = Todos.Action.Unknown()

    fun addTodo(name: String) = Todos.Action.AddTodo(name)

    fun addTodoAsync(name: String) = Todos.Action.AddTodoAsync(name)

}

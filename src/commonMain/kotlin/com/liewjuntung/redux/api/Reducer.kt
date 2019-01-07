package com.liewjuntung.redux.api

/**
 * A reducer accepts an accumulation and a value and returns a new accumulation. They are used to reduce a collection of
 * values down to a single value.
 *
 * @see [http://redux.js.org/docs/basics/Reducers.html](http://redux.js.org/docs/basics/Reducers.html)
 *
 * A pure function which returns a new state given the previous state and an action.
 *
 * Things you should never do inside a reducer:
 *
 *  * Mutate its arguments
 *  * Perform side effects like API calls and routing transitions
 *  * Call non-pure functions, e.g. Date() or Random.nextInt()
 *
 *
 * Given the same arguments, it should calculate the next state and return it. No surprises. No side effects. No API
 * calls. No mutations. Just a calculation.
 *
 * @param state The previous state
 * @param action The dispatched action
 * @return The new state
 * @see [http://redux.js.org/docs/basics/Reducers.html](http://redux.js.org/docs/basics/Reducers.html)
 */
typealias Reducer<S> = (state: S, action: Any) -> S

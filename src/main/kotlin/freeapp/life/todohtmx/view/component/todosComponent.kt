package freeapp.life.todohtmx.view.component


import freeapp.life.todohtmx.entity.Todo
import kotlinx.html.*
import org.springframework.data.domain.Page

fun DIV.todoComponent(todo: Todo)  {

    div {
        id = "todo-container-${todo.id}"
        div("flex mb-4 items-center") {
            id = "todo-box"
            p("w-full text-grey-darkest ${if (todo.status) "line-through" else ""}") {
                +"${todo.content}"
            }
            button(classes = "flex-no-shrink p-2 ml-4 mr-2 border-2 rounded hover:text-white text-green border-green hover:bg-green") {
                attributes["hx-trigger"] = "click"
                attributes["hx-put"] = "/todo/${todo.id}"
                attributes["hx-target"] = "#todo-container-${todo.id}"
                attributes["hx-swap"] = "innerHTML"
                +"${if (todo.status) "Yet" else "Done"}"
            }
            button(classes = "flex-no-shrink p-2 ml-2 border-2 rounded text-red border-red hover:text-white hover:bg-red") {
                attributes["hx-confirm"] = "Are you sure?"
                attributes["hx-swap"] = "outerHTML swap:1s"
                attributes["hx-trigger"] = "click"
                attributes["hx-delete"] = "/todo/${todo.id}"
                attributes["hx-target"] = "closest div"
                +"""Remove"""
            }
        }
    }

}


fun BODY.todos(todos: Page<Todo>) {

    div {
        id = "content-body"
        div("h-100 w-full flex items-center justify-center bg-teal-lightest font-sans") {
            div("bg-white rounded shadow p-6 m-4 w-full lg:w-3/4 lg:max-w-lg") {
                div("mb-4") {
                    h1("text-5xl font-bold") { +"Todo List" }
                    div("flex mt-4") {
                        input(classes = "shadow appearance-none border rounded w-full py-2 px-3 mr-4 text-grey-darker") {
                            attributes["autofocus"] = "autofocus"
                            id = "new-todo"
                            name = "newTodo"
                            placeholder = "To do..."
                            required = true
                            type = InputType.text
                        }
                        button(classes = "flex-no-shrink p-2 border-2 rounded text-teal border-teal hover:text-white hover:bg-teal") {
                            attributes["hx-include"] = "#new-todo"
                            attributes["hx-trigger"] = "click"
                            attributes["hx-post"] = "/todo"
                            attributes["hx-target"] = "#todo-list"
                            attributes["hx-swap"] = "afterbegin"
                            +"""Add"""
                        }
                    }
                }
                div {
                    id = "todo-list"
                    for (todo in todos) {
                        todoComponent(todo)
                    }
                }
            }
        }

        paginationViewComponent(todos, "todos")
    }


}
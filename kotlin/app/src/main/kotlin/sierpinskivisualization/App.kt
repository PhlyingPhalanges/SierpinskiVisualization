package sierpinskivisualization

fun main() {
    println("Please select the fractal object you would like to see generated:\n" +
        "[1] Sierpiński Carpet\n[2] Sierpiński Gasket\n")
    val object_choice: String? = readLine()
    var choice_show: String = ""
    if (object_choice == "1") {
        choice_show = "Sierpiński Carpet"
    } else if (object_choice == "2") {
        choice_show = "Sierpiński Gasket"
    }
    println("How many iterations of the $choice_show would you like generated?\n")
    val iterations_choice: String? = readLine()
    val num_iterations = iterations_choice?.toIntOrNull()
    if (object_choice == "1") {
        // call carpet method, passing in num_iterations
    } else if (object_choice == "2") {
        // call gasket method, passing in num_iterations
    }
}

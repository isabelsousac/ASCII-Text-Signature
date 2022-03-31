package signature

fun main() {
    //val font = createFont(true)
    do {
        var isValidName = true
        try {
            print("Enter name and surname: ")
            val (firstName, lastName) = readln().split(" ")

            print("Enter person's status: ")
            val status = readln()
            val framedName = TagName(firstName, lastName, status)

            println(framedName.getFramedName())
        } catch (e: IndexOutOfBoundsException) {
            e.printStackTrace()
            println("Insert a name and surname")
            isValidName = false
        }
    } while (!isValidName)
}
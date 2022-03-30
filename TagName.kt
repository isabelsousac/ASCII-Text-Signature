package signature

import kotlin.math.max

class TagName(name: String, surname: String, private val status: String) {
    private val fullName = "${name.uppercase()} ${surname.uppercase()}"
    companion object {
        private const val EXTRA_SPACE = 1
        private const val PADDING = 6
    }

    fun getFramedName(): String {
        val borderLength = calculateBorderLength()
        val transformedName = getTransformedName(borderLength)
        val statusLine = getStatusLine(borderLength)
        val border = getBorder(borderLength)

        return "$border\n$transformedName\n$statusLine\n$border"
    }

    private fun calculateBorderLength(): Int =
        max(calculateLineLength(), status.length) + PADDING

    private fun calculateLineLength(): Int {
        val lineLength = fullName.sumOf { getCharParts(it).length }
        return lineLength + fullName.length - 1 // fullName.length - 1 means the space -> ex: 10 letters, 9 spaces
    }

    private fun getTransformedName(borderLength: Int): String =
        constructLine(0, borderLength) + "\n" +
        constructLine(1, borderLength) + "\n" +
        constructLine(2, borderLength)

    private fun constructLine(lineIndex: Int, borderLength: Int): String {
        val line = fullName.map { getCharParts(it).parts[lineIndex] }
            .joinToString(separator = " ")

        val (spaceStart, spaceEnd) = getSpaceAroundName(line, borderLength)

        return "*$spaceStart$line$spaceEnd*"
    }

    private fun getCharParts(char: Char): CharParts =
        if (char == ' ') CharParts.SPACE
        else CharParts.valueOf(char.toString())

    private fun getSpaceAroundName(line: String, borderLength: Int): SpaceAround {
        return if (line.length >= status.length) {
            SpaceAround("  ", "  ")
        } else {
            val middle = (borderLength - line.length) / 2
            val endSpaceOffset = if (borderLength % 2 == 0) 0 else EXTRA_SPACE
            SpaceAround(
                start = " ".repeat(middle - EXTRA_SPACE),
                end = " ".repeat(middle - endSpaceOffset)
            )
        }
    }

    private fun getStatusLine(borderLength: Int): String {
        val middle = (borderLength - status.length) / 2
        val isBothEven = status.length % 2 == borderLength % 2
        val endSpaceOffset = if (isBothEven) EXTRA_SPACE else 0
        val startSpaces = " ".repeat(middle - EXTRA_SPACE)
        val endSpaces = " ".repeat(middle - endSpaceOffset)
        return "*$startSpaces$status$endSpaces*"
    }

    private fun getBorder(borderLength: Int): String = "8".repeat(borderLength)
}
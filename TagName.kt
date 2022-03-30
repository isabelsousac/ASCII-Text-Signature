package signature

import kotlin.math.max

class TagName(name: String, surname: String, private val status: String) {
    private val fullName = "${name.uppercase()} ${surname.uppercase()}"
    companion object {
        private const val EXTRA_SPACE = 1
        private const val PADDING = 6
    }

    fun getFramedName(): String {
        val starsLength = calculateStarLength()
        val transformedName = getTransformedName(starsLength)
        val statusLine = getStatusLine(starsLength)
        val stars = getStars(starsLength)

        return "$stars\n$transformedName\n$statusLine\n$stars"
    }

    private fun calculateStarLength(): Int =
        max(calculateLineLength(), status.length) + PADDING

    private fun calculateLineLength(): Int {
        val lineLength = fullName.sumOf { getCharParts(it).length }
        return lineLength + fullName.length - 1 // fullName.length - 1 means the space -> ex: 10 letters, 9 spaces
    }

    private fun getTransformedName(starsLength: Int): String =
        constructLine(0, starsLength) + "\n" +
        constructLine(1, starsLength) + "\n" +
        constructLine(2, starsLength)

    private fun constructLine(lineIndex: Int, starsLength: Int): String {
        val line = fullName.map { getCharParts(it).parts[lineIndex] }
            .joinToString(separator = " ")

        val (spaceStart, spaceEnd) = getSpaceAroundName(line, starsLength)

        return "*$spaceStart$line$spaceEnd*"
    }

    private fun getCharParts(char: Char): CharParts =
        if (char == ' ') CharParts.SPACE
        else CharParts.valueOf(char.toString())

    private fun getSpaceAroundName(line: String, starsLength: Int): SpaceAround {
        return if (line.length >= status.length) {
            SpaceAround("  ", "  ")
        } else {
            val middle = (starsLength - line.length) / 2
            val endSpaceOffset = if (starsLength % 2 == 0) 0 else EXTRA_SPACE
            SpaceAround(
                start = " ".repeat(middle - EXTRA_SPACE),
                end = " ".repeat(middle - endSpaceOffset)
            )
        }
    }

    private fun getStatusLine(starsLength: Int): String {
        val middle = (starsLength - status.length) / 2
        val isBothEven = status.length % 2 == starsLength % 2
        val endSpaceOffset = if (isBothEven) EXTRA_SPACE else 0
        val startSpaces = " ".repeat(middle - EXTRA_SPACE)
        val endSpaces = " ".repeat(middle - endSpaceOffset)
        return "*$startSpaces$status$endSpaces*"
    }

    private fun getStars(starsLength: Int): String = "*".repeat(starsLength)
}
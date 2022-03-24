package signature

private const val EXTRA_SPACE = 1

class TagName(private val name: String, private val surname: String, private val status: String) {
    private val fullName = "${name.uppercase()} ${surname.uppercase()}"
    private val fullNameNoSpace = name.uppercase() + surname.uppercase()
    companion object {
        const val PADDING = 6
    }

    fun getFramedName(): String {
        val lineLength = calculateLineLength()
        val starsLength = calculateStarLength(lineLength)
        val transformedName = getTransformedName(starsLength)
        val statusLine = getStatusLine(starsLength)
        val stars = getStars(starsLength)

        return "$stars\n$transformedName\n$statusLine\n$stars"
    }

    private fun calculateLineLength(): Int {
        var singleLine = 0
        for (i in 0 until fullNameNoSpace.lastIndex) {
            singleLine += CharParts.valueOf(fullNameNoSpace[i].toString()).length
        }
        val kerning = fullNameNoSpace.lastIndex // kerning means space between two letters
        singleLine += kerning + CharParts.valueOf(fullName.last().toString()).length + PADDING // middle padding
        return singleLine
    }

    private fun calculateStarLength(lineLength: Int): Int {
        var starLength: Int
        if (lineLength >= status.length) {
            val blankSpaces = (name.length + surname.length - 2) + PADDING * 2 // adding edge and middle padding
            starLength = blankSpaces
            for (char in fullNameNoSpace) {
                starLength += CharParts.valueOf(char.toString()).length // adding number of char spaces occupied
            }
        } else {
            starLength = status.length + PADDING
        }
        return starLength
    }

    private fun getTransformedName(starsLength: Int): String {
        var builtName = ""
        builtName += constructLine(0, starsLength) + "\n"
        builtName += constructLine(1, starsLength) + "\n"
        builtName += constructLine(2, starsLength)

        return builtName
    }

    private fun constructLine(lineIndex: Int, starsLength: Int): String {
        var line = ""
        for (i in 0 until fullName.lastIndex) {
            val charsLine = getCharParts(fullName[i], lineIndex)
            line += "$charsLine "
        }
        val lastChar = getCharParts(fullName.last(), lineIndex) // last char can't have an additional space at the end
        line += lastChar
        val (spaceStart, spaceEnd) = getSpaceAroundName(line, starsLength)

        return "*$spaceStart$line$spaceEnd*"
    }

    private fun getCharParts(char: Char, indices: Int): String {
        val charParts = if (char == ' ') CharParts.SPACE else CharParts.valueOf(char.toString())
        return charParts.parts[indices]
    }

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
        return constructStatus(middle, isBothEven)
    }

    private fun constructStatus(middle: Int, isBothEven: Boolean): String {
        val endSpaceOffset = if (isBothEven) EXTRA_SPACE else 0
        val startSpaces = " ".repeat(middle - EXTRA_SPACE)
        val endSpaces = " ".repeat(middle - endSpaceOffset)
        return "*$startSpaces$status$endSpaces*"
    }

    private fun getStars(starsLength: Int): String = "*".repeat(starsLength)
}
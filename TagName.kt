package signature

private const val REMAINING_SPACE = 1

class TagName(private val name: String, private val surname: String, private val status: String) {
    private val fullName = "${name.uppercase()} ${surname.uppercase()}"
    companion object {
        const val EXTRA_STARS = 6
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
        var singleLine = ""
        for (i in 0 until fullName.lastIndex) {
            val charsLine = getCharParts(fullName[i], 0)
            singleLine += "$charsLine "
        }
        val lastChar = getCharParts(fullName.last(), 0) // last char can't have an additional space at the end
        singleLine += lastChar
        return singleLine.length
    }

    private fun calculateStarLength(lineLength: Int): Int {
        var starLength: Int

        if (lineLength >= status.length) {
            val gapBetweenLetters = (name.length + surname.length - 2) + EXTRA_STARS * 2 // for edges and middle space
            starLength = gapBetweenLetters
            val droppedSpaceName = fullName.replace(" ", "")
            for (char in droppedSpaceName) {
                starLength += when(char) {
                    'I' -> 1
                    'J' -> 2
                    'T' -> 3
                    'W', 'Y' -> 5
                    else -> 4
                }
            }
        } else {
            starLength = status.length + EXTRA_STARS
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

    private fun getSpaceAroundName(line: String, starsLength: Int): Pair<String, String> {
        var spaceStart = ""
        var spaceEnd = ""
        if (line.length >= status.length) {
            spaceStart += "  "
            spaceEnd += "  "
        } else {
            val middle = (starsLength - line.length) / 2
            if (starsLength % 2 == 0) {
                repeat(middle - 1) { spaceStart += " " }
                repeat(middle - 1) { spaceEnd += " " }
            } else {
                repeat(middle - 1) { spaceStart += " " }
                repeat(middle) { spaceEnd += " " }
            }
        }
        return Pair(spaceStart, spaceEnd)
    }

    private fun getStatusLine(starsLength: Int): String {
        val middle = (starsLength - status.length) / 2
        val statusLine =
            if ((status.length % 2 == 0 && starsLength % 2 == 0) || (status.length % 2 != 0 && starsLength % 2 != 0)) {
                constructStatus(middle, true)
            } else {
                constructStatus(middle, false)
            }
        return statusLine
    }

    private fun getStars(starsLength: Int): String {
        var stars = ""
        repeat(starsLength) { stars += "*" }
        return stars
    }

    private fun constructStatus(middle: Int, isEqual: Boolean): String {
        var statusLine = "*"
        return if (isEqual) {
            repeat(middle - REMAINING_SPACE) { statusLine += " " }
            statusLine += status
            repeat(middle - REMAINING_SPACE) { statusLine += " " }
            "$statusLine*"
        } else {
            repeat(middle - REMAINING_SPACE) { statusLine += " " }
            statusLine += status
            repeat(middle) { statusLine += " " }
            "$statusLine*"
        }
    }

    private fun getCharParts(char: Char, indices: Int): String {
        val charParts = when (char) {
            'A' -> listOf("____", "|__|", "|  |")
            'B' -> listOf("___ ", "|__]", "|__]")
            'C' -> listOf("____", "|   ", "|___")
            'D' -> listOf("___ ", "|  \\", "|__/")
            'E' -> listOf("____", "|___", "|___")
            'F' -> listOf("____", "|___", "|   ")
            'G' -> listOf("____", "| __", "|__]")
            'H' -> listOf("_  _", "|__|", "|  |")
            'I' -> listOf("_", "|", "|")
            'J' -> listOf(" _", " |", "_|")
            'K' -> listOf("_  _", "|_/ ", "| \\_")
            'L' -> listOf("_   ", "|   ", "|___")
            'M' -> listOf("_  _", "|\\/|", "|  |")
            'N' -> listOf("_  _", "|\\ |", "| \\|")
            'O' -> listOf("____", "|  |", "|__|")
            'P' -> listOf("___ ", "|__]", "|   ")
            'Q' -> listOf("____", "|  |", "|_\\|")
            'R' -> listOf("____", "|__/", "|  \\")
            'S' -> listOf("____", "[__ ", "___]")
            'T' -> listOf("___", " | ", " | ")
            'U' -> listOf("_  _", "|  |", "|__|")
            'V' -> listOf("_  _", "|  |", " \\/ ")
            'W' -> listOf("_ _ _", "| | |", "|_|_|")
            'X' -> listOf("_  _", " \\/ ", "_/\\_")
            'Y' -> listOf("_   _", " \\_/ ", "  |  ")
            'Z' -> listOf("___ ", "  / ", " /__")
            ' ' -> listOf("    ", "    ", "    ")
            else -> return "Unknown char!"
        }

        return charParts[indices]
    }
}
package signature

import kotlin.math.max

class TagName(name: String, surname: String, private val status: String) {
    private val fullName = "$name $surname"
    private val nameFont = createFont(isNamespace = true)
    private val statusFont = createFont(isNamespace = false)
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
        max(calculateNamespaceLength(), calculateStatusLength()) + PADDING

    private fun calculateStatusLength(): Int {
        val lineLength = status.map { statusFont.getGlyph(it)[0] }
            .joinToString("").length

        return lineLength + status.length - 1 // "status.length - 1" means the space between the letters
    }

    private fun calculateNamespaceLength(): Int {
        val lineLength = fullName.map { nameFont.getGlyph(it)[0] }
            .joinToString(separator = "").length

        return lineLength + fullName.length - 1 // fullName.length - 1 means the space between the letters
    }

    private fun getTransformedName(borderLength: Int): String {
        var transformedName = ""

        for (i in 0 until nameFont.height - 1) {
            transformedName += constructNameLine(i, borderLength) + "\n"
        }
        return transformedName + constructNameLine(nameFont.height - 1, borderLength) // last line
    }

    private fun constructNameLine(lineIndex: Int, borderLength: Int): String {
        val line = fullName.map { nameFont.getGlyph(it)[lineIndex] }
            .joinToString(separator = "")

        val (spaceStart, spaceEnd) = getSpaceAroundName(line, borderLength)

        return "88$spaceStart$line${spaceEnd}88"
    }

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
        return "88$startSpaces$status${endSpaces}88"
    }

    private fun getBorder(borderLength: Int): String = "8".repeat(borderLength)
}
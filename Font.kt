package signature

import java.io.File

data class Font(
    val height: Int,
    val glyphCount: Int,
    val glyphs: List<Glyph>,
    private val spaceLength: Int
) {
    fun getGlyph(char: Char): List<String> {
        for (i in 0..glyphCount) {
            if (char == glyphs[i].representedChar) {
                return glyphs[i].lines
            } else if (char == ' ') {
                return List(height) { " ".repeat(spaceLength) }
            }
        }
        throw IllegalStateException()
    }
}

data class Glyph(
    val representedChar: Char,
    val width: Int,
    val lines: List<String>
)

fun createFont(isNamespace: Boolean): Font {
    val fontFile = if (isNamespace) "/Users/isabelsousa/IdeaProjects/ASCII Text Signature/ASCII Text Signature/task/src/signature/Fonts/BiggerFont.txt" else "/Users/isabelsousa/IdeaProjects/ASCII Text Signature/ASCII Text Signature/task/src/signature/Fonts/SmallerFont.txt"

    val lines = File(fontFile).readLines()
    val infoPart = lines.first()
    val (fontHeight, glyphCount) = infoPart.split(" ").map { it.toInt() }

    val glyphs = mutableListOf<Glyph>()
    for (i in 1 .. lines.size - fontHeight step fontHeight + 1) {
        val charLines = mutableListOf<String>()
        val (char, widthChar) = lines[i].split(" ")

        for (j in (i + 1)..i + fontHeight) {
            val offsetDifference = widthChar.toInt() - lines[j].length
            charLines += lines[j] + " ".repeat(offsetDifference)
        }

        glyphs += Glyph(
            representedChar = char.first(),
            width = widthChar.toInt(),
            lines = charLines
        )
    }

    return Font(
        height = fontHeight,
        glyphCount = glyphCount,
        glyphs = glyphs,
        spaceLength = if (isNamespace) 10 else 5
    )
}

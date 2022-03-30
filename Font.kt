package signature

import java.io.File

data class Font(
    val height: Int,
    val glyphCount: Int,
    val glyphs: List<Glyph>
) {
    fun getGlyph(char: Char): List<String> {
        for (i in 0..glyphCount) {
            if (char == glyphs[i].representedChar) {
                return glyphs[i].lines
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
    val fontFile = if (isNamespace) "/Users/isabelsousa/IdeaProjects/ASCII Text Signature/ASCII Text Signature/task/src/signature/Fonts/BiggerFont.txt" else "signature/Fonts/SmallerFont.txt"

    val lines = File(fontFile).readLines()
    val infoPart = lines.first()
    val (fontHeight, glyphCount) = infoPart.split(" ").map { it.toInt() }

    val glyphs = mutableListOf<Glyph>()
    for (i in 1 .. lines.size - fontHeight step fontHeight + 1) {
        val charLines = mutableListOf<String>()
        val (char, widthChar) = lines[i].split(" ")

        for (j in (i + 1)..i + fontHeight) {
            charLines += lines[j]
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
        glyphs = glyphs
    )
}
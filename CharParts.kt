package signature

enum class CharParts(val parts: List<String>) {
    A(listOf("____", "|__|", "|  |")),
    B(listOf("___ ", "|__]", "|__]")),
    C(listOf("____", "|   ", "|___")),
    D(listOf("___ ", "|  \\", "|__/")),
    E(listOf("____", "|___", "|___")),
    F(listOf("____", "|___", "|   ")),
    G(listOf("____", "| __", "|__]")),
    H(listOf("_  _", "|__|", "|  |")),
    I(listOf("_", "|", "|")),
    J(listOf(" _", " |", "_|")),
    K(listOf("_  _", "|_/ ", "| \\_")),
    L(listOf("_   ", "|   ", "|___")),
    M(listOf("_  _", "|\\/|", "|  |")),
    N(listOf("_  _", "|\\ |", "| \\|")),
    O(listOf("____", "|  |", "|__|")),
    P(listOf("___ ", "|__]", "|   ")),
    Q(listOf("____", "|  |", "|_\\|")),
    R(listOf("____", "|__/", "|  \\")),
    S(listOf("____", "[__ ", "___]")),
    T(listOf("___", " | ", " | ")),
    U(listOf("_  _", "|  |", "|__|")),
    V(listOf("_  _", "|  |", " \\/ ")),
    W(listOf("_ _ _", "| | |", "|_|_|")),
    X(listOf("_  _", " \\/ ", "_/\\_")),
    Y(listOf("_   _", " \\_/ ", "  |  ")),
    Z(listOf("___ ", "  / ", " /__")),
    SPACE(listOf("    ", "    ", "    "));

    val length: Int
        get() = parts[0].length

}
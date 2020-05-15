public class Token {
	private TokenClass id;
	private String lexeme;
	private Object javaValue;
	private String fileName;
	private int line;

	public Token(TokenClass id, String lexeme, Object javaValue, String fileName, int line) {
		this.id = id;
		this.lexeme = lexeme;
		this.javaValue = javaValue;
		this.fileName = fileName;
		this.line = line;
	}

	@Override
	public String toString() {
		return "Token{" +
				"id=" + id +
				", lexeme='" + lexeme + '\'' +
				", javaValue=" + javaValue +
				", fileName='" + fileName + '\'' +
				", line=" + line +
				'}';
	}

	public String getLexeme() {
		return lexeme;
	}

	public TokenClass getID() {
		return id;
	}
}

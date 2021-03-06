import java.io.*;
import java.util.*;

public class Tokenizer {
    private String fileName;
    private String source;
    private List<Token> tokens;
    private boolean verbose;

    private final static Map<String, TokenClass> KEYWORDS;

    static {
        KEYWORDS = new HashMap<>();
        KEYWORDS.put("as",              TokenClass.AS);
        KEYWORDS.put("in",              TokenClass.IN);
        KEYWORDS.put("neighborhoods",   TokenClass.NEIGHBORHOODS);
        KEYWORDS.put("not",             TokenClass.NOT);
        KEYWORDS.put("otherwise",       TokenClass.OTHERWISE);
        KEYWORDS.put("rules",           TokenClass.RULES);
        KEYWORDS.put("state",           TokenClass.STATE);
        KEYWORDS.put("states",          TokenClass.STATES);
        KEYWORDS.put("stay",            TokenClass.STAY);
        KEYWORDS.put("visual",          TokenClass.VISUAL);
        KEYWORDS.put("world",           TokenClass.WORLD);
    }

    public Tokenizer(File file, boolean verbose) throws IOException {
        Objects.requireNonNull(file);

        fileName = file.getName();

        FileReader fr = new FileReader(file);
        int c = fr.read();
        source = "";
        while (c != -1) {
            source += (char) c;
            c = fr.read();
        }

        this.verbose = verbose;
    }

    private int currentIndex;
    private int tokenStart;
    private int line;

    public void process() {
        tokens = new ArrayList<>();
        currentIndex = 0;
        line = 1;
        char c;
        while (!done()) {
            tokenStart = currentIndex;
            c = consume();
            switch (c) {
                case '[':
                    consumeTo(']');
                    consume();
                    break;
                case '>':
                    if (peek() == '>') {
                        consume();
                        addToken(TokenClass.APPLICATION);
                    }
                    break;
                case ' ':
                    consumeFollowing(' ');

                    addToken(TokenClass.SPACE);
                    break;
                case '\n':
                    addToken(TokenClass.NEWLINE);
                    while (peek() == '\t') {
                        tokenStart = currentIndex;
                        consume();
                        addToken(TokenClass.INDENT);
                    }
                    line++;
                    break;
                case '\r':
                case '\t':
                    break;
                default:
                    consumeTo(' ', '\r');
                    String lexeme = source.substring(tokenStart, currentIndex);
                    TokenClass id;
                    Object javaValue;
                    if (lexeme.matches("^\\d+$")) {
                        id = TokenClass.NUMBER;
                        javaValue = Integer.parseInt(lexeme);
                    }
                    else if (lexeme.matches("^\\d*:\\d*$")) {
                        id = TokenClass.RANGE;
                        javaValue = new Range(lexeme);
                    }
                    else if (lexeme.matches("^[-_0-9A-z]+$")) {
                        id = KEYWORDS.getOrDefault(lexeme, TokenClass.IDENTIFIER);
                        javaValue = lexeme;
                    }
                    else {
                        Tango.error(String.format("Unrecognized token on line %d: %s", line, lexeme), 74);
                        break;
                    }
                    addToken(id, javaValue);
                    break;
            }
        }
        tokenStart = currentIndex;
        addToken(TokenClass.EOF);
        if (verbose) {
            for (Token t : tokens) {
                System.out.printf("%s(%s) : ", t.getLexeme(), t.getID());
            }
            System.out.print("\n\n");
        }
    }

    private boolean done() {
        return currentIndex >= source.length();
    }

    private char consume() {
        currentIndex++;
        return source.charAt(currentIndex - 1);
    }

    private char peek() {
        if (done()) {
            return '\0';
        }
        return source.charAt(currentIndex);
    }

    private String consumeTo(char... ends) {
        String str = "";
        while (!inArray(peek(), ends) && !done()) {
            str += consume();
        }
        return str;
    }

    private boolean inArray(char c, char[] arr) {
        for (char a : arr) {
            if (c == a) {
                return true;
            }
        }
        return false;
    }

    private String consumeFollowing(char toConsume) {
        String str = "";
        while (peek() == toConsume) {
            tokenStart = currentIndex;
            str += consume();
        }
        return str;
    }

    private void addToken(TokenClass id) {
        addToken(id, null);
    }

    private void addToken(TokenClass id, Object javaValue) {
        tokens.add(new Token(id, source.substring(tokenStart, currentIndex), javaValue, fileName, line));
    }
}

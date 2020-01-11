package codeeditor;
import java.util.Collections;
import java.util.TreeSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
/**
 *
 * @author Princy
 */
public class SyntaxHighLighter {
    private TreeSet<String> keywords=null;
    private Pattern pattern=null;
    
    public SyntaxHighLighter(TreeSet<String> keywords)
    {
        this.keywords=keywords;
        pattern=createPattern(keywords);
    }
    
    public void  highLightSyntax(SmartCodeArea codeArea)
    {
        String text=codeArea.getText();
        Matcher matcher = pattern.matcher(text);
        int lastKwEnd = 0;
        if(pattern==null)
        System.out.println("sd");
        while (matcher.find()) {
            String styleClass =
                    matcher.group("KEYWORD") != null ? "keyword" :
                            matcher.group("PAREN") != null ? "paren" :
                                    matcher.group("BRACES") != null ? "brace" :
                                            matcher.group("BRACKET") != null ? "bracket" :
                                                    matcher.group("SEMICOLON") != null ? "semicolon" :
                                                            matcher.group("DIGIT") != null ? "digit" :
                                                                    matcher.group("STRING") != null ? "string" :
                                                                            matcher.group("COMMENT") != null ? "comment" :
                                                                                    null; /* never happens */
            codeArea.setStyle(lastKwEnd,matcher.start(),Collections.singleton("localText"));
            codeArea.setStyle(matcher.start(),matcher.end(),Collections.singleton(styleClass));
            lastKwEnd=matcher.end();
        }
        codeArea.setStyle(lastKwEnd,codeArea.getCaretPosition(),Collections.singleton("localText"));
    }
    public static Pattern createPattern(TreeSet<String> keywords)
    {
        String keywordPattern="\\b("+String.join("|", keywords)+")\\b";
        String parenPattern="\\(|\\)";
        String curlyPattern="\\{|\\}";
        String bracketPattern="\\[|\\]";
        String semicolonPattern="\\;";
        String digitPattern="\\d+";
        String stringPattern="\"([^\"\\\\]|\\\\.)*\"";
        String commentPattern="(((?s)\\/\\*(.)*?\\*\\/)|\\/\\/(.)*)";
        Pattern pattern=Pattern.compile("(?<KEYWORD>"+keywordPattern+")"+
                                "|(?<PAREN>"+parenPattern+")"+
                                "|(?<BRACES>"+curlyPattern+")"+
                                "|(?<SEMICOLON>"+semicolonPattern+")"+
                                "|(?<BRACKET>"+bracketPattern+")"+
                                "|(?<DIGIT>"+digitPattern+")"+
                                "|(?<STRING>"+stringPattern+")"+
                                "|(?<COMMENT>"+commentPattern+")");
        return pattern;
    }
}
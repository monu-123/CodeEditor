package codeeditor;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;
public class Helper
{
    private static TreeMap<String,String> symbolMap=null;
    private static TreeSet<String> javaKeywordSet=null;
    private static TreeSet<String> javaKwCnSet=null; //java keyword and class name  set
    private static TreeSet<String> cKeywordSet=null;
    private static TreeSet<String> cppKeywordSet=null;

    public static TreeSet<String> getKeywordList(String extention)
    {
        if(extention.equals("java"))
        {
            return getJavaKeywords();
        }
        else if(extention.equals("c"))
            return getCKeywords();
        else if(extention.equals("cpp"))
            return getCPPKeywords();
        return null;
    }
    private static TreeSet<String> getJavaKeywords()
    {
        if(javaKeywordSet!=null)
            return javaKeywordSet;
        javaKeywordSet=new TreeSet<String>();
        URL url=Helper.class.getResource("/resources/javaKeyword.txt");
        BufferedReader bufferedReader;
        try {
            bufferedReader = new BufferedReader(new InputStreamReader(url.openStream()));
            String s;
            while((s=bufferedReader.readLine())!=null)
            {
                javaKeywordSet.add(s);
            }
        }
        catch (IOException ex) {
            Logger.getLogger(Helper.class.getName()).log(Level.SEVERE, null, ex);
        }
        return javaKeywordSet;
    }
    private static TreeSet<String> getCKeywords()
    {
        if(cKeywordSet!=null)
            return cKeywordSet;
        cKeywordSet=new TreeSet<String>();
        URL url=Helper.class.getResource("/resources/cKeyword.txt");
         BufferedReader bufferedReader;
        try {
            bufferedReader = new BufferedReader(new InputStreamReader(url.openStream()));
            String s;
            while((s=bufferedReader.readLine())!=null)
            {
                cKeywordSet.add(s);
            }
        }
        catch (IOException ex) {
            Logger.getLogger(Helper.class.getName()).log(Level.SEVERE, null, ex);
        }
        return cKeywordSet;
    }
    private static TreeSet<String> getCPPKeywords()
    {
        if(cppKeywordSet!=null)
            return cppKeywordSet;
        cppKeywordSet=new TreeSet<String>();
        URL url=Helper.class.getResource("/resources/cppKeyword.txt");
        BufferedReader bufferedReader;
        try {
            bufferedReader = new BufferedReader(new InputStreamReader(url.openStream()));
            String s;
            while((s=bufferedReader.readLine())!=null)
            {
                cppKeywordSet.add(s);
            }
        }
        catch (IOException ex) {
            Logger.getLogger(Helper.class.getName()).log(Level.SEVERE, null, ex);
        }        
        return cppKeywordSet;
    }
    public static TreeSet<String> getReserveWordSet(String extention)
    {
        if(extention.equals("java"))
           return  getJavaReserveWordSet();
        else if(extention.equals("c"))
           return  null;
        else if(extention.equals("cpp"))
            return null;
        return null;
    }
    private static TreeSet<String> getJavaReserveWordSet()
    {
         if(javaKwCnSet!=null)
             return javaKwCnSet;
         javaKwCnSet=new TreeSet<String>();
        URL url=Helper.class.getResource("/resources/JavaClassesList.txt");
         BufferedReader bufferedReader;
        try {
            bufferedReader = new BufferedReader(new InputStreamReader(url.openStream()));
            String s;
            while((s=bufferedReader.readLine())!=null)
            {
                javaKwCnSet.add(s);
            }
        }
        catch(IOException ioException)
        {
            ioException.printStackTrace();
        }
        return javaKwCnSet;
    }
    public static TreeMap<String,String> getSymbolMap()
    {
        if(symbolMap!=null)
            return symbolMap;
        symbolMap=new TreeMap<String,String>();
        URL url=Helper.class.getResource("/resources/symbol.txt");
         BufferedReader bufferedReader;
        try {
            bufferedReader = new BufferedReader(new InputStreamReader(url.openStream()));
            String line;
            while((line=bufferedReader.readLine())!=null)
            {
                StringTokenizer sTokenizer=new StringTokenizer(line," ");
                String key=sTokenizer.nextToken();
                String value=sTokenizer.nextToken();
                symbolMap.put(key, value);
            }
        }
        catch (IOException ex) {
            Logger.getLogger(Helper.class.getName()).log(Level.SEVERE, null, ex);
        }
        return symbolMap;
    }
}
package codeeditor;
import edu.cmu.sphinx.api.Configuration;
import edu.cmu.sphinx.api.LiveSpeechRecognizer;
import edu.cmu.sphinx.api.SpeechResult;
import java.net.URL;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.StringTokenizer;
import javafx.collections.ObservableList;
/**
 *
 * @author princy
 */
public class VoiceRecognition extends Thread{
   private TreeSet<String> keywords=null;//(TreeSet<String>) Helper.getjavaKeywordSet();
   private TreeMap<String,String> symbolMap=null;//Helper.getSymbolMap();
   private VoiceRecognition speechThread=null;
   boolean isRunningSpeechThread=true;
   ObservableList<String> stringList=null;
   public VoiceRecognition(TreeSet<String> keywords,ObservableList<String> list)
   {
       
       super();
       this.keywords=keywords;
       this.stringList=list;
   }
   public String getAbsPath(String file)
   {
       URL url = getClass().getResource(file);
       return url.getPath();
   }
   public void run()
   {
       Configuration configuration = new Configuration();
        // Set path to the acoustic model.
        configuration.setAcousticModelPath("resource:/edu/cmu/sphinx/models/en-us/en-us");
        // Set path to the dictionary.
        
        String path=getAbsPath("/resources/5107.dic");
        path="file://"+path;
        
        try{
        configuration.setDictionaryPath(path);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }    
        path =getAbsPath("/resources/5107.lm");
        path="file://"+path;
        // Set path to the language model.
        configuration.setLanguageModelPath(path);
        //Recognizer object, Pass the Configuration object
        LiveSpeechRecognizer recognize =null;
        try{
         recognize = new LiveSpeechRecognizer(configuration);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        //Start Recognition Process (The bool parameter clears the previous cache if true)
        recognize.startRecognition(true);
 
        //Creating SpeechResult object
        SpeechResult result=null;
        //Check if recognizer recognized the speech
        while(isRunningSpeechThread)
        {
            if ((result = recognize.getResult()) != null) {
                if(isRunningSpeechThread==false)
                    break;
                String command = result.getHypothesis();
                StringTokenizer sToken=new StringTokenizer(command," ");
                while(sToken.hasMoreTokens())
                {
                    command=sToken.nextToken();
                    if(keywords.contains(command.toLowerCase()))
                    {
                        command=command.toLowerCase();
                        stringList.add(command);
                    }
                }
                recognize.stopRecognition();
                recognize.startRecognition(false);
            }
        }
   }
   public void stopSpeechThread()
   {
       isRunningSpeechThread=false;
   }
}
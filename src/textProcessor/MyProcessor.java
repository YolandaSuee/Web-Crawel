package textProcessor;


import de.l3s.boilerpipe.BoilerpipeProcessingException;
import de.l3s.boilerpipe.extractors.ArticleExtractor;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

public class MyProcessor {

    private static final String HTML_FILE_DIRECTORY = "D:/workspace/WebCrawler1/repository/";
    private static final String TXT_FILE_DIRECTORY = "D:/workspace/WebCrawler1/extract/";

    public static void main(String[] args) throws IOException, BoilerpipeProcessingException {
        File htmlDirectory = new File(HTML_FILE_DIRECTORY);
        List<String> fileList = Util.getFilelist(htmlDirectory);
        Iterator<String> it = fileList.iterator();
        HashMap<String, Integer> map = new HashMap<>();
        while(it.hasNext()){
            String thisHTML = it.next();
            System.out.println("Start processing file " + thisHTML);
            String fileData = Util.fileToString(thisHTML);
            String extractorOutput = ArticleExtractor.INSTANCE.getText(fileData);
            System.out.println("Processing file " + thisHTML + " done!");
            Util.stringToTxtFile(TXT_FILE_DIRECTORY,extractorOutput);
            map = Util.countWord(extractorOutput,map);
        }
        try{
            Util.writeHashMapToCsv(map);
            } catch (Exception e) {
            e.printStackTrace();
        }


    }

}

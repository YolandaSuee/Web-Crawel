package textProcessor;

import org.supercsv.io.CsvListWriter;
import org.supercsv.prefs.CsvPreference;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.supercsv.io.ICsvListWriter;


public class Util {

    public static String fileToString(String file) throws IOException{
        BufferedReader reader = new BufferedReader( new FileReader(file));
        String         line = null;
        StringBuilder  stringBuilder = new StringBuilder();
        String         ls = System.getProperty("line.separator");

        try {
            while( ( line = reader.readLine() ) != null ) {
                stringBuilder.append( line );
                stringBuilder.append( ls );
            }
            return stringBuilder.toString();
        } finally {
            reader.close();
        }

    }

    public static void stringToTxtFile(String file,String data){

        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(file));
            writer.write(data);
            System.out.println("Successfully store extracted text to " + file + " !");
            writer.close();
        } catch (IOException e) {
            System.out.println("Fail to store extracted text to file!");
            e.printStackTrace();
        }
    }

    public static List<String> getFilelist(File dir){
        List<String> fileList = new ArrayList<>();
        File[] files = dir.listFiles();
        for(File file: files){
            fileList.add(file.toString());
            System.out.println("One file added to FileList: " + file.toString());
        }
        return fileList;
    }

    public static HashMap<String, Integer> countWord(String out1, HashMap<String, Integer> map) {
        String trim = out1.replaceAll("\n"," ");
        trim = trim.replaceAll("\r"," ");
        trim = trim.replaceAll("\\p{Punct}+", "");
        trim = trim.toLowerCase();
        String[] splitted = trim.split(" ");
        int x;
        for (int i=0; i<splitted.length ; i++) {
            splitted[i].replaceAll(" ", "");
            if( !splitted[i].equals("") && !splitted[i].equals(" ")) {
                map.put(splitted[i], i);
            }
            System.out.println("word: " + splitted[i] + "; counts: " + i);
            if (map.containsKey(splitted[i])) {
                x = map.get(splitted[i]);
                map.put(splitted[i], x+1);
            }
        }
        return map;

    }

    public static void writeHashMapToCsv(HashMap<String, Integer> map) throws Exception {
        ICsvListWriter listWriter = null;
        try {
            listWriter = new CsvListWriter(new FileWriter("D:\\workspace\\WebCrawler1\\counts.csv"),
                    CsvPreference.STANDARD_PREFERENCE);
            System.out.println(listWriter);
            for (Map.Entry<String, Integer> entry : map.entrySet()){
                listWriter.write(entry.getKey(), entry.getValue());
            }
        }
        finally {
            if( listWriter != null ) {
                listWriter.close();
            }
        }
    }

}

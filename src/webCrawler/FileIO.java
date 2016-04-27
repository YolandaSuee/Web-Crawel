package webCrawler;

import java.io.*;
import java.util.List;

public class FileIO {

    private String mFilePath;

    public FileIO() {
        mFilePath = "";
    }


    public void writeReportHtml(List<String> recordList) throws IOException {
        File file = new File("report.html");
        PrintWriter writer = new PrintWriter(file);
        writer.println("<!DOCTYPE html>");
        writer.println("<html>");
        writer.println("<head>");
        writer.println("<title>Crawler Report</title>");
        writer.println("</head>");
        writer.println("<body>");
        writer.println("<h1>Crawler Report</h1>");
        for (int i = 0; i < recordList.size(); i++) {
            writer.println(recordList.get(i));
        }
        writer.println("</body>");
        writer.println("</html>");
        writer.close();
    }

    public boolean downloadHTML(String url, String directory, InputStream in, String contentType){
        mFilePath = directory + getFileName(url,contentType);
        System.out.println("File would be stored as " + mFilePath);
        if(saveHTML(in,mFilePath)){
            return true;
        }else{
            return false;
        }
    }

    public String getFilePath() { return mFilePath; }

    private String getFileName(String url, String contentType) {
        String subURL = "";
        String fileName = "";
        if(url.toLowerCase().contains("http")){
            subURL = url.substring(7);
        }
        if(url.toLowerCase().contains("https")){
            subURL = url.substring(8);
        }
        if (contentType.indexOf("html") != -1) {
            fileName = subURL.replaceAll("[\\?/:*|<>\"]", "_") + ".html";
            System.out.println("File Name is " + fileName);
        } else {
            System.out.println("This url contains no HTML/TEXT, would not be retrieved! " );
        }
        return fileName;
    }

    private boolean saveHTML(InputStream in, String filePath) {
        try {
            DataOutputStream out = new DataOutputStream(new FileOutputStream(new File(filePath)));
            System.out.println("Start saving HTML page to " + filePath);
            int readLen = 0;
            byte[] readBuffer = new byte[4*1024];
            while ((readLen = in.read(readBuffer)) != -1) {
                out.write(readBuffer, 0, readLen);
            }
            System.out.println("Successfully save HTML page to local disk!");
            out.flush();
            out.close();
            return true;
        } catch (IOException e) {
            System.out.println("Fail to save HTML page to local disk!");
            return false;
        }
    }

}

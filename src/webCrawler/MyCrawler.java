package webCrawler;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import java.io.IOException;
import java.io.InputStream;


public class MyCrawler {

    private static final String CRAWLED_FILE_DIRECTORY = "D:/workspace/WebCrawler1/repository/";
    private static final String CSV_FILENAME = "specification.csv";
    private static final long WAIT_TIME = 3000;
    private static final String REPORT_FILENAME="report.html";

    public static void main(String[] args) throws IOException {
        CrawlingSeed cs = new CrawlingSeed();
        FileIO fio = new FileIO();
        UrlQueue queue = new UrlQueue();
        UrlParser parser = new UrlParser();

        cs.initCrawler(CSV_FILENAME);
        String seedUrl = cs.getSeedUrl();

        if (!seedUrl.isEmpty()) {
            queue.addUnvisitedURL(seedUrl);
        }

        while (queue.hasUnvisitedPage() && queue.getVisitedPagesNum() <= cs.getMaxPages()) {

            String url = queue.dequeUnvisitedURL();
            Boolean hasDownloaded = false;
            int statusCode = -1;
            CloseableHttpClient httpClient = HttpClients.createDefault();

            try {
                HttpGet httpGet = new HttpGet(url);
                System.out.println("connecting to " + url);
                CloseableHttpResponse response = httpClient.execute(httpGet);
                System.out.println(response.getStatusLine());
                statusCode = response.getStatusLine().getStatusCode();
                if(statusCode!=200){
                    System.err.println("Unable to get " + url);
                }else {
                    try {
                        HttpEntity entity = response.getEntity();
                        InputStream content = entity.getContent();
                        String contentType = entity.getContentType().getValue();
                        if(fio.downloadHTML(url,CRAWLED_FILE_DIRECTORY,content,contentType)){
                            hasDownloaded = true;
                        }
                    } finally {
                        response.close();
                    }
                }
            }catch (IOException e) {
                System.out.println("Oops! Unexpected failure!");
            }finally {
                httpClient.close();
            }

            parser.parseUrl(url, queue);
            if(hasDownloaded){
                parser.addRecord(url, parser.getTitle(), fio.getFilePath(), statusCode,
                        parser.getNumsOfLinks(), parser.getNumsOfImgs());
            }

            queue.addVisitedURL(url);

            try {
                Thread.sleep(WAIT_TIME);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        fio.writeReportHtml(parser.getRecordList());
    }
}


package webCrawler;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class UrlParser {

    private int mNumsOfImgs;
    private int mNumsOfLinks;
    private List<String> mRecordList;
    private String mTitle;

    public UrlParser() {
        mNumsOfImgs = 0;
        mNumsOfLinks = 0;
        mTitle = "";
        mRecordList = new ArrayList<String>();
    }

    public void parseUrl(String url, UrlQueue queue) throws IOException {
        Document doc = Jsoup.connect(url).get();
        mTitle = doc.title();
        Elements links = doc.select("a[href]");
        Elements images = doc.getElementsByTag("img");
        mNumsOfImgs = links.size();
        mNumsOfImgs = images.size();
        for (Element link : links) {
            String linkURL = link.attr("abs:href");
            System.out.println("One URL parsed! - " + linkURL);
            queue.addUnvisitedURL(linkURL);
        }
    }

    public void addRecord(
            String url, String title, String filePath,
            int statusCode, int numsOfLinks, int numsOfImages) {
        String record = "<p><a href=\"" + url + "\">" + title + "</a> <a href=\"" + filePath +
                "\"> local file</a>   HTTP " + statusCode + "  " + numsOfLinks + " Outlinks  " + numsOfImages + " Images </p>";
        mRecordList.add(record);
        System.out.println("Added one record to recordList!");
    }

    public int getNumsOfLinks() {
        return mNumsOfLinks;
    }

    public int getNumsOfImgs() {
        return mNumsOfImgs;
    }

    public String getTitle() {
        return mTitle;
    }

    public List<String> getRecordList() {
        return mRecordList;
    }
}

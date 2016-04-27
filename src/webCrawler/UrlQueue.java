package webCrawler;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;

public class UrlQueue {

    private Set<String> mVisitedPages;
    private Queue<String> mUnvisitedPages;

    public UrlQueue() {
        mVisitedPages = new HashSet<>();
        mUnvisitedPages = new LinkedList<>();
    }

    public void addVisitedURL(String url) {
        mVisitedPages.add(url);
        System.out.println("Add URL " + url + " to visited URLs list.");
    }

    public String dequeUnvisitedURL() {
        return mUnvisitedPages.poll();
    }

    public void addUnvisitedURL(String url) {
        if (!mVisitedPages.contains(url) && !mUnvisitedPages.contains(url)) {
            mUnvisitedPages.add(url);
            System.out.println("Add URL " + url + " to unvisited URLs list.");
        }
    }

    public int getVisitedPagesNum() {
        return mVisitedPages.size();
    }

    public boolean hasUnvisitedPage() {
        return !mUnvisitedPages.isEmpty();
    }

}

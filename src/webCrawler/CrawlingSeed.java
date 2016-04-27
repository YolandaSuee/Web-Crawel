package webCrawler;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class CrawlingSeed {
    private String mSeedUrl;
    private int mMaxPages;
    private String mDomainRestrict;

    public CrawlingSeed() {
        mSeedUrl = "";
        mMaxPages = 0;
        mDomainRestrict = "";
    }

    public void initCrawler(String fileName) {
        BufferedReader br;
        String line;
        String cvsSplit = ",";

        try {
            br = new BufferedReader(new FileReader(fileName));
            System.out.println("Getting seed URL from + " + fileName + " ...");

            while ((line = br.readLine()) != null) {

                String[] seedUrlInfo = line.split(cvsSplit);
                mSeedUrl = seedUrlInfo[0];
                System.out.println("Seed URL for crawler: " + mSeedUrl);

                mMaxPages = Integer.valueOf(seedUrlInfo[1]);
                System.out.println("Maximum number of pages to crawl in total: " + mMaxPages);

                if (seedUrlInfo.length>2 && seedUrlInfo[2].trim()!="") {
                    mDomainRestrict = seedUrlInfo[2];
                    System.out.println("Domain restriction: " + mDomainRestrict);
                } else {
                    System.out.println("No domain restriction!");
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Done getting seed URL!");
    }

    public String getSeedUrl() {
        return mSeedUrl;
    }

    public int getMaxPages() {
        return mMaxPages;
    }

    public String getDomainRestrict() {
        return mDomainRestrict;
    }
}
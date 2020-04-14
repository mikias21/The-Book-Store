package webcontentscrapper;

import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;


// class is responsible for scrapping out the data from the website www.pdfdrive.com
public class WebContentScrapper {
    // store the url
    private StringBuilder stringBuilder = new StringBuilder();
    private ArrayList<String> img = new ArrayList<String>(); // store img url
    private ArrayList<String> page = new ArrayList<String>(); // store book page number
    private ArrayList<String> hit = new ArrayList<String>(); // store download number
    private ArrayList<String> title = new ArrayList<String>(); // store title
    private ArrayList<String> year = new ArrayList<String>(); // store year
    private ArrayList<String> downloadpage = new ArrayList<>(); // store download page
    private ArrayList<String> downloadlinks = new ArrayList<>(); // stores download link
    private String queryName; // store the query name
    private int pageNo = 0; // store the html page number

    public WebContentScrapper(){
        stringBuilder.append("https://www.pdfdrive.com/search?q=");
    }

    // set the query name with the string builder as url
    public void setQueryName(String queryName , int pageNo){
        this.queryName = queryName;
        this.pageNo += pageNo;
        stringBuilder.append(queryName + "&pagecount=&pubyear=&searchin=");
        stringBuilder.append("&page=" + this.pageNo);
    }
    public String getQueryName(){return this.queryName;}
    public int getPageNo(){return this.pageNo;}
    public StringBuilder getStringBuilder(){return this.stringBuilder;}

    // method that get the download links
    public ArrayList<String> getDownloadLink() {
        // pattern to extract the download link
        Pattern pattern = Pattern.compile("\\w\\d\\d\\d\\d\\d\\d\\d?");
        try{
            // html document from the website
            final Document document = Jsoup.connect(String.valueOf(stringBuilder)).get();
            // iterate over the elements and select the specific element
            for(Element row: document.select("div.file-right a")) {
                // add the download page to the downloadPage Array list
                downloadpage.add("https://www.pdfdrive.com"+row.attr("href"));
            }
        }catch(Exception e){}

        // from the download page extract the download link from the entire url
        // and then store in the downloadLink Array list
        for(String val: downloadpage){
            String [] splits = val.split("-");
            String value = splits[splits.length - 1];
            splits[splits.length - 1] = value.replace(value.charAt(0) , 'd');
            String downloadable = String.join("-",splits);
            downloadlinks.add(downloadable);
        }
        return downloadlinks;
    }

    // method gets all the web contents
    public List<ArrayList<String>> getWebContent(){
        try{
            // get the document from the website and iterate over all the document
            // to get the specific items
            final Document document = Jsoup.connect(String.valueOf(stringBuilder)).get();
            // gets the image url
            for(Element row: document.select("img.file-img")) {
                img.add(row.attr("src").toString());
            }
            // gets the book titles
            for(Element row: document.select("div.file-right h2")) {
                title.add(row.text());
            }
            // gets the book page numbers
            for(Element row: document.select("div.file-info span.fi-pagecount")) {
                page.add(row.text());
            }
            // gets the book published year
            for(Element row: document.select("div.file-info span.fi-year")) {
                year.add(row.text());
            }
            //get the download numbers
            for(Element row: document.select("div.file-info span.fi-hit")) {
                hit.add(row.text().toString());
            }
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
        // returns all this elements as a list together for further extraction
        return Arrays.asList(img , title , year , page , hit);
    }

}

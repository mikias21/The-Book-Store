package secondwindow;

import webcontentscrapper.WebContentScrapper;
import java.util.ArrayList;
import java.util.List;

// class responsible for handling scrapped data from www.pdfdrive.com
// it process all the data that is returned from the site
// arranges them according to title , img , page ...
public class ScrappedBooks {

    private WebContentScrapper webContentScrapper = new WebContentScrapper(); // WebContentScrapper object from webcontentscrapper package
    private List<ArrayList<String>> data; // stores main data
    private ArrayList<String> downloadlinks; // stores download links
    private ArrayList<String> img = new ArrayList<String>(); // stores img url
    private ArrayList<String> title = new ArrayList<String>(); // stores title
    private ArrayList<String> page = new ArrayList<String>(); // stores page number
    private ArrayList<String> year = new ArrayList<String>(); // stores year
    private ArrayList<String> hit = new ArrayList<String>(); // stores hit / likes
    private ArrayList<String> downloadables = new ArrayList<String>(); // stores downloadable links

    public ScrappedBooks(String queryName , int pageNo){
        // page number is the html page number not the book page number
        this.webContentScrapper.setQueryName(queryName , pageNo); // set the webContentScrapper query values to search title and page number
        data = webContentScrapper.getWebContent(); // get the data
        downloadlinks = webContentScrapper.getDownloadLink(); // get the downloadable links
    }

    // get image method
    public ArrayList<String> getImage(){
        img.addAll(data.get(0));
        return img;
    }
    // get title method
    public ArrayList<String> getTitle(){
        title.addAll(data.get(1));
        return title;
    }
    // get pageNumber method
    public ArrayList<String> getPage(){
        page.addAll(data.get(2));
        return page;
    }
    // get published year method
    public ArrayList<String> getYear(){
        year.addAll(data.get(3));
        return year;
    }
    // get the Hit or downloads method
    public ArrayList<String> getHit(){
        hit.addAll(data.get(4));
        return hit;
    }
    // get the downloadable method
    public ArrayList<String> getDownloadables(){
        downloadables.addAll(downloadlinks);
        return downloadables;
    }
}

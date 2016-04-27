package main;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Date;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.queryParser.QueryParser;
import org.apache.lucene.search.Hits;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.Searcher;

public class SearchIndex {
	public static final String INDEX_DIRECTORY = "index";
	
	public static void main(String[] args) throws Exception {
		String field = "contents";
		
		IndexReader reader = IndexReader.open(INDEX_DIRECTORY);

	    Searcher searcher = new IndexSearcher(reader);
	    Analyzer analyzer = new StandardAnalyzer();

	    BufferedReader in = new BufferedReader(new InputStreamReader(System.in, "UTF-8"));
	    QueryParser parser = new QueryParser(field, analyzer);
	    while (true) {
	    	System.out.println("Enter query: ");
	    	
	        String line = in.readLine();
	        if (line == null || line.length() == -1) break;
	        line = line.trim();
	        if (line.length() == 0) break;
	        
	        Date start = new Date();
	        Query query = parser.parse(line);

	        Hits hits = searcher.search(query);
	        Date end = new Date();
		    System.out.println("[Search for '" + query.toString(field) + "'] " + (end.getTime() - start.getTime()) + " ms");
	        if (hits.length() == 0) System.out.println("No result :(");
		    for(int i=0; i<hits.length(); i++) {
	        	Document doc = hits.doc(i);
	        	System.out.println((i+1) + ". " + doc.get("path"));
	        	System.out.println("   doc="+hits.id(i)+" score="+hits.score(i));
	        }
	        System.out.println("\n");
	    }
	    reader.close();
	}
}

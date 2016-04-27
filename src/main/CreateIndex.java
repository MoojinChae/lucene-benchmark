package main;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Date;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.demo.FileDocument;
import org.apache.lucene.index.IndexWriter;

public class CreateIndex {
	public static final String FILES_DIRECTORY = "data";
	public static final String INDEX_DIRECTORY = "index";
	
	public static void main(String[] args) throws Exception {
		File docDir = new File(FILES_DIRECTORY);
		File indexDir = new File(INDEX_DIRECTORY);
		
		Date start = new Date();
		IndexWriter writer = new IndexWriter(indexDir, new StandardAnalyzer(), true);
		indexDocs(writer, docDir);
	    writer.optimize();
	    writer.close();
	    Date end = new Date();
	    
	    System.out.println("[Index Create] " + (end.getTime() - start.getTime()) + " ms");
	}
	
	private static void indexDocs(IndexWriter writer, File file) throws IOException {
		if (file.canRead()) {
			if (file.isDirectory()) {
				String[] files = file.list();
		        if (files != null) {
		        	for (int i = 0; i < files.length; i++) {
		        		indexDocs(writer, new File(file, files[i]));
		        	}
		        }
		    }
			else {
		    	 System.out.println("adding " + file);
		    	 try {
		    		 writer.addDocument(FileDocument.Document(file));
		    	 }
		    	 catch (FileNotFoundException fnfe) {}
		    }
		}
	}
}

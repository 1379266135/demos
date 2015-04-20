package com.niu.demos.images.cache;

import java.io.BufferedInputStream;
import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Writer;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;

import android.content.SharedPreferences.Editor;

public class DiskLruCache implements Closeable{
    static final String JOURNAL_FILE = "journal";
    static final String JOURNAL_FILE_TMP = "journal.tmp";
    
    private static final String CLEAN = "CLEAN";
    private static final String DIRTY = "DIRTY";
    private static final String REMOVE = "REMOVE";
    private static final String READ = "READ";

    private Writer journalWriter;
    private final File directory;
    private final File journalFile;
    private final File journalFileTmp;
    private final int appVersion;
    private final long maxSize;
    private final int valueCount;
    private long size = 0;
    
    private final LinkedHashMap<String, Entry> lruEntries = new LinkedHashMap<String, Entry>(0, 0.75f, true);
    
    private DiskLruCache(File directory, 
    						int appVersion, 
    						int valueCount,
    						long maxSize){
    	this.appVersion = appVersion;
    	this.directory = directory;
    	this.valueCount = valueCount;
    	this.maxSize = maxSize;
        this.journalFile = new File(directory, JOURNAL_FILE);
        this.journalFileTmp = new File(directory, JOURNAL_FILE_TMP);
    }
    
	@Override
	public void close() throws IOException {
		
	}
	
	public boolean isClosed(){
		return journalWriter == null;
	}
	
	public static DiskLruCache open(File directory, int appVersion, int valueCount, long maxSize, boolean checkJournal){
        if (maxSize <= 0) {
            throw new IllegalArgumentException("maxSize <= 0");
        }
        if (valueCount <= 0) {
            throw new IllegalArgumentException("valueCount <= 0");
        }
        
        DiskLruCache cache = new DiskLruCache(directory, appVersion, valueCount, maxSize);
        if(cache.journalFile.exists()){
        	try {
//				cache.readAsciiLine(in);
			} catch (Exception e) {
				// TODO: handle exception
			}
        }
        return cache;
	}
	
	private void readJournal() throws IOException{
		InputStream in = new BufferedInputStream(new FileInputStream(journalFile),  8 * 1024);
		try {
			String magic = readAsciiLine(in);
			String version = readAsciiLine(in);
			String appVersionString = readAsciiLine(in);
			String valueCountString = readAsciiLine(in);
			String blank = readAsciiLine(in);
			
			while (true) {
//				readJournal()
			}
		} finally {
			
		}
	}
	
	public static String readAsciiLine(InputStream in) throws IOException{
		StringBuilder resulBuilder = new StringBuilder(80);
		while (true) {
			int c = in.read();
			if(c == -1){
				throw new IOException();
			} else if(c == '\n'){
				break;
			}
			
			resulBuilder.append((char)c);
		}
		int length = resulBuilder.length();
		if(length > 0 && resulBuilder.charAt(length-1) == '\r'){
			resulBuilder.setLength(length - 1);
		}
		
		return resulBuilder.toString();
	}

	private void readJournalLine(String line) throws IOException{
		String[] parts = line.split(" ");
		if(parts.length < 2){
			throw new IOException("unexpected journal line: " + line);
		}
		
		String key = parts[1];
		if(parts[0].equals(REMOVE) && parts.length == 2){
			lruEntries.remove(key);
			return;
		}
		
		Entry entry = lruEntries.get(key);
		if(entry == null){
			entry = new Entry(key);
			lruEntries.put(key, entry);
		}
		
		if(parts[0].equals(CLEAN) && parts.length == 2+valueCount){
			entry.readable = true;
			entry.currentEditor = null;
			entry.setLengths(copyOfRange(parts, 2, parts.length));
		} else if(parts[0].equals(DIRTY) && parts.length == 2){
			entry.currentEditor = new Editor(entry);
		} else if(parts[0].equals(READ) && parts.length == 2){
			
		} else {
			throw new IOException("unexpected journal line: " + line);
		}
	}
	
	private static <T> T[] copyOfRange(T[] original, int start, int end){
		final int originalLength = original.length;
		
		if(start > end){
			throw new IllegalArgumentException();
		}
		
		if (start < 0 || start > originalLength) {
            throw new ArrayIndexOutOfBoundsException();
        }
		
		final int resultLength = end - start;
		final int copyLength = Math.min(resultLength, originalLength - start);
		final T[] result = (T[]) Array.newInstance(original.getClass().getComponentType(), resultLength);
		System.arraycopy(original, start, result, 0, copyLength);
		
		return result;
	}
	
	private final class Entry {
        private final String key;

        /** 文件长度 */
        private final long[] lengths;

        /** True if this entry has ever been published */
        private boolean readable;

        /** The ongoing edit or null if this entry is not being edited.*/
        private Editor currentEditor;

        /**
         * The sequence number of the most recently committed edit to this
         * entry.
         */
        private long sequenceNumber;
		private Entry(String key) {
			this.key = key;
			this.lengths = new long[valueCount];
		}
		
		private void setLengths(String[] strings) throws IOException{
			if(strings.length != valueCount){
				throw new IOException("unexpected journal line: "
	                    + Arrays.toString(strings));
			}
			
			try {
				for (int i = 0; i < strings.length; i++) {
					lengths[i] = Long.parseLong(strings[i]);
				}
			} catch (NumberFormatException e) {
				throw new IOException("unexpected journal line: "
	                    + Arrays.toString(strings));
			}
		}
	}
	
	public final class Editor {
		private final Entry entry;
		private boolean hasErrors;
		
		public Editor(Entry entry){
			this.entry = entry;
		}

	}
}

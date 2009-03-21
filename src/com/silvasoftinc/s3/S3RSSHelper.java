// Copyright (c) 2006 SilvaSoft, Inc.
//
// Permission is hereby granted, free of charge, to any person obtaining a
// copy of this software and associated documentation files (the "Software"),
// to deal in the Software without restriction, including without limitation
// the rights to use, copy, modify, merge, publish, distribute, sublicense,
// and/or sell copies of the Software, and to permit persons to whom the 
// Software is furnished to do so, subject to the following conditions:
//
// The above copyright notice and this permission notice shall be included in
// all copies or substantial portions of the Software.
//
// THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
// IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
// FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. 
// IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, 
// DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR
// OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE
// USE OR OTHER DEALINGS IN THE SOFTWARE.

// author:    http://www.silvasoftinc.com
// author:    Dominic Da Silva (dominic.dasilva@gmail.com)
// version:   2.1
// date:      04/19/2006

package com.silvasoftinc.s3;

import java.text.SimpleDateFormat;
import java.util.Date;

public class S3RSSHelper {
	private StringBuffer rssDoc;

	public S3RSSHelper() {
		rssDoc = new StringBuffer();
	}

	public void createRSSDocument() {
		rssDoc.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
		rssDoc.append("\n");
	}

	public void openRSSDocument() {
		rssDoc.append("<rss version=\"2.0\">");
		rssDoc.append("\n");
	}

	public void closeRSSDocument() {
		rssDoc.append("</rss>");
	}

	public void openRSSChannel() {
		rssDoc.append("<channel>");
		rssDoc.append("\n");
	}

	public void addRSSChannelInfo(String title, String link, String desc,
			Date lastBuildDate, String language) {
		SimpleDateFormat dateFormat = new SimpleDateFormat(
				"EEE, d MMM yyyy HH:mm:ss z");
		rssDoc.append("<title>" + title + "</title>");
		rssDoc.append("\n");
		rssDoc.append("<link>" + link + "</link>");
		rssDoc.append("\n");
		rssDoc.append("<description>" + desc + "</description>");
		rssDoc.append("\n");
		rssDoc.append("<lastBuildDate>" + dateFormat.format(lastBuildDate)
				+ "</lastBuildDate>");
		rssDoc.append("\n");
		rssDoc.append("<language>" + language + "</language>");
		rssDoc.append("\n");
	}

	public void closeRSSChannel() {
		rssDoc.append("</channel>");
		rssDoc.append("\n");
	}

	public void openRSSItem() {
		rssDoc.append("<item>");
		rssDoc.append("\n");
	}

	public void closeRSSItem() {
		rssDoc.append("</item>");
		rssDoc.append("\n");
	}

	public void addRSSItemInfo(String title, String author, String link,
			String description, Date pubDate, String guid) {
		SimpleDateFormat dateFormat = new SimpleDateFormat(
				"EEE, d MMM yyyy HH:mm:ss z");
		rssDoc.append("<title>" + title + "</title>");
		rssDoc.append("\n");
		rssDoc.append("<author>" + author + "</author>");
		rssDoc.append("\n");
		rssDoc.append("<link>" + link + "</link>");
		rssDoc.append("\n");
		rssDoc.append("<guid>" + guid + "</guid>");
		rssDoc.append("\n");
		rssDoc.append("<pubDate>" + dateFormat.format(pubDate) + "</pubDate>");
		rssDoc.append("\n");
	}

	public void addRSSItemEnclosure(String url, String length, String type) {
		rssDoc.append("<enclosure url=\"" + url + "\" length=\"" + length
				+ "\" type=\"" + type + "\" />");
		rssDoc.append("\n");
	}

	public String getRSSDocument() {
		return this.rssDoc.toString();
	}
}

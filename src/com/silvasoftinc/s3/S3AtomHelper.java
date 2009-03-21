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

public class S3AtomHelper {
	private StringBuilder atomDoc;

	public S3AtomHelper() {
		atomDoc = new StringBuilder();
	}

	public void createAtomDocument() {
		atomDoc.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
		atomDoc.append("\n");
	}

	public void openAtomDocument() {
		atomDoc.append("<feed xmlns=\"http://www.w3.org/2005/Atom\">");
		atomDoc.append("\n");
	}

	public void closeAtomDocument() {
		atomDoc.append("</feed>");
	}

	public void addAtomFeedInfo(String title, String subtitle, String link,
			Date updated, String author, String email, String id) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
		atomDoc.append("<title>" + title + "</title>");
		atomDoc.append("\n");
		atomDoc.append("<subtitle>" + subtitle + "</subtitle>");
		atomDoc.append("\n");
		atomDoc.append("<updated>" + dateFormat.format(updated) + "T"
				+ timeFormat.format(updated) + "Z" + "</updated>");
		atomDoc.append("\n");
		atomDoc.append("<author>");
		atomDoc.append("\n");
		atomDoc.append("<name>" + author + "</name>");
		atomDoc.append("\n");
		atomDoc.append("<email>" + email + "</email>");
		atomDoc.append("\n");
		atomDoc.append("</author>");
		atomDoc.append("\n");
		atomDoc.append("<id>" + id + "</id>");
		atomDoc.append("\n");
	}

	public void openAtomEntry() {
		atomDoc.append("<entry>");
		atomDoc.append("\n");
	}

	public void closeAtomEntry() {
		atomDoc.append("</entry>");
		atomDoc.append("\n");
	}

	public void addAtomEntryInfo(String title, String link, String id,
			Date updated, String summary) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
		atomDoc.append("<title>" + title + "</title>");
		atomDoc.append("\n");
		atomDoc.append("<link href=\"" + link + "\" />");
		atomDoc.append("\n");
		atomDoc.append("<id>" + id + "</id>");
		atomDoc.append("\n");
		atomDoc.append("<updated>" + dateFormat.format(updated) + "T"
				+ timeFormat.format(updated) + "Z" + "</updated>");
		atomDoc.append("\n");
		atomDoc.append("<summary>" + summary + "</summary>");
		atomDoc.append("\n");
	}

	public String getAtomDocument() {
		return this.atomDoc.toString();
	}
}

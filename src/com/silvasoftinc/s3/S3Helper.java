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

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.zip.DataFormatException;
import java.util.zip.Deflater;
import java.util.zip.Inflater;

public class S3Helper {

	/**
	 * Compress a byte array using ZLIB compresssion
	 * 
	 * @param uncompressedData
	 *            byte array of uncompressed data
	 * @return byte array of compressed data
	 */
	public static byte[] compressBytes(byte[] uncompressedData) {
		// Create the compressor with highest level of compression
		Deflater compressor = new Deflater();
		compressor.setLevel(Deflater.BEST_COMPRESSION);
	
		// Give the compressor the data to compress
		compressor.setInput(uncompressedData);
		compressor.finish();
	
		// Create an expandable byte array to hold the compressed data.
		// You cannot use an array that's the same size as the orginal because
		// there is no guarantee that the compressed data will be smaller than
		// the uncompressed data.
		ByteArrayOutputStream bos = new ByteArrayOutputStream(
				uncompressedData.length);
	
		// Compress the data
		byte[] buf = new byte[1024];
		while (!compressor.finished()) {
			int count = compressor.deflate(buf);
			bos.write(buf, 0, count);
		}
		try {
			bos.close();
		} catch (IOException e) {
		}
	
		// Get the compressed data
		byte[] compressedData = bos.toByteArray();
		return compressedData;
	}

	/**
	 * Decompress a byte array compressed with ZLIB compresssion
	 * 
	 * @param compressedData
	 *            byte array of compressed data
	 * @return byte array of decompressed data
	 */
	public static byte[] decompressBytes(byte[] compressedData) {
		// Create the decompressor and give it the data to compress
		Inflater decompressor = new Inflater();
		decompressor.setInput(compressedData);
	
		// Create an expandable byte array to hold the decompressed data
		ByteArrayOutputStream bos = new ByteArrayOutputStream(
				compressedData.length);
	
		// Decompress the data
		byte[] buf = new byte[1024];
		while (!decompressor.finished()) {
			try {
				int count = decompressor.inflate(buf);
				bos.write(buf, 0, count);
			} catch (DataFormatException e) {
			}
		}
		try {
			bos.close();
		} catch (IOException e) {
		}
	
		// Get the decompressed data
		byte[] decompressedData = bos.toByteArray();
		return decompressedData;
	}

	/**
	 * Gets Public-Read ACL
	 * 
	 * @param selfId
	 * @return
	 */
	public static String getACLTemplatePublicRead(String selfId) {
		StringBuffer acl = new StringBuffer();
		acl.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
		acl
				.append("<AccessControlPolicy xmlns=\"http://s3.amazonaws.com/doc/2006-03-01/\">");
		acl.append("<Owner>");
		acl.append("<ID>" + selfId + "</ID>");
		acl.append("</Owner>");
		acl.append("<AccessControlList>");
		acl.append("<Grant>");
		acl
				.append("<Grantee xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:type=\"CanonicalUser\">");
		acl.append("<ID>" + selfId + "</ID>");
		acl.append("</Grantee>");
		acl.append("<Permission>FULL_CONTROL</Permission>");
		acl.append("</Grant>");
		acl.append("<Grant>");
		acl
				.append("<Grantee xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:type=\"Group\">");
		acl
				.append("<URI>http://acs.amazonaws.com/groups/global/AllUsers</URI>");
		acl.append("</Grantee>");
		acl.append("<Permission>READ</Permission>");
		acl.append("</Grant>");
		acl.append("</AccessControlList>");
		acl.append("</AccessControlPolicy>");

		return acl.toString();
	}

	/**
	 * Gets Public-Read-Write ACL
	 * 
	 * @param selfId
	 * @return
	 */
	public static String getACLTemplatePublicReadWrite(String selfId) {
		StringBuffer acl = new StringBuffer();
		acl.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
		acl
				.append("<AccessControlPolicy xmlns=\"http://s3.amazonaws.com/doc/2006-03-01/\">");
		acl.append("<Owner>");
		acl.append("<ID>" + selfId + "</ID>");
		acl.append("</Owner>");
		acl.append("<AccessControlList>");
		acl.append("<Grant>");
		acl
				.append("<Grantee xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:type=\"CanonicalUser\">");
		acl.append("<ID>" + selfId + "</ID>");
		acl.append("<DisplayName>duspense</DisplayName>");
		acl.append("</Grantee>");
		acl.append("<Permission>FULL_CONTROL</Permission>");
		acl.append("</Grant>");
		acl.append("<Grant>");
		acl
				.append("<Grantee xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:type=\"Group\">");
		acl
				.append("<URI>http://acs.amazonaws.com/groups/global/AllUsers</URI>");
		acl.append("</Grantee>");
		acl.append("<Permission>READ</Permission>");
		acl.append("</Grant>");
		acl.append("<Grant>");
		acl
				.append("<Grantee xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:type=\"Group\">");
		acl
				.append("<URI>http://acs.amazonaws.com/groups/global/AllUsers</URI>");
		acl.append("</Grantee>");
		acl.append("<Permission>WRITE</Permission>");
		acl.append("</Grant>");
		acl.append("</AccessControlList>");
		acl.append("</AccessControlPolicy>");

		return acl.toString();
	}

	/**
	 * Gets Private ACL
	 * 
	 * @param selfId
	 * @return
	 */
	public static String getACLTemplatePrivate(String selfId) {
		StringBuffer acl = new StringBuffer();
		acl.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
		acl
				.append("<AccessControlPolicy xmlns=\"http://s3.amazonaws.com/doc/2006-03-01/\">");
		acl.append("<Owner>");
		acl.append("<ID>" + selfId + "</ID>");
		acl.append("</Owner>");
		acl.append("<AccessControlList>");
		acl.append("<Grant>");
		acl
				.append("<Grantee xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:type=\"CanonicalUser\">");
		acl.append("<ID>" + selfId + "</ID>");
		acl.append("</Grantee>");
		acl.append("<Permission>FULL_CONTROL</Permission>");
		acl.append("</Grant>");
		acl.append("</AccessControlList>");
		acl.append("</AccessControlPolicy>");

		return acl.toString();
	}
	
}

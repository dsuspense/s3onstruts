package com.silvasoftinc.s3onstruts.action;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.actions.MappingDispatchAction;

import com.amazon.s3.AWSAuthConnection;

public class BaseMappingDispatchAction extends MappingDispatchAction {

	protected static String SUCCESS = "success";

	protected static String ITEM = "item";

	protected static String ITEMS = "items";
	
	protected static String ACL = "acl";

	protected static String BUCKET = "bucket";

	protected static String BUCKETS = "buckets";

	protected static String S3_CONN = "s3_conn";
	
	protected static String FLASH = "flash";

	protected static final int RESPONSE_OK = 200;

	protected String bucket;

	protected AWSAuthConnection s3Conn;

	protected void initAWSConnection(HttpServletRequest request) {
		bucket = (String) request.getParameter(BUCKET);
		s3Conn = (AWSAuthConnection) request.getSession().getAttribute(S3_CONN);
	}

}

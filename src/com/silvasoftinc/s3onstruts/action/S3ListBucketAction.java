package com.silvasoftinc.s3onstruts.action;

import java.util.logging.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.amazon.s3.AWSAuthConnection;
import com.amazon.s3.ListBucketResponse;

public class S3ListBucketAction extends Action {

	private static Logger logger = Logger.getLogger(S3ListBucketAction.class.getName());
    public static String SUCCESS = "success";
    private static String BUCKET = "bucket";
    private static String ITEMS = "items";
    private static String S3_CONN = "s3_conn";
    
	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		String bucket = (String) request.getParameter(BUCKET);
		AWSAuthConnection s3Conn = (AWSAuthConnection) request.getSession().getAttribute(S3_CONN);
		
		ListBucketResponse items = s3Conn.listBucket(bucket, null, null, null, null);
		request.setAttribute(ITEMS, items.entries);
		logger.info(bucket + " items " + items.entries);
		request.setAttribute(BUCKET, bucket);
		request.getSession().setAttribute(S3_CONN, s3Conn);

		return mapping.findForward(SUCCESS);
	}
}

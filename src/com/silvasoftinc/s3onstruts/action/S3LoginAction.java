package com.silvasoftinc.s3onstruts.action;

import java.util.logging.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.validator.DynaValidatorForm;

import com.amazon.s3.AWSAuthConnection;
import com.amazon.s3.ListAllMyBucketsResponse;

public class S3LoginAction extends Action {

	private static Logger logger = Logger.getLogger(S3LoginAction.class.getName());
    public static String SUCCESS = "success";
    public static String FAILURE = "failure";
    public static String CANCEL = "cancel";
    private static String S3_HOST = "s3.amazonaws.com";
    private static String BUCKETS = "buckets";
    private static String S3_CONN = "s3_conn";
	private static String FLASH = "flash";
    
	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		DynaValidatorForm s3LoginForm = (DynaValidatorForm) form;
		
		if (this.isCancelled(request)) {
			return mapping.findForward(CANCEL);
		}
		
		String s3AccessKeyId = (String) s3LoginForm.get("s3AccessKeyId");
		String s3SecretAccessKey = (String) s3LoginForm.get("s3SecretAccessKey");
		if (s3AccessKeyId == null || s3SecretAccessKey == null) {
			logger.info("S3 login attempted with emtpy input");
			request.setAttribute(FLASH, "Error: Please enter your S3 Access Key ID and S3 Secret Access Key");
			return mapping.findForward(FAILURE);
		}
		if (s3AccessKeyId.equals("") || s3SecretAccessKey.equals("")) {
			logger.info("S3 login attempted with emtpy input");
			request.setAttribute(FLASH, "Error: Please enter your S3 Access Key ID and S3 Secret Access Key");
			return mapping.findForward(FAILURE);
		}
		
		AWSAuthConnection s3Conn = this.initAWSAuthConnection(S3_HOST, s3AccessKeyId, s3SecretAccessKey);
		
		ListAllMyBucketsResponse buckets = s3Conn.listAllMyBuckets(null);
		request.getSession().setAttribute(S3_CONN, s3Conn);
		request.getSession().setAttribute(BUCKETS, buckets.entries);
		logger.info("S3 buckets " + buckets.entries);

		return mapping.findForward(SUCCESS);
	}

	/**
	 * Initialize the Amazon S3 connection.
	 * 
	 * @param host
	 *            the S3 host
	 * @param user
	 *            the Amazon Access Key ID
	 * @param pass
	 *            the Amazon Secret Access Key
	 */
	private AWSAuthConnection initAWSAuthConnection(final String host, final String user,
			final String pass) {
		// Default to isSecure=true
		return new AWSAuthConnection(user, pass, true, host);
	}	
}

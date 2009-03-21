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
import com.amazon.s3.Response;

public class CreateBucketAction extends Action {

	private static Logger logger = Logger.getLogger(CreateBucketAction.class
			.getName());

	private static String SUCCESS = "success";

	protected static String S3_CONN = "s3_conn";

	protected static String BUCKETS = "buckets";

	protected static String FLASH = "flash";
	
	protected static final int RESPONSE_OK = 200;

	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		DynaValidatorForm createBucketForm = (DynaValidatorForm) form;

		String bucketName = (String) createBucketForm.get("bucketName");
		AWSAuthConnection s3Conn = (AWSAuthConnection) request.getSession()
				.getAttribute(S3_CONN);
		Response s3Response = s3Conn.createBucket(bucketName, null);

		if (RESPONSE_OK == s3Response.connection.getResponseCode()) {
			logger.info("Created bucket '" + bucketName + "'");
			request
					.setAttribute(FLASH, "Created bucket '" + bucketName
							+ "'");
			ListAllMyBucketsResponse buckets = s3Conn.listAllMyBuckets(null);
			request.getSession().setAttribute(S3_CONN, s3Conn);
			request.getSession().setAttribute(BUCKETS, buckets.entries);
			logger.info("S3 buckets " + buckets.entries);
		} else {
			logger.info(s3Response.connection.getResponseMessage());
			request.setAttribute(FLASH, "Error: "
					+ s3Response.connection.getResponseMessage());
		}

		createBucketForm.initialize(mapping);

		return mapping.findForward(SUCCESS);
	}

}

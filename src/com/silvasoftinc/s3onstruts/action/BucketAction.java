package com.silvasoftinc.s3onstruts.action;

import java.util.logging.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.amazon.s3.ListAllMyBucketsResponse;
import com.amazon.s3.Response;
import com.amazon.s3.S3Object;
import com.silvasoftinc.s3.S3Helper;

public class BucketAction extends BaseMappingDispatchAction {

	private static Logger logger = Logger.getLogger(BucketAction.class
			.getName());

	public ActionForward delete(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		initAWSConnection(request);

		Response s3Response = s3Conn.deleteBucket(bucket, null);
		if (RESPONSE_OK == s3Response.connection.getResponseCode()) {
			logger.info("Deleted bucket '" + bucket + "'");
			request.setAttribute(FLASH, "Deleted bucket '" + bucket + "'");
		} else {
			logger.info(s3Response.connection.getResponseMessage());
			request.setAttribute(FLASH, "Error: "
					+ s3Response.connection.getResponseMessage());
		}

		ListAllMyBucketsResponse buckets = s3Conn.listAllMyBuckets(null);
		request.getSession().setAttribute(BUCKETS, buckets.entries);
		logger.info("S3 buckets " + buckets.entries);

		return mapping.findForward(SUCCESS);
	}

	public ActionForward setACL(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		initAWSConnection(request);

		String acl = (String) request.getParameter(ACL);
		S3Object s3Object = null;
		s3Object = s3Conn.getACL(bucket, null, null).object;
		if (s3Object == null) {
			logger.info("Error: bucket '" + bucket + "' not found");
			request.setAttribute(FLASH, "Error: bucket '" + bucket + "' not found");
			return mapping.findForward(SUCCESS);
		}
		String currentACL = new String(s3Object.data);
		logger.info(currentACL);

		String currenId = currentACL.substring(currentACL.indexOf("<ID>") + 4,
				currentACL.indexOf("</ID>"));

		String aclXmlDoc = "";
		if (acl.equals("private")) {
			aclXmlDoc = S3Helper.getACLTemplatePrivate(currenId);
		} else if (acl.equals("public-read")) {
			aclXmlDoc = S3Helper.getACLTemplatePublicRead(currenId);
		} else if (acl.equals("public-read-write")) {
			aclXmlDoc = S3Helper.getACLTemplatePublicReadWrite(currenId);
		} else {
			logger.info(acl + " not supported at this time.");
			request.setAttribute(FLASH, acl + " not supported at this time.");
			return mapping.findForward(SUCCESS);
		}
		logger.info("aclXmlDoc: " + aclXmlDoc);

		Response s3Response = s3Conn.putBucketACL(bucket, aclXmlDoc, null);
		if (RESPONSE_OK == s3Response.connection.getResponseCode()) {
			logger.info("Set ACL for bucket '" + bucket + "' to " + acl);
			request.setAttribute(FLASH, "Set ACL for bucket '" + bucket + "' to " + acl);
		} else {
			logger.info("Error: could not set ACL for bucket '" + bucket
					+ "' to " + acl);
			logger.info(s3Response.connection.getResponseMessage());
			request.setAttribute(FLASH, "Error: "
					+ s3Response.connection.getResponseMessage());
		}

		ListAllMyBucketsResponse buckets = s3Conn.listAllMyBuckets(null);
		request.getSession().setAttribute(BUCKETS, buckets.entries);
		logger.info("S3 buckets " + buckets.entries);

		return mapping.findForward(SUCCESS);
	}

}

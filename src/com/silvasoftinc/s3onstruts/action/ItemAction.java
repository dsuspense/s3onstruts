package com.silvasoftinc.s3onstruts.action;

import java.util.logging.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.amazon.s3.ListBucketResponse;
import com.amazon.s3.Response;
import com.amazon.s3.S3Object;
import com.silvasoftinc.s3.S3Helper;

public class ItemAction extends BaseMappingDispatchAction {

	private static Logger logger = Logger.getLogger(ItemAction.class.getName());

	public ActionForward delete(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		initAWSConnection(request);

		String item = (String) request.getParameter(ITEM);
		Response s3Response = s3Conn.delete(bucket, item, null);
		if (RESPONSE_OK == s3Response.connection.getResponseCode()) {
			logger.info("Deleted item '" + bucket + "/" + item + "'");
			request.setAttribute(FLASH, "Deleted item '" + bucket + "/" + item
					+ "'");
		} else {
			logger.info(s3Response.connection.getResponseMessage());
			request.setAttribute(FLASH, "Error: "
					+ s3Response.connection.getResponseMessage());
		}

		ListBucketResponse items = s3Conn.listBucket(bucket, null, null, null,
				null);
		request.setAttribute(ITEMS, items.entries);
		logger.info(bucket + " items " + items.entries);
		request.setAttribute(BUCKET, bucket);
		request.getSession().setAttribute(S3_CONN, s3Conn);

		return mapping.findForward(SUCCESS);
	}

	public ActionForward setACL(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		initAWSConnection(request);

		String acl = (String) request.getParameter(ACL);
		String item = (String) request.getParameter(ITEM);
		S3Object s3Object = null;
		s3Object = s3Conn.getACL(bucket, item, null).object;
		if (s3Object == null) {
			logger.info("Error: item '" + bucket + "/" + item + "' not found");
			request.setAttribute(FLASH, "Error: item '" + bucket + "/" + item
					+ "' not found");
			return mapping.findForward(SUCCESS);
		}
		String currentACL = new String(s3Object.data);
		logger.info(currentACL);

		String currentId = currentACL.substring(currentACL.indexOf("<ID>") + 4,
				currentACL.indexOf("</ID>"));

		String aclXmlDoc = "";
		if (acl.equals("private")) {
			aclXmlDoc = S3Helper.getACLTemplatePrivate(currentId);
		} else if (acl.equals("public-read")) {
			aclXmlDoc = S3Helper.getACLTemplatePublicRead(currentId);
		} else if (acl.equals("public-read-write")) {
			aclXmlDoc = S3Helper.getACLTemplatePublicReadWrite(currentId);
		} else {
			logger.info(acl + " not supported at this time.");
			request.setAttribute(FLASH, acl + " not supported at this time.");
			return mapping.findForward(SUCCESS);
		}
		logger.info("aclXmlDoc: " + aclXmlDoc);

		Response s3Response = s3Conn.putACL(bucket, item, aclXmlDoc, null);
		if (RESPONSE_OK == s3Response.connection.getResponseCode()) {
			logger.info("Set ACL for item '" + bucket + "/" + item + "' to "
					+ acl);
			request.setAttribute(FLASH, "Set ACL for item '" + bucket + "/"
					+ item + "' to " + acl);
		} else {
			logger.info("Error: could not set ACL for item '" + bucket + "/"
					+ item + "' to " + acl);
			logger.info(s3Response.connection.getResponseMessage());
			request.setAttribute(FLASH, "Error: "
					+ s3Response.connection.getResponseMessage());
		}

		ListBucketResponse items = s3Conn.listBucket(bucket, null, null, null,
				null);
		request.setAttribute(ITEMS, items.entries);
		logger.info(bucket + " items " + items.entries);
		request.setAttribute(BUCKET, bucket);
		request.getSession().setAttribute(S3_CONN, s3Conn);

		return mapping.findForward(SUCCESS);
	}

}

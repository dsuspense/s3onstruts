package com.silvasoftinc.s3onstruts.action;

import java.util.logging.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.upload.FormFile;
import org.apache.struts.validator.DynaValidatorForm;

import com.amazon.s3.AWSAuthConnection;
import com.amazon.s3.ListBucketResponse;
import com.amazon.s3.Response;
import com.silvasoftinc.s3.S3StreamObject;

public class UploadItemAction extends Action {

	private static Logger logger = Logger.getLogger(CreateBucketAction.class
			.getName());

	private static String SUCCESS = "success";

	protected static String S3_CONN = "s3_conn";

	protected static String BUCKET = "bucket";

	protected static String ITEMS = "items";

	protected static String FLASH = "flash";

	protected static final int RESPONSE_OK = 200;

	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		DynaValidatorForm uploadItemForm = (DynaValidatorForm) form;

		FormFile newItem = (FormFile) uploadItemForm.get("newItem");

		String bucket = (String) request.getParameter(BUCKET);
		AWSAuthConnection s3Conn = (AWSAuthConnection) request.getSession()
				.getAttribute(S3_CONN);
		S3StreamObject s3Object = new S3StreamObject(newItem.getInputStream(),
				null);
		Response s3Response = s3Conn.putStream(bucket, newItem.getFileName(),
				s3Object, null);
		if (RESPONSE_OK == s3Response.connection.getResponseCode()) {
			logger.info("Created item '" + bucket + "/" + newItem.getFileName()
					+ "'");
			request.setAttribute(FLASH, "Created item '" + bucket + "/"
					+ newItem.getFileName() + "'");
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

		uploadItemForm.initialize(mapping);

		return mapping.findForward(SUCCESS);
	}

}

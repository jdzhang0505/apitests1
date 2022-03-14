package com.api.mtx;

import java.io.File;
import java.io.IOException;

import javax.imageio.IIOImage;

import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.util.EntityUtils;

/**
* @author 迨
* @version Oct 21, 2021 4:03:19 PM
*/
public class MtxUpload {

	public static String upload(CloseableHttpClient httpClient) throws Exception {
		HttpPost httpPost=new HttpPost("http://192.168.96.157/mtx/index.php?s=/index/user/useravatarupload.html");
		httpPost.setHeader("X-Requested-With","XMLHttpRequest");
		
		MultipartEntityBuilder builder=MultipartEntityBuilder.create();
		HttpEntity entity = builder.addTextBody("img_x", "392")
					.addTextBody("img_y", "73")
					.addTextBody("img_height", "346")
					.addTextBody("img_width", "582")
					.addTextBody("img_rotate", "0")
					.addBinaryBody("file", new File("D:\\work\\image file\\1.png"),ContentType.IMAGE_PNG,"1.png").build();
		
		httpPost.setEntity(entity);
		CloseableHttpResponse response = httpClient.execute(httpPost);
		String reString = EntityUtils.toString(response.getEntity());
		return reString;
	}
}

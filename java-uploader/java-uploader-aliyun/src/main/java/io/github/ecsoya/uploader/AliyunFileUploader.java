package io.github.ecsoya.uploader;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.model.ObjectMetadata;

import io.github.ecsoya.uploader.core.AbstractFileUploader;
import io.github.ecsoya.uploader.core.FileUploadException;
import io.github.ecsoya.uploader.core.UploadConfig;
import io.github.ecsoya.uploader.core.UploadData;

/**
 * 文件上传工具类
 * 
 * @author AngryRED (jin.liu@soyatec.com)
 */
public class AliyunFileUploader extends AbstractFileUploader {

	public AliyunFileUploader(UploadConfig config) throws FileUploadException {
		super(config);
	}

	@Override
	protected List<String> doUpload(List<UploadData> files, UploadConfig config) throws FileUploadException {
		OSS client = new OSSClientBuilder().build(config.getEndpoint(), config.getAccessKey(), config.getSecretKey());

		List<String> result = new ArrayList<>();
		try {
			for (UploadData file : files) {
				String fileName = file.getFileName();
				String contentType = file.getContentType();
				Long length = file.getLength();
				InputStream inputStream = file.getInputStream();
				if (inputStream != null && inputStream.available() > 0) {
					ObjectMetadata metadata = new ObjectMetadata();
					if (length != null) {
						metadata.setContentLength(length);
					}
					if (contentType != null) {
						metadata.setContentType(contentType);
					}
					client.putObject(config.getBucket(), fileName, inputStream, metadata);
				}
				String url = getUrl(config.getBaseUrl(), fileName);
				result.add(url);
			}
		} catch (Exception oe) {
		} finally {
			/*
			 * Do not forget to shut down the client finally to release all allocated
			 * resources.
			 */
			client.shutdown();
		}

		return result;
	}

}
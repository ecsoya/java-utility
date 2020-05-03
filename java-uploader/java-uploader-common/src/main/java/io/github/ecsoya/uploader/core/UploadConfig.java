package io.github.ecsoya.uploader.core;

import io.github.ecsoya.uploader.utils.StringUtils;

public class UploadConfig {
	private String baseUrl;

	private String endpoint;

	private String accessKey;

	private String secretKey;

	private String bucket;

	public String getBaseUrl() {
		return baseUrl;
	}

	public UploadConfig setBaseUrl(String baseUrl) {
		this.baseUrl = baseUrl;
		return this;
	}

	public String getEndpoint() {
		return endpoint;
	}

	public UploadConfig setEndpoint(String endpoint) {
		this.endpoint = endpoint;
		return this;
	}

	public String getAccessKey() {
		return accessKey;
	}

	public UploadConfig setAccessKey(String accessKey) {
		this.accessKey = accessKey;
		return this;
	}

	public String getSecretKey() {
		return secretKey;
	}

	public UploadConfig setSecretKey(String secretKey) {
		this.secretKey = secretKey;
		return this;
	}

	public String getBucket() {
		return bucket;
	}

	public UploadConfig setBucket(String bucket) {
		this.bucket = bucket;
		return this;
	}

	public static UploadConfig build() {
		return new UploadConfig();
	}

	public void checkValid() throws FileUploadException {
		if (StringUtils.isEmpty(baseUrl)) {
			throw new FileUploadException("Base URL is empty");
		}
		if (StringUtils.isEmpty(bucket)) {
			throw new FileUploadException("Bucket is empty");
		}
		if (StringUtils.isEmpty(accessKey)) {
			throw new FileUploadException("AccessKey is empty");
		}
		if (StringUtils.isEmpty(secretKey)) {
			throw new FileUploadException("SecretKey is empty");
		}
		if (StringUtils.isEmpty(endpoint)) {
			throw new FileUploadException("Endpoint or Region is empty");
		}
	}

}

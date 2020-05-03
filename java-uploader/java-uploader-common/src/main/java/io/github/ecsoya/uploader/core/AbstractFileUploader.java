package io.github.ecsoya.uploader.core;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;

import io.github.ecsoya.uploader.utils.MimeTypeUtils;
import io.github.ecsoya.uploader.utils.StringUtils;

public abstract class AbstractFileUploader implements FileUploader {

	private UploadConfig config;

	public AbstractFileUploader(UploadConfig config) throws FileUploadException {
		if (config == null) {
			throw new FileUploadException("Upload config is empty");
		}
		config.checkValid();
		this.config = config;
	}

	@Override
	public String upload(UploadData file) throws FileUploadException {
		if (file == null) {
			throw new FileUploadException("Upload file is empty");
		}
		return upload(Collections.singletonList(file)).stream().findFirst().orElse(null);
	}

	@Override
	public List<String> upload(List<UploadData> files) throws FileUploadException {
		if (files == null || files.isEmpty()) {
			throw new FileUploadException("Upload file is empty");
		}
		return doUpload(files, config);
	}

	protected String getFileName(UploadData file) {
		String fileName = file.getFileName();
		String extension = file.getExtension();
		if (StringUtils.isEmpty(fileName)) {
			fileName = Long.toString(System.nanoTime());
		}
		String name = datePath() + "/" + encodingFilename(fileName);

		if (!name.contains(".") && StringUtils.isEmpty(extension)) {
			extension = MimeTypeUtils.IMAGE_JPEG;
		}
		if (StringUtils.isNotEmpty(extension)) {
			return name + "." + extension;
		}
		return name;
	}

	private String datePath() {
		return LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy/MM/dd"));
	}

	/**
	 * 编码文件名
	 */
	private String encodingFilename(String fileName) {
		fileName = fileName.replace("_", " ");
		return fileName;
	}

	protected String getUrl(String baseUrl, String fileName) {
		if (!baseUrl.endsWith("/")) {
			baseUrl += "/";
		}
		if (fileName.startsWith("/")) {
			fileName = fileName.substring(1);
		}
		return baseUrl + fileName;
	}

	protected abstract List<String> doUpload(List<UploadData> files, UploadConfig config) throws FileUploadException;

}

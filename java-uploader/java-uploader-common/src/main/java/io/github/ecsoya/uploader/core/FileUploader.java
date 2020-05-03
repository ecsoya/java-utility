package io.github.ecsoya.uploader.core;

import java.util.List;

public interface FileUploader {

	String upload(UploadData file) throws FileUploadException;

	List<String> upload(List<UploadData> files) throws FileUploadException;
}

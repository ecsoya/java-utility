package io.github.ecsoya.uploader.core;

import java.io.IOException;
import java.io.InputStream;

import org.springframework.web.multipart.MultipartFile;

/**
 * 上传对象实体类
 */
public class UploadData {

	private String extension;

	/**
	 * 输入流
	 */
	private InputStream inputStream;

	/**
	 * 数据
	 */
	private byte[] datas;

	private String contentType;

	private Long length;

	private String fileName;

	/**
	 * 构造上传对象
	 *
	 * @param inputStream 输入流
	 * @param datas       数据
	 * @return 返回上传对象
	 */
	public static UploadData build(String fileName, InputStream inputStream) {
		UploadData uploadData = new UploadData();
		uploadData.setFileName(fileName);
		uploadData.setInputStream(inputStream);
		return uploadData;
	}

	public static UploadData build(MultipartFile file) throws FileUploadException {
		if (file == null) {
			throw new RuntimeException("Upload file is empty");
		}

		UploadData uploadData;
		try {
			InputStream inputStream = file.getInputStream();
			byte[] datas = file.getBytes();
			uploadData = new UploadData();

			String name = file.getOriginalFilename();
			if (name != null && name.indexOf(".") != -1) {
				uploadData.setExtension(name.substring(name.lastIndexOf(".")));
			}
			uploadData.setFileName(name);
			uploadData.setContentType(file.getContentType());
			uploadData.setLength(file.getSize());
			uploadData.setInputStream(inputStream);
			uploadData.setDatas(datas);
			return uploadData;
		} catch (IOException e) {
			throw new FileUploadException(e.getLocalizedMessage());
		}
	}

	public String getExtension() {
		return extension;
	}

	public void setExtension(String extension) {
		this.extension = extension;
	}

	public InputStream getInputStream() {
		return inputStream;
	}

	public void setInputStream(InputStream inputStream) {
		this.inputStream = inputStream;
	}

	public byte[] getDatas() {
		return datas;
	}

	public void setDatas(byte[] datas) {
		this.datas = datas;
	}

	public String getContentType() {
		return contentType;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

	public Long getLength() {
		return length;
	}

	public void setLength(Long length) {
		this.length = length;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

}

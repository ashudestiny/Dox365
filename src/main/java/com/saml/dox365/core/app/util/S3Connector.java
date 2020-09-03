package com.saml.dox365.core.app.util;

import java.io.File;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.Bucket;
import com.amazonaws.services.s3.model.DeleteObjectsRequest;
import com.amazonaws.services.s3.model.DeleteObjectsRequest.KeyVersion;
import com.amazonaws.services.s3.model.DeleteObjectsResult;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.PutObjectResult;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import com.amazonaws.services.s3.transfer.MultipleFileUpload;
import com.amazonaws.services.s3.transfer.TransferManager;
import com.amazonaws.services.s3.transfer.TransferManagerBuilder;
import com.saml.dox365.core.app.config.S3Properties;
import com.saml.dox365.core.app.exceptions.DoxS3Exception;
import com.saml.dox365.core.app.exceptions.DoxS3KeyNotFoundException;

/**
 * 
 * @author ashish tuteja S3 Connector will connect to the S3 bucket. It is
 *         generic code, not specific to any cloud
 */

@Component
public class S3Connector {
	Logger logger = Logger.getLogger(S3Connector.class);

	private final S3Properties s3Properties;

	private AmazonS3 s3Client;

	private TransferManager transfer_manager;

	private final String DATE_FORMAT = "MM-dd-yyyy";

	/**
	 * Initializes AmazonS3 Client to connect to Object Store. Initializer runs on
	 * Post Construct so that it gets setup when app is starting
	 */
	@Autowired
	public S3Connector(S3Properties s3Properties) {
		this.s3Properties = s3Properties;

		BasicAWSCredentials credentials = new BasicAWSCredentials(s3Properties.getAccessKeyId(),
				s3Properties.getAccessSecretKey());
		s3Client = AmazonS3ClientBuilder.standard().withPathStyleAccessEnabled(Boolean.TRUE)
				.withEndpointConfiguration(new AwsClientBuilder.EndpointConfiguration(s3Properties.getS3EndPoint(),
						s3Properties.getRegion()))
				.withCredentials(new AWSStaticCredentialsProvider(credentials)).build();

		transfer_manager = TransferManagerBuilder.standard().withS3Client(s3Client).build();
	}

	/**
	 * Takes in a files and stores in the Object Store. Returns true if object has
	 * been pushed successfully
	 *
	 * @param file - The file to be pushed into Object Store
	 * @return - String containing the file The File Path File to be stored to
	 *         Object Store
	 */
	public String putFile(File file, String contentGroup) throws DoxS3Exception {
		if (file == null) {
			return "";
		}

		try {
			String key = getOOSKey(contentGroup) + "/" + file.getName();
			PutObjectResult result = s3Client.putObject(new PutObjectRequest(s3Properties.getBucketName(), key, file));
			if (result != null) {
				return s3Properties.getBucketName() + "/" + key;
			}
			return "";
		} catch (Exception e) {
			throw new DoxS3Exception("Error in uploading file to Object Store", e);
		}

	}

	/**
	 * Takes in a files and stores in the Object Store. Returns true if object has
	 * been pushed successfully
	 *
	 * @param file - The file to be pushed into Object Store
	 * @return - String containing the file The File Path File to be stored to
	 *         Object Store
	 */
	public String putFile(String fileName, String contentGroup, MultipartFile multiPartfile, String docId)
			throws DoxS3Exception {
		try {
			InputStream fis = multiPartfile.getInputStream();
			if (fis == null) {
				return "";
			}
			ObjectMetadata objectMetadata = new ObjectMetadata();
			objectMetadata.setContentLength(multiPartfile.getSize());
			String key = getOOSKey(contentGroup) + "/" + docId + "/" + fileName;
			PutObjectResult result = s3Client
					.putObject(new PutObjectRequest(s3Properties.getBucketName(), key, fis, objectMetadata));
			if (result != null) {
				return s3Properties.getS3EndPoint() + "/" + s3Properties.getBucketName() + "/" + key;
			}

			return "";
		} catch (Exception e) {
			throw new DoxS3Exception("Error in uploading file to Object Store", e);
		}
	}

	public void getPartialFile(String mergedFile, String destFileAbsPath, long startByte, long endByte,
			String contentGroup) throws DoxS3Exception {
		String key = getOOSKey(contentGroup) + "/" + mergedFile;
		try {
			GetObjectRequest objRequest = new GetObjectRequest(s3Properties.getBucketName(), key);

			objRequest.setRange(startByte + 1, endByte);

			S3ObjectInputStream s3InputStream = s3Client.getObject(objRequest).getObjectContent();

			byte[] bytes = IOUtils.toByteArray(s3InputStream);
			Files.write(Paths.get(destFileAbsPath), new org.apache.commons.codec.binary.Base64().decode(bytes));

			logger.debug("File Written :" + destFileAbsPath);
		} catch (Exception e) {
			throw new DoxS3Exception(
					"Error in downloading the file, ObjectStore key :" + key + " ,destination :  " + destFileAbsPath,
					e);
		}

	}

	public void getPartialFile(String mergedFile, String destFileAbsPath, long startByte, long endByte)
			throws DoxS3Exception {
		String key = getOOSKey() + "/" + mergedFile;
		try {
			GetObjectRequest objRequest = new GetObjectRequest(s3Properties.getBucketName(), key);

			objRequest.setRange(startByte, endByte);

			S3ObjectInputStream s3InputStream = s3Client.getObject(objRequest).getObjectContent();
			byte[] bytes = new byte[1024];

			while (s3InputStream.read(bytes) != -1) {
				Files.write(Paths.get(destFileAbsPath), new String(bytes).getBytes("ISO-8859-1"));
			}
			logger.debug("File Written :" + destFileAbsPath);
		} catch (Exception e) {
			throw new DoxS3Exception(
					"Error in downloading the file, ObjectStore key :" + key + " ,destination :  " + destFileAbsPath,
					e);
		}

	}

	/**
	 * Download the file from object store
	 * 
	 * @param srcFile : Source key of object store.
	 * @param dstFile : Location where file will be downloaded.
	 * @throws DoxS3Exception
	 */
	public void downloadFiles(String srcFile, String dstFile) throws DoxS3KeyNotFoundException, DoxS3Exception {
		try {
			GetObjectRequest objectRequest = new GetObjectRequest(s3Properties.getBucketName(), srcFile);
			s3Client.getObject(objectRequest, new File(dstFile));

		} catch (Exception e) {
			if (e.getLocalizedMessage().contains("The specified key does not exist.")) {
				throw new DoxS3KeyNotFoundException(e.getMessage(), e);
			} else {
				throw new DoxS3Exception("Error in Downloading the file, src : " + srcFile, e);
			}
		}
	}

	/**
	 * Get the input stream from S3
	 * 
	 * @param srcFile : Source key of object store.
	 * @throws DoxS3Exception
	 */
	public S3ObjectInputStream downloadFromStream(String srcFile) throws DoxS3KeyNotFoundException, DoxS3Exception {

		try {
			String key = srcFile.split(s3Properties.getBucketName() + "/")[1];
			GetObjectRequest objectRequest = new GetObjectRequest(s3Properties.getBucketName(), key);
			S3ObjectInputStream stream = s3Client.getObject(objectRequest).getObjectContent();
			
			return stream;
		} catch (Exception e) {
			if (e.getLocalizedMessage().contains("The specified key does not exist.")) {
				throw new DoxS3KeyNotFoundException(e.getMessage(), e);
			} else {
				throw new DoxS3Exception("Error in Downloading the file, src : " + srcFile, e);
			}
		}
	}

	/**
	 * Method upload all the content with in directory to object store
	 * 
	 * @param folderPath    - Direcotry to upload
	 * @param index         - Path where directory will get store in object store
	 * @param includeSubDir - Flag to upload sub directories and its content
	 * @throws DoxS3Exception
	 */
	public void uploadFolder(String folderPath, String index, boolean includeSubDir) throws DoxS3Exception {
		MultipleFileUpload upload;

		File dir = new File(folderPath);
		String key = getOOSKey() + index.replace("\\", "/");

		try {
			upload = transfer_manager.uploadDirectory(s3Properties.getBucketName(), key, dir, includeSubDir);
			upload.waitForCompletion();
		} catch (Exception e) {
			throw new DoxS3Exception("Exception in uploading the directory, path : " + folderPath, e);
		}
	}

	public void delteBulk(List<String> l_path, String docClass, int deleteMaxCount) throws DoxS3Exception {
		ArrayList<KeyVersion> keys = new ArrayList<KeyVersion>();
		int count = 0;
		try {
			for (String path : l_path) {

				String key = getOOSKey() + docClass + "/" + path;
				keys.add(new KeyVersion(key));
				if (count % deleteMaxCount == 0) {
					DeleteObjectsRequest deleteReq = new DeleteObjectsRequest(s3Properties.getBucketName())
							.withKeys(keys);
					DeleteObjectsResult deleteRes = s3Client.deleteObjects(deleteReq);
					logger.debug("No of object deleted :" + deleteRes.getDeletedObjects().size());
					keys.clear();
				}
				count++;
			}

			if (keys.size() > 0) {
				DeleteObjectsRequest deleteReq = new DeleteObjectsRequest(s3Properties.getBucketName()).withKeys(keys);
				DeleteObjectsResult deleteRes = s3Client.deleteObjects(deleteReq);
				logger.debug("No of object deleted :" + deleteRes.getDeletedObjects().size());
			}
		} catch (Exception e) {
			throw new DoxS3Exception("Error while bulk deletion", e);
		}
	}

	/**
	 * Gets the current date and formats it to MM-dd-yyyy
	 * 
	 * @return Formatted Date String
	 */
	private String getCurrentDateString() {
		LocalDate date = LocalDate.now();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DATE_FORMAT);
		String formattedString = date.format(formatter);
		return formattedString;
	}

	public void getBucketList() {
		for (Bucket bucket : s3Client.listBuckets()) {
			System.out.println("Bucket Name : " + bucket.getName());
		}
	}

	/**
	 * Constructs the OOS Key needed to push into Object Store
	 *
	 * @return String containing they key needed for OOS
	 */
	private String getOOSKey(String contentGroup) {
		return s3Properties.getRootFolder() + "/" + contentGroup + "/" + getCurrentDateString();
	}

	private String getOOSKey() {
		return s3Properties.getRootFolder();
	}
}

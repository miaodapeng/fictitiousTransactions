package com.vedeng.system.service.impl;

import com.common.constants.Contant;
import com.itextpdf.text.Document;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfImportedPage;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfWriter;
import com.vedeng.common.model.FileInfo;
import com.vedeng.common.model.QRCode;
import com.vedeng.common.model.ResultInfo;
import com.vedeng.common.qrcodesoft.QRCodeUtil;
import com.vedeng.common.util.DateUtil;
import com.vedeng.system.service.FtpUtilService;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;
import org.im4java.core.ConvertCmd;
import org.im4java.core.GMOperation;
import org.im4java.core.IM4JavaException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;


import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.SocketException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service("ftpUtilService")
public class FtpUtilServiceImpl implements FtpUtilService {

	private static final Logger logger = LoggerFactory.getLogger(FtpUtilServiceImpl.class);

	/**
	 * FTP服务器IP地址
	 */
	@Value("${ftp_url}")
	private String ftp_url;

	/**
	 * FTP服务器端口
	 */
	@Value("${ftp_port}")
	private int ftp_port;

	/**
	 * FTP服务器文件路径
	 */
	@Value("${ftp_path}")
	private String ftp_path;

	/**
	 * FTP登录账号
	 */
	@Value("${ftp_user}")
	private String ftp_user;

	/**
	 * FTP登录密码
	 */
	@Value("${ftp_password}")
	private String ftp_password;

	/**
	 * 文件服务器http地址
	 */
	@Value("${file_url}")
	private String file_url;

	/**
	 * 图片压缩地址
	 */
	@Value("${gm_url}")
	private String gm_url;

	private static FTPClient ftpClient = new FTPClient();
	private static String encoding = System.getProperty("file.encoding");// 系統編碼
	private static String LOCAL_CHARSET = "GBK";// 本地字符编码
	private static String SERVER_CHARSET = "ISO-8859-1";// FTP协议里面，规定文件名编码为iso-8859-1

	@Override
	public FileInfo uploadFile(MultipartFile upfile, String save_path, HttpServletRequest request,
			String new_fileName) {
		FileInfo fileInfo = exeUploadFile(upfile, save_path, request, new_fileName);
		if (fileInfo.getCode() != 0) {// 上传失败后再次上传
			return exeUploadFile(upfile, save_path, request, new_fileName);
		} else {
			return fileInfo;
		}
	}

	/**
	 * <b>Description:</b><br>
	 * 向FTP服务器上传文件
	 * 
	 * @param upfile
	 * @param save_path
	 * @param request
	 * @return
	 * @Note <b>Author:</b> duke <br>
	 *       <b>Date:</b> 2017年5月23日 下午6:24:54
	 */
	public FileInfo exeUploadFile(MultipartFile upfile, String save_path, HttpServletRequest request,
			String new_fileName) {

		boolean result = false;
		// 临时文件存放地址
		String path = request.getSession().getServletContext().getRealPath(save_path);
		// 上传文件名称
		String fileName = upfile.getOriginalFilename();
		// 文件后缀名称
		String prefix = fileName.substring(fileName.lastIndexOf(".") + 1);

		if (new_fileName == null || new_fileName.isEmpty()) {
			// 时间戳
			/*
			 * Date dt = new Date(); String time = dt.getTime() + "";
			 */
			String time = DateUtil.gainNowDate() + "";
			Random random = new Random();
			time = time + "_" + String.format("%04d", random.nextInt(10000));// 随机数4位，不足4位，补位
			new_fileName = time + "." + prefix;// 新文件名称
		}

		String new_file = path + File.separator + new_fileName;// 新文件地址

		/*
		 * File targetFile = new File(save_path, fileName);
		 * 
		 * if (!targetFile.exists()) { targetFile.mkdirs(); } // 保存 try {
		 * upfile.transferTo(targetFile); } catch (Exception e) { logger.error(Contant.ERROR_MSG, e); }
		 */
		FileOutputStream out = null;
		try {
			File file = new File(path);
			if (!file.exists()) {
				file.mkdirs();
			}
			out = new FileOutputStream(new_file);// 写入临时地址
			// 写入文件
			out.write(upfile.getBytes());
			out.flush();

		} catch (Exception e1) {
			logger.error("写入临时文件失败", e1);
			return new FileInfo(-1, "写入临时文件失败");
		} finally {
			IOUtils.closeQuietly(out);
		}

		Calendar calendar = Calendar.getInstance();
		// 获取年份
		int year = calendar.get(Calendar.YEAR);
		// 获取月份
		int month = calendar.get(Calendar.MONTH) + 1;
		// 拼接年份和月份
		String date = year + "-" + String.format("%02d", month);

		try {
			BufferedImage bi = ImageIO.read(new File(new_file));
			// 获取日期
			String day = String.format("%02d", calendar.get(Calendar.DAY_OF_MONTH));

			// prefix = prefix.toLowerCase();
			// 区分文件和图片
			if (bi != null) {
				save_path = save_path + "/" + date + "/" + day + "/image";
			} else {
				save_path = save_path + "/" + date + "/" + day + "/file";
			}
		} catch (IOException e1) {
			logger.error("文件类型获取失败", e1);
			return new FileInfo(-1, "文件类型获取失败");
		}

		// fileName = fileName.substring(0, fileName.length()-prefix.length());
		try {
			int reply;
			// 登录
			ftpClient.connect(ftp_url, ftp_port);
			ftpClient.login(ftp_user, ftp_password);
			ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
			// 检验是否连接成功
			reply = ftpClient.getReplyCode();
			if (!FTPReply.isPositiveCompletion(reply)) {
				ftpClient.disconnect();
				return new FileInfo(-1, "连接ftp失败");
			}
			ftpClient.setControlEncoding(encoding);

			save_path = save_path.replace("\\", "/");

			CreateDirecroty(save_path);// 创建多层文件夹

			// ftp保存文件地址
			// String ftp_save_path =ftp_path + "/" + save_path;

			ftpClient.makeDirectory(save_path);

			// 转移工作目录至指定目录下
			ftpClient.changeWorkingDirectory(save_path);

			FileInputStream input = new FileInputStream(new File(new_file));

			result = ftpClient.storeFile(new String(new_fileName.getBytes(LOCAL_CHARSET), SERVER_CHARSET), input);
			input.close();
			ftpClient.logout();
			if (result) {
				logger.info("上传成功!");
				deleteLocalFile(new_file);// 删除本地临时文件
				if (request.getParameter("uploadType") != null
						&& request.getParameter("uploadType").toString().equals("uedit")) {
					return new FileInfo(0, "操作成功", new_fileName, save_path, "http://" + file_url, prefix);
				} else {
					return new FileInfo(0, "操作成功", new_fileName, save_path, file_url, prefix);
				}
			}
		} catch (IOException e) {
			logger.error("ftp远程上传文件失败", e);
			deleteLocalFile(new_file);// 删除本地临时文件
			return new FileInfo(-1, "ftp远程上传文件失败");
		} finally {
			if (ftpClient.isConnected()) {
				try {
					ftpClient.disconnect();
				} catch (IOException ioe) {
					deleteLocalFile(new_file);// 删除本地临时文件
					return new FileInfo(-1, "关闭连接失败");
				}
			}
		}
		return new FileInfo();
	}

	/**
	 * 浏览器显示图片
	 */
	@Override
	public FileInfo downImageShow(String domain, String ftpFilePath) {
		if (ftpFilePath != null && !ftpFilePath.isEmpty()) {
			ftpFilePath = ftpFilePath.replaceAll("\\\\", "/");
			String fileName = ftpFilePath.substring(ftpFilePath.lastIndexOf("/") + 1);
			ftpFilePath = "http://" + domain + ftpFilePath;
			return new FileInfo(0, "操作成功", fileName, ftpFilePath);
		} else {
			return new FileInfo();
		}
	}

	/**
	 * 浏览器下载附件
	 */
	@Override
	public FileInfo downFile(String domain, String ftpFilePath) {
		if (ftpFilePath != null && !ftpFilePath.isEmpty()) {
			ftpFilePath = ftpFilePath.replaceAll("\\\\", "/");
			String fileName = ftpFilePath.substring(ftpFilePath.lastIndexOf("/") + 1);
			ftpFilePath = "http://" + domain + "/download" + ftpFilePath;
			// ftpFilePath = file_url + "/download" + ftpFilePath;
			return new FileInfo(0, "操作成功", fileName, ftpFilePath);
		} else {
			return new FileInfo();
		}
	}

	/**
	 * 下载ftp文件到本地
	 */
	@Override
	public FileInfo downloadFtpFile(HttpServletRequest request, String ftpPath, String fileName) {
		OutputStream os = null;
		FileInfo result = new FileInfo();
		try {
			// 临时文件存放地址
			String localPath = request.getSession().getServletContext().getRealPath("download");
			File file_path = new File(localPath);
			if (!file_path.isDirectory()) {
				file_path.mkdirs();
			}
			int reply;
			// 登录
			ftpClient.connect(ftp_url, ftp_port);
			ftpClient.login(ftp_user, ftp_password);
			ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
			// 检验是否连接成功
			reply = ftpClient.getReplyCode();
			if (!FTPReply.isPositiveCompletion(reply)) {
				ftpClient.disconnect();
			}
			ftpClient.setControlEncoding("UTF-8"); // 中文支持
			ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);
			ftpClient.enterLocalPassiveMode();
			ftpPath = ftpPath.replaceAll("\\\\", "/");

			ftpClient.changeWorkingDirectory(ftpPath.substring(0, ftpPath.lastIndexOf("/")));

			String ftpFileName = ftpPath.substring(ftpPath.lastIndexOf("/") + 1);

			File localFile = new File(localPath + File.separatorChar + fileName);
			os = new FileOutputStream(localFile);
			ftpClient.retrieveFile(ftpFileName, os);
			os.flush();
			result.setCode(0);
			result.setMessage("操作成功");
			result.setFilePath(localPath + File.separatorChar + fileName);
			result.setFileName(fileName);
		} catch (FileNotFoundException e) {
			logger.error("没有找到" + ftpPath + "文件");
			logger.error(Contant.ERROR_MSG, e);
		} catch (SocketException e) {
			logger.error("连接FTP失败.");
			logger.error(Contant.ERROR_MSG, e);
		} catch (IOException e) {
			logger.error("文件读取错误。");
			logger.error(Contant.ERROR_MSG, e);
		} finally {
			try {
				os.close();
				ftpClient.logout();
				if (ftpClient.isConnected()) {
					ftpClient.disconnect();
				}
			} catch (IOException ioe) {
				logger.error(Contant.ERROR_MSG, ioe);
			}
		}
		return result;
	}

	/**
	 * 删除ftp文件
	 */
	@Override
	public FileInfo deleteFile(String ftpFilePaht) {
		FileInfo result = removeFile(ftpFilePaht);
		if (result.getCode() != 0) {
			return removeFile(ftpFilePaht);
		}
		return result;
	}

	// 删除ftp文件
	public FileInfo removeFile(String srcFname) {
		boolean flag = false;
		try {
			int reply;
			// 登录
			ftpClient.connect(ftp_url, ftp_port);
			ftpClient.login(ftp_user, ftp_password);
			ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
			// 检验是否连接成功
			reply = ftpClient.getReplyCode();
			if (!FTPReply.isPositiveCompletion(reply)) {
				ftpClient.disconnect();
			}
			if (ftpClient != null) {
				try {
					flag = ftpClient.deleteFile(srcFname);
				} catch (IOException e) {
					logger.error(Contant.ERROR_MSG, e);
					return new FileInfo(-1, "删除失败");
				} finally {
					if (ftpClient != null) {
						if (ftpClient.isConnected()) {
							try {
								ftpClient.logout();
								ftpClient.disconnect();
							} catch (IOException e) {
								logger.error(Contant.ERROR_MSG, e);
							}
						}
					}
				}
			}
			if (flag) {
				return new FileInfo(0, "操作成功");
			}
		} catch (IOException e) {
			logger.error(Contant.ERROR_MSG, e);
			return new FileInfo(-1, "连接ftp异常");
		}
		return new FileInfo();
	}

	// 上传后删除本地文件
	public static boolean deleteLocalFile(String fileName) {
		File file = new File(fileName);
		// 如果文件路径所对应的文件存在，并且是一个文件，则直接删除
		if (file.exists() && file.isFile()) {
			if (file.delete()) {
				logger.info("删除单个文件" + fileName + "成功！");
				return true;
			} else {
				logger.warn("删除单个文件" + fileName + "失败！");
				return false;
			}
		} else {
			logger.warn("删除单个文件失败：" + fileName + "不存在！");
			return false;
		}
	}

	/**
	 * <b>Description:</b><br>
	 * 递归创建远程服务器多层文件夹
	 * 
	 * @param remote
	 * @return
	 * @throws IOException
	 * @Note <b>Author:</b> duke <br>
	 *       <b>Date:</b> 2017年5月24日 上午11:16:30
	 */
	public static boolean CreateDirecroty(String remote) throws IOException {
		boolean success = true;
		String directory = remote + "/";
		// String directory = remote.substring(0, remote.lastIndexOf("/") + 1);
		// 如果远程目录不存在，则递归创建远程服务器目录
		if (!directory.equalsIgnoreCase("/") && !changeWorkingDirectory(new String(directory), remote)) {
			int start = 0;
			int end = 0;
			if (directory.startsWith("/")) {
				start = 1;
			} else {
				start = 0;
			}
			end = directory.indexOf("/", start);
			String path = "";
			String paths = "";
			while (true) {

				String subDirectory = new String(remote.substring(start, end).getBytes("GBK"), "iso-8859-1");
				path = path + "/" + subDirectory;
				if (!existFile(path)) {
					if (makeDirectory(subDirectory)) {
						changeWorkingDirectory(subDirectory, remote);
					} else {
						logger.warn("创建目录[" + subDirectory + "]失败");
						return false;
						// changeWorkingDirectory(subDirectory);
					}
				} else {
					changeWorkingDirectory(subDirectory, remote);
				}

				paths = paths + "/" + subDirectory;
				start = end + 1;
				end = directory.indexOf("/", start);
				// 检查所有目录是否创建完毕
				if (end <= start) {
					break;
				}
			}
		}
		return success;
	}

	// 改变远程服务器目录路径
	public static boolean changeWorkingDirectory(String directory, String path) {
		boolean flag = true;
		try {
			String[] rt = ftpClient.doCommandAsStrings("pwd", "");
			// System.out.println("88888888888888888888888888888888888888888888888" + rt ==
			// null);
			Pattern p = Pattern.compile("\"(.*?)\"");
			Matcher m = p.matcher(rt[0]);
			String locationPath = "";
			if (m.find()) {
				locationPath = m.group(0).replace("\"", "");
			}
			// 判断当前所在路径和目标路径是否相同，如果相同则不继续进入
			if (locationPath != "" && !locationPath.equals("/" + path)) {
				flag = ftpClient.changeWorkingDirectory(directory);
				if (flag) {
					logger.info("进入文件夹" + directory + " 成功！");
				} else {
					logger.warn("进入文件夹" + directory + " 失败！");
				}
			} else {
				logger.warn("进入文件夹" + directory + " 失败！");
				return false;
			}
		} catch (IOException ioe) {
			logger.warn(" 改变远程服务器目录路径：" + path + "失败！", ioe);
		}
		return flag;
	}

	// 创建目录
	public static boolean makeDirectory(String dir) {
		boolean flag = true;
		try {
			flag = ftpClient.makeDirectory(dir);
			if (flag) {
				logger.info("创建文件夹" + dir + " 成功！");
			} else {
				logger.warn("创建文件夹" + dir + " 失败！");
			}
		} catch (Exception e) {
			logger.error(Contant.ERROR_MSG, e);
		}
		return flag;
	}

	// 判断ftp服务器文件是否存在
	public static boolean existFile(String path) throws IOException {
		boolean flag = false;
		FTPFile[] ftpFileArr = ftpClient.listFiles(path);
		if (ftpFileArr.length > 0) {
			flag = true;
		}
		return flag;
	}

	public FileInfo fileUploadServe(String path, MultipartFile lwfile) {
		FileInfo result = new FileInfo();
		// 上传文件名称
		String fileName = lwfile.getOriginalFilename();
		// 文件后缀名称
		String prefix = fileName.substring(fileName.lastIndexOf(".") + 1);

		// 时间戳
		String time = DateUtil.gainNowDate() + "";
		Random random = new Random();
		time = time + "_" + String.format("%04d", random.nextInt(10000));// 随机数4位，不足4位，补位
		String new_fileName = time + "." + prefix;// 新文件名称

		Calendar calendar = Calendar.getInstance();
		// 获取年份
		int year = calendar.get(Calendar.YEAR);
		// 获取月份
		int month = calendar.get(Calendar.MONTH) + 1;
		// 拼接年份和月份
		String date = year + "-" + String.format("%02d", month);

		String day = String.format("%02d", calendar.get(Calendar.DAY_OF_MONTH));

		String save_path = path + "/" + date + "/" + day + "/file";

		File targetFile = new File(save_path, new_fileName);

		if (!targetFile.exists()) {
			targetFile.mkdirs();
		}
		// 保存
		try {
			lwfile.transferTo(targetFile);
			result.setCode(0);
			result.setMessage("上传成功");
			result.setFilePath(save_path + File.separator + new_fileName);
		} catch (Exception e) {
			logger.error(Contant.ERROR_MSG, e);
			return result;
		}
		return result;
	}

	/**
	 * 生成不帶logo的二維碼
	 */
	@Override
	public ResultInfo<QRCode> makeQRCode(List<QRCode> qcList, String saveUrl) {
		try {
			if (qcList != null && !qcList.isEmpty()) {
				if (saveUrl == null || saveUrl.isEmpty()) {
					return new ResultInfo<>(-1, "二维码图片保存地址不允许为空");
				}
				for (int i = 0; i < qcList.size(); i++) {
					if (qcList.get(i).getContent() == null || qcList.get(i).getContent().isEmpty()) {
						return new ResultInfo<>(-1, "二维码内容不允许为空");
					}
				}
				// 生成二维码图片流
				BufferedImage image = null;
				for (QRCode qr : qcList) {
					image = QRCodeUtil.createImage(qr.getContent(), "", true);
					qr.setbImage(image);
				}
				return uploadFtpQRCode(qcList, saveUrl);
			}
		} catch (Exception e) {
			logger.error(Contant.ERROR_MSG, e);
			return new ResultInfo<>();
		}
		return new ResultInfo<>();
	}

	/**
	 * 生成帶logo的二維碼
	 */
	@Override
	public ResultInfo<QRCode> makeLogoQRCode(List<QRCode> qcList, String saveUrl, boolean b) {
		try {
			if (qcList != null && !qcList.isEmpty()) {
				if (saveUrl == null || saveUrl.isEmpty()) {
					return new ResultInfo<>(-1, "二维码图片保存地址不允许为空");
				}
				for (int i = 0; i < qcList.size(); i++) {
					if (qcList.get(i).getContent() == null || qcList.get(i).getContent().isEmpty()) {
						return new ResultInfo<>(-1, "二维码内容不允许为空");
					}
					if (qcList.get(i).getLogoUrl() == null || qcList.get(i).getLogoUrl().isEmpty()) {
						return new ResultInfo<>(-1, "二维码logo地址不允许为空");
					}
				}
				// 生成二维码图片流
				BufferedImage image = null;
				for (QRCode qr : qcList) {
					image = QRCodeUtil.createImage(qr.getContent(), qr.getLogoUrl(), true);
					qr.setbImage(image);
				}
				return uploadFtpQRCode(qcList, saveUrl);
			}
		} catch (Exception e) {
			logger.error(Contant.ERROR_MSG, e);
			return new ResultInfo<>();
		}
		return new ResultInfo<>();
	}

	public ResultInfo<QRCode> uploadFtpQRCode(List<QRCode> qcList, String saveUrl) {
		InputStream is = null;
		ByteArrayOutputStream os = null;
		String temp = "";
		boolean isCreate = false;// 创建多层文件夹
		try {
			int reply;
			// ftp登录
			ftpClient.connect(ftp_url, ftp_port);
			ftpClient.login(ftp_user, ftp_password);
			// System.out.println("ftpClientftpClientftpClientftpClient" +
			// ftpClient.getStatus());
			ftpClient.setFileType(FTP.BINARY_FILE_TYPE);

			ftpClient.setDataTimeout(60000); // 设置传输超时时间为60秒
			ftpClient.setConnectTimeout(60000); // 连接超时为60秒
			// 检验是否连接成功
			reply = ftpClient.getReplyCode();
			if (!FTPReply.isPositiveCompletion(reply)) {
				ftpClient.disconnect();
				return new ResultInfo<>(-1, "FTP连接失败");
			}
			ftpClient.setControlEncoding(encoding);// 设置ftp编码

			Calendar calendar = Calendar.getInstance();

			// 获取年份
			int year = calendar.get(Calendar.YEAR);
			// 获取月份
			int month = calendar.get(Calendar.MONTH) + 1;
			// 拼接年份和月份
			String date = year + "-" + String.format("%02d", month);

			// 获取日期
			String day = String.format("%02d", calendar.get(Calendar.DAY_OF_MONTH));

			saveUrl = saveUrl + "/" + date + "/" + day;

			isCreate = CreateDirecroty(saveUrl);
		} catch (IOException e) {
			logger.error(Contant.ERROR_MSG, e);
			logger.info("ftp操作发生错误：", e);
		}
		// ftpClient.makeDirectory(saveUrl);

		// 转移工作目录至指定目录下
		// ftpClient.changeWorkingDirectory(saveUrl);
		// 判断文件夹是否创建成功
		if (isCreate) {
			File file = null;
			// 将BufferedImage转为输入流
			os = new ByteArrayOutputStream();
			for (QRCode qr : qcList) {
				try {
					ImageIO.write(qr.getbImage(), "jpg", os);
					is = new ByteArrayInputStream(os.toByteArray());

					String file_name = qr.getSaveName();
					boolean b = false;
					if (file_name == null || file_name.isEmpty()) {
						SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH-mm-ss");
						String dateNowStr = sdf.format(new Date());
						file_name = dateNowStr
								+ ((qr.getLogoUrl() == null || qr.getLogoUrl().isEmpty()) ? "_" : "_logo_")
								+ (new Random().nextInt(99999)) + qr.getContent();
						// 生成临时文件
						file = File.createTempFile(file_name, ".jpg");

						temp = StringUtils.substringBefore(file.getPath(), "temp") + "temp";

						ImageIO.write(qr.getbImage(), "jpg", file);
						FileInputStream in = new FileInputStream(file);
						file_name += ".jpg";
						// 将临时文件移动到指定的ftp文件夹下
						b = ftpClient.storeFile(file_name, in);
						if (b) {
							qr.setFtpPath(saveUrl + "/" + file_name);
							qr.setFtpName(file_name);
						}
					}

					// Boolean b = ftpClient.storeFile(new String(file_name.getBytes(LOCAL_CHARSET),
					// SERVER_CHARSET), is);

					qr.getbImage().flush();
					qr.setbImage(null);// 清空图片流
				} catch (IOException e) {
					logger.info("生成临时图片或转移到ftp时发生错误：", e);
				} finally {
					try {
						if (is != null) {
							is.close();
						}
						/*
						 * if (os != null) { os.close(); }
						 */
					} catch (IOException e) {
						logger.error(Contant.ERROR_MSG, e);
						logger.info("关闭文件流发生错误1：", e);
					}
					if (file != null) {
						// 删除临时文件
						file.deleteOnExit();
						// forceDelete(file);
					}
				}
			}
		}
		try {
			ftpClient.logout();
			if (!isCreate) {
				return new ResultInfo<>(-1, "ftp远程文件创建失败");
			}
		} catch (Exception e) {
			logger.error(Contant.ERROR_MSG, e);
			logger.info("退出fpt时发生错误：", e);
			return new ResultInfo<>(-1, "ftp远程上传文件失败");
		} finally {
			try {
				if (is != null) {
					is.close();
				}
				if (os != null) {
					os.close();
				}
			} catch (IOException e) {
				logger.error(Contant.ERROR_MSG, e);
				logger.info("关闭文件流发生错误2：", e);
				if (ftpClient.isConnected()) {
					try {
						ftpClient.disconnect();
					} catch (IOException ioe) {
						logger.info("关闭ftp链接时发生错误1：", e);
						return new ResultInfo<>(-1, "关闭ftp连接失败");
					}
				}
			}
			if (ftpClient.isConnected()) {
				try {
					ftpClient.disconnect();
				} catch (IOException ioe) {
					logger.info("关闭ftp链接时发生错误2：", ioe);
					return new ResultInfo<>(-1, "关闭ftp连接失败");
				}
			}
		}
		System.out.println("文件临时地址：" + temp);
		logger.info("文件临时地址：" + temp);
		delFolder(temp);
		return new ResultInfo<>(0, "操作成功", qcList);
	}

	public static boolean forceDelete(File file) {
		boolean result = file.delete();
		// int tryCount = 0;
		while (!result) {// && tryCount++ < 3
			System.gc(); // 回收资源
			result = file.delete();
		}
		return result;
	}

	public static void delFolder(String folderPath) {
		System.gc(); // 回收资源
		try {
			delAllFile(folderPath); // 删除完里面所有内容
			/*
			 * String filePath = folderPath; java.io.File myFilePath = new
			 * java.io.File(filePath); myFilePath.delete();
			 */ // 删除空文件夹
		} catch (Exception e) {
			logger.error(Contant.ERROR_MSG, e);
		}
	}

	// 删除指定文件夹下所有文件
	// param path 文件夹完整绝对路径
	public static boolean delAllFile(String path) {
		boolean flag = false;
		File file = new File(path);
		if (!file.exists()) {
			return flag;
		}
		if (!file.isDirectory()) {
			return flag;
		}
		String[] tempList = file.list();
		File temp = null;
		for (int i = 0; i < tempList.length; i++) {
			if (path.endsWith(File.separator)) {
				temp = new File(path + tempList[i]);
			} else {
				temp = new File(path + File.separator + tempList[i]);
			}
			if (temp.isFile()) {
				temp.delete();
			}
			if (temp.isDirectory()) {
				delAllFile(path + "/" + tempList[i]);// 先删除文件夹里面的文件
				delFolder(path + "/" + tempList[i]);// 再删除空文件夹
				flag = true;
			}
		}
		return flag;
	}

	/**
	 * <b>Description:</b><br>
	 * 上传指定的文件到ftp上
	 * 
	 * @param upfile
	 * @param save_path
	 * @param new_fileName
	 * @return
	 * @Note <b>Author:</b> duke <br>
	 *       <b>Date:</b> 2018年7月25日 下午4:01:44
	 */
	@Override
	public FileInfo exeUploadFileToFtp(String upfile, String save_path, String new_fileName) {

		boolean result = false;
		// 临时文件存放地址
		// String path =
		// request.getSession().getServletContext().getRealPath(save_path);
		// 上传文件名称
		File tempFile = new File(upfile.trim());
		String fileName = tempFile.getName();
		// 文件后缀名称
		String prefix = fileName.substring(fileName.lastIndexOf(".") + 1);

		if (new_fileName == null || new_fileName.isEmpty()) {
			// 时间戳
			String time = DateUtil.gainNowDate() + "";
			Random random = new Random();
			time = time + "_" + String.format("%04d", random.nextInt(10000));// 随机数4位，不足4位，补位
			new_fileName = time + "." + prefix;// 新文件名称
		}

		// String new_file = path + File.separator + new_fileName;//新文件地址

		Calendar calendar = Calendar.getInstance();
		// 获取年份
		int year = calendar.get(Calendar.YEAR);
		// 获取月份
		int month = calendar.get(Calendar.MONTH) + 1;
		// 拼接年份和月份
		String date = year + "-" + String.format("%02d", month);

		try {
			BufferedImage bi = ImageIO.read(new File(upfile));
			// 获取日期
			String day = String.format("%02d", calendar.get(Calendar.DAY_OF_MONTH));

			// 区分文件和图片
			if (bi != null) {
				save_path = save_path + "/" + date + "/" + day + "/image";
			} else {
				save_path = save_path + "/" + date + "/" + day + "/file";
			}
		} catch (IOException e1) {
			e1.printStackTrace();
			return new FileInfo(-1, "文件类型获取失败");
		}

		try {
			int reply;
			// 登录
			ftpClient.connect(ftp_url, ftp_port);
			ftpClient.login(ftp_user, ftp_password);
			ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
			// 检验是否连接成功
			reply = ftpClient.getReplyCode();
			if (!FTPReply.isPositiveCompletion(reply)) {
				ftpClient.disconnect();
				return new FileInfo(-1, "连接ftp失败");
			}
			ftpClient.setControlEncoding(encoding);

			ftpClient.enterLocalPassiveMode();
			ftpClient.setRemoteVerificationEnabled(false);

			save_path = save_path.replace("\\", "/");

			CreateDirecroty(save_path);// 创建多层文件夹

			ftpClient.makeDirectory(save_path);

			// 转移工作目录至指定目录下
			ftpClient.changeWorkingDirectory(save_path);

			FileInputStream input = new FileInputStream(new File(upfile));

			result = ftpClient.storeFile(new String(new_fileName.getBytes(LOCAL_CHARSET), SERVER_CHARSET), input);
			input.close();
			ftpClient.logout();
			if (result) {
				logger.info("上传成功!");
				deleteLocalFile(upfile);// 删除本地临时文件
				return new FileInfo(0, "操作成功", new_fileName, save_path, file_url, prefix);
			}
		} catch (IOException e) {
			logger.error(Contant.ERROR_MSG, e);
			deleteLocalFile(upfile);// 删除本地临时文件
			return new FileInfo(-1, "ftp远程上传文件失败");
		} finally {
			if (ftpClient.isConnected()) {
				try {
					ftpClient.disconnect();
				} catch (IOException ioe) {
					deleteLocalFile(upfile);// 删除本地临时文件
					return new FileInfo(-1, "关闭连接失败");
				}
			}
		}
		return new FileInfo();
	}

	@Override
	public FileInfo compressImg(MultipartFile upfile, HttpServletRequest request, String new_fileName, String save_path,
			Map<String, Integer> map) {

		/**********************************************
		 * 创建临时工作区间
		 **************************/
		// 临时文件存放地址
		String path = request.getSession().getServletContext().getRealPath(save_path);
		// 上传文件名称
		String fileName = upfile.getOriginalFilename();
		// 文件后缀名称
		String prefix = fileName.substring(fileName.lastIndexOf(".") + 1);

		if (StringUtils.isBlank(new_fileName)) {
			String time = DateUtil.gainNowDate() + "";
			Random random = new Random();
			// 随机数4位，不足4位，补位
			time = time + "_" + String.format("%04d", random.nextInt(10000));
			// 新文件名称
			new_fileName = time + "." + prefix;
		}
		// 新文件地址
		String new_file = path + File.separator + new_fileName;

		/**********************************************
		 * 创建临时工作区间
		 **************************/

		Calendar calendar = Calendar.getInstance();
		// 获取年份
		int year = calendar.get(Calendar.YEAR);
		// 获取月份
		int month = calendar.get(Calendar.MONTH) + 1;
		// 拼接年份和月份
		String date = year + "-" + String.format("%02d", month);
		// 获取日期
		String day = String.format("%02d", calendar.get(Calendar.DAY_OF_MONTH));
		save_path = save_path + "/" + date + "/" + day + "/image";

		try {
			File file = new File(path);
			if (!file.exists()) {
				file.mkdirs();
			}
			FileOutputStream out = new FileOutputStream(new_file);
			out.write(upfile.getBytes());
			out.flush();
			out.close();
		} catch (IOException e) {
			logger.error(Contant.ERROR_MSG, e);
			return new FileInfo(-1, "写入临时文件失败");
		}

		// 压缩临时地址
		String temp_path = new_file.substring(0, new_file.lastIndexOf("\\") + 1)
				+ new_fileName.substring(0, new_fileName.lastIndexOf(".")) + 1 + "." + prefix;

		String str = new_fileName.substring(0, new_fileName.lastIndexOf(".")) + 1 + "." + prefix;
		logger.info("压缩上传开始!");
		// 临时文件名
		String finalNew_fileName = str;

		// 连接ftp
		try {
			ftpClient.connect(ftp_url, ftp_port);
			ftpClient.login(ftp_user, ftp_password);
			ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
			// 检验是否连接成功
			int reply = ftpClient.getReplyCode();
			if (!FTPReply.isPositiveCompletion(reply)) {
				ftpClient.disconnect();
				logger.info("连接ftp失败!");
			}
			ftpClient.setControlEncoding(encoding);
			Boolean re = null;

			// 进行压缩操作 (各种参数的设置)

			GMOperation op = new GMOperation();
			op.addImage();
			op.quality(map.get("quality").doubleValue());
			op.addImage();
			ConvertCmd convert = new ConvertCmd(true);
			// windows目录
			// convert.setSearchPath("E:\\GraphicsMagick-1.3.31-Q8");
			// 测试库Linux目录
			logger.info(gm_url);
			convert.setSearchPath(gm_url);
			// 正式库Linux目录
			// convert.setSearchPath("/usr/local/GraphicsMagick-1.3.31/bin/");

			convert.run(op, new_file, temp_path);
			FileInputStream is = new FileInputStream(new File(temp_path));
			save_path = save_path.replace("\\", "/");

			/* 创建多层文件夹 */
			CreateDirecroty(save_path);

			// 转移工作目录至指定目录下
			ftpClient.changeWorkingDirectory(save_path);
			// 创建文件夹
			// ftpClient.makeDirectory(entry.getKey() + "_" + entry.getValue());
			// 转移工作目录至指定目录下
			// ftpClient.changeWorkingDirectory(save_path + "/" + entry.getKey() + "_" +
			// entry.getValue());

			re = ftpClient.storeFile(new String(finalNew_fileName.getBytes(LOCAL_CHARSET), SERVER_CHARSET), is);

			if (re) {
				is.close();
				ftpClient.logout();
			}

			// 删除压缩后保存的本地临时文件
			deleteLocalFile(temp_path);
			// 删除本地临时文件
			deleteLocalFile(new_file);
		} catch (Exception e) {
			logger.error(Contant.ERROR_MSG, e);
			// 删除压缩后保存的本地临时文件
			deleteLocalFile(temp_path);
			// 删除本地临时文件
			deleteLocalFile(new_file);
			logger.error("ftp远程压缩上传文件失败!" + e.getMessage(), e);
		} finally {
			if (ftpClient.isConnected()) {
				try {
					ftpClient.disconnect();
				} catch (Exception e) {
					// 删除压缩后保存的本地临时文件
					deleteLocalFile(temp_path);
					// 删除本地临时文件
					deleteLocalFile(new_file);
					logger.error("关闭ftp连接失败!" + e.getMessage(), e);
				}
			}
		}

		logger.info("压缩上传成功!");

		if (request.getParameter("uploadType") != null && request.getParameter("uploadType").equals("uedit")) {
			return new FileInfo(0, "操作成功", finalNew_fileName, save_path, "http://" + file_url, prefix);
		} else {
			return new FileInfo(0, "操作成功", finalNew_fileName, save_path, file_url, prefix);
		}
	}

	/**
	 *
	 * <b>Description:上传到FTP，包含图片压缩</b>
	 *
	 * @param upfile
	 * @param save_path
	 * @param request
	 * @param new_fileName
	 * @return FileInfo
	 * @Note <b>Author：</b> cooper.xu <b>Date:</b> 2018年10月11日 下午2:46:05
	 */
	public synchronized FileInfo exeUploadImg(MultipartFile upfile, String save_path, HttpServletRequest request,
											  String new_fileName, Map<Integer, Integer> map) {

		boolean result = false;
		// 临时文件存放地址
		String path = request.getSession().getServletContext().getRealPath(save_path);
		// 上传文件名称
		String fileName = upfile.getOriginalFilename();
		// 文件后缀名称
		String prefix = fileName.substring(fileName.lastIndexOf(".") + 1);
		if (new_fileName == null || new_fileName.isEmpty()) {
			// 时间戳
			/*
			 * Date dt = new Date(); String time = dt.getTime() + "";
			 */
			String time = DateUtil.gainNowDate() + "";
			Random random = new Random();
			time = time + "_" + String.format("%04d", random.nextInt(10000));// 随机数4位，不足4位，补位
			new_fileName = time + "." + prefix;// 新文件名称
		}

		String new_file = path + File.separator + new_fileName;// 新文件地址

		/*
		 * File targetFile = new File(save_path, fileName);
		 *
		 * if (!targetFile.exists()) { targetFile.mkdirs(); } // 保存 try {
		 * upfile.transferTo(targetFile); } catch (Exception e) {
		 * logger.error(Contant.ERROR_MSG, e); }
		 */
		try {
			File file = new File(path);
			if (!file.exists()) {
				file.mkdirs();
			} // PDF上传设置属性为文件名称
			FileOutputStream out = new FileOutputStream(new_file);// 写入临时地址
			// 写入文件
			out.write(upfile.getBytes());
			out.flush();
			out.close();
			// 如果是pdf的话，进行属性title修改
			if (prefix.equals("pdf") || prefix.equals("PDF")) {
				// 创建一个pdf读入流
				PdfReader reader = new PdfReader(new_file);
				// 生成新的文件名
				String new_pdf_name = DateUtil.gainNowDate() + "" + "_"
						+ String.format("%04d", new Random().nextInt(10000)) + "." + prefix;
				// 生成新的临时文件路径
				String new_pdf_path = path + File.separator + new_pdf_name;
				Document doc = new Document();
				// 创建输出流
				FileOutputStream newOut = new FileOutputStream(new_pdf_path);
				PdfWriter writer = PdfWriter.getInstance(doc, newOut);
				doc.addTitle(fileName.substring(0, fileName.lastIndexOf(".")));
				doc.open();
				PdfContentByte cb = writer.getDirectContent();
				int pageOfCurrentReaderPDF = 0;
				// 写内容
				while (pageOfCurrentReaderPDF < reader.getNumberOfPages()) {
					doc.newPage();
					pageOfCurrentReaderPDF++;
					PdfImportedPage page = writer.getImportedPage(reader, pageOfCurrentReaderPDF);
					cb.addTemplate(page, 0, 0);
				}
				out.flush();
				doc.close();
				out.close();
				deleteLocalFile(new_file);// 删除原临时文件
				new_file = new_pdf_path;
				new_fileName = new_pdf_name;
			}
		} catch (Exception e1) {
			logger.error("写入临时文件失败!",e1);
			return new FileInfo(-1, "写入临时文件失败");
		}

		Calendar calendar = Calendar.getInstance();
		// 获取年份
		int year = calendar.get(Calendar.YEAR);
		// 获取月份
		int month = calendar.get(Calendar.MONTH) + 1;
		// 拼接年份和月份
		String date = year + "-" + String.format("%02d", month);
		// 获取日期
		String day = String.format("%02d", calendar.get(Calendar.DAY_OF_MONTH));
		save_path = save_path + "/" + date + "/" + day + "/image";
		try {
			int reply;
			// 登录
			ftpClient.connect(ftp_url, ftp_port);
			ftpClient.login(ftp_user, ftp_password);
			ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
			// 检验是否连接成功
			reply = ftpClient.getReplyCode();
			if (!FTPReply.isPositiveCompletion(reply)) {
				ftpClient.disconnect();
				return new FileInfo(-1, "连接ftp失败");
			}
			ftpClient.setControlEncoding(encoding);

			save_path = save_path.replace("\\", "/");
			String file_path = "";
			file_path = save_path + "/default/";

			CreateDirecroty(file_path);// 创建多层文件夹

			// ftp保存文件地址
			// String ftp_save_path =ftp_path + "/" + save_path;

			ftpClient.makeDirectory(file_path);
			// 转移工作目录至指定目录下
			ftpClient.changeWorkingDirectory(file_path);
			FileInputStream input = new FileInputStream(new File(new_file));
			result = ftpClient.storeFile(new String(new_fileName.getBytes(LOCAL_CHARSET), SERVER_CHARSET), input);
			input.close();
			ftpClient.logout();
			if (result) {
				logger.info("上传成功!");
				// 遍历压缩尺寸map集合,pdf不压缩
				if (!prefix.equals("pdf") && !prefix.equals("PDF") && !map.isEmpty()) {
					// 开始压缩
					compressImg(new_file, new_fileName, prefix, save_path, map);
				} else {
					deleteLocalFile(new_file);// 删除本地临时文件
				}
				if (request.getParameter("uploadType") != null
						&& request.getParameter("uploadType").toString().equals("uedit")) {
					return new FileInfo(0, "操作成功", new_fileName, file_path, "http://" + file_url, prefix);
				} else {
					return new FileInfo(0, "操作成功", new_fileName, file_path, file_url, prefix);
				}
			}
		} catch (Exception e) {
			logger.error("上传失败!",e);
			deleteLocalFile(new_file);// 删除本地临时文件
			return new FileInfo(-1, "ftp远程上传文件失败");
		} finally {
			if (ftpClient.isConnected()) {
				try {
					ftpClient.disconnect();
				} catch (IOException ioe) {
					return new FileInfo(-1, "关闭连接失败");
				}
			}
		}
		return new FileInfo();
	}

	@Override
	public FileInfo uploadImg(MultipartFile file, String save_path, HttpServletRequest request, String fileName,Map<Integer,Integer> map) {
		FileInfo fileInfo = exeUploadImg(file,save_path,request,fileName,map);
		if(fileInfo.getCode()!=0){//上传失败后再次上传
			return exeUploadImg(file,save_path,request,fileName,map);
		}else{
			return fileInfo;
		}
	}

	@Override
	public void compressImg(String new_file, String new_fileName, String prefix, String save_path,
							Map<Integer, Integer> map) throws InterruptedException, IOException, IM4JavaException {
		// 压缩临时地址
		String temp_path = new_file.substring(0, new_file.lastIndexOf("\\") + 1)
				+ new_fileName.substring(0, new_fileName.lastIndexOf(".")) + 1 + "." + prefix;
		logger.info("压缩上传开始!");
//		new Thread() {
//			@Override
//			public void run() {
				// 连接ftp
				try {
					ftpClient.connect(ftp_url, ftp_port);
					ftpClient.login(ftp_user, ftp_password);
					ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
					// 检验是否连接成功
					int reply = ftpClient.getReplyCode();
					if (!FTPReply.isPositiveCompletion(reply)) {
						ftpClient.disconnect();
						logger.info("连接ftp失败!");
					}
					ftpClient.setControlEncoding(encoding);
					for (Map.Entry<Integer, Integer> entry : map.entrySet()) {
						// 进行压缩操作
						GMOperation op = new GMOperation();
						op.addImage();
						op.resize(entry.getKey(), entry.getValue()).sharpen(1.0, 5.0).quality(90d);
						op.addImage();
						ConvertCmd convert = new ConvertCmd(true);
						// windows目录
						// convert.setSearchPath("E:\\GraphicsMagick-1.3.31-Q8");
						// 测试库Linux目录
						convert.setSearchPath(gm_url);
						// 正式库Linux目录
						// convert.setSearchPath("/usr/local/GraphicsMagick-1.3.31/bin/");
						convert.run(op, new_file, temp_path);
						FileInputStream is = new FileInputStream(new File(temp_path));
						// 转移工作目录至指定目录下
						ftpClient.changeWorkingDirectory(save_path);
						// 创建文件夹
						ftpClient.makeDirectory(entry.getKey() + "x" + entry.getValue());
						// 转移工作目录至指定目录下
						ftpClient.changeWorkingDirectory(save_path + "/" + entry.getKey() + "x" + entry.getValue());
						ftpClient.storeFile(new String(new_fileName.getBytes(LOCAL_CHARSET), SERVER_CHARSET), is);
						is.close();
					}
					ftpClient.logout();
					deleteLocalFile(temp_path);// 删除压缩后保存的本地临时文件
					deleteLocalFile(new_file);// 删除本地临时文件
				} catch (Exception e) {
					// TODO Auto-generated catch block
					//logger.error(Contant.ERROR_MSG, e);
					deleteLocalFile(temp_path);// 删除压缩后保存的本地临时文件
					deleteLocalFile(new_file);// 删除本地临时文件
					logger.error("ftp远程压缩上传文件失败!" , e);
					throw e;
				} finally {
					if (ftpClient.isConnected()) {
						try {
							ftpClient.disconnect();
						} catch (Exception e) {
							deleteLocalFile(temp_path);// 删除压缩后保存的本地临时文件
							deleteLocalFile(new_file);// 删除本地临时文件
							logger.error("关闭ftp连接失败!" + e.getMessage(), e);
						}
					}
				}
				logger.info("压缩上传成功!");
//			}
//		}.start();
	}
}

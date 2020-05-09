package com.vedeng.system.service;

import com.vedeng.common.model.FileInfo;
import com.vedeng.common.model.QRCode;
import com.vedeng.common.model.ResultInfo;
import org.im4java.core.IM4JavaException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@Service
public interface FtpUtilService {
	
	/**
	 * <b>Description:</b><br> 上传附件到远程服务器上
	 * @param file
	 * @param save_path
	 * @param request
	 * @param fileName
	 * @return
	 * @Note
	 * <b>Author:</b> duke
	 * <br><b>Date:</b> 2017年5月25日 上午11:00:10
	 */
	FileInfo uploadFile(MultipartFile file,String save_path ,HttpServletRequest request,String fileName);
	
	/**
	 * <b>Description:</b><br> 根据url地址直接下载附件
	 * @param domain
	 * @param ftpFilePath
	 * @return
	 * @Note
	 * <b>Author:</b> duke
	 * <br><b>Date:</b> 2017年5月25日 上午10:59:52
	 */
	FileInfo downFile(String domain,String ftpFilePath);
	
	/**
	 * <b>Description:</b><br> 根据url地址在浏览器上显示图片
	 * @param domain
	 * @param ftpFilePath
	 * @return
	 * @Note
	 * <b>Author:</b> duke
	 * <br><b>Date:</b> 2017年5月25日 上午10:59:52
	 */
	FileInfo downImageShow(String domain,String ftpFilePath);

	/**
	 * <b>Description:</b><br> 删除远程服务器文件
	 * @param ftpFilePaht
	 * @return
	 * @Note
	 * <b>Author:</b> duke
	 * <br><b>Date:</b> 2017年5月25日 上午10:59:40
	 */
	FileInfo deleteFile(String ftpFilePaht);
	
	/**
	 * <b>Description:</b><br> 下载ftp远程服务器文件到本地
	 * @param request
	 * @param ftpFile
	 * @param fileName
	 * @return
	 * @Note
	 * <b>Author:</b> duke
	 * <br><b>Date:</b> 2017年5月25日 上午10:59:24
	 */
	FileInfo downloadFtpFile(HttpServletRequest request,String ftpFile,String fileName);
	
	/**
	 * <b>Description:</b><br> 上传附件到服务器
	 * @param path
	 * @param file
	 * @return
	 * @Note
	 * <b>Author:</b> duke
	 * <br><b>Date:</b> 2017年6月7日 上午11:03:42
	 */
	FileInfo fileUploadServe(String path,MultipartFile file);
	
	/**
	 * <b>Description:</b><br> 生成二維碼(无logo)
	 * @param content 二維碼內容
	 * @param save_path 保存地址
	 * @param file_name 文件名稱（可為空）
	 * @return
	 * @Note
	 * <b>Author:</b> duke
	 * <br><b>Date:</b> 2017年8月8日 上午9:36:44
	 */
	ResultInfo<QRCode> makeQRCode(List<QRCode> list,String saveUrl);

	/**
	 * <b>Description:</b><br> 生成二維碼(有logo)
	 * @param content 二維碼內容
	 * @param save_path 保存地址
	 * @param file_name 文件名稱（可為空）
	 * @param logo_url logo地址
	 * @param b 是否压缩logo图片
	 * @return
	 * @Note
	 * <b>Author:</b> duke
	 * <br><b>Date:</b> 2017年8月8日 上午10:14:12
	 */
	ResultInfo<QRCode> makeLogoQRCode(List<QRCode> list,String saveUrl, boolean b);
	
	/**
	 * <b>Description:</b><br> 
	 * @param file
	 * @param save_path
	 * @param request
	 * @param fileName
	 * @return
	 * @Note
	 * <b>Author:</b> leo.yang
	 * <br><b>Date:</b> 2018年7月25日 下午5:14:18
	 */
	FileInfo exeUploadFileToFtp(String file,String save_path ,String fileName);
	
	/**
	 *  图片的压缩
	 * @param new_file
	 * @param new_fileName
	 * @param prefix
	 * @param save_path
	 * @param map
	 */
	FileInfo compressImg(MultipartFile upfile ,HttpServletRequest request, String new_fileName,String save_path, Map<String, Integer> map);

	/**
	 *
	 * <b>Description:上传图片，包含压缩</b>
	 * @param file
	 * @param save_path
	 * @param request
	 * @param fileName
	 * @return FileInfo
	 * @Note
	 * <b>Author：</b> cooper.xu
	 * <b>Date:</b> 2018年10月11日 下午2:34:05
	 */
	FileInfo uploadImg(MultipartFile file, String save_path, HttpServletRequest request, String fileName, Map<Integer, Integer> map);

	/**
	 *
	 * <b>Description:线程压缩图片</b>
	 *
	 * @param new_file
	 * @param new_fileName
	 * @param prefix
	 * @param save_path
	 *            void
	 * @Note <b>Author：</b> Cooper.xu <b>Date:</b> 2018年12月25日 下午3:56:39
	 */
	void compressImg(String new_file, String new_fileName, String prefix, String save_path, Map<Integer, Integer> map) throws InterruptedException, IOException, IM4JavaException;
}

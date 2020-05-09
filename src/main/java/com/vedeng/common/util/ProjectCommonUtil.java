
package com.vedeng.common.util;
/**
 * <b>Description:获取项目路径工具类</b><br>
 * <b>Author: Franlin.wu</b> 
 * @fileName ProjectCommonUtil.java
 * <br><b>Date: 2019年3月4日 上午10:54:52 </b> 
 *
 */
public class ProjectCommonUtil
{
	/**
	 * 实例
	 */
	private static ProjectCommonUtil INSTANCE = new ProjectCommonUtil();
	
	/**
	 * 项目路径
	 */
	private String projectPath;
	
	/**
	 * 
	 * <b>Description: 构造方法私有化</b><br> 
	 * <b>Author: Franlin</b>
	 * <br><b>Date: 2019年3月4日 上午11:12:16</b>
	 */
	private ProjectCommonUtil()
	{
		if(null == this.projectPath)
		{
			// 获取当前class路径
			String classPath = this.getClass().getResource("").getPath();
			// 存在空格
			if(-1 != classPath.indexOf("%20"))
			{
				classPath = StringUtil.repaceAll(classPath, "%20", " ");
			}

			// 存在WEB-INF
			if(-1 != classPath.indexOf("WEB-INF"))
			{
				this.projectPath = classPath.split("WEB-INF")[0];
			}
		}
	}
	
	
	/**
	 * 
	 * <b>Description: 获取项目路径</b><br> 
	 * @return
	 * <b>Author: Franlin.wu</b>  
	 * <br><b>Date: 2019年3月4日 上午11:13:34 </b>
	 */
	public static String getProjectPath()
	{

		return INSTANCE.projectPath;
	}
	
	
	
}


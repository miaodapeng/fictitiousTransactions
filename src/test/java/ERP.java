
import java.io.File;

import org.apache.catalina.Context;
import org.apache.catalina.Host;
import org.apache.catalina.Server;
import org.apache.catalina.connector.Connector;
import org.apache.catalina.core.AprLifecycleListener;
import org.apache.catalina.startup.Tomcat;

public class ERP {
	// 设置主机或ip
	private String hostname = "localhost";
	// 设置端口,默认的端口，主要看配置属性
	private int port = 7072;
	//
	private String webappDir = "src/main/webapp";
	// 设置 连接时的一些参数
	private int maxPostSize = -1;
	private int maxThreads = 20;
	private int acceptCount = 20;

	// tomcat引用
	private Tomcat tomcat;

	public static void main(String[] args) {
		getWebserce().start();
	}

	// 启动
	public void start() {
		try {
			// tomcat实例
			this.tomcat = new Tomcat();
			this.tomcat.setPort(this.port);
			this.tomcat.setHostname(this.hostname);
			// tomcat存储自身信息，保存在项目目录下
			this.tomcat.setBaseDir(".");

			this.configServer(this.tomcat.getServer());
			this.tomcat.getEngine();
			this.configHost(this.tomcat.getHost());
			this.configConnector(this.tomcat.getConnector());
			// 第一个参数上下文路径contextPath,第二个参数docBase
			Context context = this.tomcat.addWebapp("",
					System.getProperty("user.dir") + File.separator + this.webappDir);
			// 这种方式也行
			// this.tomcat.getHost().setAppBase(System.getProperty("user.dir")+
			// File.separator+".");
			// this.tomcat.addWebapp("",this.webappDir);

			this.tomcat.start();

			this.tomcat.getServer().await();

		} catch (Exception e) {

		}
	}

	private void configHost(Host host) {
		// user.dir 用户的当前工作目录
		host.setAppBase(System.getProperty("user.dir"));
	}

	private void configServer(Server server) {
		AprLifecycleListener listener = new AprLifecycleListener();
		server.addLifecycleListener(listener);
	}

	// 设置连接属性
	private void configConnector(Connector connector) {
		connector.setURIEncoding("UTF-8");
		connector.setMaxPostSize(this.maxPostSize);
		connector.setAttribute("maxThreads", Integer.valueOf(this.maxThreads));
		connector.setAttribute("acceptCount", Integer.valueOf(this.acceptCount));
		connector.setAttribute("disableUploadTimeout", Boolean.valueOf(true));
		connector.setAttribute("enableLookups", Boolean.valueOf(false));
	}

	public static ERP getWebserce() {
		return new ERP();
	}

}

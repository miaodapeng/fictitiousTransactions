package com.vedeng.common.page;

import java.util.List;
import java.util.Map;

import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.common.base.Joiner;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
public class Page {
	private static final Logger logger = LoggerFactory.getLogger(Page.class);
	private static ObjectMapper mapper = new ObjectMapper();

    public static int DEFAULT_PAGESIZE = 10;
    private Integer pageNo=1;          //当前页码
    private Integer pageSize;        //每页行数
    private Integer totalRecord;      //总记录数
    private Integer totalPage;        //总页数
    private Map<String, String> params;  //查询条件
    private Map<String, List<String>> paramLists;  //数组查询条件
    private String searchUrl;      //Url地址
    private String pageNoDisp;       //可以显示的页号(分隔符"|"，总页数变更时更新)
    private String tableName;         //查询语句的主表名

  public Page(Integer pageNo, Integer pageSize) {
		super();
		this.pageNo = pageNo;
		this.pageSize = pageSize;
		calcSplitPage();
	}

  private Page() {
      pageNo = 1;
      pageSize = DEFAULT_PAGESIZE;
      totalRecord = 0;
      totalPage = 0;
      params = Maps.newHashMap();
      paramLists = Maps.newHashMap();
      searchUrl = "";
      pageNoDisp = "";
    }

    public static Page newBuilder(Integer pageNo, Integer pageSize, String url){
      Page page = new Page();
      page.setPageNo(pageNo==null?1:pageNo);
      page.setPageSize(pageSize==null?DEFAULT_PAGESIZE:pageSize);
      page.setSearchUrl(url);
      return page;
    }

	public static Page newBuilder2(Integer pageNo, Integer pageSize, String url, Integer count) {
		Page page = new Page();
		page.setPageNo(pageNo == null ? 1 : pageNo);
		page.setPageSize(pageSize == null ? Integer.valueOf(DEFAULT_PAGESIZE) : pageSize);
		page.setSearchUrl(url);
		page.setTotalRecord(count);
		return page;
	}
	@JsonIgnore
	private int startRecord = 0;
	@JsonIgnore
	private int endRecord = 0;
	private void calcSplitPage() {
		startRecord = ((pageNo - 1) < 0 ? 0 : (pageNo - 1)) * pageSize;
		endRecord = pageNo * pageSize > 1000 ? 1000 : pageNo * pageSize;
	}

	/**
	 * 查询条件转JSON
	 */
	@JsonIgnore
	public String getParaJson() {
		Map<String, Object> map = Maps.newHashMap();
		for (String key : params.keySet()) {
			if (params.get(key) != null) {
				map.put(key, params.get(key));
			}
		}
		String json = "";
		try {
			json = mapper.writeValueAsString(map);
		} catch (Exception e) {
			logger.error("转换JSON失败" + params, e);
		}
		return json;
	}

	/**
	 * 数组查询条件转JSON
	 */
	@JsonIgnore
	public String getParaListJson() {
		Map<String, Object> map = Maps.newHashMap();
		for (String key : paramLists.keySet()) {
			List<String> lists = paramLists.get(key);
			if (lists != null && lists.size() > 0) {
				map.put(key, lists);
			}
		}
		String json = "";
		try {
			json = mapper.writeValueAsString(map);
		} catch (Exception e) {
			logger.error("转换JSON失败", params, e);
		}
		return json;
	}

	/**
	 * 总件数变化时，更新总页数并计算显示样式
	 */
	private void refreshPage() {
		// 总页数计算
		totalPage = totalRecord % pageSize == 0 ? totalRecord / pageSize : (totalRecord / pageSize + 1);
		// 防止超出最末页（浏览途中数据被删除的情况）
		if (pageNo > totalPage && totalPage != 0) {
			pageNo = totalPage;
		}
		pageNoDisp = computeDisplayStyleAndPage();
	}

	/**
	 * 计算页号显示样式 这里实现以下的分页样式("[]"代表当前页号)，可根据项目需求调整 [1],2,3,4,5,6,7,8..12,13
	 * 1,2..5,6,[7],8,9..12,13 1,2..6,7,8,9,10,11,12,[13]
	 */
	private String computeDisplayStyleAndPage() {
		List<Integer> pageDisplays = Lists.newArrayList();
		if (totalPage <= 11) {
			for (int i = 1; i <= totalPage; i++) {
				pageDisplays.add(i);
			}
		} else if (pageNo < 7) {
			for (int i = 1; i <= 8; i++) {
				pageDisplays.add(i);
			}
			pageDisplays.add(0);// 0 表示 省略部分(下同)
			pageDisplays.add(totalPage - 1);
			pageDisplays.add(totalPage);
		} else if (pageNo > totalPage - 6) {
			pageDisplays.add(1);
			pageDisplays.add(2);
			pageDisplays.add(0);
			for (int i = totalPage - 7; i <= totalPage; i++) {
				pageDisplays.add(i);
			}
		} else {
			pageDisplays.add(1);
			pageDisplays.add(2);
			pageDisplays.add(0);
			for (int i = pageNo - 2; i <= pageNo + 2; i++) {
				pageDisplays.add(i);
			}
			pageDisplays.add(0);
			pageDisplays.add(totalPage - 1);
			pageDisplays.add(totalPage);
		}
		return Joiner.on("|").join(pageDisplays.toArray());
	}

	public Integer getPageNo() {
		return pageNo == null ? 1 : pageNo;
	}

	public void setPageNo(Integer pageNo) {
		this.pageNo = pageNo;
	}

	public Integer getPageSize() {
		return pageSize==null?DEFAULT_PAGESIZE:pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

	public Integer getTotalRecord() {
		return totalRecord;
	}

	public void setTotalRecord(Integer totalRecord) {
		this.totalRecord = totalRecord;
		refreshPage();
	}

	public Integer getTotalPage() {
		return totalPage;
	}

	public void setTotalPage(Integer totalPage) {
		this.totalPage = totalPage;
	}

	public Map<String, String> getParams() {
		return params;
	}

	public void setParams(Map<String, String> params) {
		this.params = params;
	}

	public Map<String, List<String>> getParamLists() {
		return paramLists;
	}

	public void setParamLists(Map<String, List<String>> paramLists) {
		this.paramLists = paramLists;
	}
	public String getSearchUrl() {
		return searchUrl;
	}
	public void setSearchUrl(String searchUrl) {
    	/*int i = searchUrl.indexOf(".do?");
    	if(i != -1){
    		int j = searchUrl.indexOf("pageNo=");
    		if(j != -1){
    			System.out.println(searchUrl.substring(0, i) + searchUrl.substring(j, searchUrl.length()));
    		}
    	}*/
		this.searchUrl = searchUrl;
	}
	public String getPageNoDisp() {
		return pageNoDisp;
	}
	public void setPageNoDisp(String pageNoDisp) {
		this.pageNoDisp = pageNoDisp;
	}

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public boolean noUpPage(int pageCount) {
		if(1 == pageCount) {
			return true;
		}
		return false;
	}

	public boolean noNextPage(int pageCount) {
		if(totalPage == pageCount) {
			return true;
		}
		return false;
	}

	public int upThree(int pageCount) {
		if(pageCount == 0) {
			pageCount = 1;
		}
		if(pageCount - 3 < 3 ) {
			return 2;
		}
		//需要向前扩展的情况
		if(pageCount + 3 > totalPage - 1 ) {
			return totalPage - 7;
		}
		return pageCount - 3;

	}

	public boolean upEllipsis(int pageCount) {
		return pageCount > 5;

	}

	public boolean downEllipsis(int pageCount) {
		return (totalPage-pageCount) >= 5;

	}

	public int downThree(int pageCount) {
		if(pageCount == 0) {
			pageCount = 1;
		}
		if(pageCount + 3 < 8 ) {
			return 8;
		}
		if(pageCount + 3 > totalPage - 1 ) {
			return totalPage - 1;
		}
		return pageCount + 3;
	}
	@JsonIgnore
	public int getStartRecord() {
		return startRecord;
	}

	public void setStartRecord(int startRecord) {
		this.startRecord = startRecord;
	}
	@JsonIgnore
	public int getEndRecord() {
		return endRecord;
	}

	public void setEndRecord(int endRecord) {
		this.endRecord = endRecord;
	}
}

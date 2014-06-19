package ${packgeDO.utilPackageName};

import java.util.ArrayList;
import java.util.List;

/**
 * 封装分页和排序参数及分页查询结果.
 */
public class Page<T> {

	public final static int DEFAULT_PAGESIZE = 2;

	public final static int DEFAULT_PAGE = 1;

	private int pageNo = DEFAULT_PAGE;

	private int pageSize = DEFAULT_PAGESIZE;

	private int totalCount = -1;

	private List<T> list = null;

	public Page() {
	}

	public Page(int pageNo, int pageSize) {
		this.pageNo = pageNo;
		this.pageSize = pageSize;
	}

	public Page(int pageNo, List<T> list, int pageSize, int totalCount) {
		this.pageNo = pageNo;
		this.list = list;
		this.pageSize = pageSize;
		this.totalCount = totalCount;
	}

	/** 第一条记录在结果集中的位置,序号从0开始 */
	public int getStart() {
		if (pageNo < 0 || pageSize < 0) {
			return -1;
		} else {
			return ((pageNo - 1) * pageSize);
		}
	}

	/** 总页数 */
	public int getTotalPageCount() {
		int count = totalCount / pageSize;
		if (totalCount % pageSize > 0) {
			count++;
		}
		return count;
	}

	/** 是否还有下一页 */
	public boolean isHasNextPage() {
		return (pageNo + 1 <= getTotalPageCount());
	}

	/** 返回下页的页号,序号从1开始 */
	public int getNextPage() {
		if (isHasNextPage()) {
			return pageNo + 1;
		} else {
			return pageNo;
		}
	}

	/** 是否还有上一页 */
	public boolean isHasPrePage() {
		return (pageNo - 1 >= 1);
	}

	/** 返回上页的页号,序号从1开始 */
	public int getPrePage() {
		if (isHasPrePage()) {
			return pageNo - 1;
		} else {
			return pageNo;
		}
	}

	/** 每页的记录数量 */
	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	/** 当前页的页号,序号从1开始 */
	public int getPageNo() {
		return pageNo;
	}

	public void setPageNo(int page) {
		this.pageNo = page;
	}

	/** 页内的数据列表 */
	public List<T> getList() {
		if (totalCount == -1) {
			return new ArrayList<T>();
		}
		return list;
	}

	public void setList(List<T> list) {
		this.list = list;
	}

	/** 总记录数量 */
	public int getTotalCount() {
		return totalCount < 0 ? 0 : totalCount;
	}

	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}
}

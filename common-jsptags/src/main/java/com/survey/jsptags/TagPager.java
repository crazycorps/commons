package com.survey.jsptags;

import java.io.IOException;

import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.tagext.TagSupport;

@SuppressWarnings("serial")
public class TagPager extends TagSupport implements java.io.Serializable {
	
	public enum PagerInfo{
		
		FIRST("第一页"),PREV("上一页"),NEXT("下一页"),LAST("尾页"),DI("第"),YE("页"),TOTAL("共"),CONFIRM("确定");
		
		private String displayName;

		private PagerInfo(String displayName) {
			this.displayName = displayName;
		}

		public String getDisplayName() {
			return displayName;
		}
		
	}

	/**
	 * 当前页
	 */
	private int currentpage;

	/**
	 * 总页数
	 */
	private int totalpage;

	public int doStartTag() throws JspTagException {
		return EVAL_BODY_INCLUDE;
	}

	public int doEndTag() throws JspTagException {
		try {
			StringBuilder sb=new StringBuilder();
			sb.append("<div class='pager_tag'>");
			sb.append("<span class='pager_info'>");
			if(this.totalpage==1){// 如果就一页不显示分页信息
			}else if(this.totalpage==this.currentpage){// 最后一页
				sb.append(this.generatePager(PagerInfo.FIRST.displayName, 1));
				sb.append(this.generatePager(PagerInfo.PREV.displayName,this.currentpage-1));
			}else if(this.currentpage==1){// 第一页
				sb.append(this.generatePager(PagerInfo.NEXT.displayName, this.currentpage+1));
				sb.append(this.generatePager(PagerInfo.LAST.displayName,this.totalpage));
			}else{
				sb.append(this.generatePager(PagerInfo.FIRST.displayName, 1));
				sb.append(this.generatePager(PagerInfo.PREV.displayName,this.currentpage-1));
				sb.append(this.generatePager(PagerInfo.NEXT.displayName, this.currentpage+1));
				sb.append(this.generatePager(PagerInfo.LAST.displayName,this.totalpage));
			}
			sb.append("</span>");
			sb.append(this.getPagerOverview());
			sb.append(this.generatePagerJump());
			sb.append("</div>");
			pageContext.getOut().write(sb.toString());
		} catch (IOException ex) {
			ex.printStackTrace();
			throw new JspTagException("Fatal error");
		}

		return EVAL_PAGE;
	}
	
	private String getPagerOverview(){
		StringBuilder sb=new StringBuilder();
		
		sb.append("<span class='overview'>");
		sb.append(PagerInfo.DI.displayName);
		sb.append(this.currentpage);
		sb.append(PagerInfo.YE.displayName);
		sb.append("</span>");
		
		sb.append("<span class='overview'>");
		sb.append(PagerInfo.TOTAL.displayName);
		sb.append(this.totalpage);
		sb.append(PagerInfo.YE.displayName);
		sb.append("</span>");
		
		return sb.toString();
	}
	
	
	@SuppressWarnings("unused")
	private String appendPrevSeparator(String str,String separator,int blankNum){
		StringBuilder sb=new StringBuilder();
		for(int i=0;i<blankNum;i++){
			sb.append(separator);
		}
		sb.append(str);
		return sb.toString();
	}
	
	@SuppressWarnings("unused")
	private String appendNextSeparator(String str,String separator,int blankNum){
		StringBuilder sb=new StringBuilder(str);
		for(int i=0;i<blankNum;i++){
			sb.append(separator);
		}
		return sb.toString();
	}

	private String generatePager(String pagerInfo,int toPage) {
		StringBuilder sb = new StringBuilder("<span class='pager'><a href='javascript:return false;'");
		sb.append(" toPage='");
		sb.append(toPage);
		sb.append("'>");
		sb.append(pagerInfo);
		sb.append("</a></span>");
		return sb.toString();
	}
	
	private String generatePagerJump() {
		StringBuilder sb = new StringBuilder("<span class='pager_jump'>");
		sb.append("<input type='text' name='pagerJumpNo' class='no'/>");
		sb.append("<input type='button' name='pagerJumpBtn' class='btn' value='");
		sb.append(PagerInfo.CONFIRM.displayName);
		sb.append("'/>");
		sb.append("</span>");
		return sb.toString();
	}

	public int getCurrentpage() {
		return currentpage;
	}

	public void setCurrentpage(int currentpage) {
		this.currentpage = currentpage;
	}

	public int getTotalpage() {
		return totalpage;
	}

	public void setTotalpage(int totalpage) {
		this.totalpage = totalpage;
	}
}
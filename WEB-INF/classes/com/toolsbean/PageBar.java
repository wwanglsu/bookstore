package com.toolsbean;

public class PageBar {
	private int allR;				//all book records
	private int perR;				//book records per page to show
	private int perP;				//page number per page to show
	private int allP;				//all page number
	private int allG;				//all groups number	
	private int currentP=1;			//current page number
	private int currentG=1;			//current group number	
	private String pageLink;		//page navigation bar
	
	public PageBar(){
		allR=0;				
		perR=12;				
		perP=3;				
		currentP=1;
		currentG=1;
		pageLink="";
	}
	
	/**set all book records*/
	public void setAllR(int allR){
		this.allR=allR;
	}
	/**set book records per page*/
	public void setPerR(int perR){
		this.perR=perR;
	}	
	/**set page number per page*/
	public void setPerP(int perP){
		this.perP=perP;
	}
	/**set page navigation bar*/
	public void setPageBar(String strcurrentP,String strcurrentG,String goWhich){
		setAllP();								
		setAllG();								
		setCurrentP(strcurrentP);				
		setCurerntG(strcurrentG);				
		setCurrent();							
		setPageLink(goWhich);				
	}
	/**set all page number*/
	private void setAllP(){
		allP=(allR%perR==0)?(allR/perR):(allR/perR+1);
	}
	/**set all group number*/
	private void setAllG(){
		allG=(allP%perP==0)?(allP/perP):(allP/perP+1);
	}
	/**set current page*/
	private void setCurrentP(String currentP) {		
		try{
			this.currentP=Integer.parseInt(currentP);
		}catch(NumberFormatException e){
			this.currentP=-1;
		}	
	}
	/**set current group number*/
	private void setCurerntG(String currentG){
		try{
			this.currentG=Integer.parseInt(currentG);
		}catch(Exception e){
			this.currentG=-1;
		}
	}
	/**set current page and group at last*/
	private void setCurrent(){
		if(currentP==-1&&currentG==-1){
			currentP=1;
			currentG=1;
		}
		else if(currentP!=-1&&currentG!=-1){
			currentP=1;
			currentG=1;
		}		
		else if(currentP!=-1){
			if(currentP>allP)
				currentP=allP;
			currentG=currentP/perP+1;
		}
		else if(currentG!=-1){
			if(currentG>allG)
				currentG=allG;
			currentP=(currentG-1)*perP+1;
		}
	}
    /**set page navigation link*/
	private void setPageLink(String goWhich){
		pageLink="<table width='100%' border='0' cellpadding='2' cellspacing='0'>";
		pageLink+="<tr class='pager'>";
		pageLink+="<td>";
		pageLink+="Total("+allR+")records &nbsp;&nbsp;&nbsp; total("+allP+")pages &nbsp;&nbsp;&nbsp;";
		pageLink+="</td>";
		
		if(goWhich==null)goWhich="";
		if(goWhich.indexOf("?")>=0)
			goWhich+="&";
		else
			goWhich+="?";		
		
		pageLink+="<td align='right'>[";

		if(currentG>1)
			pageLink+="<a href='"+goWhich+"currentG="+(currentG-1)+"' class='pagertext'>Last"+perP+"Page</a> ";
		if(currentP>1){
			pageLink+="<a href='"+goWhich+"currentP=1'>Home</a> ";
			pageLink+="<a href='"+goWhich+"currentP="+(currentP-1)+"'>Pre</a> | ";
		}
		
		if(currentP%perP==0)
			pageLink+="<a class='pagerCurrentP'>"+currentP+"</a>&nbsp;&nbsp;";
		
		int temp=currentG;
		int start=(currentG-1)*perP+1;
		for(int i=0;((i<perP)&&((start+i)<=allP));i++){
			if((start+i)%perP==0)
				temp++;
			else
				temp=currentG;
			if((start+i)==currentP)
				pageLink+="<a class='pagerCurrentP'>"+(start+i)+"</a>&nbsp;&nbsp;";
			else
				pageLink+="<a href='"+goWhich+"currentP="+(start+i)+"' class='pagerline'>"+(start+i)+"</a>&nbsp;&nbsp;";
		}		
		
		if(currentP<allP){
			pageLink+="| <a href='"+goWhich+"currentP="+(currentP+1)+"'>Next</a>";
			pageLink+=" <a href='"+goWhich+"currentP="+allP+"#listtop'>End</a>";
		}		
		if(currentG<allG)
			pageLink+=" <a href='"+goWhich+"currentG="+(currentG+1)+"' class='pagertext'>Next"+perP+"Page</a>";
		
		pageLink+="]</td>";		
		pageLink+="</tr></table>";	
	}

	public int getAllR() {
		return allR;
	}
	public int getPerR() {
		return perR;
	}
	public int getPerP() {
		return perP;
	}
	public int getAllP() {
		return allP;
	}
	public int getAllG() {
		return allG;
	}
	public int getCurrentP() {
		return currentP;
	}
	public int getCurrentG() {
		return currentG;
	}
	public String getPageLink() {
		return pageLink;
	}	
}

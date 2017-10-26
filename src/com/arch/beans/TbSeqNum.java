package com.arch.beans;

public class TbSeqNum implements java.io.Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String tbName;
	private Integer seqNextVal;
	/**
	 * @return the tbName
	 */
	public String getTbName() {
		return tbName;
	}
	/**
	 * @param tbName the tbName to set
	 */
	public void setTbName(String tbName) {
		this.tbName = tbName;
	}
	/**
	 * @return the seqNextVal
	 */
	public Integer getSeqNextVal() {
		return seqNextVal;
	}
	/**
	 * @param seqNextVal the seqNextVal to set
	 */
	public void setSeqNextVal(Integer seqNextVal) {
		this.seqNextVal = seqNextVal;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "TbSeqNum [tbName=" + tbName + ", seqNextVal=" + seqNextVal
				+ "]";
	}
	
}

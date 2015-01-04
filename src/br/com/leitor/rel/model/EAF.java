package br.com.leitor.rel.model;

public class EAF {
	
	private int index;
	private String data;
	private String se;
	private String su;
	private String ne;
	private String no;
	
	public String getData() {
		return data;
	}
	public void setData(String data) {
		this.data = data;
	}
	public String getSe() {
		return se;
	}
	public void setSe(String se) {
		this.se = se;
	}
	public String getSu() {
		return su;
	}
	public void setSu(String su) {
		this.su = su;
	}
	public String getNe() {
		return ne;
	}
	public void setNe(String ne) {
		this.ne = ne;
	}
	public String getNo() {
		return no;
	}
	public void setNo(String no) {
		this.no = no;
	}
	public int getIndex() {
		return index;
	}
	public void setIndex(int index) {
		this.index = index;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((data == null) ? 0 : data.hashCode());
		result = prime * result + index;
		result = prime * result + ((ne == null) ? 0 : ne.hashCode());
		result = prime * result + ((no == null) ? 0 : no.hashCode());
		result = prime * result + ((se == null) ? 0 : se.hashCode());
		result = prime * result + ((su == null) ? 0 : su.hashCode());
		return result;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		EAF other = (EAF) obj;
		if (data == null) {
			if (other.data != null)
				return false;
		} else if (!data.equals(other.data))
			return false;
		if (index != other.index)
			return false;
		if (ne == null) {
			if (other.ne != null)
				return false;
		} else if (!ne.equals(other.ne))
			return false;
		if (no == null) {
			if (other.no != null)
				return false;
		} else if (!no.equals(other.no))
			return false;
		if (se == null) {
			if (other.se != null)
				return false;
		} else if (!se.equals(other.se))
			return false;
		if (su == null) {
			if (other.su != null)
				return false;
		} else if (!su.equals(other.su))
			return false;
		return true;
	}
	
}

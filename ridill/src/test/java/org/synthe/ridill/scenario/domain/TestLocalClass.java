package org.synthe.ridill.scenario.domain;

public class TestLocalClass {
	public class LocalClass{
		private String innerString;

		public String getInnerString() {
			return innerString;
		}

		public void setInnerString(String innerString) {
			this.innerString = innerString;
		}
	}
	private String string;
	private LocalClass local;
	public String getString() {
		return string;
	}
	public void setString(String string) {
		this.string = string;
	}
	public LocalClass getLocal() {
		return local;
	}
	public void setLocal(LocalClass local) {
		this.local = local;
	}
}

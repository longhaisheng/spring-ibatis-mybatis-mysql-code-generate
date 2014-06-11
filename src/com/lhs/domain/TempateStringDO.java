package com.lhs.domain;

public class TempateStringDO {

	private String formInput;

	private String showErrors;

	public TempateStringDO() {
		setFormInput("@spring.formInput");
		setShowErrors("@spring.showErrors");
	}

	public String getFormInput() {
		return formInput;
	}

	public void setFormInput(String formInput) {
		this.formInput = formInput;
	}

	public String getShowErrors() {
		return showErrors;
	}

	public void setShowErrors(String showErrors) {
		this.showErrors = showErrors;
	}

}

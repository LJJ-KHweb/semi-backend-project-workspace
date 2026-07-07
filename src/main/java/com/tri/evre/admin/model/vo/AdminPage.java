package com.tri.evre.admin.model.vo;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class AdminPage {
	private int sumRequires;
	private int finishRequires;
	private int notFinishRequires;
	private int sumUsers;
}

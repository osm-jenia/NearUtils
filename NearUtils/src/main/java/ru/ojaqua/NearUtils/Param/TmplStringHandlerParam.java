package ru.ojaqua.NearUtils.Param;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;

public class TmplStringHandlerParam {

	@XmlElementWrapper(name = "TmplStringList")
	@XmlElement(name = "TmplString")
	private List<String> tmplStringList = new ArrayList<>();

	public List<String> getTmplStringList() {
		return Collections.unmodifiableList(tmplStringList);
	}

}

package ru.ojaqua.NearUtils.Handlers.TemplStringHandler;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlType(name = "TemplStringPrm")
@XmlRootElement
public class TemplStringPrm {

	@XmlElementWrapper(name = "PrmList", nillable = true)
	@XmlElement(name = "TemplString")
	private List<TemplString> templStringList = new ArrayList<>();

	public List<TemplString> getTemplStringList() {
		return Collections.unmodifiableList(templStringList);
	}

}

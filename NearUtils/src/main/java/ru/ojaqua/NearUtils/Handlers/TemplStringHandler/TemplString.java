package ru.ojaqua.NearUtils.Handlers.TemplStringHandler;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;

public class TemplString {

	@XmlAttribute(name = "name")
	private String name;

	@XmlElement(name = "templ")
	private String templ;

	public String getName() {
		return name;
	}

	public String getTempl() {
		return templ;
	}

}

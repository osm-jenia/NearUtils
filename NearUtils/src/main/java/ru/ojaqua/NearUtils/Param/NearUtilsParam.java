package ru.ojaqua.NearUtils.Param;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlType(name = "NearUtilsParam")
@XmlRootElement
public class NearUtilsParam {

	@XmlElement(name = "tmplStringHandlerParam")
	TmplStringHandlerParam tmplStringHandlerParam;

	public TmplStringHandlerParam getStringHandlerPrm() {
		return tmplStringHandlerParam;
	}

}

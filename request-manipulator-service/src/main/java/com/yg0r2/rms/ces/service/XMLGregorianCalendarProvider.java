package com.yg0r2.rms.ces.service;

import java.util.Calendar;
import java.util.GregorianCalendar;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import org.springframework.stereotype.Component;

@Component
class XMLGregorianCalendarProvider {

    public XMLGregorianCalendar newXMLGregorianCalendar() {
        try {
            return DatatypeFactory.newInstance().newXMLGregorianCalendar(newGregorianCalendar());
        }
        catch (DatatypeConfigurationException e) {
            throw new IllegalStateException("Error instantiating the xml data type factory, "
                + "this means JAXB is not configured correctly, most likely the JVM is not of the right version", e);
        }
    }

    private GregorianCalendar newGregorianCalendar() {
        return (GregorianCalendar) Calendar.getInstance();
    }

}

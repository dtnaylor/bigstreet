package edu.uiowa.nursing;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import edu.uiowa.nursing.models.Diagnosis;
import edu.uiowa.nursing.models.Intervention;
import edu.uiowa.nursing.models.Outcome;

public class XMLParser extends DefaultHandler {
	HashMap<Integer, Diagnosis> diagnoses = new HashMap<Integer, Diagnosis>();
	
	Diagnosis tempDiagnosis;
	String tempDiagnosisName;
	String tempDiagnosisDefn;
	List<Outcome> tempOutcomes;
	Outcome tempOutcome;
	String tempOutcomeName;
	String tempOutcomeDefn;
	List<Intervention> tempMajors;
	List<Intervention> tempSuggested;
	List<Intervention> tempOptional;
	Intervention tempIntervention;
	String tempVal;
	
	String interventionType;
	
	int count = 0;
	
	public HashMap<Integer, Diagnosis> parseDocument() {

		//get a factory
		SAXParserFactory spf = SAXParserFactory.newInstance();
		try {

			//get a new instance of parser
			SAXParser sp = spf.newSAXParser();

			//parse the file and also register this class for call backs
			InputStream in = getClass().getResourceAsStream("diagnoses.xml");
			sp.parse(in, this);

		}catch(SAXException se) {
			se.printStackTrace();
		}catch(ParserConfigurationException pce) {
			pce.printStackTrace();
		}catch (IOException ie) {
			ie.printStackTrace();
		}
		
		return diagnoses;
	}
	
	
	
	
	//Event Handlers
	public void startElement(String uri, String localName, String qName,
		Attributes attributes) throws SAXException {
		//reset
		tempVal = "";
		if(qName.equalsIgnoreCase("diagnosis")) {
			//reset stuff
			 tempDiagnosis = null;
			 tempDiagnosisName = "";
			 tempDiagnosisDefn = "";
			 tempOutcomes = new ArrayList<Outcome>();
			 tempOutcome = null;
			 tempOutcomeName = "";
			 tempOutcomeDefn = "";
			 tempMajors = null;
			 tempSuggested = null;
			 tempOptional = null;
			 tempIntervention = null;
			 tempVal = "";
		}
		else if (qName.equalsIgnoreCase("outcome")) {
			tempOutcome = null;
			 tempOutcomeName = "";
			 tempOutcomeDefn = "";
			 tempMajors = new ArrayList<Intervention>();
			 tempSuggested = new ArrayList<Intervention>();
			 tempOptional = new ArrayList<Intervention>();
			 tempIntervention = null;
			 tempVal = "";
			
		}
		else if (qName.equalsIgnoreCase("intervention")) {
			
			 tempIntervention = null;
			 tempVal = "";
			
		}
		else if (qName.equalsIgnoreCase("major_interventions")) {
			
			 interventionType = "major_interventions";
		}
		else if (qName.equalsIgnoreCase("suggested_interventions")) {
			
			 interventionType = "suggested_interventions";
		}
		else if (qName.equalsIgnoreCase("optional_interventions")) {
			
			 interventionType = "optional_interventions";
		}
	}


	public void characters(char[] ch, int start, int length) throws SAXException {
		tempVal = new String(ch,start,length);
	}

	public void endElement(String uri, String localName,
		String qName) throws SAXException {

		if(qName.equalsIgnoreCase("diagnosis")) {
			tempDiagnosis = new Diagnosis(tempDiagnosisName.trim(), -1, tempDiagnosisDefn.trim(), tempOutcomes);
			diagnoses.put(count++, tempDiagnosis);

		}else if (qName.equalsIgnoreCase("name")) {
			if(tempDiagnosisName == "") tempDiagnosisName = tempVal;
			else if(tempOutcomeName == "") tempOutcomeName = tempVal;
			
		}else if (qName.equalsIgnoreCase("definition")) {
			if(tempDiagnosisDefn == "") tempDiagnosisDefn = tempVal;
			else if(tempOutcomeDefn == "") tempOutcomeDefn = tempVal;
			
			
		}else if (qName.equalsIgnoreCase("outcome")) {
			tempOutcome = new Outcome(tempOutcomeName.trim(), -1, tempOutcomeDefn.trim(),
					tempMajors, tempSuggested, tempOptional);
			
			tempOutcomes.add(tempOutcome);
			
			
		}else if (qName.equalsIgnoreCase("intervention")) {
			Intervention tempIntervention = new Intervention(tempVal.trim(), -1);
			if(interventionType == "major_interventions")
				tempMajors.add(tempIntervention);
			else if(interventionType == "suggested_interventions")
				tempSuggested.add(tempIntervention);
			else if(interventionType == "optional_interventions")
				tempOptional.add(tempIntervention);
		}

	}
}

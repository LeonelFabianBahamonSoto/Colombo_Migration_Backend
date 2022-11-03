package co.edu.colomboamericano.caelassessment.utils;

import org.springframework.stereotype.Service;

@Service
public class ProspectiveTypeDescription
{
	public static String GetNameDocumentType( String documentName )
	{		
		switch ( documentName ) {
			case "1": return "Tarjeta de identidad";
			case "2": return "Cedula de ciudadania";
			case "3": return "Tarjeta de extranjeria";
			case "4": return "Cedula de extranjeria";
			case "5": return "NIT";
			case "6": return "Pasaporte";
			case "7": return "Documento de identificacion extranjero";
			case "8": return "Cedula militar";
			case "9": return "Registro civil";
			case "10": return "NP";
			default: return "No indica";
		}
	};
	
	public static String GetAssessmentStatusDescription( String assessmentName )
	{		
		switch ( assessmentName ) {
			case "1": return "En Progreso";
			case "2": return "Completada";
			case "3": return "Expirada en progreso";
			case "4": return "Expirada completada";
			default:    return "No indica";
		}
	};

}
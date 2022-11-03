package co.edu.colomboamericano.caelassessment.utils;

import java.util.List;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import co.edu.colomboamericano.caelassessment.dto.ProspectiveByDateRangeStateFilterDto;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class CsvExportHelper
{	
    private XSSFWorkbook workbook;
    private XSSFSheet sheet;
    private List<ProspectiveByDateRangeStateFilterDto> filteredProspectivesByState;
    private String state;
    
	/**
	 * @author Smarthink
	 * @param  ProspectiveByDateRangeFilterDto filtered list from prospective service.
	 * @return CSV from ProspectiveByDateRangeFilterDto filtered list 
	 */
    public CsvExportHelper( List<ProspectiveByDateRangeStateFilterDto> filteredProspectivesByState ) {
        this.filteredProspectivesByState = filteredProspectivesByState;
        workbook = new XSSFWorkbook();
    };

	private void writeHeaderLine()
    {
		if( state.equals("R") ) sheet = workbook.createSheet("Registered students");
		if( state.equals("NR") ) sheet = workbook.createSheet("Students not registered");
		if( !state.equals("R") && !state.equals("NR") ) sheet = workbook.createSheet("All records");

        Row row = sheet.createRow(0);
         
        CellStyle style = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setBold( true );
        font.setFontHeight( 13 );
        style.setFont( font );

        createCell(row, 0, "Nombre completo", style);      
        createCell(row, 1, "Tipo de documento", style);       
        createCell(row, 2, "Numero de documento", style);    
        createCell(row, 3, "Fecha nacimiento", style);
        createCell(row, 4, "Email", style);
        createCell(row, 5, "Celular", style);
        createCell(row, 6, "Nivelacion", style);
        createCell(row, 7, "Estado", style);
        createCell(row, 8, "Sede", style);
        createCell(row, 9, "Fecha inicio de registro", style);
        createCell(row, 10, "Fecha de ultimo cambio", style);
    };
    
    private void createCell( Row row, int columnCount, Object value, CellStyle style )
    {
        sheet.autoSizeColumn( columnCount );
        Cell cell = row.createCell( columnCount );
        
        if ( value instanceof Integer ) {
            cell.setCellValue((Integer) value);
        } else if ( value instanceof Boolean ) {
            cell.setCellValue((Boolean) value);
        }else {
            cell.setCellValue((String) value);
        }
        cell.setCellStyle(style);
    };
    
	/**
	 * @author Smarthink
	 * @return De la lista de estudiantes filtrados en el ProspectiveService consulta por el numero el nombre
	 * del tipo de documento al cual pertenece documentTypeName de igual manera por el numero el assessment
	 * status el estado al cual pertenece assessmentStatus.
	 */
    private void writeDataLines()
    {
        int rowCount = 1;
 
        CellStyle style = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setFontHeight(11);
        style.setFont(font);
                 
        for ( ProspectiveByDateRangeStateFilterDto list : filteredProspectivesByState )
        {
            Row row = sheet.createRow( rowCount++ );
            int columnCount = 0;
             
            createCell( row, columnCount++, list.getFullName(), style );
			String documentTypeName = ProspectiveTypeDescription.GetNameDocumentType(  list.getDocumentType() );
            createCell( row, columnCount++, documentTypeName , style );
            createCell( row, columnCount++, String.valueOf( list.getDocumentNumber() ), style );
            createCell( row, columnCount++, String.valueOf( list.getBirthdate() ), style );
            createCell( row, columnCount++, list.getEmail(), style );
            createCell( row, columnCount++, list.getCellphone(), style );
            createCell( row, columnCount++, list.getCourse(), style );
            String assessmentStatus = ProspectiveTypeDescription.GetAssessmentStatusDescription( list.getAssessmentStatus() );
            createCell( row, columnCount++, assessmentStatus, style );
            createCell( row, columnCount++, list.getHeadquarter(), style );
            createCell( row, columnCount++, String.valueOf( list.getAssessmentCreatedAt() ), style );
            createCell( row, columnCount++, String.valueOf( list.getAssessmentUpdatedAt() ), style );
        }
    };

    public void getProspectiveCsvFile( HttpServletResponse response, String status )
    {
    	try {
    			this.state = status;
    			writeHeaderLine();
    			writeDataLines();

            	ServletOutputStream outputStream = response.getOutputStream();
            	workbook.write( outputStream );
            	workbook.close();
             
            	outputStream.close();
		} catch (Exception e) {
			log.error( "No fue posible generar el CSV del metodo writeStudentsToCsv. Error: " + e.getMessage() + " " + e.getCause() );
			e.printStackTrace();
		};
    };

}
package co.edu.colomboamericano.caelassessment.dto;

import lombok.Data;

@Data
public class ParamsEmailDto
{
    private String Destination;
    private String Template;
    private String TemplateData;
    private String Source;
}
package co.edu.colomboamericano.caelassessment.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import co.edu.colomboamericano.caelassessment.dto.ProspectiveDto;
import co.edu.colomboamericano.caelassessment.entity.Prospective;

@Mapper(componentModel = "spring")
public interface ProspectiveMapper
{
	@Mapping( source="prospectiveStatusId.id", target="prospectiveStatusId"  )
	public ProspectiveDto prospectiveToProspectiveDto( Prospective prospective );

	@Mapping( target="prospectiveStatusId.id", source="prospectiveStatusId"  )
	public Prospective prospectiveDtoToProspective( ProspectiveDto prospectiveDto );
	
	public List<ProspectiveDto> prospectiveListToProspectiveDtoList( List<Prospective> prospectives );
	
	public List<Prospective> prospectiveDtoListToProspectiveList( List<ProspectiveDto> prospectivesDto );
}
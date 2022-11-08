package co.edu.colomboamericano.caelassessment.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import co.edu.colomboamericano.caelassessment.dto.AssessmentDto;
import co.edu.colomboamericano.caelassessment.entity.Assessment;

@Mapper(componentModel = "spring")
public interface AssessmentMapper
{
	@Mapping( source = "prospectiveId", target = "prospective.id" )
	@Mapping( source = "assessmentStatusId", target = "assessmentStatus.id" )
	Assessment assessmentDtoToAssessment( AssessmentDto assessmentDto );
	
	@Mapping( source = "prospective.id", target = "prospectiveId" )
	@Mapping( source = "assessmentStatus.id", target = "assessmentStatusId" )
	AssessmentDto assessmentToAssessmentDto( Assessment assessment );
	
	List< Assessment > assessmentDtoListToassessmentList( List< AssessmentDto > assessmentDtoList );

	List< AssessmentDto > assessmentListToAssessmentDtoList( List< Assessment > assessmentList );
};
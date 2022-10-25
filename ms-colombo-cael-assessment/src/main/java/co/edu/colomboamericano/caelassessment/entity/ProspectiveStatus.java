package co.edu.colomboamericano.caelassessment.entity;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "prospectiveStatus")
public class ProspectiveStatus implements Serializable
{
	@Id
	@GeneratedValue( strategy=GenerationType.IDENTITY )
    @Column(name = "id", nullable=false, precision=10)
	private Integer id;

    @Column(nullable=false, length=255)
    private String name;

    @Column(nullable=false, length=255)
    private String key;
    
    @Column(nullable=false)
    private LocalDateTime createdAt;
    
    @Column(nullable=false)
    private LocalDateTime updatedAt;
    
	private static final long serialVersionUID = 1L;
}

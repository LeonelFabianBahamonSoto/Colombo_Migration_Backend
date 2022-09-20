package co.edu.colomboamericano.caelstudent.security;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import co.edu.colomboamericano.caelstudent.entity.Student;
import co.edu.colomboamericano.caelstudent.service.StudentService;
import co.edu.colomboamericano.caelstudent.service.utils.SecurityUtils;

@Service
public class CustomUserDetailsService implements UserDetailsService
{
	@Autowired
    private StudentService studentService;

	@Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException
	{
    	Student student = studentService.findByDocument(username)
    	  .orElseThrow(() ->new UsernameNotFoundException("El usuario no fue encontrado: "+username));
    	
        Set<GrantedAuthority> authorities = Set.of(SecurityUtils.convertToAuthority("USER"));

        return UserPrincipal.builder()
                .student(student)
                .id(student.getId())
                .username(student.getDocumentNumber())
                .password(student.getPassword())
                .authorities(authorities)
                .build();
    }
}

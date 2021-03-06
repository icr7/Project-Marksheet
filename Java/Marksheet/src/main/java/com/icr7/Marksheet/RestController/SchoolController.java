package com.icr7.Marksheet.RestController;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.icr7.Marksheet.Dao.SchoolDao;
import com.icr7.Marksheet.entity.AdminEntity;
import com.icr7.Marksheet.entity.StudentEntity;
import com.icr7.Marksheet.service.LoginService;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:4200")
public class SchoolController {

	@Autowired
	SchoolDao schoolDao;

	@Autowired
	LoginService loginService;

	@GetMapping("/loginAdmin/{id}/{password}")
	public AdminEntity adminLogin(@PathVariable("id") int id, @PathVariable("password") String pass) {
		return loginService.AdminLogin(id, pass);
	}

	@GetMapping("/loginStudent/{id}/{password}")
	public StudentEntity studentLogin(@PathVariable("id") int id, @PathVariable("password") String pass) {
		return loginService.StudentLogin(id, pass);
	}

	@PostMapping("/newStudent")
	public void saveNewStudent(@RequestBody StudentEntity entity) {
		schoolDao.RegisterNewStudent(entity);
	}

	@PutMapping("/updateMarks")
	public void UpdateStudentMarks(@RequestBody StudentEntity entity) {
		String pass = (schoolDao.getStudentData(entity.getId())).getPassword();
		entity.setPassword(pass);
		schoolDao.updateStudentMarks(entity);

//		return (schoolDao.getStudentData(entity.getId())).getPassword();
	}

	@GetMapping("/allStudent")
	public List<StudentEntity> findall() {
		return schoolDao.getAllStudentData();
	}

}

package logrest.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import logrest.model.Log;
import logrest.model.LogDTO;
import logrest.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import logrest.services.DataServices;

import javax.servlet.http.HttpSession;

@org.springframework.web.bind.annotation.RestController
@RequestMapping("/logs")
public class RestController {
	@Autowired
	DataServices dataServices;

    @RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody
	ResponseEntity<?> addLog(@RequestBody Log log, HttpSession httpSession) {
		User currentUser = dataServices.getUserByName((String)httpSession.getAttribute("username"));
		if(currentUser == null)
			return new ResponseEntity<>(HttpStatus.FORBIDDEN);
		try {
			log.setCreator(currentUser);
			log.setDate(new Date());
			dataServices.addEntity(log);
			return new ResponseEntity<>(new LogDTO(log), HttpStatus.CREATED);
		} catch (Exception e) {
			// e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}

	}

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public @ResponseBody
	LogDTO getLog(@PathVariable("id") long id) {

		Log log = null;
		try {
			log = dataServices.getEntityById(id);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return new LogDTO(log);
	}

    @RequestMapping(method = RequestMethod.GET)
	public @ResponseBody
	List<LogDTO> getLogs(@RequestParam(value = "username", required = false) String username,
						 @RequestParam(value = "severity", required = false) String severity,
						 @RequestParam(value = "type", required = false) String type) {

		List<LogDTO> logList = new ArrayList<>();
		try {
			for(Log log : dataServices.getEntityList(username, severity, type))
				logList.add(new LogDTO(log));

		} catch (Exception e) {
			e.printStackTrace();
		}

		return logList;
	}

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	public @ResponseBody
	ResponseEntity<?> deleteLog(@PathVariable("id") long id, HttpSession httpSession) {
       try {
            User currentUser = dataServices.getUserByName((String)httpSession.getAttribute("username"));
            Log log = dataServices.getEntityById(id);
            if(log.getCreator().getId() != currentUser.getId())
                return new ResponseEntity<>(HttpStatus.FORBIDDEN);
            dataServices.deleteEntity(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}
}

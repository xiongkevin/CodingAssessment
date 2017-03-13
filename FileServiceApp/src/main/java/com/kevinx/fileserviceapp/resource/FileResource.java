package com.kevinx.fileserviceapp.resource;

import java.io.File;
import java.io.InputStream;

import javax.ws.rs.Consumes;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import com.kevinx.fileserviceapp.service.FileService;
import com.sun.jersey.core.header.FormDataContentDisposition;
import com.sun.jersey.multipart.FormDataParam;

@Component
@Path("file")
public class FileResource {

	@Autowired
	private FileService fileService;

	@POST
	@Path("upload")
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	public Response uploadFile(@FormDataParam("file") InputStream inputStream,
			@FormDataParam("file") FormDataContentDisposition disposition) {

		if(disposition.getFileName().isEmpty()){
			return Response.status(HttpStatus.BAD_REQUEST.value()).entity(
					"fileName must be provided.").build();
		}
		String fileLocation = fileService.saveFileToRepo(inputStream, disposition);
		return Response.ok().entity(fileLocation).build();
	}

	@GET
	@Path("read")
	public Response readFile(@DefaultValue("") @QueryParam("fileName") String fileName) {

		if(fileName.isEmpty()){
			return Response.status(HttpStatus.BAD_REQUEST.value()).entity(
					"fileName must be provided.").build();
		}
		String output = fileService.readFileFromRepo(fileName);
		return Response.ok().entity(output).build();
	}
	
	@GET
	@Path("download")
	@Produces(MediaType.APPLICATION_OCTET_STREAM)
	public Response downloadFile(@DefaultValue("") @QueryParam("fileName") String fileName) {

		if(fileName.isEmpty()){
			return Response.status(HttpStatus.BAD_REQUEST.value()).entity(
					"fileName must be provided.").build();
		}
		File file = fileService.getFileByName(fileName);
		if(file==null){
			return Response.status(HttpStatus.NOT_FOUND.value()).entity(
					fileName+" cannot be found.").build();
		}
	    ResponseBuilder response = Response.ok((Object) file);
	    response.header("Content-Disposition", "attachment; filename="+fileName);
	    return response.build();
	}
	
	@GET
	@Path("search")
	public Response findFiles(@DefaultValue("") @QueryParam("keyword") String keyword) {

		if(keyword.isEmpty()){
			return Response.status(HttpStatus.BAD_REQUEST.value()).entity(
					"keyword must be provided.").build();
		}
		String output = fileService.getFileListByKeyword(keyword);
		return Response.ok().entity(output).build();
	}
}

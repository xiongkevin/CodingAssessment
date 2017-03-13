<html>
<body>
	<h2>Upload your file:</h2>
	<form action="rest/file/upload" method="post"
		enctype="multipart/form-data">
		<p>
			<input type="file" name="file" size="45" />
		</p>
		<input type="submit" value="Upload It" />
	</form>
	<h2>Get data from file:</h2>
	<p>Example:
		http://localhost:8080/FileServiceApp/rest/file/read?fileName=test.txt</p>
	<h2>Download file:</h2>
	<p>Example:
		http://localhost:8080/FileServiceApp/rest/file/download?fileName=test.txt</p>
	<h2>Search files by keyword:</h2>
	<p>Example:
		http://localhost:8080/FileServiceApp/rest/file/search?keyword=es</p>
</body>
</html>

<!DOCTYPE html>
<html>
<head>
<title>File Upload</title>
</head>
<body>
	<h2>Upload File</h2>
	<form action="UploadServlet" method="post"
		enctype="multipart/form-data">
		<label for="file">Select a file:</label> <input type="file"
			name="file" id="file"> <br>
		<br> <input type="submit" value="Upload">
	</form>
</body>
</html>

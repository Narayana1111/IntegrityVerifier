//import java.io.IOException;
//import java.io.InputStream;
//import java.sql.Connection;
//import java.sql.DriverManager;
//import java.sql.PreparedStatement;
//import java.sql.ResultSet;
//
//import jakarta.servlet.ServletException;
//import jakarta.servlet.annotation.WebServlet;
//import jakarta.servlet.http.HttpServlet;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//
//@WebServlet("/DownloadServlet")
//public class DownloadServlet extends HttpServlet {
//    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        // Retrieve the file name from the request parameter
//        String fileName = request.getParameter("file");
//
//        // Retrieve the file content from the database based on the file name
//        byte[] fileContent = retrieveFileContentFromMySQL(fileName);
//
//        if (fileContent != null) {
//            // Set the response content type
//            response.setContentType("application/octet-stream");
//            // Set the Content-Disposition header to prompt the user to download the file
//            response.setHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\"");
//
//            // Write the file content to the response output stream
//            response.getOutputStream().write(fileContent);
//        } else {
//            // Handle the case where the file content is not found
//            response.getWriter().println("File not found.");
//        }
//    }
//
//    private byte[] retrieveFileContentFromMySQL(String fileName) {
//        String jdbcUrl = "jdbc:mysql://localhost:3306/ternaryhashtree";
//        String username = "root";
//        String password = "Narayana#3837";
//
//        try {
//            Class.forName("com.mysql.cj.jdbc.Driver");
//        } catch (ClassNotFoundException e) {
//            e.printStackTrace();
//        }
//
//        try (Connection conn = DriverManager.getConnection(jdbcUrl, username, password)) {
//            String sql = "SELECT hash FROM files WHERE name = ?";
//            PreparedStatement statement = conn.prepareStatement(sql);
//            statement.setString(1, fileName);
//
//            ResultSet resultSet = statement.executeQuery();
//            if (resultSet.next()) {
//                // Retrieve the file content from the result set
//                return resultSet.getBytes("hash");
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//        return null; // Return null if the file content is not found
//    }
//}
import java.io.IOException;
import java.security.MessageDigest;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Base64;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


//public class DownloadServlet extends HttpServlet {
//    private static final long serialVersionUID = 1L;
//
//    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        String fileName = request.getParameter("file");
//        FileContentAndHash fileContentAndHash = retrieveFileContentAndHashFromMySQL(fileName);
//
//        if (fileContentAndHash != null) {
//            byte[] decodedContent = Base64.getDecoder().decode(fileContentAndHash.getFileContent());
//            String calculatedHash = calculateHash(decodedContent);
//
//            if (calculatedHash.equals(fileContentAndHash.getOriginalHash())) {
//                response.setContentType("text/plain");
//                response.setHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\"");
//                response.getOutputStream().write(decodedContent);
//            } else {
//                // Integrity verification failed, redirect to tampered file page
//                response.sendRedirect("TamperedFile.html");
//            }
//        } else {
//            response.getWriter().println("File not found.");
//        }
//    }
@WebServlet("/DownloadServlet")
public class DownloadServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String fileName = request.getParameter("file");
        FileContentAndHash fileContentAndHash = retrieveFileContentAndHashFromMySQL(fileName);
try {
        if (fileContentAndHash != null) {
            byte[] decodedContent = Base64.getDecoder().decode(fileContentAndHash.getFileContent());
            String calculatedHash = calculateHash(decodedContent);
            
            // Convert original hash from Base64 to hexadecimal
            String originalHashBase64 = fileContentAndHash.getOriginalHash();
            String originalHash = byteArrayToHexString(Base64.getDecoder().decode(originalHashBase64));

            System.out.println("Calculated Hash: " + calculatedHash);
            System.out.println("Original Hash: " + originalHash);

            if (calculatedHash.equals(originalHash)) {
                response.setContentType("application/octet-stream");
                response.setHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\"");
                response.getOutputStream().write(decodedContent);
            } else {
                // Integrity verification failed, send an appropriate response
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST); // Set the appropriate status code
                response.getWriter().println("The file you are trying to download has been tampered with and cannot be downloaded.");
                return;
            }
        } else {
            response.getWriter().println("File not found.");
        }
    }catch(Exception e) {
    	response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
    }}

    private FileContentAndHash retrieveFileContentAndHashFromMySQL(String fileName) {
        String jdbcUrl = "jdbc:mysql://localhost:3306/ternaryhashtree";
        String username = "root";
        String password = "Narayana@0011";

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        try (Connection conn = DriverManager.getConnection(jdbcUrl, username, password)) {
            String sql = "SELECT hash, original_hash FROM files WHERE name = ?";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setString(1, fileName);

            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                String fileContent = resultSet.getString("hash");
                String originalHash = resultSet.getString("original_hash");
                return new FileContentAndHash(fileContent, originalHash);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    private String calculateHash(byte[] content) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hashBytes = digest.digest(content);

            StringBuilder hashHex = new StringBuilder();
            for (byte b : hashBytes) {
                hashHex.append(String.format("%02x", b));
            }

            return hashHex.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private class FileContentAndHash {
        private String fileContent;
        private String originalHash;

        public FileContentAndHash(String fileContent, String originalHash) {
            this.fileContent = fileContent;
            this.originalHash = originalHash;
        }

        public String getFileContent() {
            return fileContent;
        }

        public String getOriginalHash() {
            return originalHash;
        }
    }
    
    private String byteArrayToHexString(byte[] bytes) {
        StringBuilder hexString = new StringBuilder();
        for (byte b : bytes) {
            hexString.append(String.format("%02x", b));
        }
        return hexString.toString();
    }
}